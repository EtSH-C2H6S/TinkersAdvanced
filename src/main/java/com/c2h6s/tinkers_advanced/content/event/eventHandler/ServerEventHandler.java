package com.c2h6s.tinkers_advanced.content.event.eventHandler;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.objects.ToolEnergyProduction;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = TinkersAdvanced.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {
    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event){
        ToolEnergyProduction.saveAll();
    }
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event){
        ToolEnergyProduction.saveAll();
    }
    @SubscribeEvent
    public static void onSeverTick(TickEvent.ServerTickEvent event){
        MinecraftServer server = event.getServer();
        List<UUID> list =List.copyOf(ToolEnergyProduction.ENERGY_PRODUCTION_MAP.keySet());
        for (UUID uuid:list){
            ToolEnergyProduction production = ToolEnergyProduction.ENERGY_PRODUCTION_MAP.get(uuid);
            if (production!=null&&Math.abs(server.getTickCount() - production.gameTimeLastTick)>1000){
                production.saveToTool();
                production.remove();
            }
        }
    }
}
