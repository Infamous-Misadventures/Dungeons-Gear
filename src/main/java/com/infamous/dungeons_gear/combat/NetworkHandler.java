package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(DungeonsGear.MODID, "network"))
            .clientAcceptedVersions("1"::equals)
            .serverAcceptedVersions("1"::equals)
            .networkProtocolVersion(() -> "1")
            .simpleChannel();
    protected static int packetID = 0;

    public NetworkHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(getPacketID(), PacketOffhandAttack.class, PacketOffhandAttack::encode, PacketOffhandAttack::decode, PacketOffhandAttack.OffhandHandler::handle);
        INSTANCE.registerMessage(getPacketID(), PacketBreakItem.class, PacketBreakItem::encode, PacketBreakItem::decode, PacketBreakItem.BreakItemHandler::handle);
        INSTANCE.registerMessage(getPacketID(), PacketUpdateSouls.class, PacketUpdateSouls::encode, PacketUpdateSouls::decode, PacketUpdateSouls.UpdateSoulsHandler::handle);
    }

    public static int getPacketID() {
        return packetID++;
    }
}
