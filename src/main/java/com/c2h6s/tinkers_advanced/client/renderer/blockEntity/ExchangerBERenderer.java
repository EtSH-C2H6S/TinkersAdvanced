package com.c2h6s.tinkers_advanced.client.renderer.blockEntity;

import com.c2h6s.tinkers_advanced.content.block.blockEntity.ExchangerBlockEntity;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import com.c2h6s.tinkers_advanced.network.packets.PExchangerBEItemSyncC2S;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;

public class ExchangerBERenderer implements BlockEntityRenderer<ExchangerBlockEntity> {

    @Override
    public void render(ExchangerBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ClientLevel level = Minecraft.getInstance().level;
        if (!pBlockEntity.ensureSynced) TiAcPacketHandler.sendToServer(new PExchangerBEItemSyncC2S(pBlockEntity.getBlockPos()));
        if (!pBlockEntity.exchangingItem.isEmpty()&&level!=null){
            int ticks = (int) (level.getGameTime()%40);
            pPoseStack.pushPose();
            pPoseStack.translate(0.5,0.375,0.5);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(9*(ticks+pPartialTick)));
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(pBlockEntity.exchangingItem, ItemDisplayContext.GROUND, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,level,0);
            pPoseStack.popPose();
        }
    }
}
