package com.c2h6s.tinkers_advanced.tools.data.providers;

import com.c2h6s.tinkers_advanced.core.library.registry.SimpleMaterialObject;
import com.c2h6s.tinkers_advanced.materials.data.enums.EnumMaterial;
import com.c2h6s.tinkers_advanced.materials.data.enums.EnumMaterialModifier;
import com.c2h6s.tinkers_advanced.materials.data.providers.tinker.TiAcMeMaterialProvider;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeMaterials;
import com.c2h6s.tinkers_advanced.tools.data.enums.TiAcTEnumTconMaterial;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class TiAcTMaterialModifierProvider extends AbstractMaterialTraitDataProvider {
    public TiAcTMaterialModifierProvider(PackOutput packOutput) {
        super(packOutput, new TiAcTMaterialProvider(packOutput));
    }

    @Override
    protected void addMaterialTraits() {
        for (TiAcTEnumTconMaterial material: TiAcTEnumTconMaterial.values()){
            for (EnumMaterialModifier modifier:material.modifiers){
                addTraits(material.id,modifier.statType,modifier.modifiers);
            }
        }
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced-Tools Material Modifier Provider";
    }
}
