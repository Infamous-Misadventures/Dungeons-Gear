package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.utilties.ProjectileEffectHelper;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;

public class BurstBowstringEnchantment extends DungeonsEnchantment {

    public BurstBowstringEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public static void activateBurstBowString(LivingEntity jumper) {
        ItemStack mainhandStack = jumper.getHeldItemMainhand();
        ItemStack offhandStack = jumper.getHeldItemOffhand();
        boolean uniqueWeaponFlag = false;
        int burstBowStringLevel = 0;
        float arrowVelocity = 0.0F;
        if (mainhandStack.getItem() instanceof BowItem || mainhandStack.getItem() instanceof CrossbowItem) {
            uniqueWeaponFlag = hasBurstBowstringBuiltIn(mainhandStack);
            burstBowStringLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.BURST_BOWSTRING, mainhandStack);
            arrowVelocity = RangedAttackHelper.getVanillaOrModdedCrossbowArrowVelocity(mainhandStack);
        } else if (offhandStack.getItem() instanceof BowItem || offhandStack.getItem() instanceof CrossbowItem) {
            uniqueWeaponFlag = hasBurstBowstringBuiltIn(offhandStack);
            burstBowStringLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.BURST_BOWSTRING, offhandStack);
            arrowVelocity = RangedAttackHelper.getVanillaOrModdedCrossbowArrowVelocity(offhandStack);
        }

        if(burstBowStringLevel > 0 || uniqueWeaponFlag){
            int arrowsToFire = burstBowStringLevel;
            if(uniqueWeaponFlag) arrowsToFire++;
            ProjectileEffectHelper.fireBurstBowstringShots(jumper, 16, 0.4F, arrowVelocity, arrowsToFire);
        }
    }

    public int getMaxLevel() {
        return 3;
    }


    public static boolean hasBurstBowstringBuiltIn(ItemStack stack) {
        return stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon) stack.getItem()).hasBurstBowstringBuiltIn(stack);
    }
}
