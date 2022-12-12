package com.infamous.dungeons_gear.enchantments.types;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class DungeonsEnchantment extends Enchantment {
    protected DungeonsEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
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
                && (DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() || MODID.equals(stack.getItem().getRegistryName().getNamespace()))
                && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this)
                && super.canApplyAtEnchantingTable(stack)
                && (DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() || MODID.equals(stack.getItem().getRegistryName().getNamespace()));
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
