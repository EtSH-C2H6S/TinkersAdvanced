package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;

public class TiAcEntities {
    public static final EntityTypeDeferredRegister ENTITIES = new EntityTypeDeferredRegister(TinkersAdvanced.MODID);
    public static final EntityTypeDeferredRegister THERMAL_ENTITIES = new EntityTypeDeferredRegister(TinkersAdvanced.MODID);

    public static final RegistryObject<EntityType<PlasmaExplosionProjectile>> PLASMA_EXPLOSION = ENTITIES.register("plasma_explosion",()->EntityType.Builder.<PlasmaExplosionProjectile>of(PlasmaExplosionProjectile::new, MobCategory.MISC)
            .sized(1,1)
            .setCustomClientFactory(((spawnEntity, level) -> new PlasmaExplosionProjectile(level,1)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .setUpdateInterval(4));
    public static final RegistryObject<EntityType<PlasmaBeamProjectile>> PLASMA_BEAM = ENTITIES.register("plasma_beam",()->EntityType.Builder.<PlasmaBeamProjectile>of(PlasmaBeamProjectile::new, MobCategory.MISC)
            .sized(0.01f,0.01f)
            .setCustomClientFactory(((spawnEntity, level) -> new PlasmaBeamProjectile(level,1)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(4));
    public static final RegistryObject<EntityType<AirSlashProjectile>> AIR_SLASH = ENTITIES.register("air_slash",()-> EntityType.Builder.<AirSlashProjectile>of(AirSlashProjectile::new, MobCategory.MISC)
            .sized(4,0.25f)
            .setCustomClientFactory(((spawnEntity, level) -> new AirSlashProjectile(level)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(4));
    public static final RegistryObject<EntityType<MiningBeamProjectile>> MINING_BEAM = ENTITIES.register("mining_beam",()-> EntityType.Builder.<MiningBeamProjectile>of(MiningBeamProjectile::new, MobCategory.MISC)
            .sized(0.01f,0.01f)
            .setCustomClientFactory(((spawnEntity, level) -> new MiningBeamProjectile(level)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .setUpdateInterval(1));
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> PLASMA_SLASH = ENTITIES.register("plasma_slash",()-> EntityType.Builder.<PlasmaSlashEntity>of(PlasmaSlashEntity::new, MobCategory.MISC)
            .sized(2,2)
            .setCustomClientFactory(((spawnEntity, level) -> new PlasmaSlashEntity(level)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(4));


    public static final RegistryObject<EntityType<ThermalSlashProjectile>> THERMAL_SLASH = THERMAL_ENTITIES.register("thermal_slash",()->
            EntityType.Builder.<ThermalSlashProjectile>of(ThermalSlashProjectile::new, MobCategory.MISC)
                    .sized(4,0.25f)
                    .setCustomClientFactory(((spawnEntity, level) -> new ThermalSlashProjectile(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
}
