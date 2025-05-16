package com.c2h6s.tinkers_advanced.content.entity;

import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.content.item.toolItem.MatterManipulator;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MiningBeamProjectile extends VisualScaledProjectile {
    public static final EntityDataAccessor<Integer> DATA_TICK = SynchedEntityData.defineId(MiningBeamProjectile.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DATA_PROGRESS = SynchedEntityData.defineId(MiningBeamProjectile.class, EntityDataSerializers.INT);
    public ItemStack stack;
    public boolean render =false;
    public MiningBeamProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public MiningBeamProjectile(Level pLevel,float Scale) {
        this(TiAcEntities.MINING_BEAM.get(), pLevel);
        this.setScale(Scale);
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public MiningBeamProjectile(Level pLevel) {
        this(pLevel,1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TICK,0);
        this.entityData.define(DATA_PROGRESS,0);
    }
    public int getTick(){
        return this.entityData.get(DATA_TICK);
    }
    public void setTick(int value){
        this.entityData.set(DATA_TICK,value);
    }

    public int getProgress(){
        return this.entityData.get(DATA_PROGRESS);
    }
    public void setProgress(int value){
        this.entityData.set(DATA_PROGRESS,value);
    }

    @Override
    public void tick() {
        this.setTick(this.getTick()+1);
        if (this.getOwner() instanceof Player player&&stack!=null&&stack.getItem() instanceof MatterManipulator item&&!this.level().isClientSide) {
            if (this.getTick() < 10) {
                if (player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof MatterManipulator) {
                    this.setTick(0);
                } else this.setTick(10);
            }
            InteractionHand hand = player.getUsedItemHand();
            Vec3 offset = player.getLookAngle().cross(new Vec3(0, 1, 0)).normalize().scale(0.3f);
            boolean OffHand = hand == InteractionHand.OFF_HAND;
            if (offset.length() == 0) offset = new Vec3(0.3, 0, 0);
            if (OffHand) {
                offset = offset.reverse();
            }
            Vec3 finalpos = player.getEyePosition().add(offset);
            this.setPos(finalpos);
        }
        if (this.getTick()>15) this.discard();

    }
}
