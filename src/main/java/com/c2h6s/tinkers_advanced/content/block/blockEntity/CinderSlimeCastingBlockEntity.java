package com.c2h6s.tinkers_advanced.content.block.blockEntity;

import com.c2h6s.etstlib.api.interfaces.IConditionalSpeedCastingBlockEntity;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.registery.TiAcBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.recipe.TinkerRecipeTypes;
import slimeknights.tconstruct.library.recipe.casting.ICastingRecipe;
import slimeknights.tconstruct.library.recipe.molding.MoldingRecipe;
import slimeknights.tconstruct.smeltery.block.entity.CastingBlockEntity;

public abstract class CinderSlimeCastingBlockEntity extends CastingBlockEntity implements IConditionalSpeedCastingBlockEntity {
    protected CinderSlimeCastingBlockEntity(BlockEntityType<?> beType, BlockPos pos, BlockState state, RecipeType<ICastingRecipe> castingType, RecipeType<MoldingRecipe> moldingType, TagKey<Item> emptyCastTag) {
        super(beType, pos, state, castingType, moldingType, emptyCastTag);
    }

    @Override
    public int getBoost(int i) {
        return 0;
    }

    public static class Table extends CinderSlimeCastingBlockEntity{
        public Table(BlockPos pos, BlockState state) {
            super(TiAcBlockEntities.CINDER_SLIME_TABLE.get(), pos, state, TinkerRecipeTypes.CASTING_TABLE.get(), TinkerRecipeTypes.MOLDING_TABLE.get(), TinkerTags.Items.TABLE_EMPTY_CASTS);
        }
        @Override
        public int modifyTotalCoolingTime(FluidStack fluidStack, ICastingRecipe iCastingRecipe, int i) {
            var common = TiAcConfig.COMMON;
            return (int) (i / (i >= common.CINDER_SLIME_TABLE_SEPARATION.get() ?
                    common.CINDER_SLIME_CASTING_DECREASE.get() :
                    common.CINDER_SLIME_CASTING_INCREASE.get()));
        }
    }
    public static class Basin extends CinderSlimeCastingBlockEntity{
        public Basin(BlockPos pos, BlockState state) {
            super(TiAcBlockEntities.CINDER_SLIME_BASIN.get(), pos, state, TinkerRecipeTypes.CASTING_BASIN.get(), TinkerRecipeTypes.MOLDING_BASIN.get(), TinkerTags.Items.BASIN_EMPTY_CASTS);
        }
        @Override
        public int modifyTotalCoolingTime(FluidStack fluidStack, ICastingRecipe iCastingRecipe, int i) {
            var common = TiAcConfig.COMMON;
            return (int) (i / (i >= common.CINDER_SLIME_BASIN_SEPARATION.get() ?
                    common.CINDER_SLIME_CASTING_DECREASE.get() :
                    common.CINDER_SLIME_CASTING_INCREASE.get()));
        }
    }
}
