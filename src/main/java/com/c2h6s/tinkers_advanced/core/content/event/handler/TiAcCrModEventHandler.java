package com.c2h6s.tinkers_advanced.core.content.event.handler;

import com.c2h6s.tinkers_advanced.core.content.entity.VisualScaledProjectile;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcCrModEventHandler {
    @SubscribeEvent
    static void commonSetup(final FMLCommonSetupEvent event)
    {
        EntityModifierCapability.registerEntityPredicate(entity -> entity instanceof VisualScaledProjectile);
    }
}
