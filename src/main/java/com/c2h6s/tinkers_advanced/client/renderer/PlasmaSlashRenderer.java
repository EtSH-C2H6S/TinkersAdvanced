package com.c2h6s.tinkers_advanced.client.renderer;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaSlashEntity;
import com.c2h6s.tinkers_advanced.util.RenderUtil;
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
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Random;

public class PlasmaSlashRenderer extends EntityRenderer<PlasmaSlashEntity> {
    public PlasmaSlashRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(PlasmaSlashEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.tickCount>=2){
            if (pEntity.rotation==0){
                Random random = new Random();
                pEntity.rotation=random.nextFloat()*60-30;
            }
            float scale = pEntity.getScale();
            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(pEntity.getYRot()));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(-pEntity.getXRot()));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(pEntity.rotation));
            PoseStack.Pose pose = pPoseStack.last();
            Matrix4f poseMatrix = pose.pose();
            Matrix3f normalMatrix = pose.normal();
            VertexConsumer consumer =pBuffer.getBuffer(RenderUtil.brightProjectileRenderType(getTextureLocation(pEntity)));
            consumer.vertex(poseMatrix, -2*scale, -0.2f,-2*scale).color(255,255,255,255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 2*scale,-0.1f, -2*scale).color(255,255,255,255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, 2*scale,-0.1f, 2*scale).color(255,255,255,255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, -1, 0).endVertex();
            consumer.vertex(poseMatrix, -2*scale, -0.1f,2*scale).color(255,255,255,255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, -1, 0).endVertex();
            pPoseStack.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(PlasmaSlashEntity pEntity) {
        int frame = Mth.clamp(pEntity.tickCount-1,1,6);
        return TinkersAdvanced.getLocation("textures/entity/plasma_slash/plasma_slash_"+frame+".png");
    }
}
