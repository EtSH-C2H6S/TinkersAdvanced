package com.c2h6s.tinkers_advanced.content.modifier.compat.thermal;

import cofh.core.client.CoreKeys;
import com.c2h6s.etstlib.tool.modifiers.base.BasicFEModifier;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.network.packets.PCofhModSwitchC2S;
import com.c2h6s.tinkers_advanced.registery.TiAcModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.*;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

import static cofh.core.client.CoreKeys.MULTIMODE_DECREMENT;
import static cofh.core.client.CoreKeys.MULTIMODE_INCREMENT;
import static net.minecraft.ChatFormatting.*;
import static cofh.lib.util.helpers.StringHelper.*;

public class FluxInfused extends BasicFEModifier {
    public static final ResourceLocation MODE_LOCATION = TinkersAdvanced.getLocation("cofh_mode");
    public FluxInfused(){
        MinecraftForge.EVENT_BUS.addListener(this::onKeyInput);
    }


    private void onKeyInput(InputEvent.Key event) {
        if (CoreKeys.MULTIMODE_DECREMENT.consumeClick()){
            TiAcPacketHandler.sendToServer(new PCofhModSwitchC2S(true));
        }else if (CoreKeys.MULTIMODE_INCREMENT.consumeClick()){
            TiAcPacketHandler.sendToServer(new PCofhModSwitchC2S(false));
        }
    }

    public static void switchMode(ServerPlayer player,boolean increment){
        ToolStack tool = null;
        if (player.getMainHandItem().getItem() instanceof IModifiable){
            tool = ToolStack.from(player.getMainHandItem());
            if (tool.getModifierLevel(TiAcModifiers.FLUX_INFUSED.getId())<=0){
                tool=null;
            }
        }
        if (player.getOffhandItem().getItem() instanceof IModifiable&& tool==null){
            tool =ToolStack.from(player.getOffhandItem());
            if (tool.getModifierLevel(TiAcModifiers.FLUX_INFUSED.getId())<=0){
                tool=null;
            }
        }
        if (tool==null) return;
        tool.getPersistentData().putInt(MODE_LOCATION,(tool.getPersistentData().getInt(MODE_LOCATION)+(increment?+1:-1))%3+3);
        for (ModifierEntry entry:tool.getModifierList()){
            if (entry.getModifier() instanceof FluxInfused modifier){
                modifier.onModeSwitch(tool,player,entry);
            }
        }
    }

    public static int getMode(IToolStackView tool){
        return tool.getPersistentData().getInt(MODE_LOCATION)%3;
    }

    public static int getMode(IModDataView toolData){
        return toolData.getInt(MODE_LOCATION)%3;
    }

    @Override
    public int getCapacity(ModifierEntry modifier) {
        return (int) (modifier.getLevel()*1E6);
    }

    public void onModeSwitch(IToolStackView tool, ServerPlayer player, ModifierEntry entry){

    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player != null) {
            tooltip.add(Component.translatable("info.cofh.mode_change", Component.keybind("key.cofh.mode_change_increment"), Component.keybind("key.cofh.mode_change_decrement")).withStyle(YELLOW));

            if (MULTIMODE_INCREMENT.getKey().getValue() == -1) {
                tooltip.add(Component.translatable("info.cofh.key_not_bound", localize("key.cofh.mode_change_increment")).withStyle(RED));
            }
            if (MULTIMODE_DECREMENT.getKey().getValue() == -1) {
                tooltip.add(Component.translatable("info.cofh.key_not_bound", localize("key.cofh.mode_change_decrement")).withStyle(RED));
            }
        }
        super.addTooltip(iToolStackView,modifierEntry,player,tooltip,tooltipKey,tooltipFlag);
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if (ToolEnergyUtil.extractEnergy(tool,500*amount,true)>=500){
            int reduce = ToolEnergyUtil.extractEnergy(tool,500*amount,false)/500;
            if (reduce>=amount) return 0;
            return amount-reduce;
        }
        return amount;
    }
}
