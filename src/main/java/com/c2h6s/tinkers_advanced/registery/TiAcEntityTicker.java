package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTicker;
import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.materials.content.entityTicker.IonizedEntityTicker;
import com.c2h6s.tinkers_advanced.materials.content.entityTicker.SculkMarkedTicker;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcEntityTicker {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}

    public static final RegistryObject<EntityTicker> SCULK_MARKED = TiAcCrModule.TICKERS.register("sculk_marked",SculkMarkedTicker::new);
    public static final RegistryObject<EntityTicker> IONIZED = TiAcCrModule.TICKERS.register("ionized", IonizedEntityTicker::new);
}
