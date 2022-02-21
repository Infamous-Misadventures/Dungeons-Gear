package com.infamous.dungeons_gear.integration.curios.client.message;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;
import java.util.function.Supplier;

public class CuriosArtifactMessage {
    private final int slot;

    public CuriosArtifactMessage(int slot) {
        this.slot = slot;
    }

    public static void encode(CuriosArtifactMessage packet, PacketBuffer buf) {
        buf.writeInt(packet.slot);
    }

    public static CuriosArtifactMessage decode(PacketBuffer buf) {
        return new CuriosArtifactMessage(buf.readInt());
    }

    public static class CuriosArtifactHandler {
        public static void handle(CuriosArtifactMessage packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().setPacketHandled(true);
                ctx.get().enqueueWork(() -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    if (player != null) {
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
                            Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
                            if(artifactStackHandler.isPresent()) {
                                ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(packet.slot);
                                if (!artifact.isEmpty()) {
                                    artifact.use(player.level, player, Hand.MAIN_HAND);
                                }
                            }
                        });
                    }

                });
            }
        }
    }
}