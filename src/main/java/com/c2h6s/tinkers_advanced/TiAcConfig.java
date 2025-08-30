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
        public final ForgeConfigSpec.IntValue ELECTRON_TUNER_CONSUMPTION;
        public final ForgeConfigSpec.IntValue SMELTERY_GENERATOR_BASIC_GENERATION;
        public final ForgeConfigSpec.IntValue SMELTERY_GENERATOR_EACH_BURNING_TIME;
        public final ForgeConfigSpec.DoubleValue SMELTERY_GENERATOR_TEMPERATURE_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue ANNIHILATE_EXPLOSION_ATTACK_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue ANNIHILATE_EXPLOSION_SELF_MULTIPLIER;
        public final ForgeConfigSpec.IntValue COMPRESSED_AIR_GENERATON_BASIC_GENERATION;
        public final ForgeConfigSpec.IntValue DISINTEGRATE_EACH_BONUS;
        public final ForgeConfigSpec.IntValue DISINTEGRATE_MAX_BONUS;
        public final ForgeConfigSpec.IntValue DISINTEGRATE_EACH_DECREASE;
        public final ForgeConfigSpec.DoubleValue VOID_DODGING_CHANCE;

        public final ForgeConfigSpec.DoubleValue EFFECT_TETANUS_DAMAGE_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue EFFECT_PROTO_POISON_HEALTH_DECREASE;
        public final ForgeConfigSpec.DoubleValue EFFECT_PROTO_POISON_REGENERATION_DECREASE;
        public final ForgeConfigSpec.DoubleValue EFFECT_PLAGUE_DAMAGE;
        public final ForgeConfigSpec.DoubleValue EFFECT_PLAGUE_REGENERATION_DECREASE;

        public final ForgeConfigSpec.BooleanValue ALLOW_BISMUTHINITE;
        public final ForgeConfigSpec.BooleanValue ALLOW_STIBNITE;
        public final ForgeConfigSpec.BooleanValue ALLOW_LEAN_IRIDIUM;
        public final ForgeConfigSpec.BooleanValue ALLOW_STIBNITE_UNSTABLE;

