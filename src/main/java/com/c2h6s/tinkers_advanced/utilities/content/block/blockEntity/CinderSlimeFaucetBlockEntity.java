package com.c2h6s.tinkers_advanced.utilities.content.block.blockEntity;

import com.c2h6s.etstlib.content.blockEntity.ConfigurableFaucetBlockEntity;
import com.c2h6s.tinkers_advanced.registery.TiAcBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CinderSlimeFaucetBlockEntity extends ConfigurableFaucetBlockEntity {
    public CinderSlimeFaucetBlockEntity(BlockPos pos, BlockState state) {
        super(TiAcBlockEntities.CINDER_SLIME_FAUCET.get(), pos, state);
    }
}
