package com.infamous.dungeons_gear.enchantments.armor;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class CooldownEnchantment extends Enchantment {

    public CooldownEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_CHEST, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }
}
