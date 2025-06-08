package com.c2h6s.tinkers_advanced.network.packets;

import com.c2h6s.tinkers_advanced.content.block.blockEntity.ExchangerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PExchangerBEItemSyncC2S {
    private final BlockPos blockPos;
    public PExchangerBEItemSyncC2S(BlockPos blockPos){
        this.blockPos = blockPos;
    }
    public PExchangerBEItemSyncC2S(FriendlyByteBuf buf){
        this(buf.readBlockPos());
    }

    public void toByte(FriendlyByteBuf buf){
        buf.writeBlockPos(this.blockPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer!=null){
                BlockEntity entity = serverPlayer.level().getBlockEntity(this.blockPos);
                if (entity instanceof ExchangerBlockEntity exchanger){
                    exchanger.syncToClient(exchanger.exchangingItem);
                }
            }
        });
        return true;
    }
}
