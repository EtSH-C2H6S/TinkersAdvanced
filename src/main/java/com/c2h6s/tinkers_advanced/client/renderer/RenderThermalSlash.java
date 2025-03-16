package com.c2h6s.tinkers_advanced.client.renderer;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.AirSlashProjectile;
import com.c2h6s.tinkers_advanced.content.entity.ThermalSlashProjectile;
import com.c2h6s.tinkers_advanced.util.RenderUtil;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Random;

public class RenderThermalSlash extends EntityRenderer<ThermalSlashProjectile> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(TinkersAdvanced.MODID,"textures/entity/plasma_beam/white.png");
    public RenderThermalSlash(EntityRendererProvider.Context pContext) {
        super(pContext);
    }


    @Override
    public void render(ThermalSlashProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.tickCount>=2){
            if (entity.rotDeg==0){
                Random random = new Random();
                entity.rotDeg=random.nextFloat()*60-30;
            }
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(-entity.getXRot()));
            poseStack.mulPose(Axis.ZP.rotationDegrees(entity.rotDeg));
            PoseStack.Pose pose = poseStack.last();
            Matrix4f poseMatrix = pose.pose();
            Matrix3f normalMatrix = pose.normal();

            VertexConsumer consumer = buffer.getBuffer(RenderUtil.brightProjectileRenderType(TEXTURE));
            Random random = new Random(entity.hashCode());
            float colTick = (entity.tickCount+partialTick+random.nextFloat()*40)%30;
            float tick = entity.tickCount+partialTick;
            float percent =Mth.clamp((5-(entity.tickCount-8))/5f,0,1);
            int alpha = (int) (percent*255);
            consumer.vertex(poseMatrix, 0f, -0.5f,3f).color(255,255,255,alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3f*percent, -0.5f,0f).color(255,255,255,alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3f*percent, -0.5f,-0.1f).color(255,255,255,alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 0f, -0.5f,2.9f).color(255,255,255,alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();

            consumer.vertex(poseMatrix, 0f, -0.5f,3f).color(255,255,255,alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 0f, -0.5f,2.9f).color(255,255,255,alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3f*percent, -0.5f,-0.1f).color(255,255,255,alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3f*percent, -0.5f,0f).color(255,255,255,alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();


            consumer.vertex(poseMatrix, 0f, -0.5f,2.9f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3f*percent, -0.5f,-0.1f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3f*percent, -0.5f,-0.2f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 0f, -0.5f,2.8f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();

            consumer.vertex(poseMatrix, 0f, -0.5f,2.9f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 0f, -0.5f,2.8f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3f*percent, -0.5f,-0.2f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3f*percent, -0.5f,-0.1f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();


            consumer.vertex(poseMatrix, 0f, -0.5f,2.8f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3f*percent, -0.5f,-0.2f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3f*percent, -0.5f,-1).color(getR(colTick),getG(colTick),getB(colTick),alpha/3).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 0f, -0.5f,1.8f).color(getR(colTick),getG(colTick),getB(colTick),alpha/3).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();

            consumer.vertex(poseMatrix, 0f, -0.5f,2.8f).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3f*percent, -0.5f,-0.2F).color(getR(colTick),getG(colTick),getB(colTick),alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3f*percent, -0.5f,-1).color(getR(colTick),getG(colTick),getB(colTick),alpha/3).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 0f, -0.5f,1.8f).color(getR(colTick),getG(colTick),getB(colTick),alpha/3).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();


            consumer.vertex(poseMatrix, 0f, -0.5f,1.8f).color(getR(colTick),getG(colTick),getB(colTick),alpha/3).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3f*percent, -0.5f,-1f).color(getR(colTick),getG(colTick),getB(colTick),alpha/6).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3f*percent, -0.5f,-tick*2).color(getR(colTick),getG(colTick),getB(colTick),alpha/6).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 0f, -0.5f,-tick*2).color(getR(colTick),getG(colTick),getB(colTick),alpha/3).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();

            consumer.vertex(poseMatrix, 0f, -0.5f,1.8f).color(getR(colTick),getG(colTick),getB(colTick),alpha/3).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3f*percent, -0.5f,-1).color(getR(colTick),getG(colTick),getB(colTick),alpha/6).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3f*percent, -0.5f,-tick*2).color(getR(colTick),getG(colTick),getB(colTick),alpha/6).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 0f, -0.5f,-tick*2).color(getR(colTick),getG(colTick),getB(colTick),alpha/3).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();

            poseStack.popPose();
        }
    }
    public static int getB(float tick){
        if (tick>=15&&tick<25) return 255;
        if (tick>=10&&tick<15) return (int) (155+20*(tick-10));
        if (tick>25) return (int) (255-20*(tick-25));
        return 155;
    }
    public static int getG(float tick){
        if (tick>=15&&tick<20) return (int) (255-20*(tick-15));
        if (tick>=5&&tick<15) return 255;
        if (tick<5) return (int) (155+20*tick);
        return 155;
    }
    public static int getR(float tick){
        if (tick<5||tick>=25) return 255;
        if (tick>=5&&tick<10) return (int) (255-20*(tick-5));
        if (tick>=20) return (int) (155+20*(tick-20));
        return 155;
    }

    @Override
    public ResourceLocation getTextureLocation(ThermalSlashProjectile projectile) {
        return TEXTURE;
    }

}
