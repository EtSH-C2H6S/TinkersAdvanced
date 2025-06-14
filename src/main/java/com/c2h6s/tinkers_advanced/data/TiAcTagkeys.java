package com.c2h6s.tinkers_advanced.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;


public class TiAcTagkeys {
    public static class Fluids {
        private static TagKey<Fluid> forgeTag(String name){
            return TagKey.create(ForgeRegistries.FLUIDS.getRegistryKey(),new ResourceLocation("forge",name));
        }

        public static final TagKey<Fluid> MOLTEN_ANTIMATTER = forgeTag("molten_antimatter");
        public static final TagKey<Fluid> MOLTEN_BISMUTH = forgeTag("molten_bismuth");
        public static final TagKey<Fluid> MOLTEN_IRIDIUM = forgeTag("molten_iridium");
        public static final TagKey<Fluid> MOLTEN_ANTIMONY = forgeTag("molten_antimony");
        public static final TagKey<Fluid> MOLTEN_VOID_STEEL = forgeTag("molten_void_steel");
    }

    public static class Items{
        private static TagKey<Item> forgeTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation("forge",name));
        }
        private static TagKey<Item> tiacTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation(TinkersAdvanced.MODID,name));
        }

        public static final TagKey<Item> BISMUTH_INGOT = forgeTag("ingots/bismuth");
        public static final TagKey<Item> BISMUTH_NUGGET = forgeTag("nuggets/bismuth");
        public static final TagKey<Item> BISMUTH_ORE = forgeTag("ores/bismuthinite");
        public static final TagKey<Item> IRIDIUM_INGOT = forgeTag("ingots/iridium");
        public static final TagKey<Item> ANTIMONY_INGOT = forgeTag("ingots/antimony");
        public static final TagKey<Item> ANTIMONY_NUGGET = forgeTag("nuggets/antimony");
        public static final TagKey<Item> STIBNITE_ORE = forgeTag("ores/stibnite");
        public static final TagKey<Item> IRIDIUM_NUGGET = forgeTag("nuggets/iridium");
        public static final TagKey<Item> IRIDIUM_BLOCK = forgeTag("storage_blocks/iridium");
        public static final TagKey<Item> PLASTIC = tiacTag("plastic");
    }

    public static class Modifiers{
        private static TagKey<Modifier> tiacTag(String name){
            return ModifierManager.getTag(new ResourceLocation(TinkersAdvanced.MODID,name));
        }

        public static final TagKey<Modifier> GENERATOR_MODIFIERS = tiacTag("generator_modifiers");
        public static final TagKey<Modifier> SPECIAL_TOOL = tiacTag("special_tool");
    }
}
