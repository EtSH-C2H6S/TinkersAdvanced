package com.c2h6s.tinkers_advanced.content.modifier.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.RepairFactorModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public class ExtraDense extends Modifier implements ToolStatsModifierHook, RepairFactorModifierHook, TooltipModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS,ModifierHooks.REPAIR_FACTOR,ModifierHooks.TOOLTIP);
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry entry, ModifierStatsBuilder builder) {
        ToolStats.MINING_SPEED.percent(builder,0.1*entry.getLevel());
        ToolStats.ATTACK_DAMAGE.percent(builder,0.1*entry.getLevel());
        ToolStats.ARMOR.percent(builder,0.1*entry.getLevel());
        ToolStats.DURABILITY.percent(builder,0.1*entry.getLevel());
        ToolStats.VELOCITY.percent(builder,0.1*entry.getLevel());
    }

    @Override
    public float getRepairFactor(IToolStackView iToolStackView, ModifierEntry modifierEntry, float v) {
        return v*Math.max(1-0.1f*modifierEntry.getLevel(),0.01f);
    }


    @Override
    public void addTooltip(IToolStackView iToolStackView, ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        list.add(this.getDisplayName().copy().append(" + ").append(modifierEntry.getLevel()*10+"%"));
    }
}
