package com.c2h6s.tinkers_advanced.registery;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TiAcParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TinkersAdvanced.MODID);

    public static final RegistryObject<ParticleType<SimpleParticleType>> ELECTRIC = PARTICLES.register("electric", ()->new SimpleParticleType(false));
    public static final RegistryObject<ParticleType<SimpleParticleType>> SPARK_BLUE = PARTICLES.register("spark_blue", ()->new SimpleParticleType(false));
}
