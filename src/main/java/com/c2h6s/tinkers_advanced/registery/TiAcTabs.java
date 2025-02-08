package com.c2h6s.tinkers_advanced.registery;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> MATERIAL_TAB = CREATIVE_MODE_TABS.register("tiac_material", () -> CreativeModeTab.builder()
            .icon(() -> TiAcItems.BISMUTH_INGOT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> object:TiAcItems.LIST_MATERIAL){
                    output.accept(object.get());
                }
            }).build());
    public static final RegistryObject<CreativeModeTab> BLOCK_TAB = CREATIVE_MODE_TABS.register("tiac_block", () -> CreativeModeTab.builder()
            .icon(() -> TiAcItems.BISMUTHINITE_ORE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> object:TiAcItems.LIST_SIMPLE_BLOCK){
                    output.accept(object.get());
                }
            }).build());
}
