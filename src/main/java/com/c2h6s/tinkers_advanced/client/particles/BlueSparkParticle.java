package com.c2h6s.tinkers_advanced.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;


public class BlueSparkParticle extends TextureSheetParticle {
    public double xdo;
    public double ydo;
    public double zdo;
    BlueSparkParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites){
        super(pLevel,pX,pY,pZ,pXSpeed,pYSpeed,pZSpeed);
        this.lifetime = 10;
        this.quadSize *= 0.5f + this.random.nextFloat()*0.5f;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.friction = 0.8F;
        this.sprite = pSprites.get(this.random);
        this.hasPhysics = false;

    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        Vec3 cameraPos = pRenderInfo.getPosition();
        Vector3f lookVec = pRenderInfo.getLookVector();
        float vx = (float)Mth.lerp(pPartialTicks, this.xdo, this.xd);
        float vy = (float)Mth.lerp(pPartialTicks, this.ydo, this.yd);
        float vz = (float)Mth.lerp(pPartialTicks, this.zdo, this.zd);
        Vector3f velocity = new Vector3f(vx, vy, vz);
        Vector3f side;
        side = lookVec.cross(velocity).normalize();
        side.mul(this.quadSize);
        if (velocity.length()<0.5){
            side.mul(velocity.length()*2);
        }
        float x = (float)(Mth.lerp(pPartialTicks, this.xo, this.x) - cameraPos.x());
        float y = (float)(Mth.lerp(pPartialTicks, this.yo, this.y) - cameraPos.y());
        float z = (float)(Mth.lerp(pPartialTicks, this.zo, this.z) - cameraPos.z());
        Vector3f pos = new Vector3f(x,y,z);
        Vector3f[] positions = new Vector3f[]{
                new Vector3f(pos).add(side).add(velocity),
                new Vector3f(pos).add(side).sub(velocity),
                new Vector3f(pos).sub(side).sub(velocity),
                new Vector3f(pos).sub(side).add(velocity)
        };
        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        pBuffer.vertex(positions[0].x(), positions[0].y(), positions[0].z()).uv(u1, v1).color(1f, 1, 1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
        pBuffer.vertex(positions[1].x(), positions[1].y(), positions[1].z()).uv(u1, v0).color(1f, 1, 1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
        pBuffer.vertex(positions[2].x(), positions[2].y(), positions[2].z()).uv(u0, v0).color(1f, 1, 1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
        pBuffer.vertex(positions[3].x(), positions[3].y(), positions[3].z()).uv(u0, v1).color(1f, 1, 1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
    }

    @Override
    public void tick() {
        this.xdo = this.xd;
        this.ydo = this.yd;
        this.zdo = this.zd;
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
    @Override
    protected int getLightColor(float pPartialTick) {
        return LightTexture.FULL_BRIGHT;
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new BlueSparkParticle(pLevel,pX,pY,pZ,pXSpeed,pYSpeed,pZSpeed,this.sprites);
        }
    }
}
