package com.c2h6s.tinkers_advanced.content.entity;

import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PlasmaBeamProjectile extends VisualScaledProjectile {
    public PlasmaBeamProjectile(EntityType<? extends VisualScaledProjectile> pEntityType, Level pLevel, float scale) {
        super(pEntityType, pLevel);
        this.setScale(scale);
    }
    public PlasmaBeamProjectile(EntityType<? extends VisualScaledProjectile> pEntityType, Level pLevel) {
        this(pEntityType, pLevel,1);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate((this.getScale()-1)*0.25);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (this.firstTick) {
                this.tickCount = 0;
                double distance = this.getDeltaMovement().length();
                Vec3 direction = this.getDeltaMovement().normalize();
                float scale = this.getScale();
                for (double i = 0; i <= distance; i += scale) {
                    AABB aabb = this.getBoundingBox().inflate((scale - 1) * 0.25);
                    Vec3 position = this.position();
                    Vec3 movement = position.add(direction.scale(scale));
                    HitResult hitresult = this.level().clip(new ClipContext(position, movement, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                    if (hitresult.getType() != HitResult.Type.MISS) {
                        movement = hitresult.getLocation();
                    }
                    EntityHitResult entityhitresult = this.findHitEntity(position, movement);
                    if (entityhitresult != null&&this.getOwner() instanceof Player player) {
                        entityhitresult.getEntity().hurt(this.damageSources().playerAttack(player),this.baseDamage);
                    }
                    entityhitresult = this.findHitLivingEntity(position, movement);
                    if (entityhitresult != null) {
                        hitresult =entityhitresult;
                    }
                    if (hitresult.getType() == HitResult.Type.ENTITY) {
                        Entity entity = null;
                        if (hitresult instanceof EntityHitResult) {
                            entity = ((EntityHitResult)hitresult).getEntity();
                        }
                        Entity entity1 = this.getOwner();
                        if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
                            hitresult = null;
                            entityhitresult = null;
                        }
                    }
                    if (hitresult!=null){
                        PlasmaExplosionProjectile projectile = new PlasmaExplosionProjectile(this.level(),scale);
                        projectile.setPos(hitresult.getLocation());
                        projectile.baseDamage =this.baseDamage/2;
                        projectile.setOwner(this.getOwner());
                        this.level().addFreshEntity(projectile);
                    }

                }
            } else tickCount++;
            if (this.tickCount >= 3) {
                this.discard();
            }
        }
        super.tick();
    }
    @Nullable
    protected EntityHitResult findHitLivingEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), (entity)->(entity instanceof LivingEntity)&&entity.canBeHitByProjectile());
    }
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), (entity)->!(entity instanceof LivingEntity)&&entity.canBeHitByProjectile());
    }
}
