package com.c2h6s.tinkers_advanced.core.content.event;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

public class TiAcLoadRegistryClassEvent extends Event implements IModBusEvent {
    @Override
    public boolean isCancelable() {
        return false;
    }

    @Override
    public boolean hasResult() {
        return false;
    }
}
