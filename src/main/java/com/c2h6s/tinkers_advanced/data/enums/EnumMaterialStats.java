package com.c2h6s.tinkers_advanced.data.enums;

import net.minecraft.world.item.Tiers;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.tools.stats.*;

import static slimeknights.tconstruct.tools.stats.PlatingMaterialStats.*;

public enum EnumMaterialStats {
    ALLOY_ATOMIC(
            null,
            false,
            StatlessMaterialStats.BINDING
    ),
    BISMUTH(
            armor(70,4.5f,9,6,4),
            true,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(-0.1f,0.2f,-0.1f,0.1f),
            new HeadMaterialStats(1020,7f, Tiers.NETHERITE,2.45f),
            new GripMaterialStats(0.05f,-0.05f,2.45f),
            new LimbMaterialStats(980,-0.3f,0.25f,0.1f),
            StatlessMaterialStats.MAILLE
    ),
    BISMUTHINITE(
            null,
            false,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(-0.1f,-0.12f,-0.1f,0.15f),
            new HeadMaterialStats(379,4.5f, Tiers.DIAMOND,2.5f),
            new GripMaterialStats(-0.05f,0.1f,2.5f),
            new LimbMaterialStats(376,-0.1f,0.15f,0.1f)
    ),
    CERTUS(
            null,
            false,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(-0.1f,-0.2f,0.0f,0.05f),
            new HeadMaterialStats(202,4.5f, Tiers.IRON,1.5f),
            new GripMaterialStats(-0.05f,0.1f,1.5f),
            new LimbMaterialStats(202,-0.08f,-0.05f,0.03f)
    ),
    FLUIX(
            null,
                    false,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(0.05f,0f,0.05f,-0.05f),
            new HeadMaterialStats(396,6.5f, Tiers.IRON,2.05f),
            new GripMaterialStats(0.05f,0.1f,2.05f),
            new LimbMaterialStats(396,0.15f,-0.05f,0.1f)
    ),
    ANTIMATTER(
            armor(37,5f,9,8,4),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(-0.5f,0.75f,-0.5f,0.75f),
            new HeadMaterialStats(590,8.0f, Tiers.NETHERITE,10f)
    ),
    REFINED_GLOWSTONE(
            armor(37,2f,7,5,2),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(0.0f,0.1f,0.0f,0.05f),
            new HeadMaterialStats(790,5.5f, Tiers.DIAMOND,1.85f),
            new GripMaterialStats(0.05f,0.05f,1.8f),
            new LimbMaterialStats(790,0.05f,-0.05f,0.1f)
    ),
    IRRADIUM(
            armor(59,4f,8.5f,7,3.5f),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(-0.1f,-0.25f,0.25f,0.1f),
            new HeadMaterialStats(1020,3.0f, Tiers.NETHERITE,7.5f),
            new GripMaterialStats(-0.1f,0.05f,7.5f),
            new LimbMaterialStats(1020,0.25f,0.075f,0.1f)
    ),
    REFINED_OBSIDIAN(
            armor(55,2,7,5,2),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            new HandleMaterialStats(0.1f,0.1f,-0.2f,-0.05f),
            new HeadMaterialStats(990,6.5f, Tiers.NETHERITE,2.05f)
    )


    ;
    private final IMaterialStats[] stats;
    private final Builder armorStatBuilder;
    public final boolean allowShield;
    EnumMaterialStats(Builder builder,boolean allowShield ,IMaterialStats... stats) {
        this.stats = stats;
        this.armorStatBuilder =builder;
        this.allowShield = allowShield;
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
