package com.infamous.dungeons_gear.compat;

import com.elenai.elenaidodge2.api.DodgeEvent;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.enchantments.armor.AcrobatEnchantment;
import com.infamous.dungeons_gear.enchantments.armor.MultiRollEnchantment;
import com.infamous.dungeons_gear.enchantments.ranged.BurstBowstringEnchantment;
import com.infamous.dungeons_gear.enchantments.ranged.RollChargeEnchantment;
import com.infamous.dungeons_gear.utilties.ArmorEffectHelper;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.infamous.dungeons_gear.registry.AttributeRegistry.ROLL_COOLDOWN;
import static com.infamous.dungeons_gear.registry.ItemRegistry.*;
import static com.infamous.dungeons_gear.registry.ItemRegistry.SHADOW_WALKER;

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
                float jumpBoost = helmet.getItem() == OCELOT_ARMOR_HOOD.get() || helmet.getItem() == SHADOW_WALKER_HOOD.get() ? 25 : 0;
                float jumpBoost2 = chestplate.getItem() == OCELOT_ARMOR.get() || chestplate.getItem() == SHADOW_WALKER.get() ? 25 : 0;
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
                ModifiableAttributeInstance attribute = player.getAttribute(ROLL_COOLDOWN.get());
                int jumpCooldownTimerLength = attribute != null ? (int) attribute.getValue() : 180;
                comboCap.setJumpCooldownTimer(jumpCooldownTimerLength);
            }
        }
    }
}
