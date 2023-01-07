package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

import java.util.Optional;

public class PlayerAttackHelper {

    public static void swingArm(ServerPlayer playerEntity, InteractionHand hand) {
        ItemStack stack = playerEntity.getItemInHand(hand);
        if (stack.isEmpty() || !stack.onEntitySwing(playerEntity)) {
            if (!playerEntity.swinging || playerEntity.swingTime >= getArmSwingAnimationEnd(playerEntity) / 2 || playerEntity.swingTime < 0) {
                playerEntity.swingTime = -1;
                playerEntity.swinging = true;
                playerEntity.swingingArm = hand;
                if (playerEntity.level instanceof ServerLevel) {
                    ClientboundAnimatePacket sanimatehandpacket = new ClientboundAnimatePacket(playerEntity, hand == InteractionHand.MAIN_HAND ? 0 : 3);
                    ServerChunkCache serverchunkprovider = ((ServerLevel) playerEntity.level).getChunkSource();

                    serverchunkprovider.broadcast(playerEntity, sanimatehandpacket);
                }
            }

        }
    }

    private static int getArmSwingAnimationEnd(LivingEntity livingEntity) {
        if (MobEffectUtil.hasDigSpeed(livingEntity)) {
            return 6 - (1 + MobEffectUtil.getDigSpeedAmplification(livingEntity));
        } else {
            return livingEntity.hasEffect(MobEffects.DIG_SLOWDOWN) ? 6 + (1 + livingEntity.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 2 : 6;
        }
    }

    public static void attackTargetEntityWithCurrentOffhandItem(ServerPlayer serverPlayerEntity, Entity target) {
        if (serverPlayerEntity.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
            serverPlayerEntity.setCamera(target);
        } else {
            swapHeldItems(serverPlayerEntity);
            serverPlayerEntity.attack(target);
            swapHeldItems(serverPlayerEntity);
        }

    }

    public static void swapHeldItems(LivingEntity e) {
        //attributes = new ArrayList<>();
        ItemStack main = e.getMainHandItem(), off = e.getOffhandItem();
        int tssl = e.attackStrengthTicker;
        boolean silent = e.isSilent();
        e.setSilent(true);
        Combo cap = ComboHelper.getComboCapability(e);
        e.setItemInHand(InteractionHand.MAIN_HAND, e.getOffhandItem());
        e.setItemInHand(InteractionHand.OFF_HAND, main);
//        attributes.addAll(main.getAttributeModifiers(EquipmentSlotType.MAINHAND).keys());
//        attributes.addAll(main.getAttributeModifiers(EquipmentSlotType.OFFHAND).keys());
//        attributes.addAll(off.getAttributeModifiers(EquipmentSlotType.MAINHAND).keys());
//        attributes.addAll(off.getAttributeModifiers(EquipmentSlotType.OFFHAND).keys());
//        attributes.forEach((att)->{Optional.ofNullable(e.getAttribute(att)).ifPresent(ModifiableAttributeInstance::compute);});
        main.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {
                mai.removeModifier(mod);
            });
        });
        off.getAttributeModifiers(EquipmentSlot.OFFHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {
                mai.removeModifier(mod);
            });
        });
        main.getAttributeModifiers(EquipmentSlot.OFFHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {
                mai.addTransientModifier(mod);
            });
        });
        off.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {
                mai.addTransientModifier(mod);
            });
        });
        e.attackStrengthTicker = cap.getOffhandCooldown();
        cap.setOffhandCooldown(tssl);
        e.setSilent(silent);
    }

    public static boolean isProbablyNotMeleeDamage(DamageSource damageSource) {
        return damageSource.isFire()
                || damageSource.isExplosion()
                || damageSource.isMagic()
                || damageSource.isProjectile()
                || !isDirectDamage(damageSource);
    }

    private static boolean isDirectDamage(DamageSource damageSource) {
        return damageSource.getMsgId().equals("mob")
                || damageSource.getMsgId().equals("player");
    }
}
