package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.weapon.IWeapon;
import com.infamous.dungeons_gear.capabilities.weapon.WeaponProvider;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.ranged.bows.AbstractDungeonsBowItem;
import com.infamous.dungeons_gear.ranged.crossbows.AbstractDungeonsCrossbowItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.util.LazyOptional;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;
import static net.minecraft.item.CrossbowItem.hasChargedProjectile;

public class RangedAttackHelper {

    public static int getVanillaCrossbowChargeTime(ItemStack stack){
        int quickChargeLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
        int accelerateLevel = EnchantmentHelper.getEnchantmentLevel(RangedEnchantmentList.ACCELERATE, stack);

        IWeapon weaponCap = getWeaponCapability(stack);
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

    public static IWeapon getWeaponCapability(ItemStack stack)
    {
        LazyOptional<IWeapon> lazyCap = stack.getCapability(WeaponProvider.WEAPON_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the capability from the ItemStack!"));
        }
        return null;
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

        IWeapon weaponCap = stack.getCapability(WeaponProvider.WEAPON_CAPABILITY).orElseThrow(IllegalStateException::new);
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
        if(stack.getItem() == BONEBOW) arrowEntity.addTag("Bonebow");
        if(stack.getItem() == BOW_OF_LOST_SOULS) arrowEntity.addTag("BowOfLostSouls");
        if(stack.getItem() == ELITE_POWER_BOW) arrowEntity.addTag("ElitePowerBow");
        if(stack.getItem() == GUARDIAN_BOW) arrowEntity.addTag("GuardianBow");
        if(stack.getItem() == HUNTERS_PROMISE) arrowEntity.addTag("HuntersPromise");
        if(stack.getItem() == MASTERS_BOW) arrowEntity.addTag("MastersBow");
        //if(stack.getItem() == MECHANICAL_SHORTBOW) arrowEntity.addTag("MechanicalShortbow");
        if(stack.getItem() == NOCTURNAL_BOW) arrowEntity.addTag("NocturnalBow");
        //if(stack.getItem() == PURPLE_STORM) arrowEntity.addTag("PurpleStorm");
        if(stack.getItem() == RED_SNAKE) arrowEntity.addTag("RedSnake");
        if(stack.getItem() == SABREWING) arrowEntity.addTag("Sabrewing");
        if(stack.getItem() == THE_GREEN_MENACE) arrowEntity.addTag("TheGreenMenace");
        if(stack.getItem() == THE_PINK_SCOUNDREL) arrowEntity.addTag("ThePinkScoundrel");
        if(stack.getItem() == TWIN_BOW) arrowEntity.addTag("TwinBow");
        if(stack.getItem() == HUNTING_BOW) arrowEntity.addTag("HuntingBow");
        if(stack.getItem() == LONGBOW) arrowEntity.addTag("Longbow");
        if(stack.getItem() == SHORTBOW) arrowEntity.addTag("Shortbow");
        if(stack.getItem() == POWER_BOW) arrowEntity.addTag("PowerBow");
        if(stack.getItem() == SOUL_BOW) arrowEntity.addTag("SoulBow");
        if(stack.getItem() == TRICKBOW) arrowEntity.addTag("Trickbow");
        if(stack.getItem() == SNOW_BOW) arrowEntity.addTag("SnowBow");
        if(stack.getItem() == WINTERS_TOUCH) arrowEntity.addTag("WintersTouch");

        // CROSSBOWS
        //if(stack.getItem() == AUTO_CROSSBOW) arrowEntity.addTag("AutoCrossbow");
        if(stack.getItem() == AZURE_SEEKER) arrowEntity.addTag("AzureSeeker");
        if(stack.getItem() == BUTTERFLY_CROSSBOW) arrowEntity.addTag("ButterflyCrossbow");
        if(stack.getItem() == DOOM_CROSSBOW) arrowEntity.addTag("DoomCrossbow");
        if(stack.getItem() == FERAL_SOUL_CROSSBOW) arrowEntity.addTag("FeralSoulCrossbow");
        if(stack.getItem() == FIREBOLT_THROWER) arrowEntity.addTag("FireboltThrower");
        if(stack.getItem() == HARP_CROSSBOW) arrowEntity.addTag("HarpCrossbow");
        if(stack.getItem() == LIGHTNING_HARP_CROSSBOW) arrowEntity.addTag("LightningHarpCrossbow");
        if(stack.getItem() == SLAYER_CROSSBOW) arrowEntity.addTag("SlayerCrossbow");
        if(stack.getItem() == THE_SLICER) arrowEntity.addTag("TheSlicer");
        if(stack.getItem() == VOIDCALLER) arrowEntity.addTag("Voidcaller");
        if(stack.getItem() == DUAL_CROSSBOW) arrowEntity.addTag("DualCrossbow");
        if(stack.getItem() == BABY_CROSSBOW) arrowEntity.addTag("BabyCrossbow");
        if(stack.getItem() == EXPLODING_CROSSBOW) arrowEntity.addTag("ExplodingCrossbow");
        if(stack.getItem() == HEAVY_CROSSBOW) arrowEntity.addTag("HeavyCrossbow");
        if(stack.getItem() == RAPID_CROSSBOW) arrowEntity.addTag("RapidCrossbow");
        if(stack.getItem() == SCATTER_CROSSBOW) arrowEntity.addTag("ScatterCrossbow");
        if(stack.getItem() == SOUL_CROSSBOW) arrowEntity.addTag("SoulCrossbow");
        if(stack.getItem() == IMPLODING_CROSSBOW) arrowEntity.addTag("ImplodingCrossbow");
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
            arrowVelocity = ((AbstractDungeonsCrossbowItem)stack.getItem()).getProjectileVelocity(stack);
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
