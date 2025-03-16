package com.c2h6s.tinkers_advanced.content.modifier.compat.thermal;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class BlizzDefense extends EtSTBaseModifier implements OnAttackedModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isCorrectSlot) {
            holder.addEffect(new MobEffectInstance(CoreMobEffects.COLD_RESISTANCE.get(), 10, 0, false, false));
        }
    }

    @Override
    public void onAttacked(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float amount, boolean b) {
        if (damageSource.getEntity() instanceof LivingEntity living){
            living.addEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),100+50*modifierEntry.getLevel(),modifierEntry.getLevel()-1));
        }
    }
}