//        public final ForgeConfigSpec.BooleanValue ALLOW_IONIZED_CANNON;
//        public final ForgeConfigSpec.BooleanValue ALLOW_MATTER_MANIPULATOR;
//        public final ForgeConfigSpec.BooleanValue ALLOW_ELECTRON_TUNER;

        public Common(ForgeConfigSpec.Builder builder){
            builder.comment("Worldgen").comment("世界生成").push("worldgen");
            this.ALLOW_BISMUTHINITE = builder.comment("Allows Bismuthinite generation, true by default.").comment("允许辉铋矿生成，默认是。")
                    .define("allow_bismuthinite",true);
            this.ALLOW_STIBNITE = builder.comment("Allows Stibnite generation, true by default.").comment("允许辉锑矿生成，默认是。")
                    .define("allow_stibnite",true);
            this.ALLOW_STIBNITE_UNSTABLE = builder.comment("Allows Unstable Stibnite generation when mining Stibnite, true by default.").comment("允许挖掘辉锑矿时使周围的辉锑矿在稳定和不稳定之间切换，默认是。")
                    .define("allow_stibnite_unstable",true);
            this.ALLOW_LEAN_IRIDIUM = builder.comment("Allows Iridium generation, true by default.").comment("允许贫铱矿生成，默认是。")
                    .define("allow_iridium",true);
            builder.pop();

            builder.comment("Modifiers behaviour").comment("词条性质").push("modifier_behaviour");
            builder.comment("Shaping").comment("塑性");
            this.SHAPING_MAX_SLOT = builder.comment("Max upgrade slot bonus for Shaping modifier, 3 by default.").comment("最大加槽，默认3。")
                    .defineInRange("shaping_max_slot",3,1,Integer.MAX_VALUE);
            this.SHAPING_DAMAGES_EACH_SLOT = builder.comment("How many durability loss is needed for Shaping modifier to gain 1 slot, 500 by default.").comment("磨损多少耐久才加1升级槽，默认500。")
                    .defineInRange("shaping_damages_each_slot",500,1,Integer.MAX_VALUE);

            builder.comment("Proto Refining").comment("原体精炼");
            this.PROTO_REFINING_BONUS_LEVEL = builder.comment("Bonus enchantment level each trait level for Proto Refining, 3 by default.").comment("每级最大时运增益，默认3。")
                    .defineInRange("proto_refining_bonus",3,0,Integer.MAX_VALUE);
            this.PROTO_REFINING_TIMES_REQUIRED = builder.comment("How many times needed for Proto Refining to gain bonus, 5 by default.").comment("挖多少次增加1级时运，默认5。")
                    .defineInRange("proto_refining_requirement",5,1,Integer.MAX_VALUE);

            builder.comment("Radiation Burning and Radioactive Armor").comment("镭光合金相关");
            this.IRRADIUM_MAX_BONUS = builder.comment("Max bonus for Radiation Burning and Radioactive Armor, 0.75 by default.").comment("最大增益，默认0.75。")
                    .defineInRange("irradium_max_bonus",0.75,0,1);
            this.IRRADIUM_RADIATION_INFLICT = builder.comment("Radiation amount for Radiation Burning and Radioactive Armor each trait level, 1.0 Sv by default.").comment("辐射施加，默认1.0Sv。")
                    .defineInRange("irradium_radiation_inflict",1d,0,Double.MAX_VALUE);
            this.IRRADIUM_BONUS_PER_Sv = builder.comment("Bonus for Radiation Burning and Radioactive Armor each Sv, 0.05 by default.").comment("每Sv辐射造成的增幅，默认0.05。")
                    .defineInRange("irradium_bonus_per_sv",0.05d,0,1);

            builder.comment("Fragile").comment("脆性");
            this.FRAGILE_CHANCE = builder.comment("Chance for extra durability draw for Fragile Modifier, 0.1 by default.").comment("脆性额外消耗耐久的概率，默认0.1。")
                    .defineInRange("fragile_chance",0.1d,0,1);
            this.FRAGILE_EXTRA_COST = builder.comment("Extra durability cost for Fragile, 1 by default.").comment("额外消耗的耐久，默认1。")
                    .defineInRange("fragile_cost",1,0,Integer.MAX_VALUE);

            builder.comment("Reactive Explosive Armor");
            this.REACTIVE_EXPLOSIVE_ARMOR_IMMUNITY = builder.comment("Allows Reactive Explosive Armor Modifier to block explosion and fire damage, true by default.")
                    .define("reactive_explosive_armor_immunity",true);
            this.REACTIVE_EXPLOSIVE_ARMOR_REDUCTION = builder.comment("Damage Reduction for Reactive Explosive Armor, 0.25 by default.")
                    .defineInRange("reactive_explosive_armor_reduction",0.25d,0,1);

            builder.comment("Nutritive Slime").comment("营养黏液");
            this.NUTRITIVE_SLIME_COST = builder.comment("Overslime consumption for each food level, 20 by default.")
                    .comment("每点饱食度需要的黏液覆层消耗量，默认20。")
                    .defineInRange("nutritive_slime_cost",20,0,Integer.MAX_VALUE);
            this.NUTRITIVE_SLIME_RECOVER = builder.comment("Food level to add each trait level, 1 by default.")
                    .comment("每级一次性加的饱食度，默认1。")
                    .defineInRange("nutritive_slime_recover",1,0,Integer.MAX_VALUE);

            builder.comment("Return to Slime").comment("万物归于黏液");
            this.RETURN_TO_SLIME_CHANCE = builder.comment("Overslime recover chance for Return to Slime each trait level, 20% by default.")
                    .comment("恢复黏液覆层的概率，默认20%")
                    .defineInRange("return_to_slime_chance",0.2,0,1d);

            builder.comment("Annihilation").comment("湮灭");
            this.ANNIHILATE_EXPLOSION_ATTACK_MULTIPLIER = builder.comment("Explosion damage multiplier for Annihilation Modifier, 5 by default.")
                    .comment("湮灭词条造成的爆炸伤害倍率，默认5倍。")
                    .defineInRange("annihilation_attack_multiplier",5d,0,Integer.MAX_VALUE);
            this.ANNIHILATE_EXPLOSION_SELF_MULTIPLIER = builder.comment("Explosion damage multiplier that deals to attacker, 2.5 by default.")
                    .comment("湮灭词条对自己造成的爆炸伤害倍率，默认2.5倍")
                    .defineInRange("annihilation_attack_self_multiplier",2.5d,0,Integer.MAX_VALUE);
            builder.comment("Disintegrate").comment("解离");
            this.DISINTEGRATE_EACH_BONUS = builder.comment("Bonus each block mined (%), 20(%) by default")
                    .comment("挖掘每个方块带来的加成（单位为%），默认20%。注：耐久惩罚是加成的一半。")
                    .defineInRange("disintegrate_each_bonus",20,0,Integer.MAX_VALUE);
            this.DISINTEGRATE_MAX_BONUS = builder.comment("Max bonus, 2000% by default.")
                    .comment("最大加成（单位为%），默认2000%。")
                    .defineInRange("disintegrate_max_bonus",2000,0,Integer.MAX_VALUE);
            this.DISINTEGRATE_EACH_DECREASE = builder.comment("Clear bonus after ticks of stopping, 5s by default.")
                    .comment("停止挖掘多久后清除加成，默认5秒。")
                    .defineInRange("disintegrate_each_decrease",5,0,Integer.MAX_VALUE);
            builder.comment("Void Dodging").comment("虚无闪避");
            this.VOID_DODGING_CHANCE = builder.comment("Chance for dodging arrow each level, 0.25 by default.")
                    .comment("每级闪避箭矢的概率，默认0.25。")
                    .defineInRange("void_dodging_chance",0.25,0,1);

            builder.comment("Return to Slime").comment("万物归于黏液");

            builder.comment("Generator Modules").comment("能量模块类强化");

            this.COMBUSTION_GENERATOR_BASIC_GENERATION = builder.comment("Basic FE/t for each level of Combustion Generator Module, 200 by default")
                    .comment("燃烧室能量模块的基础每tick产能，默认200FE/t。")
                    .defineInRange("combustion_generator_generation",200,0,Integer.MAX_VALUE);
            this.COMBUSTION_GENERATOR_GENERATION_EACH_BURNING_TIME = builder.comment("Energy per burning time, affects the total FE generated by fuel item. 10 by default, in this case a single coal can produce 16.00 kFE.")
                    .comment("燃料每tick燃烧时间带来的总产能，默认10FE。注：烧炼一个物品需要200 ticks，一个煤炭能烧8个物品也就是1600 ticks，这代表默认配置下一个煤炭能带来16.0kFE的总产能。")
                    .defineInRange("combustion_generator_total_factor",10,0,Integer.MAX_VALUE);

            this.OVERSLIME_GENERATOR_BASIC_GENERATION = builder.comment("Basic FE/t for each level of Overslime Generator Module, 250 by default")
                    .comment("凝胶电化学能量模块的基础每tick产出，默认250FE/t")
                    .defineInRange("overslime_generator_generation",250,0,Integer.MAX_VALUE);
            this.OVERSLIME_GENERATOR_GENERATION_EACH_OVERSLIME = builder.comment("Energy per overslime, 1000 by default.")
                    .comment("每点黏液产生的能量，默认1kFE。")
                    .defineInRange("overslime_generator_total_factor",1000,0,Integer.MAX_VALUE);

            this.PIEZOELECTRIC_EFFECT_BASIC_GENERATION = builder.comment("Basic FE/t for each level of Piezoelectric Effect modifier, 100 by default")
                    .comment("压电效应的基础每tick产能，默认100FE/t。")
                    .defineInRange("piezoelectric_effect_generation",100,0,Integer.MAX_VALUE);
            this.PIEZOELECTRIC_EFFECT_GENERATION_EACH_DAMAGE = builder.comment("Energy per damage each level, 10 by default.")
                    .comment("每级每点伤害产生的能量，默认10FE。")
                    .defineInRange("piezoelectric_effect_generation_per_damage",10,0,Integer.MAX_VALUE);

            this.ELECTRIC_FOOD_BASIC_CONSUMPTION = builder.comment("Basic FE/t for each level of Electric Food modifier, 100 by default")
                    .comment("充电饱的基础每tick消耗，默认100FE/t。")
                    .defineInRange("electric_food_consumption",100,0,Integer.MAX_VALUE);
            this.ELECTRIC_FOOD_CONSUMPTION_EACH_FOODLEVEL = builder.comment("Energy per food level, 25kFE by default.")
                    .comment("充电饱恢复1饱食度需要的能量，默认25kFE。")
                    .defineInRange("electric_food_total_consumption",25000,0,Integer.MAX_VALUE);

            this.TRANSITION_CATALYST_BONUS = builder.comment("Bonus for Transition Catalyst each trait level, 25% by default.")
                    .comment("过渡金属催化剂的增幅效果，默认25%。")
                    .defineInRange("transition_catalyst_bonus",0.25,0,Integer.MAX_VALUE);
            this.PLATINOID_CATALYST_BONUS = builder.comment("Bonus for Platinoid Catalyst each trait level, 25% by default.")
                    .comment("铂族催化剂的增幅效果，默认25%。")
                    .defineInRange("platinoid_catalyst_bonus",0.25,0,Integer.MAX_VALUE);

            this.SMELTERY_GENERATOR_BASIC_GENERATION = builder.comment("Basic FE/t for each level of Smeltery Generator modifier, 300 by default")
                    .comment("冶炼炉能量模块基础的每tick能量产出。默认300 FE/t。")
                    .defineInRange("smeltery_generator_generation",300,0,Integer.MAX_VALUE);
            this.SMELTERY_GENERATOR_EACH_BURNING_TIME = builder.comment("Energy per burning time, 10000 by default.")
                    .comment("每tick燃烧时长带来的总能量产出。默认10kFE。")
                    .defineInRange("smeltery_generator_generation_for_time",10000,0,Integer.MAX_VALUE);
            this.SMELTERY_GENERATOR_TEMPERATURE_MULTIPLIER = builder.comment("Power multiplier for fuel temp, 0.001 by default , meaning that a 2000℃ fuel makes FE/t times 2.")
                    .comment("燃料温度对产能速率的影响。默认0.001，意味着2000℃的燃料会带来2倍的产能速度。")
                    .defineInRange("smeltery_generator_temperature_multiplier",0.001d,0,Integer.MAX_VALUE);

            this.COMPRESSED_AIR_GENERATON_BASIC_GENERATION = builder.comment("Basic FE/t for each level of Compressed Air Generator modifier, 200 by default")
                    .comment("气动能量模块基础的每tick能量产出，默认50FE/t。注：最终产能会乘以工具压力。")
                    .defineInRange("compressed_air_generator_generation",50,0,Integer.MAX_VALUE);

            builder.pop();

            builder.comment("Effects").comment("状态效果行为").push("mob_effect");
            this.EFFECT_TETANUS_DAMAGE_MULTIPLIER = builder
                    .comment("Damage multiplier for Tetanus, 1.2 by default.")
                    .comment("破伤风效果导致的受击伤害倍率。默认1.2。")
                    .defineInRange("tetanus_damage_boost",1.2,0,Integer.MAX_VALUE);

            this.EFFECT_PROTO_POISON_HEALTH_DECREASE = builder
                    .comment("Permanent health decrease for Proto poison, 25% by default.")
                    .comment("原毒效果的永久生命上限减少，默认25%。")
                    .defineInRange("proto_poison_health_decrease",0.25,0,Integer.MAX_VALUE);
            this.EFFECT_PROTO_POISON_REGENERATION_DECREASE = builder
                    .comment("Regeneration reduction for Proto Poison each level, 20% by default")
                    .comment("原毒效果的每级生命再生减少，默认20%。")
                    .defineInRange("proto_poison_regen_decrease",0.2,0,Integer.MAX_VALUE);

            this.EFFECT_PLAGUE_DAMAGE = builder
                    .comment("Damage for Plague each level, 2 by default.")
                    .comment("疫毒效果的伤害，默认2。")
                    .defineInRange("plague_health_decrease",2d,0,Integer.MAX_VALUE);
            this.EFFECT_PLAGUE_REGENERATION_DECREASE = builder
                    .comment("Regeneration reduction for Plague each level, 50% by default")
                    .comment("疫毒效果的每级生命再生减少，默认50%。")
                    .defineInRange("plague_regen_decrease",0.5,0,Integer.MAX_VALUE);
            builder.pop();

            builder.comment("Tool Behaviour").comment("工具行为").push("tool_behaviour");

            builder.comment("Matter Manipulator behaviour").comment("物质操纵器");
//            this.ALLOW_MATTER_MANIPULATOR = builder.comment("Enable Matter Manipulator, true by default.").comment("启用物质操纵器，默认是。")
//                    .define("allow_matter_manipulator",true);
            this.MATTER_MANIPULATOR_BASE_BOOST = builder.comment("Base mining speed multiplier of Matter Manipulator, 1.0 by default.")
                    .comment("物质操纵器的定性挖掘效率倍率，默认1.0x。")
                    .defineInRange("matter_manipulator_base_multiplier",1.0,0.0,Integer.MAX_VALUE);
            this.MATTER_MANIPULATOR_AOE_SPEED = builder.comment("Base mining speed multiplier of Matter Manipulator when is conducting AOE mining, 0.5 by default.")
                    .comment("物质操纵器进行范围挖掘时的定性挖掘效率倍率，默认0.5x。")
                    .defineInRange("matter_manipulator_aoe_base_multiplier",0.5,0.0,Integer.MAX_VALUE);
            this.MATTER_MANIPULATOR_FLUID_BOOST = builder.comment("Mining fluid bonus multiplier of Matter Manipulator , 0.5 by default , meaning that each 200 °C adds 1 mining speed.")
                    .comment("流体温度对物质操纵器挖掘速度的定量效率增幅。默认0.5x，意味着挖掘液每200℃增加1挖掘速度。")
                    .defineInRange("matter_manipulator_fluid_bonus_multiplier",0.5,0.0,Integer.MAX_VALUE);
            this.MATTER_MANIPULATOR_FLUID_ENCHANTING = builder.comment("Allows Matter Manipulator to gain enchantment boost from fluids in mining mode (e.g. Gain silk touch from molten emerald). true by default.")
                    .comment("允许挖掘模式下的物质操纵器从流体效果中获得附魔等级（比如熔融钻石的时运III），默认true。")
                    .define("matter_manipulator_fluid_enchanting",true);
            this.MATTER_MANIPULATOR_BASE_RANGE = builder.comment("Base mining range for Matter Manipulator, 16.0 by default.")
                    .comment("物质操纵器的基础挖掘距离，默认16.0格。")
                    .defineInRange("matter_manipulator_base_range",16.0,0.0,64);
            this.MATTER_MANIPULATOR_CAPACITY_FACTOR = builder.comment("Capacity factor for Matter Manipulator, 10.0 by default.")
                    .comment("物质操纵器的流体容量倍率，默认10.0x。")
                    .defineInRange("matter_manipulator_base_capacity_factor",10.0,0.0,Integer.MAX_VALUE);
            this.MATTER_MANIPULATOR_FLUID_EFFICIENCY = builder.comment("Base fluid efficiency for Matter Manipulator, 1.0 by default.")
                    .comment("物质操纵器的基础流体效率，默认0.0。")
                    .defineInRange("matter_manipulator_base_fluid_efficiency",0.0,0.0,Integer.MAX_VALUE);

            builder.comment("Ionized Cannon behaviour").comment("等离子射线炮");
//            this.ALLOW_IONIZED_CANNON = builder.comment("Enable Ionized Cannon, true by default.").comment("启用等离子射线炮，默认是。")
//                    .define("allow_ionized_cannon",true);
            this.IONIZED_CANNON_FLUID_FACTOR = builder.comment("The multiplier of fluid consumption of Ionized Cannon, 1.0 by default.")
                    .comment("等离子射线炮的定性流体消耗倍率，默认1.0x。")
                    .defineInRange("ionized_cannon_fluid_factor",1.0,0.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_DAMAGE_BONUS = builder.comment("Damage multiplier for Ionized Cannon when using correct fluids, 4.0 by default.")
                    .comment("当有合适流体时等离子射线炮的伤害倍率，默认4.0x。")
                    .defineInRange("ionized_cannon_damage_bonus",4.0,1.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_BASE_FLUID_EFFICIENCY = builder.comment("Base fluid efficiency for Ionized Cannon, 0.0 by default.")
                    .comment("等离子射线炮的基础流体效率，默认0.0。")
                    .defineInRange("ionized_cannon_base_fluid_efficiency",0.0,0.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_BASE_SCALE = builder.comment("Base explosion scale efficiency for Ionized Cannon, 1.0 by default.")
                    .comment("等离子射线炮的基础效果范围，默认1.0。")
                    .defineInRange("ionized_cannon_base_scale",1.0,0.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_BASE_RANGE = builder.comment("Base attack range for Ionized Cannon, 12.0 by default.")
                    .comment("等离子射线炮的基础攻击距离，默认12.0格。")
                    .defineInRange("ionized_cannon_base_range",12.0,0.0,Integer.MAX_VALUE);
            this.IONIZED_CANNON_BASE_CHARGE_TIME = builder.comment("Base duration for Ionized Cannon to charge, 40 ticks by default.")
                    .comment("等离子射线炮的基础蓄力时间，默认40 ticks。最终蓄力时间会除以攻速。")
                    .defineInRange("ionized_cannon_base_duration",40,0,Integer.MAX_VALUE);

            builder.comment("Electron Tuner behaviour").comment("环流电子调谐剑");
//            this.ALLOW_ELECTRON_TUNER = builder.comment("Enable Electron Tuner, true by default.").comment("启用环流电子调谐剑，默认是。")
//                    .define("allow_electron_tuner",true);
            this.ELECTRON_TUNER_ATTACK_DAMAGE_ADJUSTABLE_RANGE = builder.comment("The adjustable range of attack damage for Electron Tuner, 1.5 by default, meaning that the damage can be adjusted within the range of ±0.75 .Note that adjusting this without adjusting electron_tuner_attack_speed_range will greatly affect balance.")
                    .comment("环流电子调谐剑的伤害调节范围。默认1.5，意味着滑动调节滑块最大能对伤害产生 1/1.75 x ~ 1.75 x 的变化。仅调节此项会极大影响平衡，请考虑和攻速范围一同调整。")
                    .defineInRange("electron_tuner_damage_range",1.5,0.0,Integer.MAX_VALUE);
            this.ELECTRON_TUNER_ATTACK_SPEED_ADJUSTABLE_RANGE = builder.comment("The adjustable range of attack damage for Electron Tuner, 1.0 by default, meaning that the damage can be adjusted within the range of ±0.5 .Note that adjusting this without adjusting electron_tuner_damage_range will greatly affect balance.")
                    .comment("环流电子调谐剑的攻击速度调节范围。默认1.0，意味着滑动调节滑块最大能对攻速产生 1/1.5 x ~ 1.5 x 的变化。仅调节此项会极大影响平衡，请考虑和伤害范围一同调整。")
                    .defineInRange("electron_tuner_attack_speed_range",1,0.0,Integer.MAX_VALUE);
            this.ELECTRON_TUNER_SPECIAL_BONUS = builder.comment("Allow Electron Tuner to perform special attack , true by default.")
                    .comment("允许环流电子调谐剑造成特殊攻击（比如剑形态的弧光），默认true。")
                    .define("electron_tuner_special",true);
            this.ELECTRON_TUNER_CONSUMPTION = builder.comment("The basic energy consumption when attacking for Electron Tuner, 250 by default.")
                    .comment("环流电子调谐剑攻击的能耗，默认250FE。")
                    .defineInRange("electron_tuner_consumption",250,0,Integer.MAX_VALUE);
            builder.pop();

            builder.comment("Mekanism Interaction").comment("通用机械交互").push("mekanism_interaction");
            this.EXPLODING_FUSION_REACTOR = builder.comment("Enables fusion reactor exploding when throwing iron ingots into it. true by default.")
                    .comment("让你能够通过向朋友的聚变反应堆丢入铁锭/块/粒/砧来增进彼此的感情，默认是。")
                    .define("allow_neutronite_crafting",true);
            builder.pop();
        }
    }
    public static class Client{
        public Client(ForgeConfigSpec.Builder builder){

        }
    }
}




