package com.c2h6s.tinkers_advanced.network;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.network.packets.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class TiAcPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE ;
    static int id = 0;

    public static void init() {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(TinkersAdvanced.MODID, "tiac_message")).networkProtocolVersion(() -> "1").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
        INSTANCE.messageBuilder(PCofhModSwitchC2S.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(PCofhModSwitchC2S::new).encoder(PCofhModSwitchC2S::toByte).consumerMainThread(PCofhModSwitchC2S::handle).add();
        INSTANCE.messageBuilder(PParticleChainS2C.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(PParticleChainS2C::new).encoder(PParticleChainS2C::toByte).consumerMainThread(PParticleChainS2C::handle).add();
        INSTANCE.messageBuilder(PElectronTunerAdjustC2S.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(PElectronTunerAdjustC2S::new).encoder(PElectronTunerAdjustC2S::toByte).consumerMainThread(PElectronTunerAdjustC2S::handle).add();
        INSTANCE.messageBuilder(PElectronTunerOpenMenuC2S.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(PElectronTunerOpenMenuC2S::new).encoder(PElectronTunerOpenMenuC2S::toByte).consumerMainThread(PElectronTunerOpenMenuC2S::handle).add();
        INSTANCE.messageBuilder(PElectronTunerMenuSyncS2C.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(PElectronTunerMenuSyncS2C::new).encoder(PElectronTunerMenuSyncS2C::toByte).consumerMainThread(PElectronTunerMenuSyncS2C::handle).add();
        INSTANCE.messageBuilder(PToolEnergyProductionSyncS2C.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(PToolEnergyProductionSyncS2C::new).encoder(PToolEnergyProductionSyncS2C::toByte).consumerMainThread(PToolEnergyProductionSyncS2C::handle).add();
        INSTANCE.messageBuilder(PExchangerBEItemSyncS2C.class, id++, NetworkDirection.PLAY_TO_CLIENT).decoder(PExchangerBEItemSyncS2C::new).encoder(PExchangerBEItemSyncS2C::toByte).consumerMainThread(PExchangerBEItemSyncS2C::handle).add();
        INSTANCE.messageBuilder(PExchangerBEItemSyncC2S.class, id++, NetworkDirection.PLAY_TO_SERVER).decoder(PExchangerBEItemSyncC2S::new).encoder(PExchangerBEItemSyncC2S::toByte).consumerMainThread(PExchangerBEItemSyncC2S::handle).add();
    }

    public static <MSG> void sendToServer(MSG msg){
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player),msg);
    }

    public static <MSG> void sendToClient(MSG msg){
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}
