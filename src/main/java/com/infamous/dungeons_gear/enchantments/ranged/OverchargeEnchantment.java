package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import net.minecraft.enchantment.Enchantment.Rarity;

public class OverchargeEnchantment extends DungeonsEnchantment {

    public OverchargeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.BOW, ModEnchantmentTypes.WEAPON_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get()
                || (enchantment != Enchantments.QUICK_CHARGE && enchantment != RangedEnchantmentList.ACCELERATE);
    }
}
