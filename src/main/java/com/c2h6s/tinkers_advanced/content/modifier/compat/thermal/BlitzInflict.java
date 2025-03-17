package com.c2h6s.tinkers_advanced.content.modifier.compat.thermal;

import cofh.core.client.particle.options.BiColorParticleOptions;
import cofh.core.init.CoreMobEffects;
import cofh.core.init.CoreParticles;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.List;

public class BlitzInflict extends EtSTBaseModifier {
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
                if (entity instanceof ItemEntity) continue;
                if (entity.hurt(source,2+modifier.getLevel()*0.5f)&&entity.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(new BiColorParticleOptions(CoreParticles.STRAIGHT_ARC.get(), 0.2F, 4.0F, 0.0F, -1, -240988),living.getX(),living.getY()+0.5*living.getBbHeight(),living.getZ(),0,entity.getX(),entity.getY()+0.5*entity.getBbHeight(),entity.getZ(),1);
                    if (entity instanceof LivingEntity livingEntity){
                        livingEntity.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),80+40*modifier.getLevel(),modifier.getLevel()-1),context.getAttacker());
                        livingEntity.forceAddEffect(new MobEffectInstance(MobEffects.GLOWING,100*modifier.getLevel(),0),context.getAttacker());
                    }
                    i++;
                }
            }
        }
    }
    @Override
    public void afterArrowHit(ModDataNBT persistentData, ModifierEntry modifier, ModifierNBT modifiers, AbstractArrow arrow, @Nullable LivingEntity attacker, @NotNull LivingEntity target, float damageDealt) {
        if (arrow.isCritArrow()&&attacker!=null){
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
    }
}
