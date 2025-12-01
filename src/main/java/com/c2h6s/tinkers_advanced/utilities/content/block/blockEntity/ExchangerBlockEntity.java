package com.c2h6s.tinkers_advanced.utilities.content.block.blockEntity;

import com.c2h6s.tinkers_advanced.utilities.content.block.ExchangerBlock;
import com.c2h6s.tinkers_advanced.tools.capability.ExchangerWrappedEnergyHandler;
import com.c2h6s.tinkers_advanced.core.content.tool.modifiers.PlayerLocating;
import com.c2h6s.tinkers_advanced.core.library.capability.TiAcCapabilities;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.utilities.network.packets.PExchangerBEItemSyncS2C;
import com.c2h6s.tinkers_advanced.utilities.init.TiAcUBlockEntities;
import com.c2h6s.tinkers_advanced.core.util.CommonUtil;
import com.c2h6s.tinkers_advanced.utilities.init.TiAcUBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.UUID;

public class ExchangerBlockEntity extends BlockEntity {
    public ExchangerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(TiAcUBlockEntities.EXCHANGER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.exchangingItem = ItemStack.EMPTY;
    }
    public @NotNull ItemStack exchangingItem;
    public boolean ensureSynced=false;

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == ForgeCapabilities.FLUID_HANDLER) {
            if (this.exchangingItem.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, direction).isPresent())
                return this.exchangingItem.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM, direction).cast();
        }
        if (capability == ForgeCapabilities.ENERGY) {
            if (this.exchangingItem.getCapability(ForgeCapabilities.ENERGY, direction).isPresent())
                return LazyOptional.of(()->new ExchangerWrappedEnergyHandler(this)).cast();
        }
        if (this.exchangingItem.getCapability(capability, direction).isPresent())
            return this.exchangingItem.getCapability(capability, direction).cast();
        if (CommonUtil.getCompactCapability(this.exchangingItem, capability, direction).isPresent())
            return CommonUtil.getCompactCapability(this.exchangingItem, capability, direction).cast();
        return super.getCapability(capability, direction);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("exchanging_item", this.exchangingItem.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.setExchangingItemServer(ItemStack.of(nbt.getCompound("exchanging_item")));
        super.load(nbt);
    }

    public void setExchangingItemServer(ItemStack stack){
        this.exchangingItem = stack;
        if (level != null) {
            level.blockEntityChanged(this.worldPosition);
            level.updateNeighborsAt(this.worldPosition, TiAcUBlocks.EXCHANGER.get());
        }
        invalidateCaps();
        this.syncToClient(stack);
    }

    public void syncToClient(ItemStack stack){
        TiAcPacketHandler.sendToClient(new PExchangerBEItemSyncS2C(stack,this.worldPosition));
    }

    public void dropItem() {
        if (this.level instanceof ServerLevel&&!this.exchangingItem.isEmpty()) {
            Vec3 pos = this.worldPosition.getCenter();
            ItemEntity entity = new ItemEntity(this.level,pos.x,pos.y,pos.z,this.exchangingItem);
            this.level.addFreshEntity(entity);
            this.setExchangingItemServer(ItemStack.EMPTY);
        }
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state,ExchangerBlockEntity blockEntity){
        if (level.isClientSide) return;
        if (blockEntity.exchangingItem.isEmpty()) return;
        blockEntity.exchangingItem.getCapability(TiAcCapabilities.EXCHANGER_TICKABLE).ifPresent(tickable-> {
            ToolStack toolStack = ToolStack.from(blockEntity.exchangingItem);
            ModDataNBT toolData = toolStack.getPersistentData();
            LivingEntity holder = null;
            if (toolData.contains(PlayerLocating.KEY_PLAYER_ID, Tag.TAG_STRING)&&level.getServer()!=null){
                UUID uuid = null;
                try {
                    uuid = UUID.fromString(toolData.getString(PlayerLocating.KEY_PLAYER_ID));
                }catch (Exception ignored){}
                if (uuid!=null){
                    holder = level.getServer().getPlayerList().getPlayer(uuid);
                }
            }
            tickable.tick(blockEntity.exchangingItem,level,holder,blockEntity);
        });
        if (state.is(TiAcUBlocks.EXCHANGER.get())&&state.getValue(ExchangerBlock.ENERGY_OUTPUT)){
            ejectEnergy(level,blockPos,state,blockEntity);
        }
    }

    public static void ejectEnergy(Level level, BlockPos blockPos, BlockState state,ExchangerBlockEntity blockEntity){
        blockEntity.exchangingItem.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyStorage->{
            if (energyStorage.canExtract()&&energyStorage.getEnergyStored()>0){
                for (Direction direction:Direction.values()){
                    BlockPos relative = blockPos.relative(direction);
                    BlockEntity neighbour = level.getBlockEntity(relative);
                    if (neighbour!=null){
                        neighbour.getCapability(ForgeCapabilities.ENERGY,direction.getOpposite()).ifPresent(energyStorage1->{
                            int extract = energyStorage.extractEnergy(energyStorage.getEnergyStored(),true);
                            extract = energyStorage1.receiveEnergy(extract,true);
                            if (extract>0){
                                energyStorage1.receiveEnergy(extract,false);
                                energyStorage.extractEnergy(extract,false);
                            }
                        });
                        if (energyStorage.getEnergyStored()<=0) return;
                    }
                }
            }
        });
    }


}
