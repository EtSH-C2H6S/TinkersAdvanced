package com.c2h6s.tinkers_advanced.utilities.init;

import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.tinkers_advanced.core.init.TiAcCrItem.ITEMS;
import static com.c2h6s.tinkers_advanced.core.init.TiAcCrItem.registerUtilitiesBlockItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcUItems {
    public static final RegistryObject<BlockItem> EXCHANGER = registerUtilitiesBlockItem(ITEMS, TiAcUBlocks.EXCHANGER);
    public static final RegistryObject<BlockItem> ROSE_GOLD_FAUCET = registerUtilitiesBlockItem(ITEMS, TiAcUBlocks.ROSE_GOLD_FAUCET);
    public static final RegistryObject<BlockItem> HEPATIZON_FAUCET = registerUtilitiesBlockItem(ITEMS, TiAcUBlocks.HEPATIZON_FAUCET);
    public static final RegistryObject<BlockItem> ROSE_GOLD_TABLE = registerUtilitiesBlockItem(ITEMS, TiAcUBlocks.ROSE_GOLD_TABLE);
    public static final RegistryObject<BlockItem> ROSE_GOLD_BASIN = registerUtilitiesBlockItem(ITEMS, TiAcUBlocks.ROSE_GOLD_BASIN);
    public static final RegistryObject<BlockItem> HEPATIZON_TABLE = registerUtilitiesBlockItem(ITEMS, TiAcUBlocks.HEPATIZON_TABLE);
    public static final RegistryObject<BlockItem> HEPATIZON_BASIN = registerUtilitiesBlockItem(ITEMS, TiAcUBlocks.HEPATIZON_BASIN);

    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
}
