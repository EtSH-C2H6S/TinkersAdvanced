package com.c2h6s.tinkers_advanced.network.packets;

import com.c2h6s.tinkers_advanced.content.item.toolItem.ElectronTunerItem;
import com.c2h6s.tinkers_advanced.content.menu.ElectronTunerMenu;
import com.c2h6s.tinkers_advanced.registery.TiAcMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import slimeknights.mantle.inventory.EmptyItemHandler;

import java.util.function.Supplier;

public class PElectronTunerOpenMenuC2S {
    public final int slot;

    public PElectronTunerOpenMenuC2S(int slot) {
        this.slot = slot;
    }
    public PElectronTunerOpenMenuC2S(FriendlyByteBuf buf){
        this.slot = buf.readInt();
    }
    public void toByte(FriendlyByteBuf buf){
        buf.writeInt(this.slot);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(()->{
            if (player!=null) {
                ItemStack stack = player.getInventory().getItem(this.slot);
                IItemHandler handler = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).filter(cap -> cap instanceof IItemHandlerModifiable).orElse(EmptyItemHandler.INSTANCE);
                NetworkHooks.openScreen(player,
                        new SimpleMenuProvider(
                                (id,inventory,p)->new ElectronTunerMenu(TiAcMenus.ELECTRON_TUNER_MENU.get(),inventory,id,stack,handler,slot),
                                ElectronTunerItem.itemName),
                        buf -> buf.writeVarInt(slot)
                );
            }
        });
        return true;
    }
}
