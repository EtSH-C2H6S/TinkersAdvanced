package com.c2h6s.tinkers_advanced.core.init;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcCrParticleTypes {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TinkersAdvanced.MODID);

    public static final RegistryObject<ParticleType<SimpleParticleType>> ELECTRIC = PARTICLES.register("electric", ()->new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<SimpleParticleType>> SPARK_BLUE = PARTICLES.register("spark_blue", ()->new SimpleParticleType(false));
}
