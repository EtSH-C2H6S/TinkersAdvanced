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
        INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(TinkersAdvanced.MODID,"tiac_message")).networkProtocolVersion(()->"1").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
        INSTANCE.messageBuilder(PCofhModSwitchC2S.class,id++, NetworkDirection.PLAY_TO_SERVER).decoder(PCofhModSwitchC2S::new).encoder(PCofhModSwitchC2S::toByte).consumerMainThread(PCofhModSwitchC2S::handle).add();
        INSTANCE.messageBuilder(PParticleChainS2C.class,id++, NetworkDirection.PLAY_TO_CLIENT).decoder(PParticleChainS2C::new).encoder(PParticleChainS2C::toByte).consumerMainThread(PParticleChainS2C::handle).add();
        INSTANCE.messageBuilder(POpenLegCellC2S.class,id++, NetworkDirection.PLAY_TO_SERVER).decoder(POpenLegCellC2S::new).encoder(POpenLegCellC2S::toByte).consumerMainThread(POpenLegCellC2S::handle).add();
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
