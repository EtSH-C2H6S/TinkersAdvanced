package com.c2h6s.tinkers_advanced.core.client.event.handler;

import com.c2h6s.tinkers_advanced.core.client.book.TiAcBookData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.tconstruct.shared.CommonsClientEvents;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TiAcCrClientModEvent {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(()->{
            TiAcBookData.intiBook();
        });
        TiAcBookData.ULTRA_DENSE_BOOK.fontRenderer = CommonsClientEvents.unicodeFontRender();
    }
}
