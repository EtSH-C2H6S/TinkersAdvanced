package com.c2h6s.tinkers_advanced.content.event.eventHandler;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.objects.EntityTicker;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {
    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event){
        EntityTicker.endAll();
    }
    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event){
        EntityTicker.endAll();
    }
}
