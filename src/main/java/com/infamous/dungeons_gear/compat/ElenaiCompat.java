package com.infamous.dungeons_gear.compat;

import com.elenai.elenaidodge2.api.DodgeEvent;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.interfaces.IArmor;
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
        LivingEntity livingEntity = event.getPlayer();
        if (DungeonsGearCompatibility.elenaiDodge) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            ItemStack helmet = playerEntity.getItemStackFromSlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = playerEntity.getItemStackFromSlot(EquipmentSlotType.CHEST);
            ICombo comboCap = CapabilityHelper.getComboCapability(playerEntity);
            if (comboCap == null) return;
            int jumpCooldownTimer = comboCap.getJumpCooldownTimer();

            if (jumpCooldownTimer <= 0) {
                ArmorEffectHelper.handleJumpBoost(playerEntity, helmet, chestplate);

                ArmorEffectHelper.handleInvulnerableJump(playerEntity, helmet, chestplate);

                ArmorEffectHelper.handleJumpEnchantments(playerEntity, helmet, chestplate);
            }

            float jumpCooldown = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getLongerJumpAbilityCooldown() : 0;
            float jumpCooldown2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getLongerJumpAbilityCooldown() : 0;
            float totalJumpCooldown = jumpCooldown * 0.01F + jumpCooldown2 * 0.01F;

            int jumpCooldownTimerLength = totalJumpCooldown > 0 ? 60 + (int) (60 * totalJumpCooldown) : 60;
            comboCap.setJumpCooldownTimer(jumpCooldownTimerLength);
        }
    }
}
