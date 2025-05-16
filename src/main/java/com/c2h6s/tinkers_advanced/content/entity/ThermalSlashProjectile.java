package com.c2h6s.tinkers_advanced.content.entity;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.List;

public class ThermalSlashProjectile extends Projectile {
    public float baseDamage=4;
    public int modifierLevel=1;
    public float rotDeg=0;
    public IntOpenHashSet set = new IntOpenHashSet();

    public ThermalSlashProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThermalSlashProjectile(Level level){
        this(TiAcEntities.THERMAL_SLASH.get(),level);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount>14) this.discard();
        if (!this.level().isClientSide&&!this.isRemoved()){
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class,this.getBoundingBox().inflate(1).expandTowards(this.getDeltaMovement()));
            for (Entity entity:list){
                if (entity.canBeHitByProjectile()&&entity!=this&&!set.contains(entity.getId())&&entity!=this.getOwner()){
                    if (entity instanceof Player player&&this.getOwner() instanceof Player player1){
                        if (!player1.canHarmPlayer(player)){
                            continue;
                        }
                    }
                    entity.hurt(LegacyDamageSource.any(this.damageSources().mobProjectile(this,this.getOwner() instanceof LivingEntity living?living:null)).setBypassArmor().setBypassInvulnerableTime(),this.baseDamage);
                    if (entity instanceof LivingEntity living){
                        living.forceAddEffect(new MobEffectInstance(CoreMobEffects.CHILLED.get(),100+50*modifierLevel,modifierLevel),this.getOwner());
                        living.forceAddEffect(new MobEffectInstance(CoreMobEffects.SHOCKED.get(),100+50*modifierLevel,modifierLevel),this.getOwner());
                        living.forceAddEffect(new MobEffectInstance(CoreMobEffects.SUNDERED.get(),100+50*modifierLevel,modifierLevel),this.getOwner());
                    }
                    set.add(entity.getId());
                }
            }
        }
        setPos(this.position().add(this.getDeltaMovement()));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putFloat("base_damage", this.baseDamage);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.baseDamage = pCompound.getFloat("base_damage");
    }
}
