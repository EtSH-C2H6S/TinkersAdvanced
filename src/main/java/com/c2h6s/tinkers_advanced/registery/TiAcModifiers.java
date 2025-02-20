package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.modifier.combat.*;
import com.c2h6s.tinkers_advanced.content.modifier.combat.ionizedCannon.*;
import com.c2h6s.tinkers_advanced.content.modifier.defense.ReactiveExplosiveArmor;
import com.c2h6s.tinkers_advanced.content.modifier.durability.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class TiAcModifiers {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);

    //无联动属性
    public static StaticModifier<Fragile> FRAGILE = MODIFIERS.register("fragile", Fragile::new);
    public static StaticModifier<TetanusModifier> TETANUS = MODIFIERS.register("tetanus", TetanusModifier::new);
    public static StaticModifier<SculkResonance> SCULK_RESONANCE = MODIFIERS.register("sculk_resonance", SculkResonance::new);
    public static StaticModifier<AutoShot> AUTO_SHOT = MODIFIERS.register("auto_shot", AutoShot::new);
    public static StaticModifier<Annihilate> ANNIHILATE = MODIFIERS.register("annihilate", Annihilate::new);
    public static StaticModifier<ReactiveExplosiveArmor> REACTIVE_EXPLOSIVE_ARMOR = MODIFIERS.register("reactive_explosive_armor", ReactiveExplosiveArmor::new);
}
