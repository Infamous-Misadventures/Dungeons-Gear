package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.weapon.IWeapon;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.ranged.bows.AbstractDungeonsBowItem;
import com.infamous.dungeons_gear.ranged.crossbows.AbstractDungeonsCrossbowItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static net.minecraft.item.CrossbowItem.hasChargedProjectile;

public class RangedAttackHelper {

    public static int getVanillaCrossbowChargeTime(ItemStack stack){
        int quickChargeLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int accelerateLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);

        IWeapon weaponCap = CapabilityHelper.getWeaponCapability(stack);
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

    public static float getVanillaArrowVelocity(ItemStack stack, int charge) {
        float bowChargeTime = RangedAttackHelper.getVanillaBowChargeTime(stack);
        if(bowChargeTime <= 0){
            bowChargeTime = 1;
        }
        float arrowVelocity = (float)charge / bowChargeTime;
        arrowVelocity = (arrowVelocity * arrowVelocity + arrowVelocity * 2.0F) / 3.0F;
        if (arrowVelocity > 1.0F) {
            arrowVelocity = 1.0F;
        }

        return arrowVelocity;
    }

    public static float getVanillaBowChargeTime(ItemStack stack){
        int quickChargeLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int accelerateLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);

        IWeapon weaponCap = CapabilityHelper.getWeaponCapability(stack);
        if(weaponCap == null) return Math.max(20.0F - 5 * quickChargeLevel, 0);
        float bowChargeTime = weaponCap.getBowChargeTime();
        long lastFiredTime = weaponCap.getLastFiredTime();

