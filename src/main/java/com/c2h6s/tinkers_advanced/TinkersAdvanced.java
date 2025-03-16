package com.c2h6s.tinkers_advanced;

import com.c2h6s.etstlib.util.ModListConstants;
import com.c2h6s.tinkers_advanced.client.renderer.*;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaBeamProjectile;
import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.eventHandler.LivingEventHandler;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.registery.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import slimeknights.tconstruct.library.client.model.TinkerItemProperties;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;

import java.util.Random;

@Mod(TinkersAdvanced.MODID)
public class TinkersAdvanced
{
    public static final String MODID = "tinkers_advanced";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Random RANDOM = new Random();
    public static ResourceLocation getLocation(String name){return new ResourceLocation(MODID,name);}


    public TinkersAdvanced()
    {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        TiAcItems.ITEMS.register(modEventBus);
        TiAcItems.TINKER_ITEMS.register(modEventBus);
        TiAcBlocks.BLOCKS.register(modEventBus);
        TiAcTabs.CREATIVE_MODE_TABS.register(modEventBus);
        TiAcEffects.EFFECTS.register(modEventBus);
        TiAcFluids.FLUIDS.register(modEventBus);
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
        TiAcModifiers.MODIFIERS.register(modEventBus);
        TiAcEntities.ENTITIES.register(modEventBus);

        TiAcPacketHandler.init();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new LivingEventHandler());



        modEventBus.addListener(this::addCreative);

        //context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        EntityModifierCapability.registerEntityPredicate(entity -> entity instanceof VisualScaledProjectile);
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
                TinkerItemProperties.registerBrokenProperty(TiAcItems.IONIZED_CANNON.get());
                TinkerItemProperties.registerToolProperties(TiAcItems.IONIZED_CANNON.get());
            });
        }

        @SubscribeEvent
        public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(TiAcEntities.PLASMA_EXPLOSION.get(), PlasmaExplosionRenderer::new);
            event.registerEntityRenderer(TiAcEntities.PLASMA_BEAM.get(), PlasmaBeamRenderer::new);
            event.registerEntityRenderer(TiAcEntities.AIR_SLASH.get(), AirSlashRenderer::new);
            event.registerEntityRenderer(TiAcEntities.MINING_BEAM.get(), MiningBeamRenderer::new);
            if (ModList.get().isLoaded("thermal")){
                event.registerEntityRenderer(TiAcEntities.THERMAL_SLASH.get(), RenderThermalSlash::new);
            }
        }
    }
}
