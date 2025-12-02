package com.c2h6s.tinkers_advanced.tools.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;


public class TiAcTTagkeys {
    public static class Fluids {
        private static TagKey<Fluid> forgeTag(String name){
            return TagKey.create(ForgeRegistries.FLUIDS.getRegistryKey(),new ResourceLocation("forge",name));
        }
    }

    public static class Items{
        private static TagKey<Item> forgeTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation("forge",name));
        }
        private static TagKey<Item> tiacTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation(TinkersAdvanced.MODID,name));
        }
    }

    public static class Modifiers{
        private static TagKey<Modifier> tiacTag(String name){
            return ModifierManager.getTag(new ResourceLocation(TinkersAdvanced.MODID,name));
        }

        public static final TagKey<Modifier> GENERATOR_MODIFIERS = tiacTag("generator_modifiers");
        public static final TagKey<Modifier> SPECIAL_TOOL = tiacTag("special_tool");
    }
}
