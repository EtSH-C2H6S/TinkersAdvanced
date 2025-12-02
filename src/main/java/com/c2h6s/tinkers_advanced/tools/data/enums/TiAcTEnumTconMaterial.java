package com.c2h6s.tinkers_advanced.tools.data.enums;

import com.c2h6s.tinkers_advanced.materials.data.enums.EnumMaterialModifier;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static com.c2h6s.tinkers_advanced.materials.data.enums.EnumMaterialModifier.*;

public enum TiAcTEnumTconMaterial {
    MANYULLYN(TiAcTEnumTconExtraStat.MANYULLYN, MaterialIds.manyullyn),
    HEPATIZON(TiAcTEnumTconExtraStat.HEPATIZON, MaterialIds.hepatizon),
    COBALT(TiAcTEnumTconExtraStat.COBALT, MaterialIds.cobalt,COBALT_FLUX_CORE),
    NAHUATL(TiAcTEnumTconExtraStat.NAHUATL,MaterialIds.nahuatl),
    ROSE_GOLD(TiAcTEnumTconExtraStat.ROSE_GOLD,MaterialIds.roseGold),
    PIG_IRON(TiAcTEnumTconExtraStat.PIG_IRON,MaterialIds.pigIron,PIG_IRON_FLUX_CORE),
    STEEL(TiAcTEnumTconExtraStat.STEEL,MaterialIds.steel),
    CINDER_SLIME(TiAcTEnumTconExtraStat.CINDER_SLIME,MaterialIds.cinderslime,CINDER_SLIME_FLUX_CORE),
    ;
    public final TiAcTEnumTconExtraStat stats;
    public final EnumMaterialModifier[] modifiers;
    public final MaterialId id;

    TiAcTEnumTconMaterial(TiAcTEnumTconExtraStat stats, MaterialId id, EnumMaterialModifier... modifiers) {
        this.stats = stats;
        this.modifiers = modifiers;
        this.id = id;
    }
}
