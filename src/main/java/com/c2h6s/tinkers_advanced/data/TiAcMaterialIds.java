package com.c2h6s.tinkers_advanced.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;

public class TiAcMaterialIds {
    public static final MaterialId BISMUTH =new MaterialId(TinkersAdvanced.getLocation("bismuth"));
    public static final MaterialId BISMUTHINITE =new MaterialId(TinkersAdvanced.getLocation("bismuthinite"));
    public static final MaterialId BLAZE_NETHERITE =new MaterialId(TinkersAdvanced.getLocation("blaze_netherite"));
    public static final MaterialId IRIDIUM =new MaterialId(TinkersAdvanced.getLocation("iridium"));
    public static final MaterialId DISINTEGRATE_CRYSTAL =new MaterialId(TinkersAdvanced.getLocation("disintegrate_crystal"));
    public static final MaterialId VOLTAIC_CRYSTAL =new MaterialId(TinkersAdvanced.getLocation("voltaic_crystal"));
    public static final MaterialId RESONANCE_CRYSTAL =new MaterialId(TinkersAdvanced.getLocation("resonance_crystal"));
    public static final MaterialId ANTIMONY =new MaterialId(TinkersAdvanced.getLocation("antimony"));
    public static final MaterialId STIBNITE =new MaterialId(TinkersAdvanced.getLocation("stibnite"));

    public static class CommonIntegration{
        public static final MaterialId PLASTIC =new MaterialId(TinkersAdvanced.getLocation("plastic"));

    }

    public static class Mekanism{
        public static final MaterialId ANTIMATTER =new MaterialId(TinkersAdvanced.getLocation("antimatter"));
        public static final MaterialId ALLOY_ATOMIC =new MaterialId(TinkersAdvanced.getLocation("alloy_atomic"));
        public static final MaterialId REFINED_GLOWSTONE =new MaterialId(TinkersAdvanced.getLocation("refined_glowstone"));
        public static final MaterialId REFINED_OBSIDIAN =new MaterialId(TinkersAdvanced.getLocation("refined_obsidian"));
        public static final MaterialId IRRADIUM =new MaterialId(TinkersAdvanced.getLocation("irradium"));
        public static final MaterialId DENSIUM =new MaterialId(TinkersAdvanced.getLocation("densium"));
        public static final MaterialId NEUTRONITE =new MaterialId(TinkersAdvanced.getLocation("neutronite"));
        public static final MaterialId OSGLOGLAS =new MaterialId(TinkersAdvanced.getLocation("osgloglas"));
        public static final MaterialId PROTOCITE =new MaterialId(TinkersAdvanced.getLocation("protocite"));
        public static final MaterialId NUTRITIVE_SLIMESTEEL =new MaterialId(TinkersAdvanced.getLocation("nutritive_slime"));
    }

    public static class AE2{
        public static final MaterialId FLUIX =new MaterialId(TinkersAdvanced.getLocation("fluix_crystal"));
        public static final MaterialId CERTUS =new MaterialId(TinkersAdvanced.getLocation("certus_quartz"));
    }

    public static class PnC{
        public static final MaterialId PNEUMATIC_STEEL =new MaterialId(TinkersAdvanced.getLocation("pneumatic_steel"));
        public static final MaterialId COMPRESSED_IRON =new MaterialId(TinkersAdvanced.getLocation("compressed_iron"));
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

    public static class IndustrialForgoing{
        public static final MaterialId PINK_SLIME_METAL =new MaterialId(TinkersAdvanced.getLocation("pink_slime_metal"));
    }

    public static class CreateUtilities{
        public static final MaterialId VOID_STEEL = new MaterialId(TinkersAdvanced.getLocation("void_steel"));
    }

}
