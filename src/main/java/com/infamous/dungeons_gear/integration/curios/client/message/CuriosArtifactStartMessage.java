package com.infamous.dungeons_gear.integration.curios.client.message;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.ArtifactUseContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;
import java.util.function.Supplier;

public class CuriosArtifactStartMessage {
    private final int slot;
    private BlockHitResult hitResult;

    public CuriosArtifactStartMessage(int slot, BlockHitResult hitResult) {
        this.slot = slot;
        this.hitResult = hitResult;
    }

    public static void encode(CuriosArtifactStartMessage packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.slot);
        buf.writeBlockHitResult(packet.hitResult);
    }

    public static CuriosArtifactStartMessage decode(FriendlyByteBuf buf) {
        return new CuriosArtifactStartMessage(buf.readInt(), buf.readBlockHitResult());
    }

    public static class CuriosArtifactHandler {
        public static void handle(CuriosArtifactStartMessage packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().setPacketHandled(true);
                ctx.get().enqueueWork(() -> {
                    ServerPlayer player = ctx.get().getSender();
                    if (player != null) {
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
                            Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
                            if(artifactStackHandler.isPresent()) {
                                ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(packet.slot);
                                if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem) {
                                    ArtifactUseContext iuc = new ArtifactUseContext(player.level, player, artifact, packet.hitResult);
                                    ((ArtifactItem) artifact.getItem()).activateArtifact(iuc);
                                }
                            }
                        });
                    }

                });
            }
        }
    }
}