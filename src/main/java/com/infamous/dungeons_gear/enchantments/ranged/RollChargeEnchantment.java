package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;

public class RollChargeEnchantment extends DungeonsEnchantment {

    public RollChargeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    public static void activateRollCharge(LivingEntity living){
        ICombo comboCap = CapabilityHelper.getComboCapability(living);
        if(comboCap == null) return;

        ItemStack mainHandWeapon = living.getMainHandItem();
        ItemStack offHandWeapon = living.getOffhandItem();

        int rollChargeLevel = 0;
        if(mainHandWeapon.getItem() instanceof BowItem || mainHandWeapon.getItem() instanceof CrossbowItem){
            rollChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ROLL_CHARGE, mainHandWeapon);
        } else if(offHandWeapon.getItem() instanceof BowItem || offHandWeapon.getItem() instanceof CrossbowItem){
            rollChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ROLL_CHARGE, offHandWeapon);
        }

        if(rollChargeLevel > 0){
            comboCap.setRollChargeTicks(20 * rollChargeLevel);

        }
    }

    public static boolean hasActiveRollCharge(LivingEntity living){
        ICombo comboCap = CapabilityHelper.getComboCapability(living);
        if(comboCap == null) return false;

        return comboCap.getRollChargeTicks() > 0;
    }

    public static void tickRollCharge(LivingEntity living){
        ICombo comboCap = CapabilityHelper.getComboCapability(living);
        if(comboCap == null) return;

        int rollChargeTicks = comboCap.getRollChargeTicks();
        if(rollChargeTicks > 0){
            comboCap.setRollChargeTicks(rollChargeTicks - 1);
        } else if(rollChargeTicks < 0){
            comboCap.setRollChargeTicks(0);
        }
    }
}
