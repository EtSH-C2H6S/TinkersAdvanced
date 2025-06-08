package com.c2h6s.tinkers_advanced.client.event;

import com.c2h6s.tinkers_advanced.client.particles.BlueSparkParticle;
import com.c2h6s.tinkers_advanced.client.particles.ElectricParticle;
import com.c2h6s.tinkers_advanced.registery.TiAcParticleTypes;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TiAcParticleEvent {
    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event){
        ParticleEngine.SpriteParticleRegistration<SimpleParticleType> provider = ElectricParticle.Provider::new;
        event.registerSpriteSet(TiAcParticleTypes.ELECTRIC.get(), provider);
        provider = BlueSparkParticle.Provider::new;
        event.registerSpriteSet(TiAcParticleTypes.SPARK_BLUE.get(), provider);
    }
}
