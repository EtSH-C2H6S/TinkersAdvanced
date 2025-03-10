package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

public class OverHold extends NoLevelsModifier implements ToolStatsModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolTankHelper.CAPACITY_STAT.percent(builder,9);
        TiAcToolStats.RANGE.update(builder,16f);
        TiAcToolStats.SCALE.update(builder,1f);
        TiAcToolStats.FLUID_EFFICIENCY.update(builder,0f);
    }
}
