package com.c2h6s.tinkers_advanced.data.enums;

import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;

public enum EnumTconExtraStat {
    MANYULLYN(new FluxCoreMaterialStat(2.2f,3.9f)),
    HEPATIZON(new FluxCoreMaterialStat(3.5f,2.7f)),
    COBALT(new FluxCoreMaterialStat(0.9f,2.1f)),
    NAHUATL(new FluxCoreMaterialStat(1.7f,1.7f)),
    ROSE_GOLD(new FluxCoreMaterialStat(0.05f,6.0f)),
    PIG_IRON(new FluxCoreMaterialStat(1.2f,1.4f)),
    STEEL(new FluxCoreMaterialStat(1.6f,2.1f)),
    CINDER_SLIME(new FluxCoreMaterialStat(2.0f,2.4f)),
    ;
    public final IMaterialStats[] stats;

    EnumTconExtraStat(IMaterialStats... stats) {
        this.stats = stats;
    }
}
