package com.c2h6s.tinkers_advanced.data.enums;

import slimeknights.tconstruct.library.materials.definition.MaterialId;

public enum EnumTconMaterial {

    ;
    public final EnumMaterialStats stats;
    public final EnumMaterialModifier[] modifiers;
    public final MaterialId id;

    EnumTconMaterial(EnumMaterialStats stats, EnumMaterialModifier[] modifiers, MaterialId id) {
        this.stats = stats;
        this.modifiers = modifiers;
        this.id = id;
    }
}
