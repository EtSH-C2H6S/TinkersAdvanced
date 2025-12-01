package com.c2h6s.tinkers_advanced.materials.init;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.materials.content.entity.AirSlashProjectile;
import com.c2h6s.tinkers_advanced.materials.content.entity.ThermalSlashProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.EntityTypeDeferredRegister;

public class TiAcMeEntities {
    public static final EntityTypeDeferredRegister THERMAL_ENTITIES = new EntityTypeDeferredRegister(TinkersAdvanced.MODID);

    public static final RegistryObject<EntityType<ThermalSlashProjectile>> THERMAL_SLASH = THERMAL_ENTITIES.register("thermal_slash",()->
            EntityType.Builder.<ThermalSlashProjectile>of(ThermalSlashProjectile::new, MobCategory.MISC)
                    .sized(4,0.25f)
                    .setCustomClientFactory(((spawnEntity, level) -> new ThermalSlashProjectile(level)))
                    .setTrackingRange(8)
                    .setShouldReceiveVelocityUpdates(true)
                    .setUpdateInterval(4));
    public static final RegistryObject<EntityType<AirSlashProjectile>> AIR_SLASH = TiAcCrModule.ENTITIES.register("air_slash",()-> EntityType.Builder.<AirSlashProjectile>of(AirSlashProjectile::new, MobCategory.MISC)
            .sized(4,0.25f)
            .setCustomClientFactory(((spawnEntity, level) -> new AirSlashProjectile(level)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(4));

    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
}
