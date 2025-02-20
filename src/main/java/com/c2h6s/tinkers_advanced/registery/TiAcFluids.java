package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;

import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;
public class TiAcFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(TinkersAdvanced.MODID);

    public static final FluidObject<ForgeFlowingFluid> MOLTEN_BISMUTH = FLUIDS.register("molten_bismuth").type(hot("molten_bismuth")).bucket().block(createBurning(MapColor.COLOR_GRAY,1,4,0.5f)).commonTag().flowing();
    public static final FluidObject<ForgeFlowingFluid> MOLTEN_ANTIMATTER = FLUIDS.register("molten_antimatter").type(hot("molten_antimatter")).bucket().block(createBurning(MapColor.COLOR_GRAY,15,16384,15f)).commonTag().flowing();


    private static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000)
                .descriptionId("fluid."+TinkersAdvanced.MODID+"."+name)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .motionScale(0.0023333333333333335D)
                .canSwim(false).canDrown(false)
                .pathType(BlockPathTypes.LAVA).adjacentPathType(null);
    }
}
