package com.c2h6s.tinkers_advanced.content.entity;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectContext;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffectManager;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffects;

import java.util.List;

public class PlasmaExplosionProjectile extends VisualScaledProjectile {
    public FluidStack fluidStack;
    public PlasmaExplosionProjectile(EntityType<? extends VisualScaledProjectile> pEntityType, Level pLevel, float scale) {
        super(pEntityType, pLevel);
        this.setScale(scale);
    }

    public PlasmaExplosionProjectile(Level pLevel, float scale){
        this(TiAcEntities.PLASMA_EXPLOSION.get(), pLevel,scale);
    }

    public PlasmaExplosionProjectile(EntityType<? extends VisualScaledProjectile> entityEntityType, Level level) {
        this(entityEntityType, level,1);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate((this.getScale()-1)*2);
    }

    @Override
    public void tick() {
        if (this.firstTick){
            this.tickCount=0;
        }
        if (this.tickCount>=14){
            this.discard();
        }
        super.tick();
        if (this.tickCount ==7&&this.getOwner() instanceof Player player&&!this.level().isClientSide){
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class,this.getBoundingBoxForCulling());
            if (!list.isEmpty()){
                for (Entity entity:list){
                    if (entity!=this.getOwner()) {
                        if (!(entity instanceof LivingEntity)) {
                            entity.hurt(this.damageSources().playerAttack(player), 2);
                        } else if (this.fluidStack != null && FluidEffectManager.INSTANCE.find(fluidStack.getFluid()).hasEntityEffects()) {
                            entity.invulnerableTime = 0;
                            FluidEffects effects = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
                            FluidEffectContext.Entity context = new FluidEffectContext.Entity(this.level(), player, this, entity);
                            effects.applyToEntity(new FluidStack(fluidStack.getFluid(), 1000), 2f*this.getScale(), context, IFluidHandler.FluidAction.EXECUTE);
                        } else entity.hurt(this.damageSources().mobProjectile(this, player), 2);
                    }
                }
            }
            if (this.level() instanceof ServerLevel serverLevel){
                if (this.getScale()>2) {
                    serverLevel.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                }
                serverLevel.sendParticles(ParticleTypes.FIREWORK,this.getX(),this.getY(),this.getZ(),(int)( 12*this.getScale()),0.05*this.getScale(),0.05*this.getScale(),0.05*this.getScale(),this.getScale()*0.2F);
                serverLevel.playSeededSound(null,this.getX(),this.getY(),this.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS,1,1, TinkersAdvanced.RANDOM.nextLong());
            }
        }
    }
}
