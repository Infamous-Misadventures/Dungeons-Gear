package com.infamous.dungeons_gear.integration.curios.client.message;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.ArtifactUseContext;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.fml.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;
import java.util.function.Supplier;

public class CuriosArtifactStartMessage {
    private final int slot;
    private BlockRayTraceResult hitResult;

    public CuriosArtifactStartMessage(int slot, BlockRayTraceResult hitResult) {
        this.slot = slot;
        this.hitResult = hitResult;
    }

    public static void encode(CuriosArtifactStartMessage packet, PacketBuffer buf) {
        buf.writeInt(packet.slot);
        buf.writeBlockHitResult(packet.hitResult);
    }

    public static CuriosArtifactStartMessage decode(PacketBuffer buf) {
        return new CuriosArtifactStartMessage(buf.readInt(), buf.readBlockHitResult());
    }

    public static class CuriosArtifactHandler {
        public static void handle(CuriosArtifactStartMessage packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().setPacketHandled(true);
                ctx.get().enqueueWork(() -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    if (player != null) {
                        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
                            Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
                            if(artifactStackHandler.isPresent()) {
                                ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(packet.slot);
                                if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem) {
                                    ArtifactUseContext iuc = new ArtifactUseContext(player.level, player, Hand.MAIN_HAND, artifact, packet.hitResult);
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