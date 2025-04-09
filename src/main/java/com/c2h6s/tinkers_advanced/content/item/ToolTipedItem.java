package com.c2h6s.tinkers_advanced.content.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToolTipedItem extends Item {
    public final List<Component> tooltip;
    public ToolTipedItem(Item.Properties properties, @NotNull List<Component> tooltip) {
        super(properties);
        this.tooltip =tooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<net.minecraft.network.chat.Component> list, TooltipFlag flag) {
        list.addAll(this.tooltip);
        super.appendHoverText(stack, level, list, flag);
    }
}