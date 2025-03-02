package com.c2h6s.tinkers_advanced.content.modifier.compat.mekanism;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import mekanism.common.lib.radiation.RadiationManager;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class RadioactiveArmor extends EtSTBaseModifier implements OnAttackedModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void onAttacked(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {
        if (damageSource.getEntity() instanceof LivingEntity living){
            RadiationManager.get().radiate(living,modifierEntry.getLevel()*0.1);
        }
    }
}
