package com.c2h6s.tinkers_advanced.tools.data.providers;

import com.c2h6s.tinkers_advanced.core.library.registry.SimpleMaterialObject;
import com.c2h6s.tinkers_advanced.materials.data.enums.EnumMaterial;
import com.c2h6s.tinkers_advanced.materials.data.providers.tinker.TiAcMeMaterialProvider;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeMaterials;
import com.c2h6s.tinkers_advanced.tools.data.enums.TiAcTEnumTconMaterial;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;

public class TiAcTMaterialStatProvider extends AbstractMaterialStatsDataProvider {
    public TiAcTMaterialStatProvider(PackOutput packOutput) {
        super(packOutput, new TiAcTMaterialProvider(packOutput));
    }

    @Override
    protected void addMaterialStats() {
        for (TiAcTEnumTconMaterial material: TiAcTEnumTconMaterial.values()){
            addMaterialStats(material.id,material.stats.stats);
        }
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced-Tools Material Stats Data Provider";
    }
}
