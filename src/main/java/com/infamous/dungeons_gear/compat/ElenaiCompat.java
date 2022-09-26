package com.infamous.dungeons_gear.compat;

import com.elenai.elenaidodge2.api.DodgeEvent;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.capabilities.combo.RollHelper;
import com.infamous.dungeons_gear.enchantments.ranged.BurstBowstringEnchantment;
import com.infamous.dungeons_gear.enchantments.ranged.RollChargeEnchantment;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.infamous.dungeons_gear.registry.ItemRegistry.OCELOT_ARMOR;
import static com.infamous.dungeons_gear.registry.ItemRegistry.SHADOW_WALKER_ARMOR;

public class ElenaiCompat {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void dodge(DodgeEvent.ServerDodgeEvent event) {
        PlayerEntity player = event.getPlayer();
        if (DungeonsGearCompatibility.elenaiDodge) {
            ItemStack helmet = player.getItemBySlot(EquipmentSlotType.HEAD);
            ItemStack chestplate = player.getItemBySlot(EquipmentSlotType.CHEST);
            ICombo comboCap = CapabilityHelper.getComboCapability(player);
            if (comboCap == null) return;
            int jumpCooldownTimer = comboCap.getJumpCooldownTimer();

            if (jumpCooldownTimer <= 0) {
                float jumpBoost = helmet.getItem() == OCELOT_ARMOR.getHead().get() || helmet.getItem() == SHADOW_WALKER_ARMOR.getHead().get() ? 25 : 0;
                float jumpBoost2 = chestplate.getItem() == OCELOT_ARMOR.getChest().get() || chestplate.getItem() == SHADOW_WALKER_ARMOR.getChest().get() ? 25 : 0;
                float totalJumpBoost = jumpBoost * 0.02F + jumpBoost2 * 0.02F;

                if (totalJumpBoost > 0) {
                    event.setForce(event.getForce()*(1+totalJumpBoost));
                }


                ArmorEffectHelper.handleInvulnerableJump(player, helmet, chestplate);

                ArmorEffectHelper.handleJumpEnchantments(player, helmet, chestplate);

                BurstBowstringEnchantment.activateBurstBowString(player);

                RollChargeEnchantment.activateRollCharge(player);
            }

            RollHelper.incrementJumpCounter(player);

            if(jumpCooldownTimer <= 0 && RollHelper.hasReachedJumpLimit(player)){
                RollHelper.startCooldown(player, comboCap);
            }
        }
    }
}
