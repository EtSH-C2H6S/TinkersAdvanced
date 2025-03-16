package com.c2h6s.tinkers_advanced.network.packets;

import com.c2h6s.tinkers_advanced.content.modifier.compat.thermal.FluxInfused;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PCofhModSwitchC2S {
    private final boolean increment;
    public PCofhModSwitchC2S(boolean increment){
        this.increment = increment;
    }

    public PCofhModSwitchC2S(FriendlyByteBuf buf){
        this.increment = buf.readBoolean();
    }

    public void toByte(FriendlyByteBuf buf){
        buf.writeBoolean(this.increment);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context =supplier.get();
        ServerPlayer serverPlayer = context.getSender();
        if (serverPlayer!=null) {
            context.enqueueWork(() -> {
                FluxInfused.switchMode(serverPlayer,this.increment);
            });
        }
        return true;
    }
}
