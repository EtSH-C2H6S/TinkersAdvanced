package com.c2h6s.tinkers_advanced.network.packets;

import appeng.items.tools.powered.AbstractPortableCell;
import appeng.menu.MenuOpener;
import com.c2h6s.tinkers_advanced.content.misc.PocketCellLocator;
import com.c2h6s.tinkers_advanced.content.modifier.compat.thermal.FluxInfused;
import com.c2h6s.tinkers_advanced.mixin.AEMixin.AbstractPortableCellAccessor;
import com.c2h6s.tinkers_advanced.network.TiAcPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class POpenLegCellC2S {
    private final int slot;
    private final ItemStack stack;
    public POpenLegCellC2S(int slot,ItemStack stack){
        this.slot = slot;
        this.stack = stack;
    }

    public POpenLegCellC2S(FriendlyByteBuf buf){
        this.slot = buf.readInt();
        this.stack = buf.readItem();
    }

    public void toByte(FriendlyByteBuf buf){
        buf.writeInt(this.slot);
        buf.writeItem(this.stack);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context =supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        if (serverPlayer!=null) {
            context.enqueueWork(() -> {
                if (this.stack.getItem() instanceof AbstractPortableCell cell) {
                    boolean b = MenuOpener.open(((AbstractPortableCellAccessor) cell).getMenuType(), serverPlayer, new PocketCellLocator(this.slot, null),false);
                    serverPlayer.sendSystemMessage(Component.literal("Cell opened : "+b));
                }
            });
        }
        return true;
    }

    public static void clientOpenCell(int slot,ItemStack stack){
        TiAcPacketHandler.sendToServer(new POpenLegCellC2S(slot,stack));
    }
}
