package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.tools.content.tool.tinkering.materialStat.FluxCoreMaterialStat;
import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;

public class TiAcMaterialStat {
    public static void init(){
        IMaterialRegistry registry = MaterialRegistry.getInstance();
        registry.registerStatType(FluxCoreMaterialStat.TYPE, MaterialRegistry.MELEE_HARVEST);
    }
}
