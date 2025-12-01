package com.c2h6s.tinkers_advanced.tools;

import com.c2h6s.tinkers_advanced.tools.init.TiAcTItems;
import com.c2h6s.tinkers_advanced.tools.init.TiAcTMaterialStat;
import net.minecraftforge.eventbus.api.IEventBus;

public class TiAcTModule {
    public static void register(IEventBus bus){
        TiAcTItems.TOOL_ITEMS.register(bus);
    }
}
