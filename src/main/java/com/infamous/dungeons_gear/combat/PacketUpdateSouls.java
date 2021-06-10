package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateSouls {
    private final float newAmount;

    public PacketUpdateSouls(float souls) {
        this.newAmount = souls;
    }

    public static void encode(PacketUpdateSouls packet, PacketBuffer buf) {
        buf.writeFloat(packet.newAmount);
    }

    public static PacketUpdateSouls decode(PacketBuffer buf) {
        return new PacketUpdateSouls(buf.readFloat());
    }

    public static class UpdateSoulsHandler {
        public static void handle(PacketUpdateSouls packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().enqueueWork(() ->
                        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                                    if (Minecraft.getInstance().player != null && CapabilityHelper.getComboCapability(Minecraft.getInstance().player) != null)
                                        CapabilityHelper.getComboCapability(Minecraft.getInstance().player).setSouls(packet.newAmount);
                                }
                        ));
            }
        }
    }
}
