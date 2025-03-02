package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.tinkers_advanced.data.enums.EnumMaterial;
import com.c2h6s.tinkers_advanced.data.enums.EnumMaterialModifier;
import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
import org.slf4j.Logger;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class TiAcMaterialModifierProvider extends AbstractMaterialTraitDataProvider {
    public TiAcMaterialModifierProvider(PackOutput packOutput) {
        super(packOutput, new TiAcMaterialProvider(packOutput));
    }

    @Override
    protected void addMaterialTraits() {
        Logger logger = LogUtils.getLogger();
        for (EnumMaterial material : EnumMaterial.values()){
            logger.info("Now generating: {}", material.id);
            for (EnumMaterialModifier materialModifier:material.modifiers){
                if (materialModifier.statType==null){
                    addDefaultTraits(material.id,materialModifier.modifiers);
                }
                else addTraits(material.id,materialModifier.statType,materialModifier.modifiers);
            }
        }
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced Material Modifier Provider";
    }
}
