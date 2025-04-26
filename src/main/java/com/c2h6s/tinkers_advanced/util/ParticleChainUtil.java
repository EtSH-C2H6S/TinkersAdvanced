package com.c2h6s.tinkers_advanced.util;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.network.packets.PParticleChainS2C;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ParticleChainUtil {
    protected static Map<Long,Double> doubleCache1 = new HashMap<>();
    protected static Map<Long,Double> doubleCache2 = new HashMap<>();
    protected static Map<Long,Double> doubleCache3 = new HashMap<>();
    public enum EnumParticleFunctions {
        ZERO(
                "zero",
                (d,l,vec3)-> Vec3.ZERO
        ),
        RANDOM(
                "random",
                (d,l,vec3)->{
                    Random random = new Random();
                    return new Vec3(random.nextDouble()*2-1d,random.nextDouble()*2-1d,random.nextDouble()*2-1d);
                }
        ),
        ;
        public final String name;
        public final TriFunction<Double,Long,Vec3,Vec3> function;
        EnumParticleFunctions(String name,TriFunction<Double,Long,Vec3,Vec3> function){
            this.name = name;
            this.function = function;
        }
    }
    protected static Map<String,TriFunction<Double,Long,Vec3,Vec3>> functionMap;
    static {
        functionMap = initMap();
    }

    protected static Map<String,TriFunction<Double,Long,Vec3,Vec3>> initMap(){
        functionMap= new HashMap<>();
        Arrays.stream(EnumParticleFunctions.values()).forEach((enumParticleFunctions -> functionMap.put(enumParticleFunctions.name,enumParticleFunctions.function)));
        return functionMap;
    }

    public static void drawLine(ParticleType<SimpleParticleType> type, Vec3 posStart, Vec3 posEnd, double spacing, String velocityFunction, double velocityMultiplier, String offsetFunction, double offsetMultiplier, double distanceLimit , int countLimit){
        TiAcPacketHandler.sendToClient(new PParticleChainS2C(
                type,
                posStart,
                posEnd,
                (float) spacing,
                velocityFunction,
                (float) velocityMultiplier,
                offsetFunction,
                (float) offsetMultiplier,
                (float) distanceLimit,
                countLimit
        ));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client{
        public static void drawLine(ParticleOptions options, ClientLevel level, Vec3 posStart, Vec3 posEnd, double spacing, TriFunction<Double,Long,Vec3,Vec3> velocityFunction, double velocityArg, TriFunction<Double,Long,Vec3,Vec3> offsetFunction,double offsetArg, double distanceLimit , int countLimit,long randomSeed){
            Vec3 vec3 = posEnd.subtract(posStart);
            int partsAdded =0;
            Vec3 direction = vec3.normalize();
            for (double d =0;d<Math.min(vec3.length(),distanceLimit);d+=spacing){
                Vec3 pos = posStart.add(direction.scale(d)).add(offsetFunction.apply(d,randomSeed,direction).scale(offsetArg));
                Vec3 velocity = velocityFunction.apply(d,randomSeed,direction).scale(velocityArg);
                level.addParticle(options,pos.x,pos.y,pos.z,velocity.x,velocity.y,velocity.z);
                partsAdded++;
                if (partsAdded>countLimit) break;
            }
        }
        public static void drawLine(ParticleOptions options, ClientLevel level, Vec3 posStart, Vec3 posEnd, double spacing, String velocityFunction, double velocityArg, String offsetFunction,double offsetArg, double distanceLimit , int countLimit){
            if (functionMap.get(velocityFunction)==null){
                TinkersAdvanced.LOGGER.error("Invalid function name : {}", velocityFunction);
                return;
            }
            if (functionMap.get(offsetFunction)==null){
                TinkersAdvanced.LOGGER.error("Invalid function name : {}", offsetFunction);
                return;
            }
            Random random = new Random();
            drawLine(options,level,posStart,posEnd,spacing,functionMap.get(velocityFunction),velocityArg,functionMap.get(offsetFunction),offsetArg,distanceLimit,countLimit,random.nextLong());
        }

    }
}
