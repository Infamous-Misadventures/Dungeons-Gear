package com.infamous.dungeons_gear.enchantments.types;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.registries.ForgeRegistries;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

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
                && (DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() || MODID.equals(ForgeRegistries.ITEMS.getKey(stack.getItem()).getNamespace()))
                && super.canEnchant(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return ModEnchantmentHelper.isNotBlacklistedEnchant(this)
                && super.canApplyAtEnchantingTable(stack)
                && (DungeonsGearConfig.ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR.get() || MODID.equals(ForgeRegistries.ITEMS.getKey(stack.getItem()).getNamespace()));
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
