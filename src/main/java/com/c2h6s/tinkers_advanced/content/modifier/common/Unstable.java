package com.c2h6s.tinkers_advanced.content.modifier.common;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class Unstable extends EtSTBaseModifier implements BreakSpeedModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED);
    }

    public float getDamageMultiplier(ModifierEntry entry){
        float multiplier = RANDOM.nextFloat();
        if (multiplier<0.75){
            multiplier*=2;
            return -(1-(1/(1+multiplier*entry.getLevel())));
        } else {
            multiplier-=0.5f;
            multiplier*=2;
            return multiplier*entry.getLevel();
        }
    }

    @Override
    public float onGetArrowDamage(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull Entity target, float baseDamage, float damage) {
        return damage+baseDamage*getDamageMultiplier(entry);
    }

    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        return damage+baseDamage*getDamageMultiplier(modifier);
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        return RANDOM.nextInt(amount*2*modifier.getLevel());
    }

    @Override
    public void onBreakSpeed(IToolStackView iToolStackView, ModifierEntry modifierEntry, PlayerEvent.BreakSpeed breakSpeed, Direction direction, boolean b, float v) {
        float multiplier = RANDOM.nextFloat();
        if (multiplier<0.5){
            multiplier*=2;
            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed()/(1+multiplier*modifierEntry.getLevel()));
        }else {
            multiplier-=0.5f;
            multiplier*=2;
            breakSpeed.setNewSpeed(breakSpeed.getNewSpeed()*(1+multiplier*modifierEntry.getLevel()));
        }
    }
}
