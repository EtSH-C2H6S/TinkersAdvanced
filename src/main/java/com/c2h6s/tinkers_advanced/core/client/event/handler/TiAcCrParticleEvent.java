package com.c2h6s.tinkers_advanced.core.client.event.handler;

import com.c2h6s.tinkers_advanced.core.client.particles.BlueSparkParticle;
import com.c2h6s.tinkers_advanced.core.client.particles.ElectricParticle;
import com.c2h6s.tinkers_advanced.core.init.TiAcCrParticleTypes;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TiAcCrParticleEvent {
    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event){
        ParticleEngine.SpriteParticleRegistration<SimpleParticleType> provider = ElectricParticle.Provider::new;
        event.registerSpriteSet(TiAcCrParticleTypes.ELECTRIC.get(), provider);
        provider = BlueSparkParticle.Provider::new;
        event.registerSpriteSet(TiAcCrParticleTypes.SPARK_BLUE.get(), provider);
    }
}
