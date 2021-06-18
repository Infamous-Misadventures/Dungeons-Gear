package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
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
                ctx.get().setPacketHandled(true);
                ctx.get().enqueueWork(() ->
                DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> new SafeRunnable() {
                    private static final long serialVersionUID = 1;

                    @Override
                    public void run() {
                        if (Minecraft.getInstance().player != null
                                && CapabilityHelper.getComboCapability(Minecraft.getInstance().player) != null)
                            CapabilityHelper.getComboCapability(Minecraft.getInstance().player)
                                    .setSouls(packet.newAmount);
                    }
                }));
            }
        }
    }
}
