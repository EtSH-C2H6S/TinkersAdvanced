package com.c2h6s.tinkers_advanced.network.packets;

import com.c2h6s.tinkers_advanced.content.menu.ElectronTunerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import slimeknights.mantle.client.SafeClientAccess;

import java.util.function.Supplier;

public class PElectronTunerMenuSyncS2C {
    private final ItemStack stack;
    private final int slotIndex;

    public PElectronTunerMenuSyncS2C(ItemStack stack, int slotIndex) {
        this.stack = stack;
        this.slotIndex = slotIndex;
    }
    public PElectronTunerMenuSyncS2C(FriendlyByteBuf buf){
        this.stack = buf.readItem();
        this.slotIndex = buf.readInt();
    }
    public void toByte(FriendlyByteBuf buf){
        buf.writeItem(this.stack);
        buf.writeInt(this.slotIndex);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            Player player = SafeClientAccess.getPlayer();
            if (player!=null&&player.containerMenu instanceof ElectronTunerMenu menu){
                Slot slot = menu.getSlot(this.slotIndex);
                slot.container.setItem(slot.getSlotIndex(),this.stack);
                slot.setChanged();
                menu.updateToolSlot(this.stack);
            }
        });
        return true;
    }
}
