package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import static com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList.ARROW_HOARDER;

public class ArrowHoarderEnchantment extends JumpingEnchantment {

    public ArrowHoarderEnchantment() {
        super(Rarity.UNCOMMON, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    public static int arrowHoarderLevel(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(ARROW_HOARDER, stack);
    }
}
