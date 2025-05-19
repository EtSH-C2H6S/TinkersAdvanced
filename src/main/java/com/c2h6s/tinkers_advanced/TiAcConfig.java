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
        public final ForgeConfigSpec.IntValue NUTRITIVE_SLIME_COST;
        public final ForgeConfigSpec.IntValue NUTRITIVE_SLIME_RECOVER;
        public final ForgeConfigSpec.DoubleValue RETURN_TO_SLIME_CHANCE;
        public final ForgeConfigSpec.DoubleValue ELECTRON_TUNER_ATTACK_SPEED_ADJUSTABLE_RANGE;
        public final ForgeConfigSpec.DoubleValue ELECTRON_TUNER_ATTACK_DAMAGE_ADJUSTABLE_RANGE;
        public final ForgeConfigSpec.BooleanValue ELECTRON_TUNER_SPECIAL_BONUS;
        public final ForgeConfigSpec.IntValue COMBUSTION_GENERATOR_BASIC_GENERATION;
        public final ForgeConfigSpec.IntValue COMBUSTION_GENERATOR_GENERATION_EACH_BURNING_TIME;
        public final ForgeConfigSpec.IntValue OVERSLIME_GENERATOR_BASIC_GENERATION;
        public final ForgeConfigSpec.IntValue OVERSLIME_GENERATOR_GENERATION_EACH_OVERSLIME;
        public final ForgeConfigSpec.IntValue PIEZOELECTRIC_EFFECT_BASIC_GENERATION;
        public final ForgeConfigSpec.IntValue PIEZOELECTRIC_EFFECT_GENERATION_EACH_DAMAGE;
        public final ForgeConfigSpec.IntValue ELECTRIC_FOOD_BASIC_CONSUMPTION;
        public final ForgeConfigSpec.IntValue ELECTRIC_FOOD_CONSUMPTION_EACH_FOODLEVEL;
        public final ForgeConfigSpec.DoubleValue TRANSITION_CATALYST_BONUS;
        public final ForgeConfigSpec.DoubleValue PLATINOID_CATALYST_BONUS;

        public Common(ForgeConfigSpec.Builder builder){
            builder.comment("Modifiers behaviour").push("modifier_behaviour");

            builder.comment("Shaping");
            this.SHAPING_MAX_SLOT = builder.comment("Max upgrade slot bonus for Shaping modifier, 3 by default.")
                    .defineInRange("shaping_max_slot",3,1,Integer.MAX_VALUE);
            this.SHAPING_DAMAGES_EACH_SLOT = builder.comment("How many durability loss is needed for Shaping modifier to gain 1 slot, 500 by default.")
                    .defineInRange("shaping_damages_each_slot",500,1,Integer.MAX_VALUE);

            builder.comment("Proto Refining");
            this.PROTO_REFINING_BONUS_LEVEL = builder.comment("Bonus enchantment level each trait level for Proto Refining, 3 by default.")
                    .defineInRange("proto_refining_bonus",3,0,Integer.MAX_VALUE);
            this.PROTO_REFINING_TIMES_REQUIRED = builder.comment("How many times needed for Proto Refining to gain bonus, 5 by default.")
                    .defineInRange("proto_refining_requirement",5,1,Integer.MAX_VALUE);

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

            builder.comment("Nutritive Slime");
            this.NUTRITIVE_SLIME_COST = builder.comment("Overslime consumption for each food level, 20 by default.")
                    .defineInRange("nutritive_slime_cost",20,0,Integer.MAX_VALUE);
            this.NUTRITIVE_SLIME_RECOVER = builder.comment("Food level to add each trait level, 1 by default.")
                    .defineInRange("nutritive_slime_recover",1,0,Integer.MAX_VALUE);
            builder.comment("Return to Slime");
            this.RETURN_TO_SLIME_CHANCE = builder.comment("Overslime recover chance for Return to Slime each trait level, 20% by default.")
                    .defineInRange("return_to_slime_chance",0.2,0,1d);

            builder.comment("Generator Modules");

            this.COMBUSTION_GENERATOR_BASIC_GENERATION = builder.comment("Basic FE/t for each level of Combustion Generator Module, 200 by default")
                    .defineInRange("combustion_generator_generation",200,0,Integer.MAX_VALUE);
            this.COMBUSTION_GENERATOR_GENERATION_EACH_BURNING_TIME = builder.comment("Energy per burning time, affects the total FE generated by fuel item. 10 by default, in this case a single coal can produce 16.00 kFE.")
                    .defineInRange("combustion_generator_total_factor",10,0,Integer.MAX_VALUE);

            this.OVERSLIME_GENERATOR_BASIC_GENERATION = builder.comment("Basic FE/t for each level of Overslime Generator Module, 250 by default")
                    .defineInRange("overslime_generator_generation",250,0,Integer.MAX_VALUE);
            this.OVERSLIME_GENERATOR_GENERATION_EACH_OVERSLIME = builder.comment("Energy per overslime, 1000 by default.")
                    .defineInRange("overslime_generator_total_factor",1000,0,Integer.MAX_VALUE);

            this.PIEZOELECTRIC_EFFECT_BASIC_GENERATION = builder.comment("Basic FE/t for each level of Piezoelectric Effect modifier, 100 by default")
                    .defineInRange("piezoelectric_effect_generation",100,0,Integer.MAX_VALUE);
            this.PIEZOELECTRIC_EFFECT_GENERATION_EACH_DAMAGE = builder.comment("Energy per damage, 10 by default.")
                    .defineInRange("piezoelectric_effect_generation_per_damage",10,0,Integer.MAX_VALUE);

            this.ELECTRIC_FOOD_BASIC_CONSUMPTION = builder.comment("Basic FE/t for each level of Electric Food modifier, 100 by default")
                    .defineInRange("electric_food_consumption",100,0,Integer.MAX_VALUE);
            this.ELECTRIC_FOOD_CONSUMPTION_EACH_FOODLEVEL = builder.comment("Energy per food level, 25kFE by default.")
                    .defineInRange("electric_food_total_consumption",25000,0,Integer.MAX_VALUE);

            this.TRANSITION_CATALYST_BONUS = builder.comment("Bonus for Transition Catalyst each trait level, 25% by default.")
                    .defineInRange("transition_catalyst_bonus",0.25,0,Integer.MAX_VALUE);
            this.PLATINOID_CATALYST_BONUS = builder.comment("Bonus for Platinoid Catalyst each trait level, 25% by default.")
                    .defineInRange("platinoid_catalyst_bonus",0.25,0,Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Tool Behaviour").push("tool_behaviour");

            builder.comment("Matter Manipulator behaviour");
            this.MATTER_MANIPULATOR_BASE_BOOST = builder.comment("Base mining speed multiplier of Matter Manipulator, 1.0 by default.")
                    .defineInRange("matter_manipulator_base_multiplier",1.0,0.0,Integer.MAX_VALUE);
            this.MATTER_MANIPULATOR_AOE_SPEED = builder.comment("Base mining speed multiplier of Matter Manipulator when is conducting AOE mining, 0.5 by default.")
                    .defineInRange("matter_manipulator_aoe_base_multiplier",0.5,0.0,Integer.MAX_VALUE);
            this.MATTER_MANIPULATOR_FLUID_BOOST = builder.comment("Mining fluid bonus multiplier of Matter Manipulator , 0.5 by default , meaning that each 200 °C adds 1 mining speed.")
                    .defineInRange("matter_manipulator_fluid_bonus_multiplier",0.5,0.0,Integer.MAX_VALUE);
            this.MATTER_MANIPULATOR_FLUID_ENCHANTING = builder.comment("Allows Matter Manipulator to gain enchantment boost from fluids in mining mode (e.g. Gain silk touch from molten emerald). true by default.")
                    .define("matter_manipulator_fluid_enchanting",true);
            this.MATTER_MANIPULATOR_BASE_RANGE = builder.comment("Base mining range for Matter Manipulator, 16.0 by default.")
                    .defineInRange("matter_manipulator_base_range",16.0,0.0,64);
            this.MATTER_MANIPULATOR_CAPACITY_FACTOR = builder.comment("Capacity factor for Matter Manipulator, 10.0 by default.")
                    .defineInRange("ionized_cannon_base_capacity_factor",10.0,0.0,Integer.MAX_VALUE);
            this.MATTER_MANIPULATOR_FLUID_EFFICIENCY = builder.comment("Base fluid efficiency for Matter Manipulator, 1.0 by default.")
                    .defineInRange("ionized_cannon_base_fluid_efficiency",0.0,0.0,Integer.MAX_VALUE);

            builder.comment("Ionized Cannon behaviour");
            this.IONIZED_CANNON_FLUID_FACTOR = builder.comment("The multiplier of fluid consumption of Ionized Cannon, 1.0 by default.")
                    .defineInRange("ionized_cannon_fluid_factor",1.0,0.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_DAMAGE_BONUS = builder.comment("Damage multiplier for Ionized Cannon when using correct fluids, 4.0 by default.")
                    .defineInRange("ionized_cannon_damage_bonus",4.0,1.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_BASE_FLUID_EFFICIENCY = builder.comment("Base fluid efficiency for Ionized Cannon, 0.0 by default.")
                    .defineInRange("ionized_cannon_base_fluid_efficiency",0.0,0.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_BASE_SCALE = builder.comment("Base explosion scale efficiency for Ionized Cannon, 1.0 by default.")
                    .defineInRange("ionized_cannon_base_scale",1.0,0.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_BASE_RANGE = builder.comment("Base attack range for Ionized Cannon, 12.0 by default.")
                    .defineInRange("ionized_cannon_base_range",12.0,0.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_BASE_CHARGE_TIME = builder.comment("Base duration for Ionized Cannon to charge, 40 ticks by default.")
                    .defineInRange("ionized_cannon_base_duration",40,0,Integer.MAX_VALUE);

            builder.comment("Electron Tuner behaviour");
            this.ELECTRON_TUNER_ATTACK_DAMAGE_ADJUSTABLE_RANGE = builder.comment("The adjustable range of attack damage for Electron Tuner, 1.5 by default, meaning that the damage can be adjusted within the range of ±0.75 .Note that adjusting this without adjusting electron_tuner_attack_speed_range will greatly affect balance.")
                    .defineInRange("electron_tuner_damage_range",1.5,0.0,Integer.MAX_VALUE);
            this.ELECTRON_TUNER_ATTACK_SPEED_ADJUSTABLE_RANGE = builder.comment("The adjustable range of attack damage for Electron Tuner, 1.0 by default, meaning that the damage can be adjusted within the range of ±0.5 .Note that adjusting this without adjusting electron_tuner_damage_range will greatly affect balance.")
                    .defineInRange("electron_tuner_attack_speed_range",1,0.0,Integer.MAX_VALUE);
            this.ELECTRON_TUNER_SPECIAL_BONUS = builder.comment("Allow Electron Tuner to perform special attack , true by default.")
                    .define("electron_tuner_special",true);
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




