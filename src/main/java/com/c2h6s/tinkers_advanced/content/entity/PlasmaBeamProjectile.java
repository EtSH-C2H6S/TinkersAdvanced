package com.c2h6s.tinkers_advanced.content.entity;

import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import com.c2h6s.etstlib.util.AttackUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import javax.annotation.Nullable;

public class PlasmaBeamProjectile extends VisualScaledProjectile {
    public static final EntityDataAccessor<Float> DATA_LENGTH = SynchedEntityData.defineId(PlasmaBeamProjectile.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> DATA_RENDER = SynchedEntityData.defineId(PlasmaBeamProjectile.class, EntityDataSerializers.BOOLEAN);
    public ToolStack tool;
    public FluidStack fluidStack;
    public boolean OffHand;
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_LENGTH,0f);
        this.entityData.define(DATA_RENDER,false);
    }

    public boolean readyToRender(){
        return this.entityData.get(DATA_RENDER);
    }

    public void setDataLength(float amount){
        this.entityData.set(DATA_LENGTH,amount);
    }
    public float getDataLength(){
        return this.entityData.get(DATA_LENGTH);
    }

    public PlasmaBeamProjectile(EntityType<? extends VisualScaledProjectile> pEntityType, Level pLevel, float scale) {
        super(pEntityType, pLevel);
        this.setScale(scale);
    }
    public PlasmaBeamProjectile(EntityType<? extends VisualScaledProjectile> pEntityType, Level pLevel) {
        this(pEntityType, pLevel,1);
    }
    public PlasmaBeamProjectile(Level pLevel,float Scale) {
        this(TiAcEntities.PLASMA_BEAM.get(), pLevel,Scale);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (this.firstTick&&this.getOwner() instanceof Player player) {
                this.tickCount = 0;
                Vec3 initialPos = new Vec3(this.getX(),this.getY(),this.getZ());
                double distance =this.getDataLength();
                float scale = this.getScale();
                Vec3 direction = this.getDeltaMovement().normalize();
                Vec3 step = direction.scale(scale*0.5);
                this.setDeltaMovement(step);
                for (double i = 0; i <= distance; i += scale*0.5) {
                    Vec3 pos = this.position();
                    Vec3 toPos = pos.add(step);
                    HitResult hitresult = this.level().clip(new ClipContext(pos, toPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                    if (hitresult.getType() == HitResult.Type.MISS){
                        hitresult =null;
                    }
                    EntityHitResult entityhitresult = this.findHitEntity(pos, toPos);
                    EntityHitResult livinghitresult = this.findHitLivingEntity(pos, toPos);
                    if (entityhitresult != null) {
                        hitresult = entityhitresult;
                    }
                    if (livinghitresult!=null){
                        entityhitresult = livinghitresult;
                        hitresult = livinghitresult;
                    }
                    if (hitresult!=null&&hitresult.getType() == HitResult.Type.ENTITY) {
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
                    if (hitresult != null && hitresult.getType() == HitResult.Type.BLOCK) toPos = this.position();

                    this.setPos(toPos);

                    if (entityhitresult!=null&&entityhitresult.getType()!= HitResult.Type.MISS){
                        AttackUtil.attackEntity(tool,player,player.getUsedItemHand(),entityhitresult.getEntity(),()->1,false,player.getUsedItemHand()== InteractionHand.MAIN_HAND? EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND,true,this.baseDamage,true);
                    }
                    if (hitresult!=null){
                        Vec3 path = this.position().subtract(initialPos);
                        PlasmaExplosionProjectile projectile = new PlasmaExplosionProjectile(this.level(),scale);
                        projectile.fluidStack = this.fluidStack;
                        projectile.setPos(this.position());
                        projectile.baseDamage =this.baseDamage/2;
                        projectile.setOwner(this.getOwner());
                        this.level().addFreshEntity(projectile);
                        float length = (float) (path.length());
                        this.setDataLength(length);
                        Vec3 offset = player.getLookAngle().cross(new Vec3(0,1,0)).normalize().scale(0.6f);
                        if (OffHand){
                            offset = offset.reverse();
                        }
                        Vec3 newDirection = path.subtract(offset).normalize();
                        this.setDeltaMovement(newDirection);
                        this.setPos(initialPos.add(offset));
                        this.xOld=this.getX();
                        this.yOld=this.getY();
                        this.zOld=this.getZ();
                        this.entityData.set(DATA_RENDER,true);
                        break;
                    }
                    if (i>=distance-scale){
                        Vec3 path = this.position().subtract(initialPos);
                        PlasmaExplosionProjectile projectile = new PlasmaExplosionProjectile(this.level(),scale);
                        projectile.fluidStack = this.fluidStack;
                        projectile.baseDamage =this.baseDamage/2;
                        projectile.setPos(this.position());
                        projectile.setOwner(this.getOwner());
                        this.level().addFreshEntity(projectile);
                        float length = (float) (path.length());
                        this.setDataLength(length);
                        Vec3 offset = player.getLookAngle().cross(new Vec3(0,1,0)).normalize().scale(0.6f);
                        if (OffHand){
                            offset = offset.reverse();
                        }
                        Vec3 newDirection = path.subtract(offset).normalize();
                        this.setDeltaMovement(newDirection);
                        this.setPos(initialPos.add(offset));
                        this.xOld=this.getX();
                        this.yOld=this.getY();
                        this.zOld=this.getZ();
                        this.entityData.set(DATA_RENDER,true);
                        break;
                    }
                }
            }
            if (this.tickCount >= 9) {
                this.discard();
            }
        }
        super.tick();
    }
    @Nullable
    protected EntityHitResult findHitLivingEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), (entity)->(entity instanceof LivingEntity)&&entity.canBeHitByProjectile()&&entity!=this.getOwner());
    }
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), (entity)->!(entity instanceof LivingEntity)&&entity.canBeHitByProjectile()&&entity!=this.getOwner());
    }

}
