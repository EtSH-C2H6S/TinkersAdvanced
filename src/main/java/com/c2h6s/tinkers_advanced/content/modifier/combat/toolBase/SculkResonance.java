package com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.registery.TiAcToolStats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

public class SculkResonance extends FluidDisplayBaseModifier implements ToolStatsModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
    }

    @Override
    public boolean isNoLevels() {
        return true;
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    @Override
    public LegacyDamageSource modifyDamageSource(IToolStackView tool, ModifierEntry entry, LivingEntity attacker, InteractionHand hand, Entity target, EquipmentSlot sourceSlot, boolean isFullyCharged, boolean isExtraAttack, boolean isCritical, LegacyDamageSource source) {
        return new LegacyDamageSource(attacker.damageSources().sonicBoom(attacker));
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder builder) {
        TiAcToolStats.RANGE.update(builder, TiAcConfig.COMMON.IONIZED_CANNON_BASE_RANGE.get().floatValue());
        TiAcToolStats.SCALE.update(builder, TiAcConfig.COMMON.IONIZED_CANNON_BASE_SCALE.get().floatValue());
        TiAcToolStats.FLUID_EFFICIENCY.update(builder, TiAcConfig.COMMON.IONIZED_CANNON_BASE_FLUID_EFFICIENCY.get().floatValue());
    }
}
