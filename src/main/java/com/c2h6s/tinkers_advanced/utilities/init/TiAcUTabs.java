package com.c2h6s.tinkers_advanced.utilities.init;

import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.c2h6s.tinkers_advanced.core.init.TiAcCrTabs.CREATIVE_MODE_TABS;

public class TiAcUTabs {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
    public static final RegistryObject<CreativeModeTab> UTILITIES_TAB = CREATIVE_MODE_TABS.register("tiac_utilities", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tinkers_advanced.tiac_utilities"))
            .icon(() -> TiAcUItems.EXCHANGER.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (RegistryObject<BlockItem> object: TiAcCrItem.getListUtilitiesBlock()){
                    if (object.isPresent()) {
                        output.accept(object.get());
                    }
                }
            }).build());
}
