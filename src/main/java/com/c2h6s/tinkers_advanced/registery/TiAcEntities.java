package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaExplosionProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;

public class TiAcEntities {
    public static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(TinkersAdvanced.MODID);

    public static final RegistryObject<EntityType<PlasmaExplosionProjectile>> PLASMA_EXPLOSION = ENTITIES.register("plasma_explosion",()->EntityType.Builder.<PlasmaExplosionProjectile>of(PlasmaExplosionProjectile::new, MobCategory.MISC).sized(1,1).setCustomClientFactory(((spawnEntity, level) -> new PlasmaExplosionProjectile(level,1))).setTrackingRange(8).setShouldReceiveVelocityUpdates(false).setUpdateInterval(4));
}
