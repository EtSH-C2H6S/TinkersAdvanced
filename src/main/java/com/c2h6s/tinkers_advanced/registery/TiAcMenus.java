package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.menu.ElectronTunerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TiAcMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES,TinkersAdvanced.MODID);

    public static final RegistryObject<MenuType<ElectronTunerMenu>> ELECTRON_TUNER_MENU = MENUS.register("electron_tuner_menu",()-> IForgeMenuType.create(ElectronTunerMenu::new));
}
