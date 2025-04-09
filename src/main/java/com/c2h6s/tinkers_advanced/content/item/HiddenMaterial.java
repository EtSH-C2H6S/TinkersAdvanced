package com.c2h6s.tinkers_advanced.content.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HiddenMaterial extends Item {
    public final List<Component> tooltip;
    public HiddenMaterial(Properties properties, @NotNull List<Component> tooltip) {
        super(properties);
        this.tooltip=tooltip;
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<net.minecraft.network.chat.Component> list, TooltipFlag flag) {
        if (Screen.hasShiftDown()){
            list.addAll(this.tooltip);
        }
        else list.add(Component.translatable("tooltip.tinkers_advanced.material_story").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(stack, level, list, flag);
    }
}
