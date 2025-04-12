package com.c2h6s.tinkers_advanced.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Function;

public class ParticleChainUtil {
    
    @OnlyIn(Dist.CLIENT)
    public static class Client{
        public static void drawLine(ParticleOptions options, ClientLevel level, Vec3 posStart, Vec3 posEnd, double spacing, Function<Double,Vec3> velocityFunction,Function<Double,Vec3> offsetFunction,double distanceLimit ,int countLimit){
            Vec3 vec3 = posEnd.subtract(posStart);
            int partsAdded =0;
            Vec3 direction = vec3.normalize();
            for (double d =0;d<Math.min(vec3.length(),distanceLimit);d++){
                Vec3 pos = posStart.add(direction.scale(spacing)).add(offsetFunction.apply(d));
                Vec3 velocity = velocityFunction.apply(d);
                level.addParticle(options,pos.x,pos.y,pos.z,velocity.x,velocity.y,velocity.z);
                partsAdded++;
                if (partsAdded>countLimit) break;
            }
        }
    }
}
