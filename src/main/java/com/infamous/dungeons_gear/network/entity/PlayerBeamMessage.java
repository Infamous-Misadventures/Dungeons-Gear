package com.infamous.dungeons_gear.network.entity;

import com.infamous.dungeons_gear.entities.BeamEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

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

    public PlayerBeamMessage(BeamEntity beamEntity) {
        this.beamEntityID = beamEntity.getId();
        this.positionX = beamEntity.position().x;
        this.positionY = beamEntity.position().y;
        this.positionZ = beamEntity.position().z;
        this.xRot = beamEntity.getXRot();
        this.yRot = beamEntity.getYRot();
        this.xRotO = beamEntity.xRotO;
        this.yRotO = beamEntity.yRotO;
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

    public static void encode(PlayerBeamMessage packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.beamEntityID);
        buf.writeDouble(packet.positionX);
        buf.writeDouble(packet.positionY);
        buf.writeDouble(packet.positionZ);
        buf.writeFloat(packet.xRot);
        buf.writeFloat(packet.yRot);
        buf.writeFloat(packet.xRotO);
        buf.writeFloat(packet.yRotO);
    }

    public static PlayerBeamMessage decode(FriendlyByteBuf buf) {
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
                    ServerPlayer player = ctx.get().getSender();
                    if (player != null) {
                        Entity entity = player.level.getEntity(packet.beamEntityID);
                        if(entity instanceof BeamEntity) {
                            BeamEntity beamEntity = (BeamEntity) entity;
                            if(beamEntity.getOwner() != player) return;
                            beamEntity.setPos(packet.positionX, packet.positionY, packet.positionZ);
                            beamEntity.setXRot(packet.xRot);
                            beamEntity.setYRot(packet.yRot);
                            beamEntity.xRotO = packet.xRotO;
                            beamEntity.yRotO = packet.yRotO;
                        }
                    }
                });
            }
        }
    }
}
