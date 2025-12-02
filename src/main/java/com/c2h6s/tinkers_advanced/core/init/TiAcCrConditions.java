package com.c2h6s.tinkers_advanced.core.init;

import com.c2h6s.tinkers_advanced.core.TiAcCrConfig;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.core.data.condition.ConfigCondition;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TiAcCrConditions {
    public static final Map<String, Supplier<Boolean>> MODID_CONFIG_MAP = new HashMap<>();
    public static final Map<String,ConfigCondition> MODID_CONDITIONAL_MAP = new HashMap<>();
    public static final Map<String, Supplier<Boolean>> MODID_ORIGINAL_CONFIG_MAP = new HashMap<>();
    public static final Map<String,ConfigCondition> MODID_ORIGINAL_CONDITIONAL_MAP = new HashMap<>();
    public static void registerModIdConfigCondition(String modId,ConfigCondition condition,Supplier<Boolean> supplier){
        MODID_CONDITIONAL_MAP.put(modId,condition);
        MODID_CONFIG_MAP.put(modId,supplier);
    }
    public static void registerModIdOriginalConfigCondition(String modId,ConfigCondition condition,Supplier<Boolean> supplier){
        MODID_ORIGINAL_CONDITIONAL_MAP.put(modId,condition);
        MODID_ORIGINAL_CONFIG_MAP.put(modId,supplier);
    }
    public static void init(){
        ConfigCondition.add(ALLOW_ORIGINAL_MATERIALS);
    }
    public static final ConfigCondition ALLOW_ORIGINAL_MATERIALS = new ConfigCondition("allow_original_materials", TiAcCrConfig.COMMON.ALLOW_ORIGINAL_MATERIALS);
}
