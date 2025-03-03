package com.c2h6s.tinkers_advanced.client.renderer;

import com.c2h6s.tinkers_advanced.content.entity.AirSlashProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Random;

public class AirSlashRenderer extends EntityRenderer<AirSlashProjectile> {
    public AirSlashRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(AirSlashProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
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
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
            consumer.vertex(poseMatrix, -3, -0.1f,-3).color(255,255,255,255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3,-0.1f, -3).color(255,255,255,255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 3,-0.1f, 3).color(255,255,255,255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -3, -0.1f,3).color(255,255,255,255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normalMatrix, 0, -1, 0).endVertex();
            poseStack.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(AirSlashProjectile airSlashProjectile) {
        return getTextureLocation(airSlashProjectile,0);
    }

    public ResourceLocation getTextureLocation(AirSlashProjectile airSlashProjectile,int offset) {
        int index =(airSlashProjectile.tickCount-2)%8;
        index+=offset;
        return new ResourceLocation("minecraft","textures/particle/sweep_" +index+".png");
    }
}
