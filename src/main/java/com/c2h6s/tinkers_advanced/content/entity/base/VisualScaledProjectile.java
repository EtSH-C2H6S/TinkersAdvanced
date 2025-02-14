package com.c2h6s.tinkers_advanced.content.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class VisualScaledProjectile extends Projectile {
    public static final String KEY_SCALE = "scale";
    public static final String KEY_DAMAGE = "damage";
    public float baseDamage = 1;
    public static final EntityDataAccessor<Float> DATA_SCALE = SynchedEntityData.defineId(VisualScaledProjectile.class, EntityDataSerializers.FLOAT);

    public VisualScaledProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setScale(1f);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_SCALE,1f);
    }

    public float getScale(){
        return this.entityData.get(DATA_SCALE);
    }
    public void setScale(float amount){
        this.entityData.set(DATA_SCALE,amount);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setScale(compoundTag.getFloat(KEY_SCALE));
        this.baseDamage = compoundTag.getFloat(KEY_DAMAGE);
    }
    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat(KEY_SCALE,this.getScale());
        compoundTag.putFloat(KEY_DAMAGE,this.baseDamage);
    }
}
