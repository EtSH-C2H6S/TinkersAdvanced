package com.c2h6s.tinkers_advanced;

import com.c2h6s.etstlib.util.ModListConstants;
import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.entity.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.materials.TiAcMeModule;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMePlacementModifier;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeItems;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeFluids;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.registery.*;
import com.c2h6s.tinkers_advanced.tools.TiAcTModule;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;

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
        TiAcCrModule.register(modEventBus);
        TiAcTModule.register(modEventBus);
        TiAcMeModule.register(modEventBus);
        TiAcMePlacementModifier.PLACEMENT_MODIFIER.register(modEventBus);
        if (ModListConstants.MekLoaded){
            TiAcMeItems.MEK_ITEMS.register(modEventBus);
            TiAcMeFluids.MEK_FLUIDS.register(modEventBus);
            TiAcModifiers.MEK_MODIFIERS.register(modEventBus);
        }
        if (ModListConstants.PnCLoaded){
            TiAcMeItems.PNC_ITEMS.register(modEventBus);
            TiAcModifiers.PNC_MODIFIERS.register(modEventBus);
        }
        if (ModList.get().isLoaded("thermal")){
            TiAcMeItems.THERMAL_ITEMS.register(modEventBus);
            TiAcMeFluids.THERMAL_FLUIDS.register(modEventBus);
            TiAcModifiers.THERMAL_MODIFIERS.register(modEventBus);
        }
        if (ModListConstants.AE2Loaded){
            TiAcModifiers.AE_MODIFIERS.register(modEventBus);
        }
        if (ModList.get().isLoaded("industrialforegoing")){
            TiAcMeFluids.IF_FLUIDS.register(modEventBus);
            TiAcMeItems.IF_ITEMS.register(modEventBus);
        }
        if (ModList.get().isLoaded("createutilities")){
            TiAcMeFluids.CREATE_UTILITIES_FLUIDS.register(modEventBus);
        }

        TiAcPacketHandler.init();

        MinecraftForge.EVENT_BUS.register(this);

        //context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    static void commonSetup(final FMLCommonSetupEvent event)
    {
        EntityModifierCapability.registerEntityPredicate(entity -> entity instanceof VisualScaledProjectile);
    }

}
