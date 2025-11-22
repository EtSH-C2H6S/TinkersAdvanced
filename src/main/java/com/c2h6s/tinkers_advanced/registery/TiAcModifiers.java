package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.etstlib.tool.modifiers.Combat.Defense.*;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.core.content.tool.modifiers.AtomGrade;
import com.c2h6s.tinkers_advanced.core.content.tool.modifiers.ExtraCapacity;
import com.c2h6s.tinkers_advanced.core.content.tool.modifiers.PlayerLocating;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat.*;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat.ionizedCannon.DeepCatalyst;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat.ionizedCannon.FocusArray;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat.ionizedCannon.ResonanceAmplifier;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat.toolBase.EnderTuning;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat.toolBase.OverHold;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat.toolBase.SculkResonance;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.common.*;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.compat.mekanism.RadiationBurning;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.compat.mekanism.RadioactiveArmor;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.compat.pnc.AerialProtection;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.compat.pnc.AirSlash;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.compat.pnc.CompressedAirGenerator;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.compat.thermal.*;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.defense.ProtoDefense;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.defense.ReactiveExplosiveArmor;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.defense.SensorInterrupt;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.defense.SupremeDensityArmor;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.durability.Fragile;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyConsumption.ElectricFood;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyGeneration.CombustionGenerator;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyGeneration.OverslimeGenerator;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyGeneration.PiezoelectricEffect;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyGeneration.SmelteryGenerator;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyModification.EnergyBin;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyModification.EnergyDistribute;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyModification.PlatinoidCatalyst;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.generatorModifiers.energyModification.TransitionCatalyst;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.harvest.DisIntegrate;
import com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.harvest.ProtoRefining;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcModifiers {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}

    public static ModifierDeferredRegister MEK_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister PNC_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister THERMAL_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister AE_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);

    //无联动属性
    //注：只要没有用联动模组的方法或类就算无联动
    public static StaticModifier<Fragile> FRAGILE = TiAcCrModule.MODIFIERS.register("fragile", Fragile::new);
    public static StaticModifier<TetanusModifier> TETANUS = TiAcCrModule.MODIFIERS.register("tetanus", TetanusModifier::new);
    public static StaticModifier<AutoShot> AUTO_SHOT = TiAcCrModule.MODIFIERS.register("auto_shot", AutoShot::new);
    public static StaticModifier<Annihilate> ANNIHILATE = TiAcCrModule.MODIFIERS.register("annihilate", Annihilate::new);
    public static StaticModifier<ReactiveExplosiveArmor> REACTIVE_EXPLOSIVE_ARMOR = TiAcCrModule.MODIFIERS.register("reactive_explosive_armor", ReactiveExplosiveArmor::new);
    public static StaticModifier<SensorInterrupt> SENSOR_INTERRUPT = TiAcCrModule.MODIFIERS.register("sensor_interrupt", SensorInterrupt::new);
    public static StaticModifier<Metamorphium> METAMORPHIUM = TiAcCrModule.MODIFIERS.register("metamorphium", Metamorphium::new);
    public static StaticModifier<FlameAdaptive> FLAME_ADAPTIVE = TiAcCrModule.MODIFIERS.register("flame_adaptive", FlameAdaptive::new);
    public static StaticModifier<SupremeDensity> SUPREME_DENSITY = TiAcCrModule.MODIFIERS.register("supreme_density", SupremeDensity::new);
    public static StaticModifier<SupremeDensityArmor> SUPREME_DENSITY_ARMOR = TiAcCrModule.MODIFIERS.register("supreme_density_armor", SupremeDensityArmor::new);
    public static StaticModifier<IonizedModifier> IONIZED = TiAcCrModule.MODIFIERS.register("ionized", IonizedModifier::new);
    public static StaticModifier<DisIntegrate> DIS_INTEGRATE = TiAcCrModule.MODIFIERS.register("disintegrate", DisIntegrate::new);
    public static StaticModifier<Electric> ELECTRIC = TiAcCrModule.MODIFIERS.register("electric", Electric::new);
    public static StaticModifier<EchoLocating> ECHO_LOCATING = TiAcCrModule.MODIFIERS.register("echo_locating", EchoLocating::new);
    public static StaticModifier<Elastic> ELASTIC = TiAcCrModule.MODIFIERS.register("elastic", Elastic::new);
    public static StaticModifier<Shaping> SHAPING = TiAcCrModule.MODIFIERS.register("shaping", Shaping::new);
    public static StaticModifier<ProtoRefining> PROTO_REFINING = TiAcCrModule.MODIFIERS.register("proto_refining", ProtoRefining::new);
    public static StaticModifier<ProtoDefense> PROTO_DEFENSE = TiAcCrModule.MODIFIERS.register("proto_defense", ProtoDefense::new);
    public static StaticModifier<ReturnToSlime> RETURN_TO_SLIME = TiAcCrModule.MODIFIERS.register("return_to_slime", ReturnToSlime::new);
    public static StaticModifier<NutritiveSlime> NUTRITIVE_SLIME = TiAcCrModule.MODIFIERS.register("nutritive_slime", NutritiveSlime::new);
    public static StaticModifier<Unstable> UNSTABLE = TiAcCrModule.MODIFIERS.register("unstable", Unstable::new);
    public static StaticModifier<PlagueModifier> PLAGUE = TiAcCrModule.MODIFIERS.register("plague", PlagueModifier::new);
    public static StaticModifier<Clearing> POISON_DEFENSE = TiAcCrModule.MODIFIERS.register("poison_defense", Clearing::new);
    public static StaticModifier<PlayerLocating> PLAYER_LOCATING = TiAcCrModule.MODIFIERS.register("player_locating", PlayerLocating::new);
    public static StaticModifier<VoidDodging> VOID_DODGING = TiAcCrModule.MODIFIERS.register("void_dodging", VoidDodging::new);
    public static StaticModifier<Stabilize> STABILIZE = TiAcCrModule.MODIFIERS.register("stabilize", Stabilize::new);


    //工具特有强化，需要工具采用特定工具属性才有效
    public static StaticModifier<ResonanceAmplifier> RESONANCE_AMPLIFIER = TiAcCrModule.MODIFIERS.register("resonance_amplifier", ResonanceAmplifier::new);
    public static StaticModifier<FocusArray> FOCUSING_ARRAY = TiAcCrModule.MODIFIERS.register("focusing_array", FocusArray::new);
    public static StaticModifier<DeepCatalyst> DEEP_CATALYST = TiAcCrModule.MODIFIERS.register("deep_catalysis", DeepCatalyst::new);
    public static StaticModifier<ExtraCapacity> EXTRA_CAPACITY = TiAcCrModule.MODIFIERS.register("extra_capacity", ExtraCapacity::new);

    //工具自带强化，与工具强绑定
    public static StaticModifier<SculkResonance> SCULK_RESONANCE = TiAcCrModule.MODIFIERS.register("sculk_resonance", SculkResonance::new);
    public static StaticModifier<SculkResonance.IonizedCannonCooldownDisplay> IONIZED_CANNON_CD = TiAcCrModule.MODIFIERS.register("ionized_cannon_cd", SculkResonance.IonizedCannonCooldownDisplay::new);
    public static StaticModifier<OverHold> OVER_HOLD = TiAcCrModule.MODIFIERS.register("over_hold", OverHold::new);
    public static StaticModifier<EnderTuning> ENDER_TUNING = TiAcCrModule.MODIFIERS.register("ender_tuner", EnderTuning::new);

    //能量模块类强化
    public static StaticModifier<CombustionGenerator> COMBUSTION_GENERATOR = TiAcCrModule.MODIFIERS.register("combustion_generator", CombustionGenerator::new);
    public static StaticModifier<OverslimeGenerator> OVERSLIME_GENERATOR = TiAcCrModule.MODIFIERS.register("overslime_generator", OverslimeGenerator::new);
    public static StaticModifier<PiezoelectricEffect> PIEZOELECTRIC_EFFECT = TiAcCrModule.MODIFIERS.register("piezoelectric_effect", PiezoelectricEffect::new);
    public static StaticModifier<SmelteryGenerator> SMELTERY_GENERATOR = TiAcCrModule.MODIFIERS.register("smeltery_generator", SmelteryGenerator::new);

    public static StaticModifier<ElectricFood> ELECTRIC_FOOD = TiAcCrModule.MODIFIERS.register("electric_food", ElectricFood::new);

    public static StaticModifier<PlatinoidCatalyst> PLATINOID_CATALYST = TiAcCrModule.MODIFIERS.register("platinoid_catalyst", PlatinoidCatalyst::new);
    public static StaticModifier<TransitionCatalyst> TRANSITION_CATALYST = TiAcCrModule.MODIFIERS.register("transition_catalyst", TransitionCatalyst::new);
    public static StaticModifier<EnergyBin> ENERGY_BIN = TiAcCrModule.MODIFIERS.register("energy_bin", EnergyBin::new);
    public static StaticModifier<EnergyDistribute> ENERGY_DISTRIBUTOR = TiAcCrModule.MODIFIERS.register("energy_distributor", EnergyDistribute::new);


    //mek联动属性
    public static StaticModifier<RadioactiveArmor> RADIOACTIVE_ARMOR = MEK_MODIFIERS.register("radioactive_armor", RadioactiveArmor::new);
    public static StaticModifier<AtomGrade> ATOM_GRADE = MEK_MODIFIERS.register("atom_grade", AtomGrade::new);
    public static StaticModifier<RadiationBurning> RADIATION_BURNING = MEK_MODIFIERS.register("radiation_burning", RadiationBurning::new);


    //PnC联动属性
    public static StaticModifier<AirSlash> AIR_SLASH = PNC_MODIFIERS.register("air_slash", AirSlash::new);
    public static StaticModifier<AerialProtection> AERIAL_PROTECTION = PNC_MODIFIERS.register("aerial_protection", AerialProtection::new);
    public static StaticModifier<CompressedAirGenerator> COMPRESSED_AIR_GENERATOR = PNC_MODIFIERS.register("compressed_air_generator", CompressedAirGenerator::new);


    //热力联动属性
    public static StaticModifier<BasalzDefense> BASALZ_DEFENSE = THERMAL_MODIFIERS.register("basalz_defense", BasalzDefense::new);
    public static StaticModifier<BasalzInflict> BASALZ_INFLICT = THERMAL_MODIFIERS.register("basalz_inflict", BasalzInflict::new);
    public static StaticModifier<BlitzDefense> Blitz_DEFENSE = THERMAL_MODIFIERS.register("blitz_defense", BlitzDefense::new);
    public static StaticModifier<BlitzInflict> Blitz_INFLICT = THERMAL_MODIFIERS.register("blitz_inflict", BlitzInflict::new);
    public static StaticModifier<BlizzDefense> BLIZZ_DEFENSE = THERMAL_MODIFIERS.register("blizz_defense", BlizzDefense::new);
    public static StaticModifier<BlizzInflict> BLIZZ_INFLICT = THERMAL_MODIFIERS.register("blizz_inflict", BlizzInflict::new);
    public static StaticModifier<FluxInfused> FLUX_INFUSED = THERMAL_MODIFIERS.register("flux_infused", FluxInfused::new);
    public static StaticModifier<ThermalSlashModifier> THERMAL_SLASH = THERMAL_MODIFIERS.register("thermal_slash", ThermalSlashModifier::new);
    public static StaticModifier<ThermalEnhance> THERMAL_ENHANCE = THERMAL_MODIFIERS.register("thermal_enhance", ThermalEnhance::new);
    public static StaticModifier<FluxArrow> FLUX_ARROW = THERMAL_MODIFIERS.register("flux_arrow", FluxArrow::new);
    public static StaticModifier<FluxDefense> FLUX_DEFENSE = THERMAL_MODIFIERS.register("flux_defense", FluxDefense::new);


    //AE联动属性

}
