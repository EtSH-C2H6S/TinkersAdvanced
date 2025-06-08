package com.c2h6s.tinkers_advanced.content.block;

import com.c2h6s.tinkers_advanced.content.block.blockEntity.ExchangerBlockEntity;
import com.c2h6s.tinkers_advanced.registery.TiAcBlockEntities;
import com.c2h6s.tinkers_advanced.registery.TiAcBlocks;
import com.c2h6s.tinkers_advanced.util.CommonConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.tools.TinkerToolParts;

public class ExchangerBlock extends BaseEntityBlock {
    public ExchangerBlock(Properties pProperties) {
        super(pProperties);
    }
    public static final BooleanProperty ENERGY_OUTPUT = BlockStateProperties.ENABLED;

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ExchangerBlockEntity(pPos,pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof ExchangerBlockEntity blockEntity){
            if (!pLevel.isClientSide){
                if (stack.isEmpty()&&!blockEntity.exchangingItem.isEmpty()){
                    pPlayer.setItemInHand(pHand,blockEntity.exchangingItem);
                    blockEntity.setExchangingItemServer(ItemStack.EMPTY);
                    return InteractionResult.CONSUME;
                } else if (stack.getItem() instanceof IModifiable){
                    pPlayer.setItemInHand(pHand,blockEntity.exchangingItem);
                    blockEntity.setExchangingItemServer(stack);
                    return InteractionResult.CONSUME;
                }
            }
        }
        if (!pLevel.isClientSide){
            if (stack.is(TinkerToolParts.toolHandle.asItem())||stack.is(CommonConstants.Tags.WRENCH)){
                pLevel.setBlockAndUpdate(pPos,pState.setValue(ENERGY_OUTPUT,!pState.getValue(ENERGY_OUTPUT)));
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ENERGY_OUTPUT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(ENERGY_OUTPUT,false);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, TiAcBlockEntities.EXCHANGER_BLOCK_ENTITY.get(),ExchangerBlockEntity::tick);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ExchangerBlockEntity entity && entity.getBlockState().is(TiAcBlocks.EXCHANGER.get())) {
                entity.dropItem();
            }
            blockEntity.setRemoved();
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
