package com.c2h6s.tinkers_advanced.materials;

import com.c2h6s.etstlib.util.ModListConstants;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeEntities;
import com.c2h6s.tinkers_advanced.materials.init.TiAcMeMaterials;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class TiAcMeModule {
    public static void register(IEventBus bus){
        if (ModList.get().isLoaded("thermal")){
            TiAcMeEntities.THERMAL_ENTITIES.register(bus);
            TiAcMeMaterials.MATERIALS.register(bus);
        }
    }
}
