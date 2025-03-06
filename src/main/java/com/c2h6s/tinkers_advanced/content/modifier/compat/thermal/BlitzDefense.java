package com.c2h6s.tinkers_advanced.content.modifier.compat.thermal;

import cofh.core.client.particle.options.BiColorParticleOptions;
import cofh.core.init.CoreMobEffects;
import cofh.core.init.CoreParticles;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class BlitzDefense extends EtSTBaseModifier implements DamageBlockModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_BLOCK);
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isCorrectSlot){
            holder.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(),10,0,false,false));
        }
    }

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount) {
        if (RANDOM.nextInt(20)<modifier.getLevel()&&source.getEntity() instanceof LivingEntity living){
            living.addEffect(new MobEffectInstance( CoreMobEffects.SHOCKED.get(),100,modifier.getLevel()-1));
            if (!source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)){
                LegacyDamageSource damageSource = LegacyDamageSource.any(new DamageSource(living.damageSources().lightningBolt().typeHolder(),context.getEntity())).setBypassArmor().setBypassInvulnerableTime().setThorn();
                if (living.hurt(damageSource,2+0.5f*modifier.getLevel())&&living.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(), 0.2F, 4.0F, 0.0F, -1, -240988),context.getEntity().getX(),context.getEntity().getY()+0.5*context.getEntity().getBbHeight(),context.getEntity().getZ(),0,living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),0);
                }
            }
            return true;
        }
        return false;
    }
}
