package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.etstlib.util.EtSTLibTags;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.tinkering.AbstractMaterialTagProvider;

public class TiAcMaterialTagProvider extends AbstractMaterialTagProvider {
    public TiAcMaterialTagProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TinkersAdvanced.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(EtSTLibTags.BLACKLISTED_RANDOM_MATERIAL).addOptional(TiAcMaterialIds.Mekanism.IRRADIUM,TiAcMaterialIds.Mekanism.ANTIMATTER,TiAcMaterialIds.Mekanism.NEUTRONITE,TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL);
    }

    @Override
    public String getName() {
        return "TiAc Material Tag Provider";
    }
}
