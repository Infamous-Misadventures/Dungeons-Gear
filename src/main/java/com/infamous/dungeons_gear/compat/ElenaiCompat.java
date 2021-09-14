package com.infamous.dungeons_gear.compat;

import com.elenai.elenaidodge2.api.DodgeEvent;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.compat.DungeonsGearCompatibility;
import com.infamous.dungeons_gear.enchantments.armor.AcrobatEnchantment;
import com.infamous.dungeons_gear.enchantments.armor.MultiRollEnchantment;
import com.infamous.dungeons_gear.enchantments.ranged.BurstBowstringEnchantment;
import com.infamous.dungeons_gear.enchantments.ranged.RollChargeEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ElenaiCompat {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void dodge(DodgeEvent.ServerDodgeEvent event) {
        PlayerEntity player = event.getPlayer();
        if (DungeonsGearCompatibility.elenaiDodge) {
            ItemStack helmet = player.getItemStackFromSlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = player.getItemStackFromSlot(EquipmentSlotType.CHEST);
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap == null) return;
            int jumpCooldownTimer = comboCap.getJumpCooldownTimer();

            if (jumpCooldownTimer <= 0) {
                float jumpBoost = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getLongerRolls() : 0;
                float jumpBoost2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getLongerRolls() : 0;
                float totalJumpBoost = jumpBoost * 0.02F + jumpBoost2 * 0.02F;

                if (totalJumpBoost > 0) {
                    event.setForce(event.getForce()*(1+totalJumpBoost));
                }


                ArmorEffectHelper.handleInvulnerableJump(player, helmet, chestplate);

                ArmorEffectHelper.handleJumpEnchantments(player, helmet, chestplate);

                BurstBowstringEnchantment.activateBurstBowString(player);

                RollChargeEnchantment.activateRollCharge(player);
            }

            MultiRollEnchantment.incrementJumpCounter(player);

            if(MultiRollEnchantment.hasReachedJumpLimit(player)){
                float jumpCooldown = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getLongerRollCooldown() : 0;
                float jumpCooldown2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getLongerRollCooldown() : 0;
                float totalJumpCooldown = jumpCooldown * 0.01F + jumpCooldown2 * 0.01F;

                int jumpCooldownTimerLength = totalJumpCooldown > 0 ? 60 + (int) (60 * totalJumpCooldown) : 60;
                AcrobatEnchantment.setJumpCooldown(comboCap, player, jumpCooldownTimerLength);
            }
        }
    }
}
