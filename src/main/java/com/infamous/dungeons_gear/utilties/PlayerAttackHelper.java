package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.damagesources.OffhandAttackDamageSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameType;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import java.util.Iterator;
import java.util.Optional;

public class PlayerAttackHelper {

    public static void swingArm(ServerPlayerEntity playerEntity, Hand hand) {
        ItemStack stack = playerEntity.getHeldItem(hand);
        if (stack.isEmpty() || !stack.onEntitySwing(playerEntity)) {
            if (!playerEntity.isSwingInProgress || playerEntity.swingProgressInt >= getArmSwingAnimationEnd(playerEntity) / 2 || playerEntity.swingProgressInt < 0) {
                playerEntity.swingProgressInt = -1;
                playerEntity.isSwingInProgress = true;
                playerEntity.swingingHand = hand;
                if (playerEntity.world instanceof ServerWorld) {
                    SAnimateHandPacket sanimatehandpacket = new SAnimateHandPacket(playerEntity, hand == Hand.MAIN_HAND ? 0 : 3);
                    ServerChunkProvider serverchunkprovider = ((ServerWorld)playerEntity.world).getChunkProvider();

                    serverchunkprovider.sendToAllTracking(playerEntity, sanimatehandpacket);
                }
            }

        }
    }

    private static int getArmSwingAnimationEnd(LivingEntity livingEntity) {
        if (EffectUtils.hasMiningSpeedup(livingEntity)) {
            return 6 - (1 + EffectUtils.getMiningSpeedup(livingEntity));
        } else {
            return livingEntity.isPotionActive(Effects.MINING_FATIGUE) ? 6 + (1 + livingEntity.getActivePotionEffect(Effects.MINING_FATIGUE).getAmplifier()) * 2 : 6;
        }
    }

    public static void attackTargetEntityWithCurrentOffhandItem(ServerPlayerEntity serverPlayerEntity, Entity target) {
        if (serverPlayerEntity.interactionManager.getGameType() == GameType.SPECTATOR) {
            serverPlayerEntity.setSpectatingEntity(target);
        } else {
            swapHeldItems(serverPlayerEntity);
            serverPlayerEntity.attackTargetEntityWithCurrentItem(target);
            swapHeldItems(serverPlayerEntity);
        }

    }

    public static void swapHeldItems(LivingEntity e) {
        //attributes = new ArrayList<>();
        ItemStack main = e.getHeldItemMainhand(), off = e.getHeldItemOffhand();
        int tssl = e.ticksSinceLastSwing;
        boolean silent = e.isSilent();
        e.setSilent(true);
        ICombo cap = CapabilityHelper.getComboCapability(e);
        e.setHeldItem(Hand.MAIN_HAND, e.getHeldItemOffhand());
        e.setHeldItem(Hand.OFF_HAND, main);
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
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {mai.applyNonPersistentModifier(mod);});
        });
        off.getAttributeModifiers(EquipmentSlotType.MAINHAND).forEach((att, mod) -> {
            Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {mai.applyNonPersistentModifier(mod);});
        });
        e.ticksSinceLastSwing = cap.getOffhandCooldown();
        cap.setOffhandCooldown(tssl);
        e.setSilent(silent);
    }
}
