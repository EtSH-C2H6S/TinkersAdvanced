package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.modifier.combat.Anisotropy;
import com.c2h6s.tinkers_advanced.content.modifier.combat.TetanusModifier;
import com.c2h6s.tinkers_advanced.content.modifier.durability.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class TiAcModifiers {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(TinkersAdvanced.MODID);

    //无联动词条
    public static StaticModifier<Fragile> FRAGILE = MODIFIERS.register("fragile", Fragile::new);
    public static StaticModifier<TetanusModifier> TETANUS = MODIFIERS.register("tetanus", TetanusModifier::new);
    public static StaticModifier<Anisotropy> ANISOTROPY = MODIFIERS.register("anisotropy", Anisotropy::new);
}
