package com.c2h6s.tinkers_advanced.core.library.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class TiAcCapabilities {
    public static final Capability<IExchangerTickable> EXCHANGER_TICKABLE = CapabilityManager
            .get(new CapabilityToken<>() {});
}
