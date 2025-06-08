package com.c2h6s.tinkers_advanced.network.packets;

import com.c2h6s.tinkers_advanced.content.block.blockEntity.ExchangerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import slimeknights.mantle.client.SafeClientAccess;


import java.util.function.Supplier;

public class PExchangerBEItemSyncS2C {
    private final ItemStack stack;
    private final BlockPos blockPos;
    public PExchangerBEItemSyncS2C(ItemStack stack, BlockPos blockPos){
        this.stack =stack;
        this.blockPos = blockPos;
    }
    public PExchangerBEItemSyncS2C(FriendlyByteBuf buf){
        this(buf.readItem(),buf.readBlockPos());
    }

    public void toByte(FriendlyByteBuf buf){
        buf.writeItem(this.stack);
        buf.writeBlockPos(this.blockPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            Level level = SafeClientAccess.getLevel();
            if (level!=null){
                BlockEntity entity = level.getBlockEntity(this.blockPos);
                if (entity instanceof ExchangerBlockEntity exchanger){
                    exchanger.exchangingItem = this.stack;
                    exchanger.ensureSynced =true;
                }
            }
        });
        return true;
    }
}
