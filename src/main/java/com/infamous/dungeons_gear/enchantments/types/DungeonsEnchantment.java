package com.infamous.dungeons_gear.enchantments.types;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class DungeonsEnchantment extends Enchantment {
    protected DungeonsEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this)
                && super.canApply(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this)
                && super.canApplyAtEnchantingTable(stack) && ((stack.getItem().getRegistryName() != null && stack.getItem().getRegistryName().getNamespace().equals(DungeonsGear.MODID)) || DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get());
    }

    @Override
    public boolean isAllowedOnBooks() {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this);
    }

    @Override
    public boolean isTreasureEnchantment() {
        return ModEnchantmentHelper.isTreasureEnchant(this);
    }
}
