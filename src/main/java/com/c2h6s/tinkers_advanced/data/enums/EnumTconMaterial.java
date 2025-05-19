package com.c2h6s.tinkers_advanced.data.enums;

import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static com.c2h6s.tinkers_advanced.data.enums.EnumMaterialModifier.*;

public enum EnumTconMaterial {
    MANYULLYN(EnumTconExtraStat.MANYULLYN, MaterialIds.manyullyn),
    HEPATIZON(EnumTconExtraStat.HEPATIZON, MaterialIds.hepatizon),
    COBALT(EnumTconExtraStat.COBALT, MaterialIds.cobalt,COBALT_FLUX_CORE),
    NAHUATL(EnumTconExtraStat.NAHUATL,MaterialIds.nahuatl),
    ROSE_GOLD(EnumTconExtraStat.ROSE_GOLD,MaterialIds.roseGold),
    PIG_IRON(EnumTconExtraStat.PIG_IRON,MaterialIds.pigIron,PIG_IRON_FLUX_CORE),
    STEEL(EnumTconExtraStat.STEEL,MaterialIds.steel),
    CINDER_SLIME(EnumTconExtraStat.CINDER_SLIME,MaterialIds.cinderslime,CINDER_SLIME_FLUX_CORE),
    ;
    public final EnumTconExtraStat stats;
    public final EnumMaterialModifier[] modifiers;
    public final MaterialId id;

    EnumTconMaterial(EnumTconExtraStat stats, MaterialId id, EnumMaterialModifier... modifiers) {
        this.stats = stats;
        this.modifiers = modifiers;
        this.id = id;
    }
}
