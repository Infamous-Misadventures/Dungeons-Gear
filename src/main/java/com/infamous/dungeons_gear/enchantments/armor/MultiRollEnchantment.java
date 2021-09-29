package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import com.infamous.dungeons_gear.items.armor.ArmorEvents;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.item.ItemStack;

import java.util.Comparator;
import java.util.stream.StreamSupport;

public class MultiRollEnchantment extends JumpingEnchantment {

    public MultiRollEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public static void incrementJumpCounter(LivingEntity livingEntity){
        ICombo comboCap = CapabilityHelper.getComboCapability(livingEntity);
        if(comboCap == null) return;

        comboCap.setJumpCounter(comboCap.getJumpCounter() + 1);
    }

    public static boolean hasReachedJumpLimit(LivingEntity livingEntity){
        ICombo comboCap = CapabilityHelper.getComboCapability(livingEntity);
        if(comboCap == null) return false;

        boolean armorFlag = StreamSupport.stream(livingEntity.getArmorSlots().spliterator(), false).anyMatch(MultiRollEnchantment::hasMultiRollBuiltIn);
        int multiRollLevel = Math.max(EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.MULTI_ROLL, livingEntity), armorFlag ? 1 : 0);
        int jumpLimit = multiRollLevel + 1;
        return comboCap.getJumpCounter() >= jumpLimit;
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof JumpingEnchantment);
    }

    private static boolean hasMultiRollBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof IArmor && ((IArmor) stack.getItem()).hasMultiRollBuiltIn(stack);
    }
}
