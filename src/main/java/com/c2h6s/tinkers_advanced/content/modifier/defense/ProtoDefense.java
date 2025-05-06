package com.c2h6s.tinkers_advanced.content.modifier.defense;

import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.hooks.EffectApplicableModifierHook;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.UUID;

public class ProtoDefense extends EtSTBaseModifier implements EffectApplicableModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, EtSTLibHooks.EFFECT_APPLICABLE);
    }

    @Override
    public Boolean isApplicable(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentSlot equipmentSlot, MobEffectInstance mobEffectInstance, Boolean aBoolean) {
        return mobEffectInstance.getEffect().getCategory()== MobEffectCategory.HARMFUL;
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isCorrectSlot&&holder.getItemBySlot(EquipmentSlot.CHEST)==stack){
            holder.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,20,modifier.getLevel()-1,false,false));
            holder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20,modifier.getLevel()-1,false,false));
            holder.addEffect(new MobEffectInstance(MobEffects.JUMP,20,modifier.getLevel()-1,false,false));
        }
    }
}
