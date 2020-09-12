package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import com.infamous.dungeons_gear.interfaces.IExtendedAttackReach;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = {Dist.CLIENT}
)
public class CombatEventHandler {

    @SubscribeEvent(
            priority = EventPriority.NORMAL,
            receiveCanceled = true
    )
    public static void onMouseEvent(InputEvent.MouseInputEvent ev) {
        KeyBinding keyAttack = Minecraft.getInstance().gameSettings.keyBindAttack;
        if (ev.getButton() == keyAttack.getKey().getKeyCode() && ev.getAction() == 1) {
            checkForReachAttack();
        }
    }

    @SubscribeEvent(
            priority = EventPriority.NORMAL,
            receiveCanceled = true
    )
    public static void onKeyEvent(InputEvent.KeyInputEvent ev) {
        KeyBinding keyAttack = Minecraft.getInstance().gameSettings.keyBindAttack;
        if (ev.getKey() == keyAttack.getKey().getKeyCode() && ev.getAction() == 1) {
            checkForReachAttack();
        }
    }

    public static void checkForOffhandAttack(){
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if (Minecraft.getInstance().world != null && Minecraft.getInstance().currentScreen == null && !Minecraft.getInstance().isGamePaused() && player != null && !player.isActiveItemStackBlocking()) {
            ItemStack offhand = player.getHeldItemOffhand();
            ItemStack mainhand = player.getHeldItemMainhand();
            if (offhand.getItem() instanceof IOffhandAttack
                    //&& !(DualWieldUtils.doesMainhandHaveRightClickUse(mainhand))
            ) {
                //DungeonsGear.LOGGER.debug("Attempting Offhand Attack!");
                IOffhandAttack offhandWeapon = (IOffhandAttack)offhand.getItem();
                float reach = offhandWeapon.getOffhandAttackReach();
                if(player.isCreative()) reach += 2.0;
                RayTraceResult rayTrace = getEntityMouseOverExtended(reach);
                if (rayTrace instanceof EntityRayTraceResult) {
                    EntityRayTraceResult entityRayTrace = (EntityRayTraceResult)rayTrace;
                    Entity entityHit = entityRayTrace.getEntity();
                    if (entityHit != null
                            //&& entityHit.hurtResistantTime == 0 &&
                            && entityHit != player
                            && entityHit != player.getRidingEntity()) {
                        NetworkHandler.sendPacketToServer(new PacketOffhandAttack(entityHit.getEntityId()));
                        //DungeonsGear.LOGGER.debug("Offhand Attack Packet sent!");
                    }

                }
                //}
            }
        }
    }

    private static void checkForReachAttack() {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if (Minecraft.getInstance().world != null && Minecraft.getInstance().currentScreen == null && !Minecraft.getInstance().isGamePaused() && player != null && !player.isActiveItemStackBlocking()) {
            ItemStack weapon = player.getHeldItemMainhand();
            if (weapon.getItem() instanceof IExtendedAttackReach) {
                    //DungeonsGear.LOGGER.debug("Attempting Long Reach Attack!");
                    IExtendedAttackReach longReachWeapon = (IExtendedAttackReach)weapon.getItem();
                    float reach = longReachWeapon.getAttackReach();
                    RayTraceResult rayTrace = getEntityMouseOverExtended(reach);
                    if (rayTrace instanceof EntityRayTraceResult) {
                        EntityRayTraceResult entityRayTrace = (EntityRayTraceResult)rayTrace;
                        Entity entityHit = entityRayTrace.getEntity();
                        if (entityHit != null && entityHit.hurtResistantTime == 0 && entityHit != player && entityHit != player.getRidingEntity()) {
                            NetworkHandler.sendPacketToServer(new PacketLongReachAttack(entityHit.getEntityId()));
                            //DungeonsGear.LOGGER.debug("Long Reach Attack Packet sent!");
                        }

                    }
                //}
            }
        }
    }

    private static RayTraceResult getEntityMouseOverExtended(float reach) {
        RayTraceResult result = null;
        Minecraft mc = Minecraft.getInstance();
        Entity viewEntity = mc.renderViewEntity;
        if (viewEntity != null && mc.world != null) {
            double reachDistance = (double)reach;
            RayTraceResult rayTrace = viewEntity.pick(reachDistance, 0.0F, false);
            Vector3d eyePos = viewEntity.getEyePosition(0.0F);
            boolean hasExtendedReach = false;
            double attackReach = reachDistance;
            if (mc.playerController.extendedReach() && reachDistance < 6.0D) {
                attackReach = 6.0D;
                reachDistance = attackReach;
            } else if (reachDistance > (double)reach) {
                hasExtendedReach = true;
            }

            attackReach = rayTrace.getHitVec().squareDistanceTo(eyePos);

            Vector3d lookVec = viewEntity.getLook(1.0F);
            Vector3d attackVec = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);
            AxisAlignedBB axisAlignedBB = viewEntity.getBoundingBox().expand(lookVec.scale(reachDistance)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityRayTrace = ProjectileHelper.rayTraceEntities(viewEntity, eyePos, attackVec, axisAlignedBB, (entity) -> !entity.isSpectator() && entity.canBeCollidedWith(), attackReach);
            if (entityRayTrace != null) {
                Vector3d hitVec = entityRayTrace.getHitVec();
                double squareDistanceTo = eyePos.squareDistanceTo(hitVec);
                if (hasExtendedReach && squareDistanceTo > (double)(reach * reach)) {
                    result = BlockRayTraceResult.createMiss(hitVec, Direction.getFacingFromVector(lookVec.x, lookVec.y, lookVec.z), new BlockPos(hitVec));
                } else if (squareDistanceTo < attackReach) {
                    result = entityRayTrace;
                }
            } else {
                result = BlockRayTraceResult.createMiss(attackVec, Direction.getFacingFromVector(lookVec.x, lookVec.y, lookVec.z), new BlockPos(attackVec));
            }
        }

        return (RayTraceResult)result;
    }
}
