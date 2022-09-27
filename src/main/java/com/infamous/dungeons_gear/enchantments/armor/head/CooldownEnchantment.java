package com.infamous.dungeons_gear.enchantments.armor.head;

import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.enchantment.EnchantmentType;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

public class CooldownEnchantment extends DungeonsEnchantment {

    public CooldownEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_HEAD, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }
}
