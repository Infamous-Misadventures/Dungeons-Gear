package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.integration.curios.client.message.CuriosArtifactMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DungeonsGear.MODID, "network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);
    protected static int packetID = 0;

    public NetworkHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(getPacketID(), PacketOffhandAttack.class, PacketOffhandAttack::encode, PacketOffhandAttack::decode, PacketOffhandAttack.OffhandHandler::handle);
        INSTANCE.registerMessage(getPacketID(), PacketBreakItem.class, PacketBreakItem::encode, PacketBreakItem::decode, PacketBreakItem.BreakItemHandler::handle);
        INSTANCE.registerMessage(getPacketID(), PacketUpdateSouls.class, PacketUpdateSouls::encode, PacketUpdateSouls::decode, PacketUpdateSouls.UpdateSoulsHandler::handle);
        INSTANCE.registerMessage(getPacketID(), CuriosArtifactMessage.class, CuriosArtifactMessage::encode, CuriosArtifactMessage::decode, CuriosArtifactMessage.CuriosArtifactHandler::handle);
    }

    public static int getPacketID() {
        return packetID++;
    }
}
