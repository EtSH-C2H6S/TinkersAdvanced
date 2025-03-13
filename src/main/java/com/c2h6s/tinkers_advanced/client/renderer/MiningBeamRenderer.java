package com.c2h6s.tinkers_advanced.client.renderer;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.content.entity.MiningBeamProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Random;

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

//        Player player = entity.getOwner() instanceof Player player1?player1:null;
//        Player clientPlayer =Minecraft.getInstance().player;
//        if (player!=null) {
//            if (clientPlayer!=null && player.getUUID()==clientPlayer.getUUID()) player = clientPlayer;
//            InteractionHand hand = player.getUsedItemHand();
//            Vec3 offset = player.getLookAngle().cross(new Vec3(0, 1, 0)).normalize().scale(0.3f);
//            boolean OffHand = hand == InteractionHand.OFF_HAND;
//            if (offset.length() == 0) offset = new Vec3(0.3, 0, 0);
//            if (OffHand) {
//                offset = offset.reverse();
//            }
//            Vec3 finalpos = player.getEyePosition().add(offset);
//            entity.setPos(finalpos);
//            entity.render=true;
//        } else return false;
        return entity.position().subtract(cameraPos).length()<64||vec3.subtract(cameraPos).length()<64||vec32.subtract(cameraPos).length()<64;
    }

    @Override
    public void render(MiningBeamProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float distance = pEntity.getScale();
        if (distance>1) {
            ClientLevel level = Minecraft.getInstance().level;
            Player player = pEntity.getOwner() instanceof Player player1?player1:null;
            if (level==null||player==null) return;

            InteractionHand hand = player.getUsedItemHand();
            Vec3 eyeOffset = player.getLookAngle().cross(new Vec3(0, 1, 0)).normalize().scale(0.3f);
            boolean OffHand = hand == InteractionHand.OFF_HAND;
            if (eyeOffset.length() == 0) eyeOffset = new Vec3(0.3, 0, 0);
            if (OffHand) {
                eyeOffset = eyeOffset.reverse();
            }
            double x = Mth.lerp(pPartialTick, pEntity.xOld, pEntity.getX());
            double y = Mth.lerp(pPartialTick, pEntity.yOld, pEntity.getY());
            double z = Mth.lerp(pPartialTick, pEntity.zOld, pEntity.getZ());

            Vec3 finalpos;
            boolean firstPerson =Minecraft.getInstance().gameRenderer.getMainCamera().getEntity()==player&&Minecraft.getInstance().options.getCameraType().isFirstPerson();
            Camera camera =Minecraft.getInstance().gameRenderer.getMainCamera();
            if (firstPerson){
                finalpos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().add(eyeOffset);
            }else {
                finalpos = player.getEyePosition().add(eyeOffset);
            }
            Vec3 offSet = finalpos.subtract(x,y,z);

            pPoseStack.translate(offSet.x,offSet.y,offSet.z);

            BlockHitResult result;
            if (firstPerson){
                result = level.clip(new ClipContext(camera.getPosition(),camera.getPosition().add(new Vec3(camera.getLookVector().mul(pEntity.getScale()))), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null));
            } else result = level.clip(new ClipContext(player.getEyePosition(),player.getEyePosition().add(player.getLookAngle().normalize().scale(pEntity.getScale())), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null));
            Vec3 vec3 = result.getLocation().subtract(finalpos);

            distance = (float) vec3.length();
            double d0 = vec3.horizontalDistance();
            float yRot = (float)(Mth.atan2(vec3.x, vec3.z) * 57.2957763671875);
            float xRot =  (float)(Mth.atan2(-vec3.y, d0) * 57.2957763671875);

            if (pEntity.getTick() < 2) {
                level.destroyBlockProgress(player.getId(), result.getBlockPos(), pEntity.getProgress());
            } else level.destroyBlockProgress(player.getId(), result.getBlockPos(), -1);

            if (!player.isUsingItem()){
                level.destroyBlockProgress(player.getId(),result.getBlockPos(),-1);
            }
            Random random = new Random();
            level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK,level.getBlockState(result.getBlockPos())),false,result.getBlockPos().getX()+random.nextFloat(),result.getBlockPos().getY()+random.nextFloat(),result.getBlockPos().getZ()+random.nextFloat(),0,0,0);
            pPoseStack.pushPose();

            pPoseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(xRot));
            PoseStack.Pose pose = pPoseStack.last();
            Matrix4f poseMatrix = pose.pose();
            Matrix3f normalMatrix = pose.normal();

            float tick =Math.max(0, pEntity.getTick()+pPartialTick-10);
            float alphaPercent = Math.max(0,(5-tick)/5F);

            VertexConsumer consumer = pBuffer.getBuffer(RenderType.beaconBeam(getTextureLocation(pEntity),false));
            drawPipe(pPoseStack,consumer,poseMatrix,0.01f *alphaPercent,distance,255,100,80,255,normalMatrix);
            consumer = pBuffer.getBuffer(RenderType.beaconBeam(getTextureLocation(pEntity),true));
            drawPipe(pPoseStack,consumer,poseMatrix,0.025f *alphaPercent,distance,255,0,0, (int) (128*alphaPercent),normalMatrix);

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
