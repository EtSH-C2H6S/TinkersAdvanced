package com.c2h6s.tinkers_advanced.tools.data.providers;

import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class TiAcTMaterialProvider extends AbstractMaterialDataProvider {
    public TiAcTMaterialProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addMaterials() {
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced-Tools Material Data Provider";
    }
}
