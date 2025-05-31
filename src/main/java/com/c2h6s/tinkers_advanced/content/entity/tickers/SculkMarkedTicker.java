package com.c2h6s.tinkers_advanced.content.entity.tickers;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTicker;
import net.minecraft.world.entity.Entity;

public class SculkMarkedTicker extends EntityTicker {
    @Override
    public void onTickerStart(int duration, int level, Entity entity) {
        entity.setGlowingTag(true);
    }

    @Override
    public void onTickerEnd(int level, Entity entity) {
        entity.setGlowingTag(false);
    }
}
