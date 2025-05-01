package com.c2h6s.tinkers_advanced.content.modifier.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.*;

import java.util.List;

public class Shaping extends EtSTBaseModifier implements VolatileDataModifierHook , TooltipModifierHook {
    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOLTIP,ModifierHooks.VOLATILE_DATA);
    }

    public static final ResourceLocation LOCATION_SHAPING = TinkersAdvanced.getLocation("shaping_slots");

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        var existingBonus = tool.getPersistentData().getInt(LOCATION_SHAPING);
        var requirement = TiAcConfig.COMMON.SHAPING_DAMAGES_EACH_SLOT.get();
        var maxBonus = TiAcConfig.COMMON.SHAPING_MAX_SLOT.get()*requirement;
        if (existingBonus<maxBonus){
            tool.getPersistentData().putInt(LOCATION_SHAPING,Math.min(maxBonus,existingBonus+amount));
        }
        if ((existingBonus+amount)/requirement!=existingBonus/requirement){
           ((ToolStack) tool).rebuildStats();
        }
        return amount;
    }

    @Override
    public void addVolatileData(IToolContext iToolContext, ModifierEntry modifierEntry, ToolDataNBT toolDataNBT) {
        var persistentDataNbt = iToolContext.getPersistentData();
        var requirement = TiAcConfig.COMMON.SHAPING_DAMAGES_EACH_SLOT.get();
        var slotBonus = persistentDataNbt.getInt(LOCATION_SHAPING)/requirement;
        slotBonus = Math.min(slotBonus,TiAcConfig.COMMON.SHAPING_MAX_SLOT.get());
        if (slotBonus>0){
            if (iToolContext.hasTag(TinkerTags.Items.ARMOR)) toolDataNBT.addSlots(SlotType.DEFENSE,slotBonus);
            else toolDataNBT.addSlots(SlotType.UPGRADE,slotBonus);
        }
    }


    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        var requirement = TiAcConfig.COMMON.SHAPING_DAMAGES_EACH_SLOT.get();
        var maxBonus = TiAcConfig.COMMON.SHAPING_MAX_SLOT.get();
        var slotBonus = tool.getPersistentData().getInt(LOCATION_SHAPING)/requirement;
        slotBonus = Math.min(slotBonus,TiAcConfig.COMMON.SHAPING_MAX_SLOT.get());
        list.add(Component.translatable("tooltip.tinkers_advanced.modifiers.shaping").append(slotBonus+" / "+maxBonus).withStyle(this.getDisplayName().getStyle()));
    }
}
