package com.c2h6s.tinkers_advanced.utilities.client.event.handler;

import com.c2h6s.tinkers_advanced.utilities.init.TiAcUBlockEntities;
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
        event.registerBlockEntityRenderer(TiAcUBlockEntities.EXCHANGER_BLOCK_ENTITY.get(), pContext -> new ExchangerBERenderer());
        event.registerBlockEntityRenderer(TiAcUBlockEntities.ROSE_GOLD_SLIME_BASIN.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.ROSE_GOLD_TABLE.get(), CastingBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(TiAcUBlockEntities.HEPATIZON_BASIN.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.HEPATIZON_TABLE.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.ROSE_GOLD_FAUCET.get(), FaucetBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.HEPATIZON_FAUCET.get(), FaucetBlockEntityRenderer::new);
    }
}
