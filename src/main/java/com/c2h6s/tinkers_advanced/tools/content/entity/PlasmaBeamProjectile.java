package com.c2h6s.tinkers_advanced.tools.content.entity;

import com.c2h6s.tinkers_advanced.core.content.entity.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import com.c2h6s.etstlib.util.AttackUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

public class PlasmaBeamProjectile extends VisualScaledProjectile {
    public static final EntityDataAccessor<Float> DATA_LENGTH = SynchedEntityData.defineId(PlasmaBeamProjectile.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> DATA_RENDER = SynchedEntityData.defineId(PlasmaBeamProjectile.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> DATA_PIERCE = SynchedEntityData.defineId(PlasmaBeamProjectile.class, EntityDataSerializers.INT);
    public ToolStack tool;
    public FluidStack fluidStack;
    public boolean OffHand;
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_LENGTH,0f);
        this.entityData.define(DATA_RENDER,false);
        this.entityData.define(DATA_PIERCE,0);
    }
    public IntOpenHashSet piercedEntityList = new IntOpenHashSet();

    @Override
    public boolean isAttackable() {
        return false;
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

    public void setDataPierce(int amount){
        this.entityData.set(DATA_PIERCE,amount);
    }
    public int getDataPierce(){
        return this.entityData.get(DATA_PIERCE);
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
    protected boolean canHitEntity(Entity pTarget) {
        if (pTarget instanceof Projectile||
                pTarget instanceof ItemEntity||
                pTarget instanceof ExperienceOrb||
                pTarget==this.getOwner()) return false;
        if (piercedEntityList.contains(pTarget.getId())) return false;
        if (pTarget instanceof LivingEntity){
            if (this.getOwner() instanceof Player player1){
                if (pTarget instanceof Player player) return player1.canHarmPlayer(player);
            }
        }
        return pTarget.isAlive();
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
                Vec3 end = null;
                HitResult hitResult = this.level().clip(new ClipContext(initialPos,initialPos.add(direction.scale(distance)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null));
                for (double i = 0; i <= distance; i += scale*0.5) {
                    Vec3 pos = initialPos.add(direction.scale(i));
                    AABB aabb = new AABB(pos.x-scale*0.5,pos.y-scale*0.5,pos.z-scale*0.5,pos.x+scale*0.5,pos.y+scale*0.5,pos.z+scale*0.5);
                    aabb.expandTowards(step);
                    List<Entity> entities = this.level().getEntitiesOfClass(Entity.class,aabb,this::canHitEntity);
                    for (Entity entity:entities){
                        if (entity instanceof LivingEntity living){
                            AttackUtil.attackEntityWithBaseDamage(tool,player,living,baseDamage,1,true);
                            if (hitResult.getType() != HitResult.Type.ENTITY) hitResult = new EntityHitResult(living,pos.add(step.scale(0.5)));
                            piercedEntityList.add(entity.getId());
                        } else {
                            entity.hurt(this.damageSources().thrown(this,this.getOwner()),this.baseDamage);
                        }
                        if (piercedEntityList.size()>getDataPierce()) {
                            break;
                        }
                    }
                    end = pos.add(step.scale(0.5));
                    if (piercedEntityList.size()>getDataPierce()){
                        this.setDataLength((float) i);
                        break;
                    }
                }
                if (hitResult.getType()== HitResult.Type.MISS){
                    Vec3 path = direction.scale(distance);
                    PlasmaExplosionProjectile projectile = new PlasmaExplosionProjectile(this.level(),scale);
                    projectile.fluidStack = this.fluidStack;
                    projectile.baseDamage =this.baseDamage/2;
                    projectile.setPos(initialPos.add(path));
                    projectile.setOwner(this.getOwner());
                    this.level().addFreshEntity(projectile);
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
                } else {
                    Vec3 path;
                    if (end!=null) path = end.subtract(initialPos);
                    else path = hitResult.getLocation().subtract(initialPos);
                    PlasmaExplosionProjectile projectile = new PlasmaExplosionProjectile(this.level(),scale);
                    projectile.fluidStack = this.fluidStack;
                    projectile.baseDamage =this.baseDamage/2;
                    Vec3 explosionPos = hitResult.getLocation().subtract(step.scale(0.5));
                    projectile.setPos(explosionPos);
                    projectile.setOwner(this.getOwner());
                    this.level().addFreshEntity(projectile);
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
                }
            }
            if (this.tickCount >= 9) {
                this.discard();
            }
        }
        super.tick();
    }
}
