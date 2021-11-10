package com.infamous.dungeons_gear.enchantments.types;

import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

import net.minecraft.enchantment.Enchantment.Rarity;

public class BeastEnchantment extends DungeonsEnchantment{

    protected BeastEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }
}
