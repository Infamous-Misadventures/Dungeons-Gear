package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantmnet;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ElectrifiedEnchantment extends JumpingEnchantmnet {

    public ElectrifiedEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof JumpingEnchantmnet);
    }

}
