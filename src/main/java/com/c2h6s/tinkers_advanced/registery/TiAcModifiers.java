package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.modifier.combat.*;
import com.c2h6s.tinkers_advanced.content.modifier.combat.ionizedCannon.*;
import com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase.OverHold;
import com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase.SculkResonance;
import com.c2h6s.tinkers_advanced.content.modifier.common.FlameAdaptive;
import com.c2h6s.tinkers_advanced.content.modifier.common.Metamorphium;
import com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism.AtomGrade;
import com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism.RadioactiveArmor;
import com.c2h6s.tinkers_advanced.content.modifier.compat.pnc.AirSlash;
import com.c2h6s.tinkers_advanced.content.modifier.compat.pnc.AerialProtection;
import com.c2h6s.tinkers_advanced.content.modifier.compat.thermal.*;
import com.c2h6s.tinkers_advanced.content.modifier.defense.*;
import com.c2h6s.tinkers_advanced.content.modifier.durability.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class TiAcModifiers {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister MEK_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister PNC_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister THERMAL_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);

    //无联动属性
    public static StaticModifier<Fragile> FRAGILE = MODIFIERS.register("fragile", Fragile::new);
    public static StaticModifier<TetanusModifier> TETANUS = MODIFIERS.register("tetanus", TetanusModifier::new);
    public static StaticModifier<SculkResonance> SCULK_RESONANCE = MODIFIERS.register("sculk_resonance", SculkResonance::new);
    public static StaticModifier<AutoShot> AUTO_SHOT = MODIFIERS.register("auto_shot", AutoShot::new);
    public static StaticModifier<Annihilate> ANNIHILATE = MODIFIERS.register("annihilate", Annihilate::new);
    public static StaticModifier<ReactiveExplosiveArmor> REACTIVE_EXPLOSIVE_ARMOR = MODIFIERS.register("reactive_explosive_armor", ReactiveExplosiveArmor::new);
    public static StaticModifier<SensorInterrupt> SENSOR_INTERRUPT = MODIFIERS.register("sensor_interrupt", SensorInterrupt::new);
    public static StaticModifier<Metamorphium> METAMORPHIUM = MODIFIERS.register("metamorphium", Metamorphium::new);
    public static StaticModifier<OverHold> OVER_HOLD = MODIFIERS.register("over_hold", OverHold::new);
    public static StaticModifier<FlameAdaptive> FLAME_ADAPTIVE = MODIFIERS.register("flame_adaptive", FlameAdaptive::new);


    public static StaticModifier<ResonanceAmplifier> RESONANCE_AMPLIFIER = MODIFIERS.register("resonance_amplifier", ResonanceAmplifier::new);
    public static StaticModifier<FocusArray> FOCUSING_ARRAY = MODIFIERS.register("focusing_array", FocusArray::new);
    public static StaticModifier<DeepCatalyst> DEEP_CATALYST = MODIFIERS.register("deep_catalysis", DeepCatalyst::new);


    //mek联动属性
    public static StaticModifier<RadioactiveArmor> RADIOACTIVE_ARMOR = MEK_MODIFIERS.register("radioactive_armor", RadioactiveArmor::new);
    public static StaticModifier<AtomGrade> ATOM_GRADE = MEK_MODIFIERS.register("atom_grade", AtomGrade::new);


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
}
