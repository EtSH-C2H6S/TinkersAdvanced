package com.c2h6s.tinkers_advanced.core.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

public class TiAcCrTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> MISC_TAG = CREATIVE_MODE_TABS.register("tiac_misc", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_misc"))
            .icon(() -> TiAcCrItem.ULTRA_DENSE_BOOK.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<Item> object:TiAcCrItem.getListMisc()){
                    if (object.isPresent()) {
                        output.accept(object.get());
                    }
                }
            }).build());
}
