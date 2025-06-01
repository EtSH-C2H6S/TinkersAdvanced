package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTicker;
import com.c2h6s.etstlib.content.register.EtSTLibRegistries;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.tickers.IonizedEntityTicker;
import com.c2h6s.tinkers_advanced.content.entity.tickers.SculkMarkedTicker;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TiAcEntityTicker {
    public static final DeferredRegister<EntityTicker> TICKERS = DeferredRegister.create(EtSTLibRegistries.ENTITY_TICKER, TinkersAdvanced.MODID);

    public static final RegistryObject<EntityTicker> SCULK_MARKED = TICKERS.register("sculk_marked",SculkMarkedTicker::new);
    public static final RegistryObject<EntityTicker> IONIZED = TICKERS.register("ionized", IonizedEntityTicker::new);
}
