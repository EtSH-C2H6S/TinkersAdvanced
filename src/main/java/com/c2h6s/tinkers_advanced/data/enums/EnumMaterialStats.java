package com.c2h6s.tinkers_advanced.data.enums;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Tiers;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.tools.stats.*;

import java.util.Arrays;
import java.util.List;

import static com.c2h6s.tinkers_advanced.util.CommonUtil.ALL_ARMOR;
import static slimeknights.tconstruct.tools.stats.PlatingMaterialStats.*;

public enum EnumMaterialStats {
    ALLOY_ATOMIC(
            null,
             null,
            false,
            StatlessMaterialStats.BINDING
    ),
    BISMUTH(
            armor(70,4.5f,9,6,4),
            ALL_ARMOR,
            true,
            StatlessMaterialStats.BINDING,
            new HandleMaterialStats(-0.1f,0.2f,-0.1f,0.1f),
            new HeadMaterialStats(1020,7f, Tiers.NETHERITE,2.45f),
            new GripMaterialStats(0.05f,-0.05f,2.45f),
            new LimbMaterialStats(980,-0.3f,0.25f,0.1f),
            StatlessMaterialStats.MAILLE
    )
    ;
    private final IMaterialStats[] stats;
    EnumMaterialStats(Builder builder, ArmorItem.Type[] types,boolean allowShield, IMaterialStats... stats) {
        if (builder != null&&types!=null){
            List<IMaterialStats> list = new java.util.ArrayList<>(Arrays.stream(stats).toList());
            for (ArmorItem.Type type :types){
                list.add(builder.build(type));
            }
            if (allowShield){
                list.add(builder.buildShield());
            }
            stats = list.toArray(new IMaterialStats[0]);
        }
        this.stats =stats;
    }

    public IMaterialStats[] getStats() {
        return stats;
    }

    public static Builder armor(int durabilityFactor,float helmet,float chestplate,float leggings,float boots){
        return PlatingMaterialStats.builder().durabilityFactor(durabilityFactor).armor(boots,leggings,chestplate,helmet);
    }
}
