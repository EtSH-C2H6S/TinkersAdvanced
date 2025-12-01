package com.c2h6s.tinkers_advanced.tools.init;

import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced.tools.content.entity.MiningBeamProjectile;
import com.c2h6s.tinkers_advanced.tools.content.entity.PlasmaBeamProjectile;
import com.c2h6s.tinkers_advanced.tools.content.entity.PlasmaExplosionProjectile;
import com.c2h6s.tinkers_advanced.tools.content.entity.PlasmaSlashEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcTEntities {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}

    public static final RegistryObject<EntityType<PlasmaExplosionProjectile>> PLASMA_EXPLOSION = TiAcCrModule.ENTITIES.register("plasma_explosion",()->EntityType.Builder.<PlasmaExplosionProjectile>of(PlasmaExplosionProjectile::new, MobCategory.MISC)
            .sized(1,1)
            .setCustomClientFactory(((spawnEntity, level) -> new PlasmaExplosionProjectile(level,1)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .setUpdateInterval(4));
    public static final RegistryObject<EntityType<PlasmaBeamProjectile>> PLASMA_BEAM = TiAcCrModule.ENTITIES.register("plasma_beam",()->EntityType.Builder.<PlasmaBeamProjectile>of(PlasmaBeamProjectile::new, MobCategory.MISC)
            .sized(0.01f,0.01f)
            .setCustomClientFactory(((spawnEntity, level) -> new PlasmaBeamProjectile(level,1)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(4));
    public static final RegistryObject<EntityType<MiningBeamProjectile>> MINING_BEAM = TiAcCrModule.ENTITIES.register("mining_beam",()-> EntityType.Builder.<MiningBeamProjectile>of(MiningBeamProjectile::new, MobCategory.MISC)
            .sized(0.01f,0.01f)
            .setCustomClientFactory(((spawnEntity, level) -> new MiningBeamProjectile(level)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .setUpdateInterval(1));
    public static final RegistryObject<EntityType<PlasmaSlashEntity>> PLASMA_SLASH = TiAcCrModule.ENTITIES.register("plasma_slash",()-> EntityType.Builder.<PlasmaSlashEntity>of(PlasmaSlashEntity::new, MobCategory.MISC)
            .sized(2,2)
            .setCustomClientFactory(((spawnEntity, level) -> new PlasmaSlashEntity(level)))
            .setTrackingRange(8)
            .setShouldReceiveVelocityUpdates(true)
            .setUpdateInterval(4));


}
