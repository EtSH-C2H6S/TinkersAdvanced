package com.c2h6s.tinkers_advanced.util;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FakeExplosionUtil {
    public static void fakeExplode(float damage,@NotNull LivingEntity causingEntity,@NotNull Level level,@NotNull Vec3 position,@NotNull IntOpenHashSet blackListEntityIds,boolean setThorn){
        float maxRadius = (float) (Math.log10(damage)/Math.log10(1.6))-1;
        LegacyDamageSource damageSource = LegacyDamageSource.explosion(causingEntity,causingEntity).setBypassInvulnerableTime();
        if (setThorn){
            damageSource.setThorn();
        }
        List<Entity> list = level.getEntitiesOfClass(Entity.class,new AABB(position.x+maxRadius,position.y+maxRadius,position.z+maxRadius,position.x-maxRadius,position.y-maxRadius,position.z-maxRadius));
        level.playSound(null,position.x,position.y,position.z, SoundEvents.GENERIC_EXPLODE,causingEntity.getSoundSource(),1,1);
        if (level instanceof ServerLevel serverLevel){
            serverLevel.sendParticles(maxRadius>3 ? ParticleTypes.EXPLOSION_EMITTER:ParticleTypes.EXPLOSION, position.x ,position.y ,position.z,1,0,0,0,0);
        }
        for (Entity entity:list){
            if (entity!=null&&!entity.isInvulnerableTo(damageSource)&&!entity.isInvulnerable()&&!blackListEntityIds.contains(entity.getId())&&!(entity instanceof ItemEntity)&&!(entity instanceof ExperienceOrb)){
                float radius = (float) entity.position().subtract(position).length();
                float actualDamage = (float) (damage*Math.pow(0.625,radius-1));
                entity.hurt(damageSource,actualDamage);
            }
        }
    }
}
