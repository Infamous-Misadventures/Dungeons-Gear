package com.infamous.dungeons_gear.items.armor;


import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.infamous.dungeons_gear.registry.ItemRegistry.CHAMPIONS_ARMOR;
import static com.infamous.dungeons_gear.registry.ItemRegistry.HEROS_ARMOR;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class ArmorEvents {

    @SubscribeEvent
    public static void onSpelunkerArmorEquipped(LivingEquipmentChangeEvent event) {
        if (event.getEntityLiving() instanceof Player && event.getSlot() != EquipmentSlot.OFFHAND && event.getSlot() != EquipmentSlot.MAINHAND) {
            Player playerEntity = (Player) event.getEntityLiving();
            Level world = playerEntity.getCommandSenderWorld();
            if (event.getTo().getItem() instanceof PetBatArmorGear) {
                if (((PetBatArmorGear) event.getTo().getItem()).doGivesYouAPetBat()) {
                    ArmorEffectHelper.summonOrTeleportBat(playerEntity, world);
                }
            }
        }
    }

    @SubscribeEvent
    public static void respawnPetBat(TickEvent.PlayerTickEvent event) {
        if (event.player.tickCount % 140 == 0)
            for (ItemStack i : event.player.getArmorSlots()) {
                if (i.getItem() instanceof PetBatArmorGear && ((PetBatArmorGear) i.getItem()).doGivesYouAPetBat()) {
                    ArmorEffectHelper.summonOrTeleportBat(event.player, event.player.level);
                    return;
                }
            }
    }

    @SubscribeEvent
    public static void onFreezingApplied(PotionEvent.PotionAddedEvent event) {
        MobEffectInstance effectInstance = event.getPotionEffect();
        LivingEntity livingEntity = event.getEntityLiving();
        ItemStack helmet = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        reduceFreezingEffect(event, effectInstance, helmet, chestplate);
    }

    private static void reduceFreezingEffect(PotionEvent.PotionAddedEvent event, MobEffectInstance effectInstance, ItemStack helmet, ItemStack chestplate) {
        float freezingResistance = helmet.getItem() instanceof FreezingResistanceArmorGear ? (float) ((FreezingResistanceArmorGear) helmet.getItem()).getFreezingResistance() : 0;
        float freezingResistance2 = chestplate.getItem() instanceof FreezingResistanceArmorGear ? (float) ((FreezingResistanceArmorGear) chestplate.getItem()).getFreezingResistance() : 0;

        float freezingMultiplier = freezingResistance * 0.01F + freezingResistance2 * 0.01F;

        if (freezingMultiplier > 0) {
            if (event.getPotionEffect().getEffect() == MobEffects.MOVEMENT_SLOWDOWN || event.getPotionEffect().getEffect() == MobEffects.DIG_SLOWDOWN) {
                int oldDuration = effectInstance.getDuration();
                effectInstance.duration = (int) (oldDuration * freezingMultiplier);
            }
        }
    }

    @SubscribeEvent
    public static void onHealthPotionConsumed(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntityLiving() instanceof Player)) return;
        Player player = (Player) event.getEntityLiving();
        if (player.isAlive()) {
            List<MobEffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if (potionEffects.isEmpty()) return;
            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);

            if (potionEffects.get(0).getEffect() == MobEffects.HEAL) {
                float healthPotionBoost = 0;
                healthPotionBoost += helmet.getItem() == CHAMPIONS_ARMOR.getHead().get() ? 1 : 0;
                healthPotionBoost += chestplate.getItem() == CHAMPIONS_ARMOR.getChest().get() ? 1 : 0;
                healthPotionBoost += leggings.getItem() == CHAMPIONS_ARMOR.getLegs().get() ? 1 : 0;
                healthPotionBoost += feet.getItem() == CHAMPIONS_ARMOR.getFeet().get() ? 1 : 0;
                healthPotionBoost += helmet.getItem() == HEROS_ARMOR.getHead().get() ? 1 : 0;
                healthPotionBoost += chestplate.getItem() == HEROS_ARMOR.getChest().get() ? 1 : 0;
                healthPotionBoost += leggings.getItem() == HEROS_ARMOR.getLegs().get() ? 1 : 0;
                healthPotionBoost += feet.getItem() == HEROS_ARMOR.getFeet().get() ? 1 : 0;

                if (healthPotionBoost > 0) {
                    player.heal(healthPotionBoost);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive()) {
            Combo comboCap = ComboHelper.getComboCapability(player);
            if (comboCap == null) return;

            if (comboCap.getJumpCooldownTimer() > 0) {
                comboCap.setJumpCooldownTimer(comboCap.getJumpCooldownTimer() - 1);
            } else if(comboCap.getJumpCooldownTimer() < 0){
                comboCap.setJumpCooldownTimer(0);
            }

            if (comboCap.getDualWieldTimer() > 0) {
                comboCap.setDualWieldTimer(comboCap.getDualWieldTimer() - 1);
            } else if (comboCap.getComboCount() < 0){
                comboCap.setComboCount(0);
            }
            comboCap.setOffhandCooldown(comboCap.getOffhandCooldown() + 1);
        }
    }

}
