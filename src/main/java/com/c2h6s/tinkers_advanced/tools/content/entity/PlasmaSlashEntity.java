package com.c2h6s.tinkers_advanced.tools.content.entity;

import com.c2h6s.etstlib.util.AttackUtil;
import com.c2h6s.tinkers_advanced.core.content.entity.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.tools.init.TiAcTEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.HashSet;
import java.util.List;

public class PlasmaSlashEntity extends VisualScaledProjectile {
    public static final EntityDataAccessor<Boolean> DATA_POWERED = SynchedEntityData.defineId(PlasmaSlashEntity.class, EntityDataSerializers.BOOLEAN);

    public PlasmaSlashEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }
    public PlasmaSlashEntity(Level pLevel) {
        this(TiAcTEntities.PLASMA_SLASH.get(), pLevel);
    }
    ToolStack toolStack = null;
    public float rotation = 0;

    public static PlasmaSlashEntity create(@NotNull LivingEntity owner, float scale, Vec3 direction, ToolStack toolStack){
        PlasmaSlashEntity entity = new PlasmaSlashEntity(owner.level());
        entity.setOwner(owner);
        entity.setScale(scale);
        entity.toolStack = toolStack;
        entity.setDeltaMovement(direction);
        double d0 = direction.horizontalDistance();
        entity.setYRot((float)(Mth.atan2(direction.x, direction.z) * (double)(180F / (float)Math.PI)));
        entity.setXRot((float)(Mth.atan2(direction.y, d0) * (double)(180F / (float)Math.PI)));
        entity.setPos(owner.position().add(0,0.5*owner.getBbHeight(),0).add(direction.scale(scale)));
        return entity;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_POWERED,false);
    }

    public void setPowered(boolean powered){
        this.entityData.set(DATA_POWERED,powered);
    }
    public boolean isPowered(){
        return this.entityData.get(DATA_POWERED);
    }

    public HashSet<Entity> set = new HashSet<>();

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isInvisible() {
        return super.isInvisible();
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.toolStack != null && this.getOwner() instanceof LivingEntity living && !this.firstTick && !this.level().isClientSide) {
            List<Entity> entities = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(this.getScale()), this::canHitEntity);
            for (int i = 0; i < 8 && i < entities.size(); i++) {
                Entity entity = entities.get(i);
                set.add(entity);
                entity.invulnerableTime = 0;
                AttackUtil.attackEntity(this.toolStack,
                        living,
                        entity,
                        0,
                        this.baseDamage,
                        true
                );
            }
        }
        if (this.getOwner() != null) {
            Vec3 vec3 = this.getDeltaMovement().normalize();
            this.setPos(this.getOwner().position().add(0, 0.5 * this.getOwner().getBbHeight(), 0).add(vec3.scale(this.getScale())));
        }
        if (this.tickCount >= 8) {
            this.set.clear();
            this.discard();
        }
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return !set.contains(pTarget) && pTarget != this.getOwner() &&
                !( pTarget instanceof ItemEntity || pTarget instanceof ExperienceOrb)&&
                !(pTarget instanceof Player)&&pTarget!=this;
    }
}
