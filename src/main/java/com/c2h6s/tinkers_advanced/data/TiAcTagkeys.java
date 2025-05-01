package com.c2h6s.tinkers_advanced.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;


public class TiAcTagkeys {
    public static class Fluids {
        private static TagKey<Fluid> forgeTag(String name){
            return TagKey.create(ForgeRegistries.FLUIDS.getRegistryKey(),new ResourceLocation("forge",name));
        }

        public static final TagKey<Fluid> MOLTEN_ANTIMATTER = forgeTag("molten_antimatter");
        public static final TagKey<Fluid> MOLTEN_BISMUTH = forgeTag("molten_bismuth");
        public static final TagKey<Fluid> MOLTEN_IRIDIUM = forgeTag("molten_iridium");
    }

    public static class Items{
        private static TagKey<Item> forgeTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation("forge",name));
        }
        private static TagKey<Item> tiacTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation(TinkersAdvanced.MODID,name));
        }

        public static final TagKey<Item> BISMUTH_INGOT = forgeTag("ingots/bismuth");
        public static final TagKey<Item> BISMUTH_ORE = forgeTag("ores/bismuthinite");
        public static final TagKey<Item> IRIDIUM_INGOT = forgeTag("ingots/iridium");
        public static final TagKey<Item> IRIDIUM_NUGGET = forgeTag("nuggets/iridium");
        public static final TagKey<Item> IRIDIUM_BLOCK = forgeTag("storage_blocks/iridium");
        public static final TagKey<Item> PLASTIC = tiacTag("plastic");
    }
}
