package com.c2h6s.tinkers_advanced.content.modifier.generatorModifiers;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.SlotStackModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;

public class PlayerLocating extends EtSTBaseModifier implements SlotStackModifierHook, TooltipModifierHook {
    public static final ResourceLocation KEY_PLAYER_ID = TinkersAdvanced.getLocation("player_id");

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public Component onModifierRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(KEY_PLAYER_ID);
        return null;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.SLOT_STACK,ModifierHooks.TOOLTIP);
    }

    @Override
    public boolean overrideOtherStackedOnMe(IToolStackView slotTool, ModifierEntry modifier, ItemStack held, Slot slot, Player player, SlotAccess access) {
        if (held.is(Items.PAPER)) {
            slotTool.getPersistentData().putString(KEY_PLAYER_ID, player.getStringUUID());
            return true;
        }
        return false;
    }

    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        ModDataNBT nbt = iToolStackView.getPersistentData();
        if (nbt.contains(KEY_PLAYER_ID,Tag.TAG_STRING)) list.add(Component.literal("UUID(p): "+nbt.getString(KEY_PLAYER_ID)).withStyle(this.getDisplayName().getStyle()));
    }
}
