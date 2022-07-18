package com.infamous.dungeons_gear.integration.curios.client.message;

import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsage;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;
import java.util.function.Supplier;

public class CuriosArtifactStopMessage {
    private final int slot;

    public CuriosArtifactStopMessage(int slot) {
        this.slot = slot;
    }

    public static void encode(CuriosArtifactStopMessage packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.slot);
    }

    public static CuriosArtifactStopMessage decode(FriendlyByteBuf buf) {
        return new CuriosArtifactStopMessage(buf.readInt());
    }

    public static class CuriosArtifactHandler {
        public static void handle(CuriosArtifactStopMessage packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().setPacketHandled(true);
                ctx.get().enqueueWork(() -> {
                    ServerPlayer player = ctx.get().getSender();
                    if (player != null) {
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
                            Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
                            if(artifactStackHandler.isPresent()) {
                                ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(packet.slot);
                                ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
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