package com.c2h6s.tinkers_advanced.network.packets;

import com.c2h6s.tinkers_advanced.util.ParticleChainUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.client.SafeClientAccess;

import java.util.function.Supplier;

public class PParticleChainS2C {
    private final ResourceLocation partId;
    private final Vec3 posStart;
    private final Vec3 posEnd;
    private final float spacing;
    private final String velocityFunctionName;
    private final float velocityFunctionArg;
    private final String offsetFunctionName;
    private final float offsetFunctionArg;
    private final float distanceLimit;
    private final int countLimit;

    public PParticleChainS2C(ParticleType<SimpleParticleType> type,Vec3 posStart,Vec3 posEnd,float spacing,String velocityFunctionName,float velocityFunctionArg,String offsetFunctionName,float offsetFunctionArg,float distanceLimit,int countLimit){
        this.partId = ForgeRegistries.PARTICLE_TYPES.getKey(type);
        this.posStart = posStart;
        this.posEnd = posEnd;
        this.spacing = spacing;
        this.velocityFunctionName = velocityFunctionName;
        this.velocityFunctionArg = velocityFunctionArg;
        this.offsetFunctionName = offsetFunctionName;
        this.offsetFunctionArg = offsetFunctionArg;
        this.distanceLimit = distanceLimit;
        this.countLimit = countLimit;
    }
    public PParticleChainS2C(FriendlyByteBuf buf){
        this.partId = buf.readResourceLocation();
        this.posStart = new Vec3(buf.readVector3f());
        this.posEnd = new Vec3(buf.readVector3f());
        this.spacing = buf.readFloat();
        this.velocityFunctionName = buf.readUtf();
        this.velocityFunctionArg = buf.readFloat();
        this.offsetFunctionName = buf.readUtf();
        this.offsetFunctionArg = buf.readFloat();
        this.distanceLimit = buf.readFloat();
        this.countLimit = buf.readInt();
    }
    public void toByte(FriendlyByteBuf buf){
        buf.writeResourceLocation(this.partId);
        buf.writeVector3f(posStart.toVector3f());
        buf.writeVector3f(posEnd.toVector3f());
        buf.writeFloat(spacing);
        buf.writeUtf(velocityFunctionName);
        buf.writeFloat(velocityFunctionArg);
        buf.writeUtf(offsetFunctionName);
        buf.writeFloat(offsetFunctionArg);
        buf.writeFloat(distanceLimit);
        buf.writeInt(countLimit);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            Level level = SafeClientAccess.getLevel();
            if (level!=null) ParticleChainUtil.Client.drawLine((ParticleOptions) ForgeRegistries.PARTICLE_TYPES.getValue(partId),(ClientLevel) level,posStart,posEnd,spacing,velocityFunctionName,velocityFunctionArg,offsetFunctionName,offsetFunctionArg,distanceLimit,countLimit);
        });
        return true;
    }


}
