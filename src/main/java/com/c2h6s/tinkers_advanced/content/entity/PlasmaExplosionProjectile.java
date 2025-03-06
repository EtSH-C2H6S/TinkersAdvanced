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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
    protected AABB makeBoundingBox() {
        return super.makeBoundingBox().move(new Vec3(0,-this.getBbHeight()/2,0)).inflate(this.getScale()*2);
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
            float scale = this.getScale();
            float damageScale = 1+scale/4f;
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class,this.getBoundingBoxForCulling());
            if (!list.isEmpty()){
                for (Entity entity:list){
                    if (entity!=this.getOwner()) {
                        boolean b = entity instanceof Player player1 && !player1.canHarmPlayer(player);
                        if (!(entity instanceof LivingEntity)) {
                            entity.hurt(this.damageSources().playerAttack(player), 2);
                        } else if (this.fluidStack != null && FluidEffectManager.INSTANCE.find(fluidStack.getFluid()).hasEntityEffects()&&!b) {
                            entity.invulnerableTime = 0;
                            FluidEffects effects = FluidEffectManager.INSTANCE.find(fluidStack.getFluid());
                            FluidEffectContext.Entity context = new FluidEffectContext.Entity(this.level(), player, this, (LivingEntity) entity);
                            effects.applyToEntity(new FluidStack(fluidStack.getFluid(), 1000), baseDamage*damageScale*0.25f, context, IFluidHandler.FluidAction.EXECUTE);
                        } else entity.hurt(this.damageSources().mobProjectile(this, player), baseDamage/8);
                    }
                }
            }
            if (this.level() instanceof ServerLevel serverLevel){
                if (scale>=2) {
                    serverLevel.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                }
                serverLevel.sendParticles(ParticleTypes.FIREWORK,this.getX(),this.getY(),this.getZ(),(int)( 12*scale),0.05*scale,0.05*scale,0.05*scale,scale*0.2F);
                serverLevel.playSeededSound(null,this.getX(),this.getY(),this.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS,scale*0.5f,1, TinkersAdvanced.RANDOM.nextLong());
            }
        }
    }
}
