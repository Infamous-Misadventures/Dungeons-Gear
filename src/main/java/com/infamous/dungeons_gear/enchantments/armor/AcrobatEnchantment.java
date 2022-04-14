package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.JumpingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

import net.minecraft.enchantment.Enchantment.Rarity;

public class AcrobatEnchantment extends JumpingEnchantment {

    public AcrobatEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public static void setJumpCooldown(ICombo comboCap, LivingEntity jumper, int timeIn){
        int acrobatLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.ACROBAT, jumper);
        if(acrobatLevel > 0){
            double reductionFactor = Math.max(1 - 0.15D * acrobatLevel, 0); // zeroes out at level 7+
            comboCap.setJumpCooldownTimer((int) (timeIn * reductionFactor));
        } else{
            comboCap.setJumpCooldownTimer(timeIn);
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof JumpingEnchantment);
    }
}
