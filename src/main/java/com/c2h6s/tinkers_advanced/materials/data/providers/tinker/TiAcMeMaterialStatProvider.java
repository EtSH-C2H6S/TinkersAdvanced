package com.c2h6s.tinkers_advanced.materials.data.providers.tinker;

import com.c2h6s.tinkers_advanced.core.library.registry.SimpleMaterialObject;
import com.c2h6s.tinkers_advanced.materials.data.enums.EnumMaterial;
import com.c2h6s.tinkers_advanced.tools.data.enums.TiAcTEnumTconMaterial;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeMaterials;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;

public class TiAcMeMaterialStatProvider extends AbstractMaterialStatsDataProvider {
    public TiAcMeMaterialStatProvider(PackOutput packOutput) {
        super(packOutput, new TiAcMeMaterialProvider(packOutput));
    }

    @Override
    protected void addMaterialStats() {
        for (EnumMaterial material:EnumMaterial.values()){
            if (material.stats.getArmorBuilder()!=null){
                addArmorStats(material.id, material.stats.getArmorBuilder(),material.stats.getStats());
                if (material.stats.allowShield){
                    addMaterialStats(material.id, material.stats.getArmorBuilder().buildShield());
                }
            }
            else addMaterialStats(material.id, material.stats.getStats());
        }
        for (TiAcTEnumTconMaterial material: TiAcTEnumTconMaterial.values()){
            addMaterialStats(material.id,material.stats.stats);
        }
        for (SimpleMaterialObject object: TiAcMeMaterials.MATERIALS.getEntryMap().values()){
            var stats = object.getMaterialInfo().getStats();
            if (stats.armorBuilder()!=null){
                addArmorStats(object.getMaterialId(), stats.armorBuilder(),stats.stats());
                if (stats.allowShield()) addMaterialStats(object.getMaterialId(),stats.armorBuilder().buildShield());
            }
            else addMaterialStats(object.getMaterialId(),stats.stats());
        }
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced-Materials Material Stats Data Provider";
    }
}
