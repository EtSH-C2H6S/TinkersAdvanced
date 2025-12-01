package com.c2h6s.tinkers_advanced.utilities.network;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.utilities.network.packets.PExchangerBEItemSyncC2S;
import com.c2h6s.tinkers_advanced.utilities.network.packets.PExchangerBEItemSyncS2C;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class TiAcUPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE ;
    static int id = 0;

    public static void init() {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(TinkersAdvanced.MODID, "tiacu_message")).networkProtocolVersion(() -> "1").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
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
