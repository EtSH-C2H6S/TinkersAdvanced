package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

public class TiAcMaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {
    public TiAcMaterialRenderInfoProvider(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(TiAcMaterialIds.BISMUTH).color(0xFFCFBFD1).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.BISMUTHINITE).color(0xFF424242).fallbacks("crystal", "rock", "stick");
        buildRenderInfo(TiAcMaterialIds.ANTIMONY).color(0xFFC7D6CC).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.STIBNITE).color(0xFF728c39).fallbacks("crystal", "rock", "stick");
        buildRenderInfo(TiAcMaterialIds.AE2.CERTUS).color(0xFFB8D8FC).fallbacks("crystal", "rock", "stick");
        buildRenderInfo(TiAcMaterialIds.AE2.FLUIX).color(0xFFB8D8FC).fallbacks("crystal", "rock", "stick").luminosity(5);
        buildRenderInfo(TiAcMaterialIds.Mekanism.ALLOY_ATOMIC).color(0xFFD896FF).fallbacks("crystal", "metal").luminosity(6);
        buildRenderInfo(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE).color(0xFFFEFF8C).fallbacks("metal").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN).color(0xFF391375).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.Mekanism.ANTIMATTER).color(0xFFD479E5).fallbacks("metal").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.Mekanism.IRRADIUM).color(0xFF17CBEB).fallbacks("metal").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.PnC.PNEUMATIC_STEEL).color(0xFF9797B1).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.Thermal.BASALZ_SIGNALUM).color(0xFFFF4E11).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.Thermal.BLITZ_LUMIUM).color(0xFFFFFB9F).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.Thermal.BLIZZ_ENDERIUM).color(0xFF39FFD1).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.BLAZE_NETHERITE).color(0xFFCF6D4F).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL).color(0xFFFFC7E7).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_ACTIVATED).color(0xFFFFC7E7).fallbacks("metal").luminosity(6);
        buildRenderInfo(TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_EMPOWERED).color(0xFFFFC7E7).fallbacks("metal").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.IRIDIUM).color(0xFFE0D6FF).fallbacks("metal").luminosity(6);
        buildRenderInfo(TiAcMaterialIds.Mekanism.DENSIUM).color(0xFF270053).fallbacks("metal").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.Mekanism.NEUTRONITE).color(0xFF30003E).fallbacks("metal").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.Mekanism.OSGLOGLAS).color(0xFF72FF7B).fallbacks("metal").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.DISINTEGRATE_CRYSTAL).color(0xFFFFB968).fallbacks("crystal", "rock").luminosity(10);
        buildRenderInfo(TiAcMaterialIds.CommonIntegration.PLASTIC).color(0xFFbebebe).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.RESONANCE_CRYSTAL).color(0xFF93d7c1).fallbacks("crystal", "rock").luminosity(7);
        buildRenderInfo(TiAcMaterialIds.VOLTAIC_CRYSTAL).color(0xFFa19aff).fallbacks("crystal", "rock").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.Mekanism.PROTOCITE).color(0xFFD7FF6B).fallbacks("metal").luminosity(15);
        buildRenderInfo(TiAcMaterialIds.PnC.COMPRESSED_IRON).color(0xFFa1a1a1).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.Mekanism.NUTRITIVE_SLIMESTEEL).color(0xFFf77dbf).fallbacks("slime_metal","metal");
        buildRenderInfo(TiAcMaterialIds.IndustrialForgoing.PINK_SLIME_METAL).color(0xFFd08cc5).fallbacks("slime_metal","metal");
        buildRenderInfo(TiAcMaterialIds.CreateUtilities.VOID_STEEL).color(0xFF1a7565).fallbacks("metal");
    }

    @Override
    public String getName() {
        return "Tinkers' Advanced Material Info Provider";
    }
}
