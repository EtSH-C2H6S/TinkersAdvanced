package com.c2h6s.tinkers_advanced.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

public class TiAcMaterialIds {
    public static final MaterialId BISMUTH =new MaterialId(TinkersAdvanced.getLocation("bismuth"));
    public static final MaterialId BISMUTHINITE =new MaterialId(TinkersAdvanced.getLocation("bismuthinite"));
    public static final MaterialId BLAZE_NETHERITE =new MaterialId(TinkersAdvanced.getLocation("blaze_netherite"));
    public static final MaterialId IRIDIUM =new MaterialId(TinkersAdvanced.getLocation("iridium"));

    public static class Mekanism{
        public static final MaterialId ANTIMATTER =new MaterialId(TinkersAdvanced.getLocation("antimatter"));
        public static final MaterialId ALLOY_ATOMIC =new MaterialId(TinkersAdvanced.getLocation("alloy_atomic"));
        public static final MaterialId REFINED_GLOWSTONE =new MaterialId(TinkersAdvanced.getLocation("refined_glowstone"));
        public static final MaterialId REFINED_OBSIDIAN =new MaterialId(TinkersAdvanced.getLocation("refined_obsidian"));
        public static final MaterialId IRRADIUM =new MaterialId(TinkersAdvanced.getLocation("irradium"));
    }

    public static class AE2{
        public static final MaterialId FLUIX =new MaterialId(TinkersAdvanced.getLocation("fluix_crystal"));
        public static final MaterialId CERTUS =new MaterialId(TinkersAdvanced.getLocation("certus_quartz"));
    }

    public static class PnC{
        public static final MaterialId PNEUMATIC_STEEL =new MaterialId(TinkersAdvanced.getLocation("pneumatic_steel"));
    }

    public static class Thermal{
        public static final MaterialId BASALZ_SIGNALUM =new MaterialId(TinkersAdvanced.getLocation("basalz_signalum"));
        public static final MaterialId BLITZ_LUMIUM =new MaterialId(TinkersAdvanced.getLocation("blitz_lumium"));
        public static final MaterialId BLIZZ_ENDERIUM =new MaterialId(TinkersAdvanced.getLocation("blizz_enderium"));
        public static final MaterialId ACTIVATED_CHROMATIC_STEEL =new MaterialId(TinkersAdvanced.getLocation("activated_chromatic_steel"));
        public static class Variant{
            public static final MaterialVariantId ACTIVATED_CHROMATIC_STEEL_ACTIVATED =MaterialVariantId.create(Thermal.ACTIVATED_CHROMATIC_STEEL,"activated");
            public static final MaterialVariantId ACTIVATED_CHROMATIC_STEEL_EMPOWERED =MaterialVariantId.create(Thermal.ACTIVATED_CHROMATIC_STEEL,"empowered");
        }
    }

}
