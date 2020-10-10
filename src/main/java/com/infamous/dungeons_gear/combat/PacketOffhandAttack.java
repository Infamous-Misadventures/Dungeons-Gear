package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import com.infamous.dungeons_gear.utilties.PlayerAttackHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOffhandAttack {
    private int entityID;

    public PacketOffhandAttack(int entityID) {
        this.entityID = entityID;
    }

    public static void encode(PacketOffhandAttack packet, PacketBuffer buf) {
        buf.writeInt(packet.entityID);
    }

    public static PacketOffhandAttack decode(PacketBuffer buf) {
        return new PacketOffhandAttack(buf.readInt());
    }

    public static class OffhandHandler {
        public OffhandHandler() {
        }

        public static void handle(PacketOffhandAttack packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ((NetworkEvent.Context)ctx.get()).enqueueWork(new Runnable() {
                    @Override
                    public void run() {
                            ServerPlayerEntity player = ((NetworkEvent.Context)ctx.get()).getSender();
                            Entity target = player.world.getEntityByID(packet.entityID);
                            if (player != null && target != null) {
                                //DungeonsGear.LOGGER.debug("Victim of offhand attack: " + target.getDisplayName().toString());
                                ItemStack offhand = player.getHeldItemOffhand();
                                ItemStack mainhand = player.getHeldItemMainhand();
                                if (!offhand.isEmpty()) {
                                    if (offhand.getItem() instanceof IOffhandAttack
                                        // && !(DualWieldUtils.doesMainhandHaveRightClickUse(mainhand))
                                    ) {
                                        IOffhandAttack offhandWeapon = (IOffhandAttack)offhand.getItem();
                                        float reach = offhandWeapon.getOffhandAttackReach();

                                        // Accounting for players being able to attack from 2 blocks further away in creative
                                        if(player.isCreative()) reach += 2.0;

                                        // This is done to mitigate the difference between the render view entity's position
                                        // that is checked in the client to the server player entity's position
                                        float renderViewEntityOffsetFromPlayerMitigator = 0.2F;
                                        reach += renderViewEntityOffsetFromPlayerMitigator;

                                        double distanceSquared = player.getDistanceSq(target);
                                        double reachSquared = (double)(reach * reach);

                                        //DungeonsGear.LOGGER.debug(distanceSquared);
                                        //DungeonsGear.LOGGER.debug(reachSquared);
                                        //DungeonsGear.LOGGER.debug("Is reachSquared >= distanceSquared? " + (reachSquared >= distanceSquared ? "yes" : "no"));
                                        if (reachSquared >= distanceSquared) {
                                            PlayerAttackHelper.attackTargetEntityWithCurrentOffhandItem(player, target);
                                            //DungeonsGear.LOGGER.debug("Attacking victim with offhand!");
                                        }

                                        PlayerAttackHelper.swingArm(player, Hand.OFF_HAND);
                                        //player.resetCooldown();
                                    }

                                }
                            }

                    }
                });
            }
        }
    }
}
