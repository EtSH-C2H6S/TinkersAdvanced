package com.c2h6s.tinkers_advanced.data;

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
    }

    public static class Items{
        private static TagKey<Item> forgeTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation("forge",name));
        }

        public static final TagKey<Item> BISMUTH_INGOT = forgeTag("ingots/bismuth");
        public static final TagKey<Item> BISMUTH_ORE = forgeTag("ores/bismuthinite");
    }
}
