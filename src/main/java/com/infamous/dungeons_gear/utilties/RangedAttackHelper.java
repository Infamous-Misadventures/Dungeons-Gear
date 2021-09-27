package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.bow.IBow;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.melee_ranged.GravityEnchantment;
import com.infamous.dungeons_gear.enchantments.melee_ranged.PoisonCloudEnchantment;
import com.infamous.dungeons_gear.enchantments.ranged.*;
import com.infamous.dungeons_gear.items.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.items.ranged.bows.AbstractDungeonsBowItem;
import com.infamous.dungeons_gear.items.ranged.crossbows.AbstractDungeonsCrossbowItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static net.minecraft.item.CrossbowItem.containsChargedProjectile;

public class RangedAttackHelper {

    public static int getVanillaCrossbowChargeTime(LivingEntity livingEntity, ItemStack stack){
        int vanillaCrossbowChargeTime = getVanillaCrossbowChargeTime(stack);
        ICombo comboCap = CapabilityHelper.getComboCapability(livingEntity);
        if(comboCap == null) {
            return vanillaCrossbowChargeTime;
        }
        int minTime = 1;
        return RollChargeEnchantment.hasActiveRollCharge(livingEntity) ? minTime : vanillaCrossbowChargeTime;
    }

    public static int getVanillaCrossbowChargeTime(ItemStack stack) {
        int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);

        IBow weaponCap = CapabilityHelper.getWeaponCapability(stack);
        if(weaponCap == null) return Math.max(25 - 5 * quickChargeLevel, 0);
        int crossbowChargeTime = weaponCap.getCrossbowChargeTime();
        long lastFiredTime = weaponCap.getLastFiredTime();

