package com.c2h6s.tinkers_advanced.tools.client.event.handler;

import com.c2h6s.tinkers_advanced.tools.init.TiAcTEntities;
import com.c2h6s.tinkers_advanced.tools.init.TiAcTMenus;
import com.c2h6s.tinkers_advanced.tools.client.TiAcToolProperty;
import com.c2h6s.tinkers_advanced.tools.client.gui.screen.ElectronTunerScreen;
import com.c2h6s.tinkers_advanced.tools.client.renderer.entity.MiningBeamRenderer;
import com.c2h6s.tinkers_advanced.tools.client.renderer.entity.PlasmaBeamRenderer;
import com.c2h6s.tinkers_advanced.tools.client.renderer.entity.PlasmaExplosionRenderer;
import com.c2h6s.tinkers_advanced.tools.client.renderer.entity.PlasmaSlashRenderer;
import com.c2h6s.tinkers_advanced.tools.content.tool.item.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.tools.init.TiAcTItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.tconstruct.library.client.model.TinkerItemProperties;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TiAcTClientModEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(()->{
            MenuScreens.register(TiAcTMenus.ELECTRON_TUNER_MENU.get(), ElectronTunerScreen::new);
            TinkerItemProperties.registerBrokenProperty(TiAcTItems.IONIZED_CANNON.get());
            TinkerItemProperties.registerToolProperties(TiAcTItems.IONIZED_CANNON.get());
            ItemProperties.register(TiAcTItems.ELECTRON_TUNER.asItem(), ElectronTunerItem.KEY_ATTACK_DAMAGE, TiAcToolProperty.FUNCTION_ELECTRON_TUNER);
        });
    }

    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(TiAcTEntities.PLASMA_EXPLOSION.get(), PlasmaExplosionRenderer::new);
        event.registerEntityRenderer(TiAcTEntities.PLASMA_BEAM.get(), PlasmaBeamRenderer::new);
        event.registerEntityRenderer(TiAcTEntities.MINING_BEAM.get(), MiningBeamRenderer::new);
        event.registerEntityRenderer(TiAcTEntities.PLASMA_SLASH.get(), PlasmaSlashRenderer::new);
    }
}
