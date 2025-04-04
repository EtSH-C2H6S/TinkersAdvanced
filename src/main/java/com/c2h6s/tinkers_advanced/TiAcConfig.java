package com.c2h6s.tinkers_advanced;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;


@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcConfig {

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
    public static final ForgeConfigSpec clientSpec;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, TiAcConfig.commonSpec);
    }
    public static class Common{
        public final ForgeConfigSpec.DoubleValue MATTER_MANIPULATOR_BASE_BOOST;
        public final ForgeConfigSpec.DoubleValue MATTER_MANIPULATOR_FLUID_BOOST;
        public final ForgeConfigSpec.DoubleValue MATTER_MANIPULATOR_AOE_SPEED;
        public Common(ForgeConfigSpec.Builder builder){
            builder.comment("Matter Manipulator behaviour").push("matter_manipulator");
            this.MATTER_MANIPULATOR_BASE_BOOST = builder.comment("Base mining speed multiplier of Matter Manipulator, 1.0 by default.")
                    .worldRestart()
                    .defineInRange("matter_manipulator_base_multiplier",1.0,0.0,100);
            this.MATTER_MANIPULATOR_AOE_SPEED = builder.comment("Base mining speed multiplier of Matter Manipulator when is conducting AOE mining, 0.5 by default.")
                    .worldRestart()
                    .defineInRange("matter_manipulator_aoe_base_multiplier",0.5,0.0,100);
            this.MATTER_MANIPULATOR_FLUID_BOOST = builder.comment("Mining fluid bonus multiplier of Matter Manipulator , 0.5 by default , meaning that each 200 °C adds 1 mining speed.")
                    .worldRestart()
                    .defineInRange("matter_manipulator_fluid_bonus_multiplier",0.5,0.0,100);
            builder.pop();
        }
    }
    public static class Client{
        public Client(ForgeConfigSpec.Builder builder){

        }
    }
}




