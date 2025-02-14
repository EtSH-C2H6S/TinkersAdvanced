package com.c2h6s.tinkers_advanced.client.renderer;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaBeamProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class PlasmaBeamRenderer extends EntityRenderer<PlasmaBeamProjectile> {
    public PlasmaBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public boolean shouldRender(PlasmaBeamProjectile entity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Vec3 vec3 = entity.position().add(entity.getDeltaMovement().scale(entity.getDataLength()));
        Vec3 vec32 = entity.position().add(entity.getDeltaMovement().scale(entity.getDataLength()/2f));
        Vec3 cameraPos = new Vec3(pCamX,pCamY,pCamZ);
        return entity.position().subtract(cameraPos).length()<64||vec3.subtract(cameraPos).length()<64||vec32.subtract(cameraPos).length()<64;
    }

    @Override
    public void render(PlasmaBeamProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float distance = pEntity.getDataLength();
        if (distance>1&&pEntity.tickCount>2&&pEntity.readyToRender()) {
            pPoseStack.pushPose();

            Vec3 direction = pEntity.getDeltaMovement().normalize();
            double d0 = direction.horizontalDistance();
            float yRot = (float)(Mth.atan2(direction.x, direction.z) * 57.2957763671875);
            float xRot =  (float)(Mth.atan2(-direction.y, d0) * 57.2957763671875);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(xRot));
            pPoseStack.scale(pEntity.getScale(), pEntity.getScale(), pEntity.getScale());
            PoseStack.Pose pose = pPoseStack.last();
            Matrix4f poseMatrix = pose.pose();
            Matrix3f normalMatrix = pose.normal();

            float tick = pEntity.tickCount+pPartialTick-2;
            float alphaPercent = Math.max(0,(7-tick)/7F);

            VertexConsumer consumer = pBuffer.getBuffer(RenderType.beaconBeam(getTextureLocation(pEntity),false));
            drawPipe(pPoseStack,consumer,poseMatrix,0.05f *alphaPercent,distance,255,255,255,255,normalMatrix);
            consumer = pBuffer.getBuffer(RenderType.beaconBeam(getTextureLocation(pEntity),true));
            drawPipe(pPoseStack,consumer,poseMatrix,0.1f,distance,128,245,255, (int) (128*alphaPercent),normalMatrix);

            pPoseStack.popPose();
        }
    }

    public void drawPipe(PoseStack pPoseStack,VertexConsumer consumer,Matrix4f poseMatrix,float radius,float distance,int r,int g,int b,int a,Matrix3f normalMatrix){
        consumer.vertex(poseMatrix, -radius, -radius, 0).color(r,g,b,a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, 0).color(r,g,b,a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, radius, radius, 0).color(r,g,b,a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, radius, -radius, 0).color(r,g,b,a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();

        consumer.vertex(poseMatrix, -radius, -radius, distance).color(r,g,b,a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, distance).color(r,g,b,a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, radius, radius, distance).color(r,g,b,a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, radius, -radius, distance).color(r,g,b,a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();

        consumer.vertex(poseMatrix, -radius, -radius, 0).color(r,g,b,a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, 0).color(r,g,b,a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, distance).color(r,g,b,a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, -radius, distance).color(r,g,b,a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));
        consumer.vertex(poseMatrix, -radius, -radius, 0).color(r,g,b,a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, 0).color(r,g,b,a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, distance).color(r,g,b,a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, -radius, distance).color(r,g,b,a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));
        consumer.vertex(poseMatrix, -radius, -radius, 0).color(r,g,b,a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, 0).color(r,g,b,a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, distance).color(r,g,b,a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, -radius, distance).color(r,g,b,a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));
        consumer.vertex(poseMatrix, -radius, -radius, 0).color(r,g,b,a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, 0).color(r,g,b,a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, radius, distance).color(r,g,b,a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        consumer.vertex(poseMatrix, -radius, -radius, distance).color(r,g,b,a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));
    }

    @Override
    protected int getBlockLightLevel(PlasmaBeamProjectile pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(PlasmaBeamProjectile pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(PlasmaBeamProjectile entity){
        return new ResourceLocation(TinkersAdvanced.MODID,"textures/entity/plasma_beam/white.png");
    }
}
