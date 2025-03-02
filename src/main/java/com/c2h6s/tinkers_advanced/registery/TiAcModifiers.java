package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.modifier.combat.*;
import com.c2h6s.tinkers_advanced.content.modifier.combat.ionizedCannon.*;
import com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism.RadioactiveArmor;
import com.c2h6s.tinkers_advanced.content.modifier.defense.*;
import com.c2h6s.tinkers_advanced.content.modifier.durability.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class TiAcModifiers {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);
    public static ModifierDeferredRegister MEK_MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);


    //无联动属性
    public static StaticModifier<Fragile> FRAGILE = MODIFIERS.register("fragile", Fragile::new);
    public static StaticModifier<TetanusModifier> TETANUS = MODIFIERS.register("tetanus", TetanusModifier::new);
    public static StaticModifier<SculkResonance> SCULK_RESONANCE = MODIFIERS.register("sculk_resonance", SculkResonance::new);
    public static StaticModifier<AutoShot> AUTO_SHOT = MODIFIERS.register("auto_shot", AutoShot::new);
    public static StaticModifier<Annihilate> ANNIHILATE = MODIFIERS.register("annihilate", Annihilate::new);
    public static StaticModifier<ReactiveExplosiveArmor> REACTIVE_EXPLOSIVE_ARMOR = MODIFIERS.register("reactive_explosive_armor", ReactiveExplosiveArmor::new);


    public static StaticModifier<ResonanceAmplifier> RESONANCE_AMPLIFIER = MODIFIERS.register("resonance_amplifier", ResonanceAmplifier::new);
    public static StaticModifier<FocusArray> FOCUSING_ARRAY = MODIFIERS.register("focusing_array", FocusArray::new);
    public static StaticModifier<DeepCatalyst> DEEP_CATALYST = MODIFIERS.register("deep_catalysis", DeepCatalyst::new);


    //mek联动属性
    public static StaticModifier<RadioactiveArmor> RADIOACTIVE_ARMOR = MEK_MODIFIERS.register("radioactive_armor", RadioactiveArmor::new);
}
