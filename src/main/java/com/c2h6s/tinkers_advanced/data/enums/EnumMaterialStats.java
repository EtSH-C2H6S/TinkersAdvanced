package com.c2h6s.tinkers_advanced.data.enums;

import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import net.minecraft.world.item.Tiers;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.tools.stats.*;

import static slimeknights.tconstruct.tools.stats.PlatingMaterialStats.*;

public enum EnumMaterialStats {
    ALLOY_ATOMIC(
            null,
            false,
            StatlessMaterialStats.BINDING,
            new FluxCoreMaterialStat(3.2f,3.2f)
    ),
    BISMUTH(
            armor(30,2.25f,7.25f,5.5f,2f).toughness(0).knockbackResistance(0.15f),
            true,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(-0.1f,0.2f,-0.15f,0.1f),
            new HeadMaterialStats(880,7f, Tiers.NETHERITE,2.45f),
            new GripMaterialStats(0.05f,-0.05f,2.45f),
            new LimbMaterialStats(760,-0.3f,0.25f,0.1f),
            new FluxCoreMaterialStat(2.5f,1.0f),
            StatlessMaterialStats.MAILLE
    ),
    BISMUTHINITE(
            null,
            false,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(-0.1f,-0.12f,-0.1f,0.15f),
            new HeadMaterialStats(379,4.5f, Tiers.DIAMOND,2.5f),
            new GripMaterialStats(-0.05f,0.1f,2.5f),
            new LimbMaterialStats(376,-0.1f,0.15f,0.1f),
            new FluxCoreMaterialStat(0.35f,1.25f)
    ),
    ANTIMONY(
            armor(25,3f,8f,6f,3f).toughness(0).knockbackResistance(0.25f),
            true,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(-0.15f,0.25f,-0.1f,0.1f),
            new HeadMaterialStats(800,7f, Tiers.NETHERITE,3f),
            new GripMaterialStats(-0.05f,-0.05f,2.45f),
            new LimbMaterialStats(700,-0.2f,0.25f,0.1f),
            new FluxCoreMaterialStat(3.5f,2.0f),
            StatlessMaterialStats.MAILLE
    ),
    STIBNITE(
            null,
            false,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(0.1f,-0.2f,0.25f,-0.1f),
            new HeadMaterialStats(325,4.5f, Tiers.DIAMOND,2.5f),
            new GripMaterialStats(-0.05f,0.1f,2.5f),
            new LimbMaterialStats(326,0.2f,-0.1f,-0.05f),
            new FluxCoreMaterialStat(1.35f,3.25f)
    ),
    CERTUS(
            null,
            false,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new HandleMaterialStats(-0.1f,-0.2f,0.0f,0.05f),
            new HeadMaterialStats(202,4.5f, Tiers.IRON,1.5f),
            new GripMaterialStats(-0.05f,0.1f,1.5f),
            new LimbMaterialStats(202,-0.08f,-0.05f,0.03f),
            new FluxCoreMaterialStat(0.1f,1.9f)
    ),
    FLUIX(
            null,
                    false,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new HandleMaterialStats(0.05f,0f,0.05f,-0.05f),
            new HeadMaterialStats(396,6.5f, Tiers.IRON,2.05f),
            new GripMaterialStats(0.05f,0.1f,2.05f),
            new FluxCoreMaterialStat(0.2f,2.5f),
            new LimbMaterialStats(396,0.15f,-0.05f,0.1f)
    ),
    ANTIMATTER(
            armor(24,5f,9,8,4).toughness(1).knockbackResistance(1),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(-0.5f,0.75f,-0.5f,0.75f),
            new FluxCoreMaterialStat(10000,1000000),
            new HeadMaterialStats(590,8.0f, Tiers.NETHERITE,10f)
    ),
    REFINED_GLOWSTONE(
            armor(30,2f,7,5,2),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(0.0f,0.1f,0.0f,0.05f),
            new HeadMaterialStats(790,5.5f, Tiers.DIAMOND,1.85f),
            new GripMaterialStats(0.05f,0.05f,1.8f),
            new FluxCoreMaterialStat(5.0f,1.0f),
            new LimbMaterialStats(790,0.05f,-0.05f,0.1f)
    ),
    IRRADIUM(
            armor(54,4f,8.5f,7,3.5f).toughness(3f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(-0.1f,-0.25f,0.25f,0.1f),
            new HeadMaterialStats(1020,3.0f, Tiers.NETHERITE,7.5f),
            new GripMaterialStats(-0.1f,0.05f,7.5f),
            new FluxCoreMaterialStat(9f,128f),
            new LimbMaterialStats(1020,0.25f,0.075f,0.1f)
    ),
    REFINED_OBSIDIAN(
            armor(36,2,7,5,2).toughness(1).knockbackResistance(0.025f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new FluxCoreMaterialStat(1.25f,5.5f),
            new HandleMaterialStats(0.1f,0.1f,-0.2f,-0.05f),
            new HeadMaterialStats(990,6.5f, Tiers.NETHERITE,2.05f)
    ),
    PNEUMATIC_STEEL(
            armor(40,2.25f,7.25f,5.25f,2.25f).toughness(3).knockbackResistance(0.1F),
            true,
            StatlessMaterialStats.BINDING,
            new FluxCoreMaterialStat(1.6f,2.6f),
            new HandleMaterialStats(0.2f,-0.1f,-0.25f,0.05f),
            new HeadMaterialStats(1105,5.5f, Tiers.NETHERITE,2.55f)
    ),
    BASALZ_SIGNALUM(
            armor(30,1.5f,6f,4.5f,1.5f).toughness(1),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new FluxCoreMaterialStat(5.4f,0.85f),
            new HandleMaterialStats(0.15f,0.15f,-0.2f,0.1f),
            new HeadMaterialStats(755,7.5f, Tiers.DIAMOND,3.0f),
            new GripMaterialStats(0.15f,0.1f,3.0f),
            new LimbMaterialStats(755,-0.2f,0.1f,0.1f)
    ),
    BLITZ_LUMIUM(
            armor(23,1.25f,5.75f,4.25f,1.25f).toughness(3),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new FluxCoreMaterialStat(1.4f,4.1f),
            new HandleMaterialStats(-0.15f,0.0f,0.25f,-0.1f),
            new HeadMaterialStats(470,4.5f, Tiers.DIAMOND,2.5f),
            new GripMaterialStats(-0.15f,-0.02f,3.0f),
            new LimbMaterialStats(470,0.25f,-0.1f,-0.01f)
    ),
    BLIZZ_ENDERIUM(
            armor(45,2,7,5,2).toughness(7).knockbackResistance(0.05f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new FluxCoreMaterialStat(2.5f,4.0f),
            new HandleMaterialStats(0.15f,0.1f,0.1f,0.15f),
            new HeadMaterialStats(1210,6.5f, Tiers.NETHERITE,3.5f),
            new GripMaterialStats(0.1f,0.05f,3.5f),
            new LimbMaterialStats(1210,0.1f,0.1f,0.05f)
    ),
    ACTIVATED_CHROMA_STEEL(
            armor(40,3,8,6,3).toughness(5).knockbackResistance(0.15f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new FluxCoreMaterialStat(9.2F,9.4F),
            new HandleMaterialStats(0.1f,0.1f,0.2f,0.1f),
            new HeadMaterialStats(994,7.5f, Tiers.NETHERITE,3.75f),
            new GripMaterialStats(0.1f,0.1f,3.75f),
            new LimbMaterialStats(994,0.2f,-0.1f,0.1f)
    ),
    BLAZE_NETHERITE(
            armor(55,2.5f,7.5f,6,2).toughness(4).knockbackResistance(0.15f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(0.1f,0.1f,-0.2f,0.15f),
            new FluxCoreMaterialStat(2.4F,4.2F),
            new HeadMaterialStats(1492,5.5f, Tiers.NETHERITE,3.5f),
            new GripMaterialStats(0.1f,0.01f,3.5f),
            new LimbMaterialStats(1492,-0.2f,0.2f,0.01f)
    ),
    IRIDIUM(
            armor(55,2.5f,7.5f,5.5f,2.5f).toughness(3).knockbackResistance(0.2F),
            true,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(0.2f,0.2f,-0.4f,0.4f),
            new HeadMaterialStats(1256,7f, Tiers.NETHERITE,4f),
            new GripMaterialStats(0.2f,0.1f,4f),
            new FluxCoreMaterialStat(1.5f,3.4f),
            new LimbMaterialStats(1256,-0.4f,0.3f,0.1f),
            StatlessMaterialStats.MAILLE
    ),
    DENSIUM(
            armor(128,3.5f,8.25f,6.25f,3.5f).toughness(3.5f).knockbackResistance(10),
            true,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(1.2f,0.2f,-0.5f,0.8f),
            new HeadMaterialStats(2048,10f, Tiers.NETHERITE,5.5f),
            new GripMaterialStats(1.2f,0.2f,5.5f),
            new FluxCoreMaterialStat(6.4f,8.6f),
            new LimbMaterialStats(2048,-0.5f,0.75f,0.2f),
            StatlessMaterialStats.MAILLE
    ),
    NEUTRONITE(
            armor(256,5f,9f,7f,5f).toughness(10f).knockbackResistance(10),
            true,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(2.4f,0.5f,-0.8f,10.24f),
            new HeadMaterialStats(4096,16f, Tiers.NETHERITE,12.5f),
            new GripMaterialStats(2.4f,10.24f,12.5f),
            new FluxCoreMaterialStat(65535,65535),
            new LimbMaterialStats(4096,-0.75f,10.5f,10.24f),
            StatlessMaterialStats.MAILLE
    ),
    OSGLOGLAS(
            null,
            false,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(0.45f,0.45f,-0.1f,0.25f),
            new HeadMaterialStats(1920,12f, Tiers.NETHERITE,4.25f),
            new GripMaterialStats(0.75f,0.1f,4.25f),
            new FluxCoreMaterialStat(6.4f,3.0f),
            new LimbMaterialStats(1920,-0.1f,0.2f,0.05f)
    ),
    DISINTEGRATE_CRYSTAL(
            null,
            false,
            new FluxCoreMaterialStat(0.1f,4.9f),
            StatlessMaterialStats.BINDING
    ),
    RESONANCE_CRYSTAL(
            null,
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.SHIELD_CORE,
            new HandleMaterialStats(0.05f,-0.25f,0.25f,-0.1f),
            new HeadMaterialStats(890,5f, Tiers.NETHERITE,3f),
            new FluxCoreMaterialStat(2f,1.8f),
            StatlessMaterialStats.MAILLE
    ),
    VOLTAIC_CRYSTAL(
            null,
            false,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(-0.25f,1f,1f,-0.25f),
            new HeadMaterialStats(925,10f, Tiers.NETHERITE,4f),
            new GripMaterialStats(-0.25f,-0.05f,4f),
            new FluxCoreMaterialStat(3.2f,4.5f),
            new LimbMaterialStats(925,1f,-0.1f,-0.1f)
    ),
    PLASTIC(
            null,
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new HandleMaterialStats(0.1f,-0.1f,0.1f,-0.1f),
            new HeadMaterialStats(255,1f, Tiers.WOOD,0.5f),
            new GripMaterialStats(0.1f,-0.05f,0.5f),
            new FluxCoreMaterialStat(25f,0.25f),
            new LimbMaterialStats(255,0.2f,-0.1f,-0.05f)
    ),
    PROTOCITE(
            armor(49,3.5f,8f,6.5f,3.5f).toughness(2f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new FluxCoreMaterialStat(76.8f,3.7f),
            new HandleMaterialStats(0.8f,0.8f,-0.1f,-0.1f),
            new HeadMaterialStats(990,10.0f, Tiers.NETHERITE,6f)
    ),
    COMPRESSED_IRON(
            null,
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new HandleMaterialStats(0.3f,0f,0f,0f),
            new HeadMaterialStats(375,6f, Tiers.IRON,2f),
            new GripMaterialStats(0.3f,0f,2f),
            new FluxCoreMaterialStat(0.9f,0.5f),
            new LimbMaterialStats(375,-0.2f,0.1f,0f)
    ),
    NUTRITIVE_SLIMESTEEL(
            armor(42,2f,5f,6f,2f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new HandleMaterialStats(-0.1f,-0.1f,-0.1f,-0.1f),
            new HeadMaterialStats(900,6f, Tiers.IRON,0.5f),
            new GripMaterialStats(-0.1f,-0.1f,0.5f),
            new LimbMaterialStats(900,-0.1f,-0.1f,-0.1f)
    ),
    PINK_SLIME_METAL(
            null,
            true,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(-0.1f,0.1f,0.1f,-0.1f),
            new HeadMaterialStats(1100,6.5f, Tiers.IRON,2.1f),
            new GripMaterialStats(-0.1f,0.05f,2.1f),
            new FluxCoreMaterialStat(25.5f,1.7f),
            new LimbMaterialStats(1100,0.1f,-0.1f,-0.05f)
    ),
    VOID_STEEL(
            armor(60,2.5f,7.5f,6,2).toughness(3).knockbackResistance(0.2f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(0.15f,0.1f,0.15f,0.1f),
            new FluxCoreMaterialStat(2.5F,4.9F),
            new HeadMaterialStats(1592,6.5f, Tiers.NETHERITE,3.75f),
            new GripMaterialStats(0.15f,0.01f,3.75f),
            new LimbMaterialStats(1592,0.05f,0.1f,0.01f)
    ),




    ;
    private final IMaterialStats[] stats;
    private final Builder armorStatBuilder;
    public final boolean allowShield;
    EnumMaterialStats(Builder builder,boolean allowShield ,IMaterialStats... stats) {
        this.stats = stats;
        this.armorStatBuilder =builder;
        this.allowShield = allowShield;
    }
    EnumMaterialStats(IMaterialStats... stats) {
        this.stats = stats;
        this.armorStatBuilder =null;
        this.allowShield = false;
    }

    public IMaterialStats[] getStats() {
        return stats;
    }
    public Builder getArmorBuilder() {
        return armorStatBuilder;
    }

    public static Builder armor(int durabilityFactor,float helmet,float chestplate,float leggings,float boots){
        return PlatingMaterialStats.builder().durabilityFactor(durabilityFactor).armor(boots,leggings,chestplate,helmet);
    }
}
