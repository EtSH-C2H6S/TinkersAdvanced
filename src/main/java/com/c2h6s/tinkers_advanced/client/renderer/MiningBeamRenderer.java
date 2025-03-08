package com.c2h6s.tinkers_advanced.client.renderer;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.MiningBeamProjectile;
import com.c2h6s.tinkers_advanced.content.entity.PlasmaBeamProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static com.c2h6s.tinkers_advanced.util.RenderUtil.drawPipe;

public class MiningBeamRenderer extends EntityRenderer<MiningBeamProjectile> {
    public MiningBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }
    @Override
    public boolean shouldRender(MiningBeamProjectile entity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        Vec3 vec3 = entity.position().add(entity.getDeltaMovement().scale(entity.getScale()));
        Vec3 vec32 = entity.position().add(entity.getDeltaMovement().scale(entity.getScale()/2f));
        Vec3 cameraPos = new Vec3(pCamX,pCamY,pCamZ);
        return entity.position().subtract(cameraPos).length()<64||vec3.subtract(cameraPos).length()<64||vec32.subtract(cameraPos).length()<64;
    }

    @Override
    public void render(MiningBeamProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float distance = pEntity.getScale();
        if (distance>1) {
            ClientLevel level = Minecraft.getInstance().level;
            Player player = pEntity.getOwner() instanceof Player player1?player1:null;
            if (level==null||player==null) return;
            BlockHitResult result = level.clip(new ClipContext(player.getEyePosition(),player.getEyePosition().add(player.getLookAngle().normalize().scale(pEntity.getScale())), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null));
            Vec3 vec3 = result.getLocation().subtract(pEntity.position());
            distance = (float) vec3.length();
            double d0 = vec3.horizontalDistance();
            float yRot = (float)(Mth.atan2(vec3.x, vec3.z) * 57.2957763671875);
            float xRot =  (float)(Mth.atan2(-vec3.y, d0) * 57.2957763671875);
            pPoseStack.pushPose();
            pPoseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(xRot));
            PoseStack.Pose pose = pPoseStack.last();
            Matrix4f poseMatrix = pose.pose();
            Matrix3f normalMatrix = pose.normal();

            float tick = pEntity.getTick()+pPartialTick-2;
            float alphaPercent = Math.max(0,(5-tick)/5F);

            VertexConsumer consumer = pBuffer.getBuffer(RenderType.beaconBeam(getTextureLocation(pEntity),false));
            drawPipe(pPoseStack,consumer,poseMatrix,0.025f *alphaPercent,distance,255,80,80,255,normalMatrix);

            pPoseStack.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(MiningBeamProjectile projectile) {
        return new ResourceLocation(TinkersAdvanced.MODID,"textures/entity/plasma_beam/white.png");
    }
    @Override
    protected int getBlockLightLevel(MiningBeamProjectile pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    protected int getSkyLightLevel(MiningBeamProjectile pEntity, BlockPos pPos) {
        return 15;
    }
}
