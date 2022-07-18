package com.infamous.dungeons_gear.enchantments.types;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class HealingEnchantment extends DungeonsEnchantment{

    protected HealingEnchantment(Rarity rarity, EnchantmentCategory enchantmentType, EquipmentSlot[] equipmentSlotTypes) {
        super(rarity, enchantmentType, equipmentSlotTypes);
    }
}
