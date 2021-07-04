package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;

public class FortuneOfTheSeaEnchantment extends DungeonsEnchantment {

    public FortuneOfTheSeaEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, ModEnchantmentTypes.ARMOR_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
