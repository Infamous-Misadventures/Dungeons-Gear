package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

public class ModEnchantmentHelper {

    public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment){
        return enchantment != null && EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) > 0;
    }

    public static boolean hasEnchantment(LivingEntity entity, Enchantment enchantment) {
        return enchantment != null && EnchantmentHelper.getEnchantmentLevel(enchantment, entity) > 0;
    }

    public static boolean shooterIsLiving(AbstractArrowEntity arrowEntity) {
        return arrowEntity.getOwner() != null && arrowEntity.getOwner() instanceof LivingEntity;
    }

    public static boolean arrowHitLivingEntity(RayTraceResult rayTraceResult) {
        if(rayTraceResult instanceof EntityRayTraceResult){
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult)rayTraceResult;
            if(entityRayTraceResult.getEntity() instanceof LivingEntity){
                return true;
            } else{
                return false;
            }
        } else{
            return false;
        }
    }

    public static boolean arrowHitMob(RayTraceResult rayTraceResult) {
        if(rayTraceResult instanceof EntityRayTraceResult){
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult)rayTraceResult;
            if(entityRayTraceResult.getEntity() instanceof MobEntity){
                return true;
            } else{
                return false;
            }
        } else{
            return false;
        }
    }

    public static boolean isNotBlacklistedEnchant(Enchantment enchantment) {
        return !isBlacklistedEnchant(enchantment);
    }

    public static boolean isBlacklistedEnchant(Enchantment enchantment) {
        return DungeonsGearConfig.ENCHANTMENT_BLACKLIST.get().contains(enchantment.getRegistryName().toString());
    }

    public static boolean isNotTreasureEnchant(Enchantment enchantment) {
        return !isTreasureEnchant(enchantment);
    }

    public static boolean isTreasureEnchant(Enchantment enchantment) {
        return DungeonsGearConfig.TREASURE_ONLY_ENCHANTMENTS.get().contains(enchantment.getRegistryName().toString());
    }
}
