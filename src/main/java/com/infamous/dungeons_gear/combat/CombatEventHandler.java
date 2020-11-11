package com.infamous.dungeons_gear.combat;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.init.AttributeRegistry;
import com.infamous.dungeons_gear.interfaces.IOffhandAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
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
            checkForLongReachAttack();
        }
    }

    @SubscribeEvent(
            priority = EventPriority.NORMAL,
            receiveCanceled = true
    )
    public static void onKeyEvent(InputEvent.KeyInputEvent ev) {
        KeyBinding keyAttack = Minecraft.getInstance().gameSettings.keyBindAttack;
        if (ev.getKey() == keyAttack.getKey().getKeyCode() && ev.getAction() == 1) {
            checkForLongReachAttack();
        }
    }

    public static void checkForOffhandAttack(){
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if (Minecraft.getInstance().world != null && Minecraft.getInstance().currentScreen == null && !Minecraft.getInstance().isGamePaused() && player != null && !player.isActiveItemStackBlocking()) {
            ItemStack offhand = player.getHeldItemOffhand();
            if (offhand.getItem() instanceof IOffhandAttack) {
                IAttributeInstance attackReachAttribute = player.getAttribute(AttributeRegistry.ATTACK_REACH);
                // getAttribute can actually return null, since the getAttributeInstance method is annotated as Nullable
                if(attackReachAttribute == null) {
                    DungeonsGear.LOGGER.warn("Attack Reach attribute was not found for " + player.toString() + " during on offhand attack");
                    return;
                }
                float reach = (float) attackReachAttribute.getBaseValue();
                if(player.isCreative()) reach *= 2.0;
                RayTraceResult rayTrace = getEntityMouseOverExtended(reach);
                if (rayTrace instanceof EntityRayTraceResult) {
                    EntityRayTraceResult entityRayTrace = (EntityRayTraceResult)rayTrace;
                    Entity entityHit = entityRayTrace.getEntity();
                    if (entityHit != player && entityHit != player.getRidingEntity()) {
                        NetworkHandler.sendPacketToServer(new PacketOffhandAttack(entityHit.getEntityId()));
                    }

                }
            }
        }
    }

    public static void checkForLongReachAttack(){
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if (Minecraft.getInstance().world != null && Minecraft.getInstance().currentScreen == null && !Minecraft.getInstance().isGamePaused() && player != null && !player.isActiveItemStackBlocking()) {
            IAttributeInstance attackReachAttribute = player.getAttribute(AttributeRegistry.ATTACK_REACH);
            // getAttribute can actually return null, since the getAttributeInstance method is annotated as Nullable
            if(attackReachAttribute == null) {
                DungeonsGear.LOGGER.warn("Attack Reach attribute was not found for " + player.toString() + " during a long reach attack");
                return;
            }
            float reach = (float) attackReachAttribute.getValue();
            if(player.isCreative()) reach *= 2.0;
            RayTraceResult rayTrace = getEntityMouseOverExtended(reach);
            if (rayTrace instanceof EntityRayTraceResult) {
                EntityRayTraceResult entityRayTrace = (EntityRayTraceResult)rayTrace;
                Entity entityHit = entityRayTrace.getEntity();
                if (entityHit != player && entityHit != player.getRidingEntity()) {
                    NetworkHandler.sendPacketToServer(new PacketLongReachAttack(entityHit.getEntityId()));
                }

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
            Vec3d eyePos = viewEntity.getEyePosition(0.0F);
            boolean hasExtendedReach = false;
            double attackReach = reachDistance;
            if (mc.playerController != null) {
                if (mc.playerController.extendedReach()) {
                    attackReach = reach * 2.0;
                    reachDistance = attackReach;
                } else if (reachDistance > (double)reach) {
                    hasExtendedReach = true;
                }
            }

            attackReach = rayTrace.getHitVec().squareDistanceTo(eyePos);

            Vec3d lookVec = viewEntity.getLook(1.0F);
            Vec3d attackVec = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);
            AxisAlignedBB axisAlignedBB = viewEntity.getBoundingBox().expand(lookVec.scale(reachDistance)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityRayTrace = ProjectileHelper.rayTraceEntities(viewEntity, eyePos, attackVec, axisAlignedBB, (entity) -> !entity.isSpectator() && entity.canBeCollidedWith(), attackReach);
            if (entityRayTrace != null) {
                Vec3d hitVec = entityRayTrace.getHitVec();
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
