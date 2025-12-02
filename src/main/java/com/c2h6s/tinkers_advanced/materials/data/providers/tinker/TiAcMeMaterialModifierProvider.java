package com.c2h6s.tinkers_advanced.materials.data.providers.tinker;

import com.c2h6s.tinkers_advanced.core.library.registry.SimpleMaterialObject;
import com.c2h6s.tinkers_advanced.materials.data.enums.EnumMaterial;
import com.c2h6s.tinkers_advanced.materials.data.enums.EnumMaterialModifier;
import com.c2h6s.tinkers_advanced.tools.data.enums.TiAcTEnumTconMaterial;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeMaterials;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class TiAcMeMaterialModifierProvider extends AbstractMaterialTraitDataProvider {
    public TiAcMeMaterialModifierProvider(PackOutput packOutput) {
        super(packOutput, new TiAcMeMaterialProvider(packOutput));
    }

    @Override
    protected void addMaterialTraits() {
        for (EnumMaterial material : EnumMaterial.values()){
            for (EnumMaterialModifier materialModifier:material.modifiers){
                if (materialModifier.statType==null){
                    addDefaultTraits(material.id,materialModifier.modifiers);
                }
                else addTraits(material.id,materialModifier.statType,materialModifier.modifiers);
            }
        }
        for (TiAcTEnumTconMaterial material: TiAcTEnumTconMaterial.values()){
            for (EnumMaterialModifier modifier:material.modifiers){
                addTraits(material.id,modifier.statType,modifier.modifiers);
            }
        }
        for (SimpleMaterialObject object: TiAcMeMaterials.MATERIALS.getEntryMap().values()){
            for (var statPair:object.getMaterialInfo().getModifiers().modifierPairs()){
                if (statPair.getA()==null) addDefaultTraits(object.getMaterialId(),statPair.getB());
                else addTraits(object.getMaterialId(),statPair.getA(),statPair.getB());
            }
        }
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced-Materials Material Modifier Provider";
    }
}
