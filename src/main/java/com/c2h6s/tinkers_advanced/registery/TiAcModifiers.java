package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.modifier.combat.*;
import com.c2h6s.tinkers_advanced.content.modifier.combat.ionizedCannon.*;
import com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase.OverHold;
import com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase.SculkResonance;
import com.c2h6s.tinkers_advanced.content.modifier.common.Elastic;
import com.c2h6s.tinkers_advanced.content.modifier.common.FlameAdaptive;
import com.c2h6s.tinkers_advanced.content.modifier.common.Metamorphium;
import com.c2h6s.tinkers_advanced.content.modifier.common.Shaping;
//import com.c2h6s.tinkers_advanced.content.modifier.compat.ae2.PocketCell;
import com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism.AtomGrade;
import com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism.RadiationBurning;
import com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism.RadioactiveArmor;
import com.c2h6s.tinkers_advanced.content.modifier.compat.pnc.AirSlash;
import com.c2h6s.tinkers_advanced.content.modifier.compat.pnc.AerialProtection;
import com.c2h6s.tinkers_advanced.content.modifier.compat.thermal.*;
import com.c2h6s.tinkers_advanced.content.modifier.defense.*;
import com.c2h6s.tinkers_advanced.content.modifier.durability.*;
import com.c2h6s.tinkers_advanced.content.modifier.harvest.DisIntegrate;
import com.c2h6s.tinkers_advanced.content.modifier.harvest.ProtoRefining;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class TiAcModifiers {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister MEK_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister PNC_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister THERMAL_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister AE_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);

    //无联动属性
    //注：只要没有引用联动模组的方法或类就算无联动
    public static StaticModifier<Fragile> FRAGILE = MODIFIERS.register("fragile", Fragile::new);
    public static StaticModifier<TetanusModifier> TETANUS = MODIFIERS.register("tetanus", TetanusModifier::new);
    public static StaticModifier<AutoShot> AUTO_SHOT = MODIFIERS.register("auto_shot", AutoShot::new);
    public static StaticModifier<Annihilate> ANNIHILATE = MODIFIERS.register("annihilate", Annihilate::new);
    public static StaticModifier<ReactiveExplosiveArmor> REACTIVE_EXPLOSIVE_ARMOR = MODIFIERS.register("reactive_explosive_armor", ReactiveExplosiveArmor::new);
    public static StaticModifier<SensorInterrupt> SENSOR_INTERRUPT = MODIFIERS.register("sensor_interrupt", SensorInterrupt::new);
    public static StaticModifier<Metamorphium> METAMORPHIUM = MODIFIERS.register("metamorphium", Metamorphium::new);
    public static StaticModifier<FlameAdaptive> FLAME_ADAPTIVE = MODIFIERS.register("flame_adaptive", FlameAdaptive::new);
    public static StaticModifier<SupremeDensity> SUPREME_DENSITY = MODIFIERS.register("supreme_density", SupremeDensity::new);
    public static StaticModifier<SupremeDensityArmor> SUPREME_DENSITY_ARMOR = MODIFIERS.register("supreme_density_armor", SupremeDensityArmor::new);
    public static StaticModifier<IonizedModifier> IONIZED = MODIFIERS.register("ionized", IonizedModifier::new);
    public static StaticModifier<DisIntegrate> DIS_INTEGRATE = MODIFIERS.register("disintegrate", DisIntegrate::new);
    public static StaticModifier<Electric> ELECTRIC = MODIFIERS.register("electric", Electric::new);
    public static StaticModifier<EchoLocating> ECHO_LOCATING = MODIFIERS.register("echo_locating", EchoLocating::new);
    public static StaticModifier<Elastic> ELASTIC = MODIFIERS.register("elastic", Elastic::new);
    public static StaticModifier<Shaping> SHAPING = MODIFIERS.register("shaping", Shaping::new);
    public static StaticModifier<ProtoRefining> PROTO_REFINING = MODIFIERS.register("proto_refining", ProtoRefining::new);
    public static StaticModifier<ProtoDefense> PROTO_DEFENSE = MODIFIERS.register("proto_defense", ProtoDefense::new);

    //工具特有强化，需要工具采用特定工具属性才有效
    public static StaticModifier<ResonanceAmplifier> RESONANCE_AMPLIFIER = MODIFIERS.register("resonance_amplifier", ResonanceAmplifier::new);
    public static StaticModifier<FocusArray> FOCUSING_ARRAY = MODIFIERS.register("focusing_array", FocusArray::new);
    public static StaticModifier<DeepCatalyst> DEEP_CATALYST = MODIFIERS.register("deep_catalysis", DeepCatalyst::new);

    public static StaticModifier<SculkResonance> SCULK_RESONANCE = MODIFIERS.register("sculk_resonance", SculkResonance::new);
    public static StaticModifier<OverHold> OVER_HOLD = MODIFIERS.register("over_hold", OverHold::new);


    //mek联动属性
    public static StaticModifier<RadioactiveArmor> RADIOACTIVE_ARMOR = MEK_MODIFIERS.register("radioactive_armor", RadioactiveArmor::new);
    public static StaticModifier<AtomGrade> ATOM_GRADE = MEK_MODIFIERS.register("atom_grade", AtomGrade::new);
    public static StaticModifier<RadiationBurning> RADIATION_BURNING = MEK_MODIFIERS.register("radiation_burning", RadiationBurning::new);


    //PnC联动属性
    public static StaticModifier<AirSlash> AIR_SLASH = PNC_MODIFIERS.register("air_slash", AirSlash::new);
    public static StaticModifier<AerialProtection> AERIAL_PROTECTION = PNC_MODIFIERS.register("aerial_protection", AerialProtection::new);


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
    //public static StaticModifier<PocketCell> POCKET_CELL = AE_MODIFIERS.register("pocket_cell", PocketCell::new);

}
