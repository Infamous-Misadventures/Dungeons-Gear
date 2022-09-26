package com.infamous.dungeons_gear.items.armor;


import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.enchantments.armor.ArrowHoarderEnchantment;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

import static com.infamous.dungeons_gear.registry.ItemRegistry.*;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class ArmorEvents {

    @SubscribeEvent
    public static void onSpelunkerArmorEquipped(LivingEquipmentChangeEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && event.getSlot() != EquipmentSlotType.OFFHAND && event.getSlot() != EquipmentSlotType.MAINHAND) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            World world = playerEntity.getCommandSenderWorld();
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
        EffectInstance effectInstance = event.getPotionEffect();
        LivingEntity livingEntity = event.getEntityLiving();
        ItemStack helmet = livingEntity.getItemBySlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = livingEntity.getItemBySlot(EquipmentSlotType.CHEST);
        reduceFreezingEffect(event, effectInstance, helmet, chestplate);
    }

    private static void reduceFreezingEffect(PotionEvent.PotionAddedEvent event, EffectInstance effectInstance, ItemStack helmet, ItemStack chestplate) {
        float freezingResistance = helmet.getItem() instanceof FreezingResistanceArmorGear ? (float) ((FreezingResistanceArmorGear) helmet.getItem()).getFreezingResistance() : 0;
        float freezingResistance2 = chestplate.getItem() instanceof FreezingResistanceArmorGear ? (float) ((FreezingResistanceArmorGear) chestplate.getItem()).getFreezingResistance() : 0;

        float freezingMultiplier = freezingResistance * 0.01F + freezingResistance2 * 0.01F;

        if (freezingMultiplier > 0) {
            if (event.getPotionEffect().getEffect() == Effects.MOVEMENT_SLOWDOWN || event.getPotionEffect().getEffect() == Effects.DIG_SLOWDOWN) {
                int oldDuration = effectInstance.getDuration();
                effectInstance.duration = (int) (oldDuration * freezingMultiplier);
            }
        }
    }

    @SubscribeEvent
    public static void onHealthPotionConsumed(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if (player.isAlive()) {
            List<EffectInstance> potionEffects = PotionUtils.getMobEffects(event.getItem());
            if (potionEffects.isEmpty()) return;
            ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = player.getItemBySlot(EquipmentSlotType.CHEST);

            if (potionEffects.get(0).getEffect() == Effects.HEAL) {
                handleHealthPotionBoost(player, helmet, chestplate);
            }
        }
    }

    private static void handleHealthPotionBoost(PlayerEntity player, ItemStack helmet, ItemStack chestplate) {
        float healthPotionBoost = helmet.getItem() == CHAMPIONS_ARMOR.getHead().get() ? 1 : 0;
        float healthPotionBoost2 = chestplate.getItem() == CHAMPIONS_ARMOR.getChest().get() ? 1 : 0;
        float totalhealthPotionBoost = (healthPotionBoost + healthPotionBoost2);

        if (totalhealthPotionBoost > 0) {
            //player.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH, 1, (int) totalhealthPotionBoost - 1));
            //nerf hammer!
            player.heal(totalhealthPotionBoost * 2);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player == null) return;
        if (event.phase == TickEvent.Phase.START) return;
        if (player.isAlive()) {
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap == null) return;

            if (comboCap.getJumpCooldownTimer() > 0) {
                comboCap.setJumpCooldownTimer(comboCap.getJumpCooldownTimer() - 1);
            } else if(comboCap.getJumpCooldownTimer() < 0){
                comboCap.setJumpCooldownTimer(0);
            }

            if (comboCap.getComboTimer() > 0) {
                comboCap.setComboTimer(comboCap.getComboTimer() - 1);
            } else if (comboCap.getComboCount() < 0){
                comboCap.setComboCount(0);
            }
            comboCap.setOffhandCooldown(comboCap.getOffhandCooldown() + 1);
        }
    }

}
