package com.infamous.dungeons_gear.interfaces;

import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IArtifact {

    static void setArtifactCooldown(PlayerEntity playerIn, Item item, int cooldownInTicks){
        ItemStack helmet = playerIn.getItemStackFromSlot(EquipmentSlotType.HEAD);
        ItemStack chestplate = playerIn.getItemStackFromSlot(EquipmentSlotType.CHEST);


        float armorCooldownModifier = helmet.getItem() instanceof IArmor ? (float) ((IArmor) helmet.getItem()).getArtifactCooldown() : 0;
        float armorCooldownModifier2 = chestplate.getItem() instanceof IArmor ? (float) ((IArmor) chestplate.getItem()).getArtifactCooldown() : 0;

        float totalArmorCooldownModifier = 1.0F - armorCooldownModifier*0.01F - armorCooldownModifier2*0.01F;
        float cooldownEnchantmentReduction = 0;
        if(ModEnchantmentHelper.hasEnchantment(playerIn, ArmorEnchantmentList.COOLDOWN)) {
            int cooldownEnchantmentLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.COOLDOWN, playerIn);
            cooldownEnchantmentReduction = (int) (cooldownEnchantmentLevel * 0.1F * cooldownInTicks);

        }
        playerIn.getCooldownTracker().setCooldown(item, Math.max(0, (int) (cooldownInTicks * totalArmorCooldownModifier - cooldownEnchantmentReduction)));
    }
}
