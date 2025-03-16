package com.c2h6s.tinkers_advanced.data.providers.tinker;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.data.TiAcMaterialIds;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

public class TiAcMaterialSpriteProvider extends AbstractMaterialSpriteProvider {
    @Override
    public String getName() {
        return "Tinkers' Advanced Material Sprite Provider";
    }

    @Override
    protected void addAllMaterials() {
        this.buildMaterial(TiAcMaterialIds.Mekanism.IRRADIUM).armor().ranged().meleeHarvest().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF98EEFF)
                .addARGB(102,0xFF98FFF8)
                .addARGB(140,0xFF0B4C87)
                .addARGB(178,0xFF146A99)
                .addARGB(216,0xFF1F95B5)
                .addARGB(255,0xFF30CAD6).build());
        this.buildMaterial(TiAcMaterialIds.Mekanism.ANTIMATTER).armor().meleeHarvest().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFFFF96E6)
                .addARGB(102,0xFFFFADDE)
                .addARGB(140,0xFF552665)
                .addARGB(178,0xFF6D397F)
                .addARGB(216,0xFFB661D0)
                .addARGB(255,0xFFD479E5).build());
        this.buildMaterial(TiAcMaterialIds.AE2.FLUIX).maille().ranged().meleeHarvest().fallbacks("crystal", "rock", "stick").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF1A172F)
                .addARGB(102,0xFF212040)
                .addARGB(140,0xFF262B54)
                .addARGB(178,0xFF6054A6)
                .addARGB(216,0xFFB06FDD)
                .addARGB(255,0xFFFF80D7).build());
        this.buildMaterial(TiAcMaterialIds.AE2.CERTUS).maille().ranged().meleeHarvest().fallbacks("crystal", "rock", "stick").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF466580)
                .addARGB(102,0xFF6689A9)
                .addARGB(140,0xFF8AB4D8)
                .addARGB(178,0xFF91C5FC)
                .addARGB(216,0xFFB8D8FC)
                .addARGB(255,0xFFEEFBFC).build());
        this.buildMaterial(TiAcMaterialIds.BISMUTH).armor().ranged().meleeHarvest().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF383338)
                .addARGB(102,0xFF564F57)
                .addARGB(140,0xFF817782)
                .addARGB(178,0xFFAEA1B0)
                .addARGB(216,0xFFCFBFD1)
                .addARGB(255,0xFFE9D6EB).build());
        this.buildMaterial(TiAcMaterialIds.BISMUTHINITE).ranged().meleeHarvest().fallbacks("crystal", "rock", "stick").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF0D0D0D)
                .addARGB(102,0xFF1A1A1A)
                .addARGB(140,0xFF212121)
                .addARGB(178,0xFF303030)
                .addARGB(216,0xFF424242)
                .addARGB(255,0xFF828181).build());
        this.buildMaterial(TiAcMaterialIds.Mekanism.ALLOY_ATOMIC).statType(StatlessMaterialStats.BINDING.getIdentifier()).fallbacks("crystal", "metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF383338)
                .addARGB(102,0xFF863F90)
                .addARGB(140,0xFF6B3E6D)
                .addARGB(178,0xFF9244AB)
                .addARGB(216,0xFFCD8AEB)
                .addARGB(255,0xFFF19CE4).build());
        this.buildMaterial(TiAcMaterialIds.Mekanism.REFINED_GLOWSTONE).ranged().meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF75581A)
                .addARGB(102,0xFFB28D1B)
                .addARGB(140,0xFFDEB846)
                .addARGB(178,0xFFFFFD6D)
                .addARGB(216,0xFFFEFF8C)
                .addARGB(255,0xFFFCFFD2).build());
        this.buildMaterial(TiAcMaterialIds.Mekanism.REFINED_OBSIDIAN).ranged().meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF1D1326)
                .addARGB(102,0xFF261A36)
                .addARGB(140,0xFF310F4A)
                .addARGB(178,0xFF391375)
                .addARGB(216,0xFF5E4DA1)
                .addARGB(255,0xFF8578CC).build());
        this.buildMaterial(TiAcMaterialIds.PnC.PNEUMATIC_STEEL).meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF4E4E60)
                .addARGB(102,0xFFA2A2BE)
                .addARGB(140,0xFF2B2B3A)
                .addARGB(178,0xFF393950)
                .addARGB(216,0xFF606070)
                .addARGB(255,0xFF76768A).build());
        this.buildMaterial(TiAcMaterialIds.Thermal.BASALZ_SIGNALUM).ranged().meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF210D00)
                .addARGB(102,0xFF3D1800)
                .addARGB(140,0xFF9E250D)
                .addARGB(178,0xFFCF4424)
                .addARGB(216,0xFFFF6B44)
                .addARGB(255,0xFFFF855A).build());
        this.buildMaterial(TiAcMaterialIds.Thermal.BLITZ_LUMIUM).ranged().meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFFFFDE44)
                .addARGB(102,0xFFFAFF62)
                .addARGB(140,0xFFD6A547)
                .addARGB(178,0xFFFFE778)
                .addARGB(216,0xFFFFF9B3)
                .addARGB(255,0xFFFFFDD5).build());
        this.buildMaterial(TiAcMaterialIds.Thermal.BLIZZ_ENDERIUM).ranged().meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFFADDDFF)
                .addARGB(102,0xFFADF4FF)
                .addARGB(140,0xFF011D36)
                .addARGB(178,0xFF0A2F56)
                .addARGB(216,0xFF1D7D8A)
                .addARGB(255,0xFF2BA6AD).build());
        this.buildMaterial(TiAcMaterialIds.BLAZE_NETHERITE).ranged().meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFFBA3B19)
                .addARGB(102,0xFFE39527)
                .addARGB(140,0xFF252226)
                .addARGB(178,0xFF363331)
                .addARGB(216,0xFF524B49)
                .addARGB(255,0xFF7A6A65).build());
        this.buildMaterial(TiAcMaterialIds.Thermal.ACTIVATED_CHROMATIC_STEEL).ranged().meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF362B34)
                .addARGB(102,0xFF736066)
                .addARGB(140,0xFF4F2F2F)
                .addARGB(178,0xFF6E3D46)
                .addARGB(216,0xFF9E7484)
                .addARGB(255,0xFFFFC7E7).build());
        this.buildMaterial(TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_ACTIVATED).ranged().meleeHarvest().armor().fallbacks("metal").colorMapper(GreyToColorMapping.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFFF00004)
                .addARGB(102,0xFFFF2529)
                .addARGB(140,0xFF4F2F2F)
                .addARGB(178,0xFF6E3D46)
                .addARGB(216,0xFF9E7484)
                .addARGB(255,0xFFFFC7E7).build());
        this.buildMaterial(TiAcMaterialIds.Thermal.Variant.ACTIVATED_CHROMATIC_STEEL_EMPOWERED).ranged().meleeHarvest().armor().fallbacks("metal").transformer(GreyToSpriteTransformer.builder()
                .addARGB(0,0xFF000000)
                .addTexture(63,TinkersAdvanced.getLocation("materials/activated_chromatic_steel/edge"))
                .addTexture(102,TinkersAdvanced.getLocation("materials/activated_chromatic_steel/edge"))
                .addARGB(140,0xFF4F2F2F)
                .addARGB(178,0xFF6E3D46)
                .addARGB(216,0xFF9E7484)
                .addARGB(255,0xFFFFC7E7).build());
    }
}
