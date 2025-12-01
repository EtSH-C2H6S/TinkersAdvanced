package com.c2h6s.tinkers_advanced.utilities.content.block;

import com.c2h6s.etstlib.content.block.ConfigurableFaucetBlock;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.utilities.content.block.blockEntity.HepatizonFaucetBlockEntity;
import com.c2h6s.tinkers_advanced.utilities.init.TiAcUBlockEntities;
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

public class HepatizonFaucetBlock extends ConfigurableFaucetBlock {
    public HepatizonFaucetBlock(Properties properties) {
        super(properties, TiAcConfig.COMMON.HEPATIZON_FAUCET_SPEED);
    }
    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(new ItemStack(this.asItem()));
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HepatizonFaucetBlockEntity(pPos,pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type) {
        return BlockEntityHelper.serverTicker(pLevel,type, TiAcUBlockEntities.HEPATIZON_FAUCET.get(), FaucetBlockEntity.SERVER_TICKER);
    }
}
