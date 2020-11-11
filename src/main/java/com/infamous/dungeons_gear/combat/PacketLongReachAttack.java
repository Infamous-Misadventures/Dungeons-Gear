package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.init.AttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketLongReachAttack {
    private int entityID;

    public PacketLongReachAttack(int entityID) {
        this.entityID = entityID;
    }

    public static void encode(PacketLongReachAttack packet, PacketBuffer buf) {
        buf.writeInt(packet.entityID);
    }

    public static PacketLongReachAttack decode(PacketBuffer buf) {
        return new PacketLongReachAttack(buf.readInt());
    }

    public static class LongReachHandler {
        public LongReachHandler() {
        }

        public static void handle(PacketLongReachAttack packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ((NetworkEvent.Context)ctx.get()).enqueueWork(new Runnable() {
                    @Override
                    public void run() {
                        ServerPlayerEntity player = ((NetworkEvent.Context)ctx.get()).getSender();
                        Entity target = null;
                        if (player != null) {
                            target = player.world.getEntityByID(packet.entityID);
                        }
                        if (target != null) {
                            ItemStack mainhand = player.getHeldItemMainhand();
                            if (!mainhand.isEmpty()) {
                                float reach = (float) player.getAttribute(AttributeRegistry.ATTACK_REACH).getValue();
                                if(player.isCreative()) reach *= 2.0D;

                                // This is done to mitigate the difference between the render view entity's position
                                // that is checked in the client to the server player entity's position
                                float renderViewEntityOffsetFromPlayerMitigator = 0.2F;
                                reach += renderViewEntityOffsetFromPlayerMitigator;

                                double distanceSquared = player.getDistanceSq(target);
                                double reachSquared = (double)(reach * reach);

                                if (reachSquared >= distanceSquared) {
                                    player.attackTargetEntityWithCurrentItem(target);
                                }

                                player.swingArm(Hand.MAIN_HAND);

                            }
                        }

                    }
                });
            }
        }
    }
}