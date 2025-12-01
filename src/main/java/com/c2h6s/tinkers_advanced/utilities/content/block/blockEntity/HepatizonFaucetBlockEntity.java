package com.c2h6s.tinkers_advanced.utilities.content.block.blockEntity;

import com.c2h6s.etstlib.content.blockEntity.ConfigurableFaucetBlockEntity;
import com.c2h6s.tinkers_advanced.utilities.init.TiAcUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class HepatizonFaucetBlockEntity extends ConfigurableFaucetBlockEntity {
    public HepatizonFaucetBlockEntity(BlockPos pos, BlockState state) {
        super(TiAcUBlockEntities.HEPATIZON_FAUCET.get(), pos, state);
    }
}
