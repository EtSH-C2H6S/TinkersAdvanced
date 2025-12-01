package com.c2h6s.tinkers_advanced.core.util;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class CommonConstants {
    public static final ResourceLocation KEY_LAST_TICK_1 = TinkersAdvanced.getLocation("last_tick_1");

    public static class Tags{
        private static TagKey<Item> forgeTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation("forge",name));
        }
        public static final TagKey<Item> WRENCH = forgeTag("tools/wrench");
    }
    public static class Materials{
        public static final int ORDER_COMPAT = 5;
        public static final int ORDER_ORIGINAL = 30;
    }
}
