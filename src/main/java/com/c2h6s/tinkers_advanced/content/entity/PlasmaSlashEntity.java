package com.c2h6s.tinkers_advanced.content.entity;

import com.c2h6s.etstlib.util.AttackUtil;
import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.HashSet;
import java.util.List;

public class PlasmaSlashEntity extends VisualScaledProjectile {
    public PlasmaSlashEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }
    public PlasmaSlashEntity(Level pLevel) {
        this(TiAcEntities.PLASMA_SLASH.get(), pLevel);
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
        if (this.tickCount > 7) {
            this.set.clear();
            this.discard();
            return;
        }

        if (this.toolStack != null && this.getOwner() instanceof LivingEntity living && !this.firstTick && !this.level().isClientSide) {
            List<Entity> entities = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(this.getScale()), entity -> !set.contains(entity) && entity != this.getOwner());
            for (int i = 0; i < 8 && i < entities.size(); i++) {
                Entity entity = entities.get(i);
                set.add(entity);
                if (entity == null||entity instanceof ItemEntity||entity instanceof ExperienceOrb) continue;
                entity.invulnerableTime = 0;
                AttackUtil.attackEntity(this.toolStack,
                        living,
                        living.getUsedItemHand(),
                        entity,
                        () -> 1,
                        false,
                        living.getUsedItemHand() == InteractionHand.OFF_HAND ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND,
                        true,
                        this.baseDamage,
                        true
                );
            }
        }

        super.tick();
        if (this.getOwner() != null) {
            Vec3 vec3 = this.getDeltaMovement().normalize();
            this.setPos(this.getOwner().position().add(0, 0.5 * this.getOwner().getBbHeight(), 0).add(vec3.scale(this.getScale())));
        }
    }
}
