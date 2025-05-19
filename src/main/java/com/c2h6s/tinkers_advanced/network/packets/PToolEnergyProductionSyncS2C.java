package com.c2h6s.tinkers_advanced.network.packets;

import com.c2h6s.tinkers_advanced.content.objects.ToolEnergyProduction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PToolEnergyProductionSyncS2C {
    public final ToolEnergyProduction production;

    public PToolEnergyProductionSyncS2C(ToolEnergyProduction production) {
        this.production = production;
    }
    public PToolEnergyProductionSyncS2C(FriendlyByteBuf buf){
        this.production = ToolEnergyProduction.readFromNetwork(buf);
    }
    public void toByte(FriendlyByteBuf buf){
        this.production.toNetwork(buf);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(this.production::start);
        return true;
    }
}
