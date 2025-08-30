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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public class ThermalEnhance extends EtSTBaseModifier implements OnAttackedModifierHook, DamageBlockModifierHook, ToolStatsModifierHook {
    @Override
    public void addToolStats(IToolContext iToolContext, ModifierEntry modifierEntry, ModifierStatsBuilder builder) {
        ToolStats.ARMOR.add(builder,modifierEntry.getLevel());
        ToolStats.ARMOR_TOUGHNESS.add(builder,modifierEntry.getLevel());
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED,ModifierHooks.DAMAGE_BLOCK,ModifierHooks.TOOL_STATS);
    }
    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (context.getTarget().isOnFire()){
            context.getTarget().setSecondsOnFire(0);
            return damage+baseDamage;
        }
        return damage;
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (context.getTarget() instanceof LivingEntity living&&context.isFullyCharged()) {
            living.forceAddEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(), 80 + 40 * modifier.getLevel(), modifier.getLevel() - 1), context.getAttacker());
            living.invulnerableTime = 0;
        }
        return knockback;
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (projectile instanceof AbstractArrow arrow &&arrow.isCritArrow()&&target!=null){
            target.forceAddEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(),80+40*modifier.getLevel(),modifier.getLevel()-1),attacker);
            target.invulnerableTime=0;
        }
        return false;
    }

    @Override
    public void afterMeleeHit(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.isFullyCharged()&&context.getTarget() instanceof LivingEntity living){
            living.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),80+40*modifier.getLevel(),modifier.getLevel()-1),context.getAttacker());
            int amount = RANDOM.nextInt(modifier.getLevel()+1);
            List<Entity> list = living.level().getEntitiesOfClass(Entity.class,new AABB(living.blockPosition().above()).inflate(4+modifier.getLevel()));
            list.remove(living);
            list.remove(context.getAttacker());
            if (list.isEmpty()) return;
            LegacyDamageSource source = LegacyDamageSource.any(new DamageSource(living.damageSources().lightningBolt().typeHolder(),context.getAttacker())).setBypassArmor().setBypassInvulnerableTime();
            int i=0;
            while (i<amount&&!list.isEmpty()){
                Entity entity = list.get(RANDOM.nextInt(list.size()));
                list.remove(entity);
                if (entity instanceof ItemEntity||entity instanceof ExperienceOrb) continue;
                if (entity.hurt(source,2+modifier.getLevel()*0.5f)&&entity.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(), 0.2F, 4.0F, 0.0F, -1, -240988),living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),0,entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),1);
                    if (entity instanceof LivingEntity livingEntity){
                        livingEntity.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),80+40*modifier.getLevel(),modifier.getLevel()-1),context.getAttacker());
                        livingEntity.forceAddEffect(new MobEffectInstance(MobEffects.GLOWING,100*modifier.getLevel(),0),context.getAttacker());
                    }
                    i++;
                }
            }
            living.hurt(LegacyDamageSource.any(living.damageSources().lava().typeHolder(),context.getAttacker()).setBypassInvulnerableTime(),modifier.getLevel());
            living.forceAddEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),100+50*modifier.getLevel(),modifier.getLevel()-1),context.getAttacker());
            living.forceAddEffect(new MobEffectInstance(MobEffects.GLOWING,100+50*modifier.getLevel(),0),context.getAttacker());
        }
    }

    @Override
    public float onGetArrowDamage(ModDataNBT persistentData, ModifierEntry entry, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull Entity target, float baseDamage, float damage) {
        if (target.isOnFire()){
            target.setSecondsOnFire(0);
            return damage+baseDamage;
        }
        return damage;
    }
    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry modifier, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (arrow.isCritArrow()){

            if (attacker!=null){
                target.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),80+40*modifier.getLevel(),modifier.getLevel()-1),attacker);
                int amount = RANDOM.nextInt(modifier.getLevel()+1);
                List<Entity> list = target.level().getEntitiesOfClass(Entity.class,new AABB(target.blockPosition().above()).inflate(4+modifier.getLevel()));
                list.remove(target);
                list.remove(attacker);
                if (list.isEmpty()) return;
                LegacyDamageSource source = LegacyDamageSource.any(new DamageSource(target.damageSources().lightningBolt().typeHolder(),attacker)).setBypassArmor().setBypassInvulnerableTime();
                int i=0;
                while (i<amount&&!list.isEmpty()){
                    Entity entity = list.get(RANDOM.nextInt(list.size()));
                    list.remove(entity);
                    if (entity instanceof ItemEntity) continue;
                    if (entity.hurt(source,2+modifier.getLevel()*0.5f)&&entity.level() instanceof ServerLevel serverLevel){
                        serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(), 0.2F, 4.0F, 0.0F, -1, -240988),target.getX(),target.getY()+0.5*target.getBbHeight(),target.getZ(),0,entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),1);
                        if (entity instanceof LivingEntity livingEntity){
                            livingEntity.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),80+40*modifier.getLevel(),modifier.getLevel()-1),attacker);
                            livingEntity.forceAddEffect(new MobEffectInstance(MobEffects.GLOWING,100*modifier.getLevel(),0),attacker);
                        }
                        i++;
                    }
                }
            }
            target.hurt(LegacyDamageSource.any(target.damageSources().lava().typeHolder(),attacker).setBypassInvulnerableTime(),modifier.getLevel());
            target.forceAddEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),100+50*modifier.getLevel(),modifier.getLevel()-1),attacker);
        }
    }


    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isCorrectSlot) {
            holder.addEffect(new MobEffectInstance(CoreMobEffects.COLD_RESISTANCE.get(), 100, 0, false, false,false));
            holder.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(),100,0,false,false,false));
            holder.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(), 100, 0, false, false,false));
            holder.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 0, false, false,false));
            holder.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false,false));
            if (holder.isOnFire()||holder.isInLava()){
                if (tool.getDamage()>0&&RANDOM.nextInt(10)<modifier.getLevel()&&!world.isClientSide){
                    tool.setDamage(tool.getDamage()-1);
                }
            }
        }
    }

    @Override
    public void onAttacked(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float amount, boolean b) {
        if (damageSource.getEntity() instanceof LivingEntity living){
            living.addEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),100+50*modifierEntry.getLevel(),modifierEntry.getLevel()-1));
        }
    }

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slot, DamageSource source, float amount) {
        if (RANDOM.nextInt(20)<modifier.getLevel()&&source.getEntity() instanceof LivingEntity living){
            living.addEffect(new MobEffectInstance( CoreMobEffects.SHOCKED.get(),100,modifier.getLevel()-1));
            if (!source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS)){
                LegacyDamageSource damageSource = LegacyDamageSource.any(new DamageSource(living.damageSources().lightningBolt().typeHolder(),context.getEntity())).setBypassArmor().setBypassInvulnerableTime().setThorn();
                if (living.hurt(damageSource,2+0.5f*modifier.getLevel())&&living.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(), 0.2F, 4.0F, 0.0F, -1, -240988),context.getEntity().getX(),context.getEntity().getY()+0.5*context.getEntity().getBbHeight(),context.getEntity().getZ(),0,living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),1);
                }
            }
            return true;
        }
        return false;
    }
}
