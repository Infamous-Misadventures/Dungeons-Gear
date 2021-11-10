package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.GameType;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;

public class PlayerAttackHelper {

    public static void swingArm(ServerPlayerEntity playerEntity, Hand hand) {
        ItemStack stack = playerEntity.getItemInHand(hand);
        if (stack.isEmpty() || !stack.onEntitySwing(playerEntity)) {
            if (!playerEntity.swinging || playerEntity.swingTime >= getArmSwingAnimationEnd(playerEntity) / 2 || playerEntity.swingTime < 0) {
                playerEntity.swingTime = -1;
                playerEntity.swinging = true;
                playerEntity.swingingArm = hand;
                if (playerEntity.level instanceof ServerWorld) {
                    SAnimateHandPacket sanimatehandpacket = new SAnimateHandPacket(playerEntity, hand == Hand.MAIN_HAND ? 0 : 3);
                    ServerChunkProvider serverchunkprovider = ((ServerWorld)playerEntity.level).getChunkSource();

                    serverchunkprovider.broadcast(playerEntity, sanimatehandpacket);
                }
            }

        }
    }

    private static int getArmSwingAnimationEnd(LivingEntity livingEntity) {
        if (EffectUtils.hasDigSpeed(livingEntity)) {
            return 6 - (1 + EffectUtils.getDigSpeedAmplification(livingEntity));
        } else {
            return livingEntity.hasEffect(Effects.DIG_SLOWDOWN) ? 6 + (1 + livingEntity.getEffect(Effects.DIG_SLOWDOWN).getAmplifier()) * 2 : 6;
        }
    }

    public static void attackTargetEntityWithCurrentOffhandItem(ServerPlayerEntity serverPlayerEntity, Entity target) {
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
        ICombo cap = CapabilityHelper.getComboCapability(e);
        e.setItemInHand(Hand.MAIN_HAND, e.getOffhandItem());
        e.setItemInHand(Hand.OFF_HAND, main);
//        attributes.addAll(main.getAttributeModifiers(EquipmentSlotType.MAINHAND).keys());
//        attributes.addAll(main.getAttributeModifiers(EquipmentSlotType.OFFHAND).keys());
//        attributes.addAll(off.getAttributeModifiers(EquipmentSlotType.MAINHAND).keys());
//        attributes.addAll(off.getAttributeModifiers(EquipmentSlotType.OFFHAND).keys());
//        attributes.forEach((att)->{Optional.ofNullable(e.getAttribute(att)).ifPresent(ModifiableAttributeInstance::compute);});
        main.getAttributeModifiers(EquipmentSlotType.MAINHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {mai.removeModifier(mod);});
        });
        off.getAttributeModifiers(EquipmentSlotType.OFFHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {mai.removeModifier(mod);});
        });
        main.getAttributeModifiers(EquipmentSlotType.OFFHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {mai.addTransientModifier(mod);});
        });
        off.getAttributeModifiers(EquipmentSlotType.MAINHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {mai.addTransientModifier(mod);});
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
