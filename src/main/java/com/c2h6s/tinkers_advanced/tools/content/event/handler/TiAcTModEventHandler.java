package com.c2h6s.tinkers_advanced.tools.content.event.handler;

import com.c2h6s.tinkers_advanced.tools.init.TiAcTMaterialStat;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.c2h6s.tinkers_advanced.TinkersAdvanced.MODID;

@Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcTModEventHandler {
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            TiAcTMaterialStat.init();
        });
    }
}
