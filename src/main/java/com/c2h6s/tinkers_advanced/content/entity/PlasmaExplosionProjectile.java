package com.c2h6s.tinkers_advanced.content.entity;

import com.c2h6s.tinkers_advanced.content.entity.base.VisualScaledProjectile;
import com.c2h6s.tinkers_advanced.registery.TiAcEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class PlasmaExplosionProjectile extends VisualScaledProjectile {
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
    public void tick() {
        if (this.firstTick){
            this.tickCount=0;
        }
        else tickCount++;
        if (this.tickCount>=12){
            this.discard();
        }
        super.tick();
    }
}
