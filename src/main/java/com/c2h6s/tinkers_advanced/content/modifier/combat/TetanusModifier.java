package com.c2h6s.tinkers_advanced.content.modifier.combat;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.registery.TiAcEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
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

public class TetanusModifier extends EtSTBaseModifier implements OnAttackedModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getTarget() instanceof LivingEntity entity){
            entity.addEffect(new MobEffectInstance(TiAcEffects.TETANUS.get(),200+40*modifier.getLevel(),modifier.getLevel()-1));
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target!=null){
            target.addEffect(new MobEffectInstance(TiAcEffects.TETANUS.get(),200+40*modifier.getLevel(),modifier.getLevel()-1));
        }
        return false;
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float amount, boolean directDamage) {
        if (damageSource.getEntity()==equipmentContext.getEntity()||damageSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)||damageSource.getEntity()==null){
            return;
        }
        damageSource.getEntity().hurt(damageSource.getEntity().damageSources().thorns(equipmentContext.getEntity()),amount*0.2f);
        if (damageSource.getEntity() instanceof LivingEntity living){
            living.addEffect(new MobEffectInstance(TiAcEffects.TETANUS.get(),200+40*modifier.getLevel(),modifier.getLevel()-1));
        }
    }
}
