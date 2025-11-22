package com.c2h6s.tinkers_advanced.materials.content.tool.modifiers.combat.toolBase;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.register.EtSTLibToolStat;
import com.c2h6s.etstlib.tool.hooks.CustomBarDisplayModifierHook;
import com.c2h6s.tinkers_advanced.TiAcConfig;
import com.c2h6s.tinkers_advanced.tools.content.tool.item.IonizedCannonItem;
import com.c2h6s.tinkers_advanced.registery.TiAcModifiers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierTraitModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

public class SculkResonance extends FluidDisplayBaseModifier implements ToolStatsModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_STATS);
        hookBuilder.addModule(new ModifierTraitModule(TiAcModifiers.IONIZED_CANNON_CD.getId(), 1,true));
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
        var Ls = new LegacyDamageSource(attacker.damageSources().sonicBoom(attacker));
        Ls.damageTypes = source.damageTypes;
        return Ls;
    }

    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder builder) {
        EtSTLibToolStat.RANGE.update(builder, TiAcConfig.COMMON.IONIZED_CANNON_BASE_RANGE.get().floatValue());
        EtSTLibToolStat.SCALE.update(builder, TiAcConfig.COMMON.IONIZED_CANNON_BASE_SCALE.get().floatValue());
        EtSTLibToolStat.FLUID_EFFICIENCY.update(builder, TiAcConfig.COMMON.IONIZED_CANNON_BASE_FLUID_EFFICIENCY.get().floatValue());
    }

    public static class IonizedCannonCooldownDisplay extends Modifier implements CustomBarDisplayModifierHook{
        @Override
        protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
            super.registerHooks(hookBuilder);
            hookBuilder.addHook(this, EtSTLibHooks.CUSTOM_BAR);
        }

        @Override
        public boolean shouldDisplay(boolean advanced) {
            return false;
        }

        @Override
        public String barId(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i) {
            return "ionized_cannon_cd";
        }

        @Override
        public boolean showBar(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i) {
            return iToolStackView.getPersistentData().getInt(IonizedCannonItem.TAG_COOLDOWN)>0&&
                    !iToolStackView.getPersistentData().contains(GeneralInteractionModifierHook.KEY_DRAWTIME);
        }

        @Override
        public Vec2 getBarXYSize(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i) {
            float cd = iToolStackView.getPersistentData().getInt(IonizedCannonItem.TAG_COOLDOWN);
            float maxCd = iToolStackView.getPersistentData().getInt(IonizedCannonItem.TAG_COOLDOWN_MAX);
            int height = (int) (16*Math.min(1,cd/maxCd));
            return new Vec2(16,-height);
        }

        @Override
        public Vec2 getBarXYPos(IToolStackView tool, ModifierEntry entry, int barsHadBeenShown) {
            return new Vec2(0,16);
        }

        @Override
        public int getBarRGB(IToolStackView iToolStackView, ModifierEntry modifierEntry, int i) {
            return 0x90FFFFFF;
        }

        @Override
        public boolean showShadow(IToolStackView tool, ModifierEntry entry, int barsHadBeenShown) {
            return false;
        }
    }
}
