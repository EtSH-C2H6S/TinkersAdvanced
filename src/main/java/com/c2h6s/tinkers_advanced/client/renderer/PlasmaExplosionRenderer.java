package com.c2h6s.tinkers_advanced.client.renderer;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaBeamProjectile;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaExplosionProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class PlasmaExplosionRenderer extends EntityRenderer<PlasmaExplosionProjectile> {
    public PlasmaExplosionRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(PlasmaExplosionProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.tickCount>1) {
            pPoseStack.pushPose();
            pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            pPoseStack.scale(pEntity.getScale(), pEntity.getScale(), pEntity.getScale());
            PoseStack.Pose pose = pPoseStack.last();
            Matrix4f poseMatrix = pose.pose();
            Matrix3f normalMatrix = pose.normal();
            VertexConsumer consumer = pBuffer.getBuffer(RenderType.text(getTextureLocation(pEntity)));
            consumer.vertex(poseMatrix, -1, -1, 0).color(0xffffffff).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 0, 0).endVertex();
            consumer.vertex(poseMatrix, 1, -1, 0).color(0xffffffff).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 0, 1).endVertex();
            consumer.vertex(poseMatrix, 1, 1, 0).color(0xffffffff).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 1).endVertex();
            consumer.vertex(poseMatrix, -1, 1, 0).color(0xffffffff).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0, 1, 0).endVertex();
            pPoseStack.popPose();
        }
    }

    @Override
    protected int getBlockLightLevel(PlasmaExplosionProjectile pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(PlasmaExplosionProjectile pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(PlasmaExplosionProjectile entity){
        int index = 1+(entity.tickCount-2)%12;
        return new ResourceLocation(TinkersAdvanced.MODID,"textures/entity/plasma_explosion/plasma_explosion_"+index+".png");
    }
}
