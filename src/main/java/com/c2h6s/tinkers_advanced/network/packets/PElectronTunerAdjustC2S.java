package com.c2h6s.tinkers_advanced.network.packets;

import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.function.Supplier;

public class PElectronTunerAdjustC2S {
    public final int slot;
    public final float amount;

    public PElectronTunerAdjustC2S(int slot, float amount) {
        this.slot = slot;
        this.amount = amount;
    }
    public PElectronTunerAdjustC2S(FriendlyByteBuf buf){
        this.slot = buf.readInt();
        this.amount = buf.readFloat();
    }
    public void toByte(FriendlyByteBuf buf){
        buf.writeInt(this.slot);
        buf.writeFloat(this.amount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(()->{
            if (player!=null) {
                ToolStack tool = ToolStack.from(player.getInventory().getItem(slot));
                tool.getPersistentData().putFloat(ElectronTunerItem.KEY_ATTACK_DAMAGE, amount);
                tool.rebuildStats();
            }
        });
        return true;
    }


}
