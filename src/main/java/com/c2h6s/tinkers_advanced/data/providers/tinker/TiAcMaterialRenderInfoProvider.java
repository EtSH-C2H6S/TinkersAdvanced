package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TiAcMaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {
    public TiAcMaterialRenderInfoProvider(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(TiAcMaterialIds.BISMUTH).color(0xFFCFBFD1).fallbacks("metal");
        buildRenderInfo(TiAcMaterialIds.BISMUTHINITE).color(0xFF424242).fallbacks("crystal", "rock", "stick");
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
    }

    public static final Set<MaterialStatsId> allStats = new HashSet<>(List.of(MaterialRegistry.MELEE_HARVEST, MaterialRegistry.RANGED,MaterialRegistry.ARMOR));
    public static final Set<MaterialStatsId> attackAndMaile = new HashSet<>(List.of(MaterialRegistry.MELEE_HARVEST, MaterialRegistry.RANGED,StatlessMaterialStats.MAILLE.getIdentifier()));
    public static final Set<MaterialStatsId> noRange = new HashSet<>(List.of(MaterialRegistry.MELEE_HARVEST,MaterialRegistry.ARMOR));

    @Override
    public String getName() {
        return "Tinkers' Advanced Material Info Provider";
    }
}
