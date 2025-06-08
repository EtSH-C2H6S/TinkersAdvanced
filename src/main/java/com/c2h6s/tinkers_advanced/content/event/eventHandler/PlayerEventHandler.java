package com.c2h6s.tinkers_advanced.content.event.eventHandler;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.registery.TiAcItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = TinkersAdvanced.MODID)
public class PlayerEventHandler {
    @SubscribeEvent
    public static void addTooltip(ItemTooltipEvent event){
        if (event.getItemStack().is(TiAcItems.STIBNITE.get())||event.getItemStack().is(TiAcItems.STIBNITE_ORE.get())){
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.stibnite_ore").withStyle(ChatFormatting.GOLD));
        }
        if (event.getItemStack().is(TiAcItems.EXCHANGER.get())){
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.exchanger1").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.exchanger2").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.exchanger3").withStyle(ChatFormatting.GRAY));
        }
        if (event.getItemStack().is(TiAcItems.ULTRA_DENSE_BOOK.get())){
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.ultra_dense_book1").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.ultra_dense_book2").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }
}
