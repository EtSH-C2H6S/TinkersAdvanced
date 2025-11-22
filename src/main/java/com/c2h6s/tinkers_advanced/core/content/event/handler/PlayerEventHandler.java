package com.c2h6s.tinkers_advanced.core.content.event.handler;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrItem;
import com.c2h6s.tinkers_advanced.core.library.interfaces.IHiddenMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = TinkersAdvanced.MODID)
public class PlayerEventHandler {
    @SubscribeEvent
    public static void addTooltip(ItemTooltipEvent event){
        if (event.getItemStack().is(TiAcCrItem.ULTRA_DENSE_BOOK.get())){
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.ultra_dense_book1").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.ultra_dense_book2").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
        if (event.getItemStack().getItem() instanceof IHiddenMaterial iHiddenMaterial){
            var originalList = List.copyOf(event.getToolTip());
            var tooltip = event.getToolTip();
            tooltip.clear();
            if (iHiddenMaterial.getConfig()!=null&&!iHiddenMaterial.getConfig().get()){
                tooltip.add(Component.translatable("tooltip.tinkers_advanced.material_banned").withStyle(ChatFormatting.RED));
            }
            else {
                if (Screen.hasShiftDown()) {
                    tooltip.addAll(iHiddenMaterial.getTooltips());
                } else tooltip.add(Component.translatable("tooltip.tinkers_advanced.material_story").withStyle(ChatFormatting.GOLD));
            }
            tooltip.addAll(originalList);
        }
    }
}
