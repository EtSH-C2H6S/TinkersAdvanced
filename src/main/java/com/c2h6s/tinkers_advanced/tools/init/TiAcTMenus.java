package com.c2h6s.tinkers_advanced.tools.init;

import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.tools.content.menu.ElectronTunerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcTMenus {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}

    public static final RegistryObject<MenuType<ElectronTunerMenu>> ELECTRON_TUNER_MENU = TiAcCrModule.MENUS.register("electron_tuner_menu",()-> IForgeMenuType.create(ElectronTunerMenu::new));
}
