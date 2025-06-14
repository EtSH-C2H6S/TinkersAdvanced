package com.c2h6s.tinkers_advanced;

import com.c2h6s.etstlib.util.ModListConstants;
import com.c2h6s.tinkers_advanced.client.TiAcToolProperty;
import com.c2h6s.tinkers_advanced.client.book.TiAcBookData;
import com.c2h6s.tinkers_advanced.client.gui.screen.ElectronTunerScreen;
import com.c2h6s.tinkers_advanced.client.renderer.*;
import com.c2h6s.tinkers_advanced.client.renderer.blockEntity.ExchangerBERenderer;
import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.content.event.eventHandler.LivingEventHandler;
import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.content.worldgen.TiAcPlacementModifier;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.registery.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import slimeknights.tconstruct.library.client.model.TinkerItemProperties;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.shared.CommonsClientEvents;

import java.util.Random;

@Mod(TinkersAdvanced.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TinkersAdvanced
{
    public static final String MODID = "tinkers_advanced";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static Random RANDOM = new Random();
    public static ResourceLocation getLocation(String name){return new ResourceLocation(MODID,name);}


    public TinkersAdvanced()
    {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus modEventBus = context.getModEventBus();

        TiAcConfig.init();
        TiAcItems.ITEMS.register(modEventBus);
        TiAcItems.TINKER_ITEMS.register(modEventBus);
        TiAcBlocks.BLOCKS.register(modEventBus);
        TiAcTabs.CREATIVE_MODE_TABS.register(modEventBus);
        TiAcEffects.EFFECTS.register(modEventBus);
        TiAcFluids.FLUIDS.register(modEventBus);
        TiAcParticleTypes.PARTICLES.register(modEventBus);
        TiAcPlacementModifier.PLACEMENT_MODIFIER.register(modEventBus);
        TiAcEntityTicker.TICKERS.register(modEventBus);
        TiAcBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        if (ModListConstants.MekLoaded){
            TiAcItems.MEK_ITEMS.register(modEventBus);
            TiAcFluids.MEK_FLUIDS.register(modEventBus);
            TiAcModifiers.MEK_MODIFIERS.register(modEventBus);
        }
        if (ModListConstants.PnCLoaded){
            TiAcItems.PNC_ITEMS.register(modEventBus);
            TiAcModifiers.PNC_MODIFIERS.register(modEventBus);
        }
        if (ModList.get().isLoaded("thermal")){
            TiAcItems.THERMAL_ITEMS.register(modEventBus);
            TiAcFluids.THERMAL_FLUIDS.register(modEventBus);
            TiAcModifiers.THERMAL_MODIFIERS.register(modEventBus);
            TiAcEntities.THERMAL_ENTITIES.register(modEventBus);
        }
        if (ModListConstants.AE2Loaded){
            TiAcModifiers.AE_MODIFIERS.register(modEventBus);
        }
        if (ModList.get().isLoaded("industrialforegoing")){
            TiAcFluids.IF_FLUIDS.register(modEventBus);
            TiAcItems.IF_ITEMS.register(modEventBus);
        }
        if (ModList.get().isLoaded("createutilities")){
            TiAcFluids.CREATE_UTILITIES_FLUIDS.register(modEventBus);
        }
        TiAcModifiers.MODIFIERS.register(modEventBus);
        TiAcEntities.ENTITIES.register(modEventBus);
        TiAcMenus.MENUS.register(modEventBus);

        TiAcPacketHandler.init();

        MinecraftForge.EVENT_BUS.register(this);



        modEventBus.addListener(this::addCreative);

        //context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    static void commonSetup(final FMLCommonSetupEvent event)
    {
        EntityModifierCapability.registerEntityPredicate(entity -> entity instanceof VisualScaledProjectile);
        event.enqueueWork(TiAcMaterialStat::init);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(()->{
                MenuScreens.register(TiAcMenus.ELECTRON_TUNER_MENU.get(), ElectronTunerScreen::new);
                TinkerItemProperties.registerBrokenProperty(TiAcItems.IONIZED_CANNON.get());
                TinkerItemProperties.registerToolProperties(TiAcItems.IONIZED_CANNON.get());
                ItemProperties.register(TiAcItems.ELECTRON_TUNER.asItem(), ElectronTunerItem.KEY_ATTACK_DAMAGE, TiAcToolProperty.FUNCTION_ELECTRON_TUNER);
                TiAcBookData.intiBook();
            });

            TiAcBookData.ULTRA_DENSE_BOOK.fontRenderer = CommonsClientEvents.unicodeFontRender();
        }

        @SubscribeEvent
        public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(TiAcEntities.PLASMA_EXPLOSION.get(), PlasmaExplosionRenderer::new);
            event.registerEntityRenderer(TiAcEntities.PLASMA_BEAM.get(), PlasmaBeamRenderer::new);
            event.registerEntityRenderer(TiAcEntities.AIR_SLASH.get(), AirSlashRenderer::new);
            event.registerEntityRenderer(TiAcEntities.MINING_BEAM.get(), MiningBeamRenderer::new);
            event.registerEntityRenderer(TiAcEntities.PLASMA_SLASH.get(),PlasmaSlashRenderer::new);
            if (ModList.get().isLoaded("thermal")){
                event.registerEntityRenderer(TiAcEntities.THERMAL_SLASH.get(), RenderThermalSlash::new);
            }

            event.registerBlockEntityRenderer(TiAcBlockEntities.EXCHANGER_BLOCK_ENTITY.get(), pContext -> new ExchangerBERenderer());
        }
    }
}
