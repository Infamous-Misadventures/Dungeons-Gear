package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.PacketOffhandAttack;
import com.infamous.dungeons_gear.registry.AttributeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.*;

public class CombatEventHandler {

    public static void checkForOffhandAttack() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().screen == null && !Minecraft.getInstance().isPaused() && player != null && !player.isBlocking()) {
            ItemStack offhand = player.getOffhandItem();
            if (offhand.getItem() instanceof IDualWieldWeapon && ((IDualWieldWeapon<?>) offhand.getItem()).canAttack(player, offhand)) {
                float reach = (float) 3.0D;
                if (mc.player != null) {
                    reach = (float) mc.player.getAttributeBaseValue(AttributeRegistry.ATTACK_REACH.get());
                }
                if (player.isCreative()) reach += 2.0;
                HitResult rayTrace = getEntityMouseOverExtended(reach);
                if (rayTrace instanceof EntityHitResult) {
                    EntityHitResult entityRayTrace = (EntityHitResult) rayTrace;
                    Entity entityHit = entityRayTrace.getEntity();
                    if (entityHit != player && entityHit != player.getVehicle()) {
                        NetworkHandler.INSTANCE.sendToServer(new PacketOffhandAttack(entityHit.getId()));
                    }

                }
            }
        }
    }

    private static HitResult getEntityMouseOverExtended(float reach) {
        HitResult result = null;
        Minecraft mc = Minecraft.getInstance();
        Entity viewEntity = mc.cameraEntity;
        if (viewEntity != null && mc.level != null) {
            double reachDistance = (double) reach;
            HitResult rayTrace = viewEntity.pick(reachDistance, 0.0F, false);
            Vec3 eyePos = viewEntity.getEyePosition(0.0F);
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

            Vec3 lookVec = viewEntity.getViewVector(1.0F);
            Vec3 attackVec = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);
            AABB axisAlignedBB = viewEntity.getBoundingBox().expandTowards(lookVec.scale(reachDistance)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult entityRayTrace = ProjectileUtil.getEntityHitResult(viewEntity, eyePos, attackVec, axisAlignedBB, (entity) -> !entity.isSpectator() && entity.isPickable(), attackReach);
            if (entityRayTrace != null) {
                Vec3 hitVec = entityRayTrace.getLocation();
                double squareDistanceTo = eyePos.distanceToSqr(hitVec);
                if (hasExtendedReach && squareDistanceTo > (double) (reach * reach)) {
                    result = BlockHitResult.miss(hitVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), new BlockPos(hitVec));
                } else if (squareDistanceTo < attackReach) {
                    result = entityRayTrace;
                }
            } else {
                result = BlockHitResult.miss(attackVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), new BlockPos(attackVec));
            }
        }

        return (HitResult) result;
    }
}
