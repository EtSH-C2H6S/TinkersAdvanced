package com.c2h6s.tinkers_advanced.content.block;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.registery.TiAcBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StibniteOreBlock extends Block {
    public static final BooleanProperty STIBNITE_STATE = BlockStateProperties.UNSTABLE;
    public StibniteOreBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STIBNITE_STATE);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        if (pParams.getOptionalParameter(LootContextParams.EXPLOSION_RADIUS)!=null) return List.of();
        return super.getDrops(pState,pParams);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(STIBNITE_STATE,false);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pNewState.is(TiAcBlocks.STIBNITE_ORE.get())&&TiAcConfig.COMMON.ALLOW_STIBNITE_UNSTABLE.get()&&!pState.getValue(STIBNITE_STATE)){
            for (Direction direction:Direction.values()){
                BlockPos pos = pPos.relative(direction);
                BlockState state = pLevel.getBlockState(pos);
                if (state.is(TiAcBlocks.STIBNITE_ORE.get())){
                    pLevel.setBlockAndUpdate(pos,defaultBlockState().setValue(STIBNITE_STATE,!state.getValue(STIBNITE_STATE)));
                }
            }
        }
        if (!pNewState.is(TiAcBlocks.STIBNITE_ORE.get())&&pState.getValue(STIBNITE_STATE)) this.blowUp(pPos,pLevel,10);
    }

    public void blowUp(BlockPos blockPos, Level level, int remains){
        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        Vec3 pos = blockPos.getCenter();
        Explosion explosion = level.explode(null, LegacyDamageSource.any(level.damageSources().explosion(null)).setBypassInvulnerableTime(), null, pos.x, pos.y, pos.z, 2.5F, true, Level.ExplosionInteraction.BLOCK);
        explosion.explode();
        if (remains>0) {
            List.copyOf(explosion.getToBlow()).forEach(blockPos1 -> {
                if (level.getBlockState(blockPos1).getBlock() instanceof StibniteOreBlock block) {
                    block.blowUp(blockPos,level,remains-1);
                }
            });
        }
        explosion.finalizeExplosion(true);
    }

}
