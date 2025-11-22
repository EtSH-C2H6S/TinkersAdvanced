package com.c2h6s.tinkers_advanced.utilities.client.event.handler;

import com.c2h6s.tinkers_advanced.registery.TiAcBlockEntities;
import com.c2h6s.tinkers_advanced.utilities.client.renderer.blockEntity.ExchangerBERenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.smeltery.client.render.CastingBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.FaucetBlockEntityRenderer;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TiAcUClientModEventHandler {
    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(TiAcBlockEntities.EXCHANGER_BLOCK_ENTITY.get(), pContext -> new ExchangerBERenderer());
        event.registerBlockEntityRenderer(TiAcBlockEntities.CINDER_SLIME_BASIN.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcBlockEntities.CINDER_SLIME_TABLE.get(), CastingBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(TiAcBlockEntities.IRIDIUM_BASIN.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcBlockEntities.IRIDIUM_TABLE.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcBlockEntities.CINDER_SLIME_FAUCET.get(), FaucetBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcBlockEntities.IRIDIUM_FAUCET.get(), FaucetBlockEntityRenderer::new);
    }
}
