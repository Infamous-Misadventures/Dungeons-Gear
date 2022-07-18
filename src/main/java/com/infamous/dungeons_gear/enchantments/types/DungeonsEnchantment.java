package com.infamous.dungeons_gear.enchantments.types;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class DungeonsEnchantment extends Enchantment {
    protected DungeonsEnchantment(Rarity rarityIn, EnchantmentCategory typeIn, EquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean isTradeable() {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this)
                && DungeonsGearConfig.ENABLE_ENCHANTMENT_TRADES.get()
                && super.isTradeable();
    }

    @Override
    public boolean isDiscoverable() {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this)
                && DungeonsGearConfig.ENABLE_ENCHANTMENT_LOOT.get()
                && super.isDiscoverable();
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this)
                && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this)
                && super.canApplyAtEnchantingTable(stack)
                && ((stack.getItem().getRegistryName() != null
                && stack.getItem().getRegistryName().getNamespace().equals(DungeonsGear.MODID))
                || DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get());
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this);
    }

    @Override
    public boolean isTreasureOnly() {
        return ModEnchantmentHelper.isTreasureEnchant(this);
    }
}
