package com.infamous.dungeons_gear.integration.curios.client.message;

import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.IArtifactUsage;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;
import java.util.function.Supplier;

public class CuriosArtifactStopMessage {
    private final int slot;

    public CuriosArtifactStopMessage(int slot) {
        this.slot = slot;
    }

    public static void encode(CuriosArtifactStopMessage packet, PacketBuffer buf) {
        buf.writeInt(packet.slot);
    }

    public static CuriosArtifactStopMessage decode(PacketBuffer buf) {
        return new CuriosArtifactStopMessage(buf.readInt());
    }

    public static class CuriosArtifactHandler {
        public static void handle(CuriosArtifactStopMessage packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().setPacketHandled(true);
                ctx.get().enqueueWork(() -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    if (player != null) {
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
                            Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
                            if(artifactStackHandler.isPresent()) {
                                ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(packet.slot);
                                IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
                                if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem && cap.isSameUsingArtifact(artifact)) {
                                    ((ArtifactItem) cap.getUsingArtifact().getItem()).stopUsingArtifact(player);
                                    cap.stopUsingArtifact();
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}