package com.c2h6s.tinkers_advanced.utilities.content.block;

import com.c2h6s.etstlib.content.block.ConfigurableFaucetBlock;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.utilities.content.block.blockEntity.IridiumFaucetBlockEntity;
import com.c2h6s.tinkers_advanced.registery.TiAcBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.util.BlockEntityHelper;
import slimeknights.tconstruct.smeltery.block.entity.FaucetBlockEntity;

import java.util.List;

public class IridiumFaucetBlock extends ConfigurableFaucetBlock {
    public IridiumFaucetBlock(Properties properties) {
        super(properties, TiAcConfig.COMMON.IRIDIUM_FAUCET_SPEED);
    }
    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(new ItemStack(this.asItem()));
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IridiumFaucetBlockEntity(pPos,pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type) {
        return BlockEntityHelper.serverTicker(pLevel,type,TiAcBlockEntities.IRIDIUM_FAUCET.get(), FaucetBlockEntity.SERVER_TICKER);
    }
}