        if(accelerateLevel > 0 && lastFiredTime > 0){
            return Math.max(crossbowChargeTime - 5 * quickChargeLevel, 0);
        }
        else{
            return Math.max(25 - 5 * quickChargeLevel, 0);
        }
    }

    public static float getVanillaArrowVelocity(LivingEntity livingEntity, ItemStack stack, int charge) {
        float bowChargeTime = RangedAttackHelper.getVanillaBowChargeTime(livingEntity, stack);
        if(bowChargeTime <= 0){
            bowChargeTime = 1;
        }
        float arrowVelocity = (float)charge / bowChargeTime;
        arrowVelocity = (arrowVelocity * arrowVelocity + arrowVelocity * 2.0F) / 3.0F;
        float velocityLimit = 1.0F;
        int overchargeLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.OVERCHARGE, stack);
        if(overchargeLevel > 0){
            velocityLimit += overchargeLevel;
        }

        if (arrowVelocity > velocityLimit) {
            arrowVelocity = velocityLimit;
        }

        return arrowVelocity;
    }

    public static float getVanillaBowChargeTime(LivingEntity livingEntity, ItemStack stack){
        float minTime = 1.0F;
        if(RollChargeEnchantment.hasActiveRollCharge(livingEntity)) return minTime;


        int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int accelerateLevel = EnchantmentHelper.getItemEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);

        IBow weaponCap = CapabilityHelper.getWeaponCapability(stack);
        if(weaponCap == null) return Math.max(20.0F - 5 * quickChargeLevel, minTime);
        float bowChargeTime = weaponCap.getBowChargeTime();
        long lastFiredTime = weaponCap.getLastFiredTime();

        if(accelerateLevel > 0 && lastFiredTime > 0){
            return Math.max(bowChargeTime - 5 * quickChargeLevel, minTime);
        }
        else {
            return Math.max(20.0F - 5 * quickChargeLevel, minTime);
        }
    }

    public static void addWeaponTags(AbstractArrowEntity arrowEntity, ItemStack stack){
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).firesHarpoons(stack)) {
            arrowEntity.addTag(IRangedWeapon.HARPOON_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).setsPetsAttackTarget(stack)) {
            arrowEntity.addTag(IRangedWeapon.HUNTING_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasGuaranteedRicochet(stack)) {
            arrowEntity.addTag(IRangedWeapon.RELIABLE_RICOCHET_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).shootsFreezingArrows(stack)) {
            arrowEntity.addTag(IRangedWeapon.FREEZING_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).shootsExplosiveArrows(stack)) {
            arrowEntity.addTag(IRangedWeapon.EXPLOSIVE_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasMultishotBuiltIn(stack)) {
            arrowEntity.addTag(IRangedWeapon.MULTISHOT_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasExtraMultishot(stack)) {
            arrowEntity.addTag(IRangedWeapon.MULTISHOT_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasMultishotWhenCharged(stack)) {
            arrowEntity.addTag(IRangedWeapon.MULTISHOT_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).canDualWield(stack)) {
            arrowEntity.addTag(IRangedWeapon.DUAL_WIELD_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasGravityBuiltIn(stack)) {
            arrowEntity.addTag(GravityEnchantment.INTRINSIC_GRAVITY_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasPoisonCloudBuiltIn(stack)) {
            arrowEntity.addTag(PoisonCloudEnchantment.INTRINSIC_POISON_CLOUD_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasChainReactionBuiltIn(stack)) {
            arrowEntity.addTag(ChainReactionEnchantment.INTRINSIC_CHAIN_REACTION_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasGrowingBuiltIn(stack)) {
            arrowEntity.addTag(GrowingEnchantment.INTRINSIC_GROWING_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasRadianceShotBuiltIn(stack)) {
            arrowEntity.addTag(RadianceShotEnchantment.INTRINSIC_RADIANCE_SHOT_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasRicochetBuiltIn(stack)) {
            arrowEntity.addTag(RicochetEnchantment.INTRINSIC_RICOCHET_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasSuperChargedBuiltIn(stack)) {
            arrowEntity.addTag(SuperchargeEnchantment.INTRINSIC_SUPERCHARGE_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasTempoTheftBuiltIn(stack)) {
            arrowEntity.addTag(TempoTheftEnchantment.INTRINSIC_TEMPO_THEFT_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasReplenishBuiltIn(stack)) {
            arrowEntity.addTag(ReplenishEnchantment.INTRINSIC_REPLENISH_TAG);
        }
        if(stack.getItem() instanceof IRangedWeapon && ((IRangedWeapon<?>) stack.getItem()).hasWildRageBuiltIn(stack)) {
            arrowEntity.addTag(WildRageEnchantment.INTRINSIC_WILD_RAGE);
        }
    }

    public static int getModdedCrossbowChargeTime(LivingEntity livingEntity, ItemStack stack){
        int chargeTime;
        if(stack.getItem() instanceof AbstractDungeonsCrossbowItem){
            chargeTime = ((AbstractDungeonsCrossbowItem)stack.getItem()).getCrossbowChargeTime(stack);
        }
        else{
            chargeTime = 25;
        }
        return chargeTime;
    }

    public static float getModdedBowChargeTime(LivingEntity livingEntity, ItemStack stack){
        float chargeTime;
        if(stack.getItem() instanceof AbstractDungeonsBowItem){
            chargeTime = ((AbstractDungeonsBowItem)stack.getItem()).getBowChargeTime(stack);
        }
        else{
            chargeTime = 20.0F;
        }
        return chargeTime;
    }

    public static float getVanillaOrModdedCrossbowArrowVelocity(ItemStack stack) {
        float arrowVelocity;
        if(stack.getItem() instanceof AbstractDungeonsCrossbowItem){
            arrowVelocity = AbstractDungeonsCrossbowItem.getArrowVelocity(stack);
        }
        else{
            arrowVelocity = containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
        }
        return arrowVelocity;
    }



    public static float getVanillaOrModdedBowArrowVelocity(LivingEntity livingEntity, ItemStack stack, int charge) {
        float arrowVelocity;
        if(stack.getItem() instanceof AbstractDungeonsBowItem){
            arrowVelocity = ((AbstractDungeonsBowItem)stack.getItem()).getBowArrowVelocity(stack, charge);
        }
        else{
            arrowVelocity = getVanillaArrowVelocity(livingEntity, stack,charge);
        }
        return arrowVelocity;
    }
}
