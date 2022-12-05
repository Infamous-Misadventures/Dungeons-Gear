package com.infamous.dungeons_gear.enchantments.types;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class DamageBoostEnchantment extends DungeonsEnchantment{

    protected DamageBoostEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot[] equipmentSlotTypes) {
        super(rarity, enchantmentType, equipmentSlotTypes);
    }
}
