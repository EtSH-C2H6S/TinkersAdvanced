package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.UUID;

public class IonizedModifier extends EtSTBaseModifier implements OnAttackedModifierHook {
    public static final String KEY_IONIZED = "tinkers_advanced_ionized";
    public static final UUID IONIZED_UUID = UUID.fromString("f839d735-470b-5120-7257-68aa4bdde97c");

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage) {
        if (context.getTarget() instanceof LivingEntity living){
            living.getPersistentData().putInt(CommonUtil.KEY_ATTACKER,context.getAttacker().getId());
            living.getPersistentData().putInt(KEY_IONIZED,living.getPersistentData().getInt(KEY_IONIZED)+200*modifier.getLevel());
        }
    }
    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (attacker!=null){
            target.getPersistentData().putInt(CommonUtil.KEY_ATTACKER,attacker.getId());
        }
        target.getPersistentData().putInt(KEY_IONIZED,target.getPersistentData().getInt(KEY_IONIZED)+200*entry.getLevel());
    }

    @Override
    public void onAttacked(IToolStackView iToolStackView, ModifierEntry modifier, EquipmentContext context, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {
        if (damageSource.getEntity() instanceof LivingEntity living){
            living.getPersistentData().putInt(CommonUtil.KEY_ATTACKER,context.getEntity().getId());
            living.getPersistentData().putInt(KEY_IONIZED,living.getPersistentData().getInt(KEY_IONIZED)+200*modifier.getLevel());
        }
    }
}
