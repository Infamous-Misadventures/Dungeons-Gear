package com.infamous.dungeons_gear.network.entity;

import com.infamous.dungeons_gear.entities.ArtifactBeamEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerBeamMessage {

    private final int beamEntityID;
    private final double positionX;
    private final double positionY;
    private final double positionZ;
    private final float xRot;
    private final float yRot;
    private final float xRotO;
    private final float yRotO;

    public PlayerBeamMessage(ArtifactBeamEntity artifactBeamEntity) {
        this.beamEntityID = artifactBeamEntity.getId();
        this.positionX = artifactBeamEntity.position().x;
        this.positionY = artifactBeamEntity.position().y;
        this.positionZ = artifactBeamEntity.position().z;
        this.xRot = artifactBeamEntity.xRot;
        this.yRot = artifactBeamEntity.yRot;
        this.xRotO = artifactBeamEntity.xRotO;
        this.yRotO = artifactBeamEntity.yRotO;
    }

    public PlayerBeamMessage(int beamEntityID, double positionX, double positionY, double positionZ, float xRot, float yRot, float xRotO, float yRotO) {
        this.beamEntityID = beamEntityID;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.xRot = xRot;
        this.yRot = yRot;
        this.xRotO = xRotO;
        this.yRotO = yRotO;
    }

    public static void encode(PlayerBeamMessage packet, PacketBuffer buf) {
        buf.writeInt(packet.beamEntityID);
        buf.writeDouble(packet.positionX);
        buf.writeDouble(packet.positionY);
        buf.writeDouble(packet.positionZ);
        buf.writeFloat(packet.xRot);
        buf.writeFloat(packet.yRot);
        buf.writeFloat(packet.xRotO);
        buf.writeFloat(packet.yRotO);
    }

    public static PlayerBeamMessage decode(PacketBuffer buf) {
        return new PlayerBeamMessage(
                buf.readInt(),
                buf.readDouble(),
                buf.readDouble(),
                buf.readDouble(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat()
        );
    }

    public static class PlayerBeamMessageHandler {
        public static void handle(PlayerBeamMessage packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().setPacketHandled(true);
                ctx.get().enqueueWork(() -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    if (player != null) {
                        Entity entity = player.level.getEntity(packet.beamEntityID);
                        if(entity instanceof ArtifactBeamEntity) {
                            ArtifactBeamEntity artifactBeamEntity = (ArtifactBeamEntity) entity;
                            if(artifactBeamEntity.getOwner() != player) return;
                            artifactBeamEntity.setPos(packet.positionX, packet.positionY, packet.positionZ);
                            artifactBeamEntity.xRot = packet.xRot;
                            artifactBeamEntity.yRot = packet.yRot;
                            artifactBeamEntity.xRotO = packet.xRotO;
                            artifactBeamEntity.yRotO = packet.yRotO;
                        }
                    }
                });
            }
        }
    }
}
