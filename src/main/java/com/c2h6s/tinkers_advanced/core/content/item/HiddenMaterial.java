package com.c2h6s.tinkers_advanced.core.content.item;

import com.c2h6s.tinkers_advanced.core.library.interfaces.IHiddenMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class HiddenMaterial extends Item implements IHiddenMaterial {
    public final List<Component> tooltip;
    public final ForgeConfigSpec.BooleanValue config;
    public HiddenMaterial(Properties properties, @NotNull List<Component> tooltip,@Nullable ForgeConfigSpec.BooleanValue config) {
        super(properties);
        this.tooltip=tooltip;
        this.config =config;
    }

    @Override
    public List<Component> getTooltips() {
        return tooltip;
    }

    @Override
    public Supplier<Boolean> getConfig() {
        return config;
    }
}
