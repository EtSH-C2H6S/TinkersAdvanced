package com.c2h6s.tinkers_advanced.content.entity;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.List;

public class AirSlashProjectile extends Projectile {
    public IntOpenHashSet set = new IntOpenHashSet();
    public float baseDamage;
    public int ticks;
    public float rotDeg=0;
    public AirSlashProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public AirSlashProjectile(Level pLevel) {
        this(TiAcEntities.AIR_SLASH.get(), pLevel);
    }


    @Override
    protected void defineSynchedData() {

    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount>9) this.discard();
        if (!this.level().isClientSide&&!this.isRemoved()){
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class,this.getBoundingBox().inflate(1).expandTowards(this.getDeltaMovement()));
            for (Entity entity:list){
                if (entity.canBeHitByProjectile()&&entity!=this&&!set.contains(entity.getId())&&entity!=this.getOwner()){
                    if (entity instanceof Player player&&this.getOwner() instanceof Player player1){
                        if (!player1.canHarmPlayer(player)){
                            continue;
                        }
                    }
                    entity.hurt(LegacyDamageSource.any(this.damageSources().mobProjectile(this,this.getOwner() instanceof LivingEntity living?living:null)).setBypassEnchantment().setBypassInvulnerableTime().setBypassMagic(),this.baseDamage);
                    set.add(entity.getId());
                    this.baseDamage*=0.75f;
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
