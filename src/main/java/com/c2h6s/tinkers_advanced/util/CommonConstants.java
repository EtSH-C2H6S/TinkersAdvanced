package com.c2h6s.tinkers_advanced.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class CommonConstants {
    public static final UUID PROTO_POISON_UUID = UUID.fromString("18038687-2500-7a0c-ba0c-744c441ad40e");

    public static class Tags{
        private static TagKey<Item> forgeTag(String name){
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation("forge",name));
        }
        public static final TagKey<Item> WRENCH = forgeTag("tools/wrench");
    }
}
