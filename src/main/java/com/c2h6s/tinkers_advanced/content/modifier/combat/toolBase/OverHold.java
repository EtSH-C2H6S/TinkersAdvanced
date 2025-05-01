package com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase;

import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

public class OverHold extends FluidDisplayBaseModifier implements ToolStatsModifierHook {

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolTankHelper.CAPACITY_STAT.percent(builder, TiAcConfig.COMMON.MATTER_MANIPULATOR_CAPACITY_FACTOR.get()-1);
        TiAcToolStats.RANGE.update(builder,(float)(double)TiAcConfig.COMMON.MATTER_MANIPULATOR_BASE_RANGE.get());
        TiAcToolStats.FLUID_EFFICIENCY.update(builder,(float)(double)TiAcConfig.COMMON.MATTER_MANIPULATOR_FLUID_EFFICIENCY.get());
    }
}
