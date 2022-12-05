package com.infamous.dungeons_gear.network;

import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.registry.AttributeInit;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOffhandAttack {
    private int entityID;

    public PacketOffhandAttack(int entityID) {
        this.entityID = entityID;
    }

    public static void encode(PacketOffhandAttack packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.entityID);
    }

    public static PacketOffhandAttack decode(FriendlyByteBuf buf) {
        return new PacketOffhandAttack(buf.readInt());
    }

    public static class OffhandHandler {
        public OffhandHandler() {
        }

        public static void handle(PacketOffhandAttack packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().setPacketHandled(true);
                ((NetworkEvent.Context)ctx.get()).enqueueWork(new Runnable() {
                    @Override
                    public void run() {
                            ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
                        Entity target = null;
                        if (player != null) {
                            target = player.level.getEntity(packet.entityID);
                        }
                        if (target != null) {
                                ItemStack offhand = player.getOffhandItem();
                                if (!offhand.isEmpty()) {
                                    if (offhand.getItem() instanceof IDualWieldWeapon) {
                                        float reach = (float) player.getAttributeBaseValue(AttributeInit.ATTACK_REACH.get());
                                        if(player.isCreative()) reach *= 2.0D;

                                        // This is done to mitigate the difference between the render view entity's position
                                        // that is checked in the client to the server player entity's position
                                        float renderViewEntityOffsetFromPlayerMitigator = 0.2F;
                                        reach += renderViewEntityOffsetFromPlayerMitigator;

                                        double distanceSquared = player.distanceToSqr(target);
                                        double reachSquared = (double)(reach * reach);

                                        if (reachSquared >= distanceSquared) {
                                            PlayerAttackHelper.attackTargetEntityWithCurrentOffhandItem(player, target);
                                        }

                                        PlayerAttackHelper.swingArm(player, InteractionHand.OFF_HAND);
                                    }

                                }
                            }

                    }
                });
            }
        }
    }
}
