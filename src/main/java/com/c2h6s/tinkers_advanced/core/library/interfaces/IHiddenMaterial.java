package com.c2h6s.tinkers_advanced.core.library.interfaces;

import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.function.Supplier;

public interface IHiddenMaterial {
    List<Component> getTooltips();
    Supplier<Boolean> getConfig();
}
