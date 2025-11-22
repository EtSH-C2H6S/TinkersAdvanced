package com.c2h6s.tinkers_advanced.materials.client.event.handler;

import com.c2h6s.tinkers_advanced.materials.client.renderer.entity.AirSlashRenderer;
import com.c2h6s.tinkers_advanced.materials.client.renderer.entity.RenderThermalSlash;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TiAcCrClientModEventHandler {
    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(TiAcEntities.AIR_SLASH.get(), AirSlashRenderer::new);
        if (ModList.get().isLoaded("thermal")){
            event.registerEntityRenderer(TiAcEntities.THERMAL_SLASH.get(), RenderThermalSlash::new);
        }
    }
}