        if(accelerateLevel > 0 && lastFiredTime > 0){
            return Math.max(bowChargeTime - 5 * quickChargeLevel, 0);
        }
        else {
            return Math.max(20.0F - 5 * quickChargeLevel, 0);
        }
    }

    public static void addWeaponTags(AbstractArrowEntity arrowEntity, ItemStack stack){
        // BOWS
        if(stack.getItem() == DeferredItemInit.BONEBOW.get()) arrowEntity.addTag("Bonebow");
        if(stack.getItem() == DeferredItemInit.BOW_OF_LOST_SOULS.get()) arrowEntity.addTag("BowOfLostSouls");
        if(stack.getItem() == DeferredItemInit.ELITE_POWER_BOW.get()) arrowEntity.addTag("ElitePowerBow");
        if(stack.getItem() == DeferredItemInit.GUARDIAN_BOW.get()) arrowEntity.addTag("GuardianBow");
        if(stack.getItem() == DeferredItemInit.HUNTERS_PROMISE.get()) arrowEntity.addTag("HuntersPromise");
        if(stack.getItem() == DeferredItemInit.MASTERS_BOW.get()) arrowEntity.addTag("MastersBow");
        //if(stack.getItem() == DeferredItemInit.MECHANICAL_SHORTBOW.get()) arrowEntity.addTag("MechanicalShortbow");
        if(stack.getItem() == DeferredItemInit.NOCTURNAL_BOW.get()) arrowEntity.addTag("NocturnalBow");
        //if(stack.getItem() == DeferredItemInit.PURPLE_STORM.get()) arrowEntity.addTag("PurpleStorm");
        if(stack.getItem() == DeferredItemInit.RED_SNAKE.get()) arrowEntity.addTag("RedSnake");
        if(stack.getItem() == DeferredItemInit.SABREWING.get()) arrowEntity.addTag("Sabrewing");
        if(stack.getItem() == DeferredItemInit.THE_GREEN_MENACE.get()) arrowEntity.addTag("TheGreenMenace");
        if(stack.getItem() == DeferredItemInit.THE_PINK_SCOUNDREL.get()) arrowEntity.addTag("ThePinkScoundrel");
        if(stack.getItem() == DeferredItemInit.TWIN_BOW.get()) arrowEntity.addTag("TwinBow");
        if(stack.getItem() == DeferredItemInit.HUNTING_BOW.get()) arrowEntity.addTag("HuntingBow");
        if(stack.getItem() == DeferredItemInit.LONGBOW.get()) arrowEntity.addTag("Longbow");
        if(stack.getItem() == DeferredItemInit.SHORTBOW.get()) arrowEntity.addTag("Shortbow");
        if(stack.getItem() == DeferredItemInit.POWER_BOW.get()) arrowEntity.addTag("PowerBow");
        if(stack.getItem() == DeferredItemInit.SOUL_BOW.get()) arrowEntity.addTag("SoulBow");
        if(stack.getItem() == DeferredItemInit.TRICKBOW.get()) arrowEntity.addTag("Trickbow");
        if(stack.getItem() == DeferredItemInit.SNOW_BOW.get()) arrowEntity.addTag("SnowBow");
        if(stack.getItem() == DeferredItemInit.WINTERS_TOUCH.get()) arrowEntity.addTag("WintersTouch");
        //if(stack.getItem() == DeferredItemInit.HAUNTED_BOW.get()) arrowEntity.addTag("HauntedBow");
        if(stack.getItem() == DeferredItemInit.ANCIENT_BOW.get()) arrowEntity.addTag("AncientBow");
        if(stack.getItem() == DeferredItemInit.LOVE_SPELL_BOW.get()) arrowEntity.addTag("LoveSpellBow");

        // CROSSBOWS
        //if(stack.getItem() == DeferredItemInit.AUTO_CROSSBOW.get()) arrowEntity.addTag("AutoCrossbow");
        if(stack.getItem() == DeferredItemInit.AZURE_SEEKER.get()) arrowEntity.addTag("AzureSeeker");
        if(stack.getItem() == DeferredItemInit.BUTTERFLY_CROSSBOW.get()) arrowEntity.addTag("ButterflyCrossbow");
        if(stack.getItem() == DeferredItemInit.DOOM_CROSSBOW.get()) arrowEntity.addTag("DoomCrossbow");
        if(stack.getItem() == DeferredItemInit.FERAL_SOUL_CROSSBOW.get()) arrowEntity.addTag("FeralSoulCrossbow");
        if(stack.getItem() == DeferredItemInit.FIREBOLT_THROWER.get()) arrowEntity.addTag("FireboltThrower");
        if(stack.getItem() == DeferredItemInit.HARP_CROSSBOW.get()) arrowEntity.addTag("HarpCrossbow");
        if(stack.getItem() == DeferredItemInit.LIGHTNING_HARP_CROSSBOW.get()) arrowEntity.addTag("LightningHarpCrossbow");
        if(stack.getItem() == DeferredItemInit.SLAYER_CROSSBOW.get()) arrowEntity.addTag("SlayerCrossbow");
        if(stack.getItem() == DeferredItemInit.THE_SLICER.get()) arrowEntity.addTag("TheSlicer");
        if(stack.getItem() == DeferredItemInit.VOIDCALLER.get()) arrowEntity.addTag("Voidcaller");
        if(stack.getItem() == DeferredItemInit.DUAL_CROSSBOW.get()) arrowEntity.addTag("DualCrossbow");
        if(stack.getItem() == DeferredItemInit.BABY_CROSSBOW.get()) arrowEntity.addTag("BabyCrossbow");
        if(stack.getItem() == DeferredItemInit.EXPLODING_CROSSBOW.get()) arrowEntity.addTag("ExplodingCrossbow");
        if(stack.getItem() == DeferredItemInit.HEAVY_CROSSBOW.get()) arrowEntity.addTag("HeavyCrossbow");
        if(stack.getItem() == DeferredItemInit.RAPID_CROSSBOW.get()) arrowEntity.addTag("RapidCrossbow");
        if(stack.getItem() == DeferredItemInit.SCATTER_CROSSBOW.get()) arrowEntity.addTag("ScatterCrossbow");
        if(stack.getItem() == DeferredItemInit.SOUL_CROSSBOW.get()) arrowEntity.addTag("SoulCrossbow");
        if(stack.getItem() == DeferredItemInit.IMPLODING_CROSSBOW.get()) arrowEntity.addTag("ImplodingCrossbow");
    }

    public static int getModdedCrossbowChargeTime(ItemStack stack){
        int chargeTime;
        if(stack.getItem() instanceof AbstractDungeonsCrossbowItem){
            chargeTime = ((AbstractDungeonsCrossbowItem)stack.getItem()).getCrossbowChargeTime(stack);
        }
        else{
            chargeTime = 25;
        }
        return chargeTime;
    }

    public static float getModdedBowChargeTime(ItemStack stack){
        float chargeTime;
        if(stack.getItem() instanceof AbstractDungeonsBowItem){
            chargeTime = ((AbstractDungeonsBowItem)stack.getItem()).getBowChargeTime(stack);
        }
        else{
            chargeTime = 20.0F;
        }
        return chargeTime;
    }

    public static float getvVanillaOrModdedCrossbowArrowVelocity(ItemStack stack) {
        float arrowVelocity;
        if(stack.getItem() instanceof AbstractDungeonsCrossbowItem){
            arrowVelocity = ((AbstractDungeonsCrossbowItem)stack.getItem()).func_220013_l(stack);
        }
        else{
            arrowVelocity = hasChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
        }
        return arrowVelocity;
    }



    public static float getVanillaOrModdedBowArrowVelocity(ItemStack stack, int charge) {
        float arrowVelocity;
        if(stack.getItem() instanceof AbstractDungeonsBowItem){
            arrowVelocity = ((AbstractDungeonsBowItem)stack.getItem()).getBowArrowVelocity(stack, charge);
        }
        else{
            arrowVelocity = getVanillaArrowVelocity(stack,charge);
        }
        return arrowVelocity;
    }
}
