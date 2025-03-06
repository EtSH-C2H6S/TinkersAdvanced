package com.c2h6s.tinkers_advanced.registery;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import mekanism.common.lib.radiation.RadiationManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.block.BurningLiquidBlock;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;
public class TiAcFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(TinkersAdvanced.MODID);
    public static final FluidDeferredRegister MEK_FLUIDS = new FluidDeferredRegister(TinkersAdvanced.MODID);
    public static final FluidDeferredRegister THERMAL_FLUIDS = new FluidDeferredRegister(TinkersAdvanced.MODID);
    protected static Map<FluidObject<ForgeFlowingFluid>,Boolean> FLUID_MAP = new HashMap<>();
    public static Set<FluidObject<ForgeFlowingFluid>> getFluids(){
        return FLUID_MAP.keySet();
    }
    public static Map<FluidObject<ForgeFlowingFluid>,Boolean> getFluidMap(){
        return FLUID_MAP;
    }
    private static FluidObject<ForgeFlowingFluid> registerHotBurning(FluidDeferredRegister register,String name,int temp,int lightLevel,int burnTime,float damage,boolean gas){
        FluidObject<ForgeFlowingFluid> object = register.register(name).type(hot(name,temp)).bucket().block(createBurning(MapColor.COLOR_GRAY,lightLevel,burnTime,damage)).commonTag().flowing();
        FLUID_MAP.put(object,gas);
        return object;
    }
    private static FluidObject<ForgeFlowingFluid> registerFluid(FluidDeferredRegister register, String name,int temp, Function<Supplier<? extends FlowingFluid>, LiquidBlock> blockFunction, boolean gas){
        FluidObject<ForgeFlowingFluid> object = register.register(name).type(hot(name,temp)).bucket().block(blockFunction).commonTag().flowing();
        FLUID_MAP.put(object,gas);
        return object;
    }

    public static final FluidObject<ForgeFlowingFluid> MOLTEN_BISMUTH = registerHotBurning(FLUIDS,"molten_bismuth",770,1,4,0.5f,false);



    public static final FluidObject<ForgeFlowingFluid> MOLTEN_ANTIMATTER = registerHotBurning(MEK_FLUIDS,"molten_antimatter",2980,15,16384,15f,true);
    public static final FluidObject<ForgeFlowingFluid> MOLTEN_IRRADIUM = registerFluid(MEK_FLUIDS,"molten_irradium",2250, (Function<Supplier<? extends FlowingFluid>, LiquidBlock>) supplier -> new BurningLiquidBlock(supplier, FluidDeferredRegister.createProperties(MapColor.COLOR_GRAY, 15), 200, 8){
        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            super.entityInside(state, level, pos, entity);
            if (entity instanceof LivingEntity living) {
                RadiationManager.get().radiate(living,0.01);
            }
        }
    },false);
    public static final FluidObject<ForgeFlowingFluid> FUSION_PLASMA = registerFluid(MEK_FLUIDS,"fusion_plasma",3840, (Function<Supplier<? extends FlowingFluid>, LiquidBlock>) supplier -> new BurningLiquidBlock(supplier, FluidDeferredRegister.createProperties(MapColor.COLOR_GRAY, 15), 200, 8){
        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            if (entity instanceof LivingEntity living) {
                RadiationManager.get().radiate(living,0.0005);
                living.hurt(LegacyDamageSource.any(living.damageSources().generic()).setBypassInvulnerableTime().setBypassArmor().setBypassEnchantment().setBypassMagic().setBypassShield().setMsgId("plasma"),2);
                living.setSecondsOnFire(100000);
            }
        }
    },true);
    public static final FluidObject<ForgeFlowingFluid> MOLTEN_BASALZ_SIGNALUM = registerFluid(THERMAL_FLUIDS,"molten_basalz_signalum",950, (Function<Supplier<? extends FlowingFluid>, LiquidBlock>) supplier -> new BurningLiquidBlock(supplier, FluidDeferredRegister.createProperties(MapColor.COLOR_GRAY, 7), 200, 5){
        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            super.entityInside(state, level, pos, entity);
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(),100));
            }
        }
    },false);
    public static final FluidObject<ForgeFlowingFluid> MOLTEN_BILTZ_LUMIUM = registerFluid(THERMAL_FLUIDS,"molten_biltz_lumium",1440, (Function<Supplier<? extends FlowingFluid>, LiquidBlock>) supplier -> new BurningLiquidBlock(supplier, FluidDeferredRegister.createProperties(MapColor.COLOR_GRAY, 15), 200, 5){
        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            super.entityInside(state, level, pos, entity);
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),100));
            }
        }
    },false);
    public static final FluidObject<ForgeFlowingFluid> MOLTEN_BLIZZ_ENDERIUM = registerFluid(THERMAL_FLUIDS,"molten_blizz_enderium",0, (Function<Supplier<? extends FlowingFluid>, LiquidBlock>) supplier -> new BurningLiquidBlock(supplier, FluidDeferredRegister.createProperties(MapColor.COLOR_GRAY, 15), 0, 0){
        @Override
        public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),100));
            }
        }
    },false);


    private static FluidType.Properties hot(String name,int Temp) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(Temp)
                .descriptionId("fluid."+TinkersAdvanced.MODID+"."+name)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .motionScale(0.0023333333333333335D)
                .canSwim(false).canDrown(false)
                .pathType(BlockPathTypes.LAVA).adjacentPathType(null);
    }
}
