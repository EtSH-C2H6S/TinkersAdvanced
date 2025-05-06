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
        public final ForgeConfigSpec.DoubleValue MATTER_MANIPULATOR_BASE_RANGE;
        public final ForgeConfigSpec.DoubleValue MATTER_MANIPULATOR_CAPACITY_FACTOR;
        public final ForgeConfigSpec.DoubleValue MATTER_MANIPULATOR_FLUID_EFFICIENCY;
        public final ForgeConfigSpec.BooleanValue MATTER_MANIPULATOR_FLUID_ENCHANTING;
        public final ForgeConfigSpec.BooleanValue EXPLODING_FUSION_REACTOR;
        public final ForgeConfigSpec.DoubleValue IONIZED_CANNON_FLUID_FACTOR;
        public final ForgeConfigSpec.DoubleValue IONIZED_CANNON_DAMAGE_BONUS;
        public final ForgeConfigSpec.DoubleValue IONIZED_CANNON_BASE_RANGE;
        public final ForgeConfigSpec.DoubleValue IONIZED_CANNON_BASE_SCALE;
        public final ForgeConfigSpec.DoubleValue IONIZED_CANNON_BASE_FLUID_EFFICIENCY;
        public final ForgeConfigSpec.IntValue IONIZED_CANNON_BASE_CHARGE_TIME;
        public final ForgeConfigSpec.IntValue SHAPING_MAX_SLOT;
        public final ForgeConfigSpec.IntValue SHAPING_DAMAGES_EACH_SLOT;
        public final ForgeConfigSpec.IntValue PROTO_REFINING_BONUS_LEVEL;
        public final ForgeConfigSpec.IntValue PROTO_REFINING_TIMES_REQUIRED;
        public final ForgeConfigSpec.DoubleValue IRRADIUM_RADIATION_INFLICT;
        public final ForgeConfigSpec.DoubleValue IRRADIUM_MAX_BONUS;
        public final ForgeConfigSpec.DoubleValue IRRADIUM_BONUS_PER_Sv;
        public final ForgeConfigSpec.DoubleValue FRAGILE_CHANCE;
        public final ForgeConfigSpec.IntValue FRAGILE_EXTRA_COST;
        public final ForgeConfigSpec.DoubleValue REACTIVE_EXPLOSIVE_ARMOR_REDUCTION;
        public final ForgeConfigSpec.BooleanValue REACTIVE_EXPLOSIVE_ARMOR_IMMUNITY;

        public Common(ForgeConfigSpec.Builder builder){
            builder.comment("Modifiers behaviour").push("modifier_behaviour");

            builder.comment("Shaping");
            this.SHAPING_MAX_SLOT = builder.comment("Max upgrade slot bonus for Shaping modifier, 3 by default.")
                    .defineInRange("shaping_max_slot",3,1,1000);
            this.SHAPING_DAMAGES_EACH_SLOT = builder.comment("How many durability loss is needed for Shaping modifier to gain 1 slot, 500 by default.")
                    .defineInRange("shaping_damages_each_slot",500,1,1000000);

            builder.comment("Proto Refining");
            this.PROTO_REFINING_BONUS_LEVEL = builder.comment("Bonus enchantment level each trait level for Proto Refining, 2 by default.")
                    .defineInRange("proto_refining_bonus",2,0,1000);
            this.PROTO_REFINING_TIMES_REQUIRED = builder.comment("How many times needed for Proto Refining to gain bonus, 10 by default.")
                    .defineInRange("proto_refining_requirement",10,1,100000);

            builder.comment("Radiation Burning and Radioactive Armor");
            this.IRRADIUM_MAX_BONUS = builder.comment("Max bonus for Radiation Burning and Radioactive Armor, 0.75 by default.")
                    .defineInRange("irradium_max_bonus",0.75,0,1);
            this.IRRADIUM_RADIATION_INFLICT = builder.comment("Radiation amount for Radiation Burning and Radioactive Armor each trait level, 1.0 Sv by default.")
                    .defineInRange("irradium_radiation_inflict",1d,0,Double.MAX_VALUE);
            this.IRRADIUM_BONUS_PER_Sv = builder.comment("Bonus for Radiation Burning and Radioactive Armor each Sv, 0.05 by default.")
                    .defineInRange("irradium_bonus_per_sv",0.05d,0,1);

            builder.comment("Fragile");
            this.FRAGILE_CHANCE = builder.comment("Chance for extra durability draw for Fragile Modifier, 0.1 by default.")
                    .defineInRange("fragile_chance",0.1d,0,1);
            this.FRAGILE_EXTRA_COST = builder.comment("Extra durability cost for Fragile, 1 by default.")
                    .defineInRange("fragile_cost",1,0,Integer.MAX_VALUE);

            builder.comment("Reactive Explosive Armor");
            this.REACTIVE_EXPLOSIVE_ARMOR_IMMUNITY = builder.comment("Allows Reactive Explosive Armor Modifier to block explosion and fire damage, true by default.")
                    .define("reactive_explosive_armor_immunity",true);
            this.REACTIVE_EXPLOSIVE_ARMOR_REDUCTION = builder.comment("Damage Reduction for Reactive Explosive Armor, 0.25 by default.")
                    .defineInRange("reactive_explosive_armor_reduction",0.25d,0,1);

            builder.pop();
            builder.comment("Matter Manipulator behaviour").push("matter_manipulator");
            this.MATTER_MANIPULATOR_BASE_BOOST = builder.comment("Base mining speed multiplier of Matter Manipulator, 1.0 by default.")
                    .defineInRange("matter_manipulator_base_multiplier",1.0,0.0,1000);
            this.MATTER_MANIPULATOR_AOE_SPEED = builder.comment("Base mining speed multiplier of Matter Manipulator when is conducting AOE mining, 0.5 by default.")
                    .defineInRange("matter_manipulator_aoe_base_multiplier",0.5,0.0,1000);
            this.MATTER_MANIPULATOR_FLUID_BOOST = builder.comment("Mining fluid bonus multiplier of Matter Manipulator , 0.5 by default , meaning that each 200 °C adds 1 mining speed.")
                    .defineInRange("matter_manipulator_fluid_bonus_multiplier",0.5,0.0,1000);
            this.MATTER_MANIPULATOR_FLUID_ENCHANTING = builder.comment("Allows Matter Manipulator to gain enchantment boost from fluids in mining mode (e.g. Gain silk touch from molten emerald). true by default.")
                    .define("matter_manipulator_fluid_enchanting",true);
            this.MATTER_MANIPULATOR_BASE_RANGE = builder.comment("Base mining range for Matter Manipulator, 16.0 by default.")
                    .defineInRange("matter_manipulator_base_range",16.0,0.0,64);
            this.MATTER_MANIPULATOR_CAPACITY_FACTOR = builder.comment("Capacity factor for Matter Manipulator, 10.0 by default.")
                    .defineInRange("ionized_cannon_base_capacity_factor",10.0,0.0,1000);
            this.MATTER_MANIPULATOR_FLUID_EFFICIENCY = builder.comment("Base fluid efficiency for Matter Manipulator, 1.0 by default.")
                    .defineInRange("ionized_cannon_base_fluid_efficiency",0.0,0.0,1000);
            builder.pop();

            builder.comment("Ionized Cannon behaviour").push("ionized_cannon");
            this.IONIZED_CANNON_FLUID_FACTOR = builder.comment("The multiplier of fluid consumption of Ionized Cannon, 1.0 by default.")
                    .defineInRange("ionized_cannon_fluid_factor",1.0,0.0,1000);
            this.IONIZED_CANNON_DAMAGE_BONUS = builder.comment("Damage multiplier for Ionized Cannon when using correct fluids, 4.0 by default.")
                    .defineInRange("ionized_cannon_damage_bonus",4.0,1.0,1000);
            this.IONIZED_CANNON_BASE_FLUID_EFFICIENCY = builder.comment("Base fluid efficiency for Ionized Cannon, 0.0 by default.")
                    .defineInRange("ionized_cannon_base_fluid_efficiency",0.0,0.0,1000);
            this.IONIZED_CANNON_BASE_SCALE = builder.comment("Base explosion scale efficiency for Ionized Cannon, 1.0 by default.")
                    .defineInRange("ionized_cannon_base_scale",1.0,0.0,8);
            this.IONIZED_CANNON_BASE_RANGE = builder.comment("Base attack range for Ionized Cannon, 12.0 by default.")
                    .defineInRange("ionized_cannon_base_range",12.0,0.0,64);
            this.IONIZED_CANNON_BASE_CHARGE_TIME = builder.comment("Base duration for Ionized Cannon to charge, 40 ticks by default.")
                    .defineInRange("ionized_cannon_base_duration",40,0,2147483647);
            builder.pop();

            builder.comment("Mekanism Interaction").push("mekanism_interaction");
            this.EXPLODING_FUSION_REACTOR = builder.comment("Enables fusion reactor exploding when throwing iron ingots into it. true by default.")
                    .define("allow_neutronite_crafting",true);
            builder.pop();
        }
    }
    public static class Client{
        public Client(ForgeConfigSpec.Builder builder){

        }
    }
}




