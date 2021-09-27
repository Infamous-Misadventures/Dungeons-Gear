package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.registry.AttributeRegistry;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;


public class CombatEventHandler {

    public static void checkForOffhandAttack() {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().screen == null && !Minecraft.getInstance().isPaused() && player != null && !player.isBlocking()) {
            ItemStack offhand = player.getOffhandItem();
            if (offhand.getItem() instanceof IDualWieldWeapon && ((IDualWieldWeapon<?>) offhand.getItem()).canAttack(player, offhand)) {
                float reach = (float) 3.0D;
                if (mc.player != null) {
                    reach = (float) mc.player.getAttributeBaseValue(AttributeRegistry.ATTACK_REACH.get());
                }
                if (player.isCreative()) reach += 2.0;
                RayTraceResult rayTrace = getEntityMouseOverExtended(reach);
                if (rayTrace instanceof EntityRayTraceResult) {
                    EntityRayTraceResult entityRayTrace = (EntityRayTraceResult) rayTrace;
                    Entity entityHit = entityRayTrace.getEntity();
                    if (entityHit != player && entityHit != player.getVehicle()) {
                        NetworkHandler.INSTANCE.sendToServer(new PacketOffhandAttack(entityHit.getId()));
                    }

                }
            }
        }
    }

    private static RayTraceResult getEntityMouseOverExtended(float reach) {
        RayTraceResult result = null;
        Minecraft mc = Minecraft.getInstance();
        Entity viewEntity = mc.cameraEntity;
        if (viewEntity != null && mc.level != null) {
            double reachDistance = (double) reach;
            RayTraceResult rayTrace = viewEntity.pick(reachDistance, 0.0F, false);
            Vector3d eyePos = viewEntity.getEyePosition(0.0F);
            boolean hasExtendedReach = false;
            double attackReach = reachDistance;
            if (mc.gameMode != null) {
                if (mc.gameMode.hasFarPickRange() && reachDistance < 6.0D) {
                    attackReach = 6.0D;
                    reachDistance = attackReach;
                } else if (reachDistance > (double) reach) {
                    hasExtendedReach = true;
                }
            }

            attackReach = rayTrace.getLocation().distanceToSqr(eyePos);

            Vector3d lookVec = viewEntity.getViewVector(1.0F);
            Vector3d attackVec = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);
            AxisAlignedBB axisAlignedBB = viewEntity.getBoundingBox().expandTowards(lookVec.scale(reachDistance)).inflate(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityRayTrace = ProjectileHelper.getEntityHitResult(viewEntity, eyePos, attackVec, axisAlignedBB, (entity) -> !entity.isSpectator() && entity.isPickable(), attackReach);
            if (entityRayTrace != null) {
                Vector3d hitVec = entityRayTrace.getLocation();
                double squareDistanceTo = eyePos.distanceToSqr(hitVec);
                if (hasExtendedReach && squareDistanceTo > (double) (reach * reach)) {
                    result = BlockRayTraceResult.miss(hitVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), new BlockPos(hitVec));
                } else if (squareDistanceTo < attackReach) {
                    result = entityRayTrace;
                }
            } else {
                result = BlockRayTraceResult.miss(attackVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), new BlockPos(attackVec));
            }
        }

        return (RayTraceResult) result;
    }
}
