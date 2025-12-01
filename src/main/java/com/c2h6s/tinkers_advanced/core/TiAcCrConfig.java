package com.c2h6s.tinkers_advanced.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class TiAcCrConfig {

    public static final ForgeConfigSpec commonSpec;
    public static final TiAcCrConfig.Common COMMON;
    static {
        final Pair<TiAcCrConfig.Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TiAcCrConfig.Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
    public static final ForgeConfigSpec clientSpec;
    public static final TiAcCrConfig.Client CLIENT;

    static {
        final Pair<TiAcCrConfig.Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TiAcCrConfig.Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TiAcCrConfig.commonSpec);
    }
    public static class Common{
        public final ForgeConfigSpec.BooleanValue ALLOW_ORIGINAL_MATERIALS;

        public Common(ForgeConfigSpec.Builder builder){
            builder.comment("***注意！").comment("***Notice")
                    .comment("这些配置并不会同步修改语言文件中的描述，当你修改了某个工具/工具属性的性质后如果想要更改描述则需要手动覆盖语言文件！")
                    .comment("This configure won't affect language display. When a certain modifier or tool is configured, please manually replace the description of the relating language contents.");
            builder.comment("平衡性和游玩").push("Gameplay");
            ALLOW_ORIGINAL_MATERIALS = builder.comment("启用TiAc的原创材料，这些材料会有偏高的强度，默认true。")
                    .comment("Toggle original materials (Slightly OP), true by default.")
                    .define("AllowOriginalMaterials",true);
            builder.pop();
        }
    }
    public static class Client{
        public Client(ForgeConfigSpec.Builder builder){

        }
    }
}
