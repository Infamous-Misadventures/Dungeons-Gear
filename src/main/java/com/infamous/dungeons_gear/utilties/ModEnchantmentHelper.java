package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEnchantmentHelper {

    public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment) {
        return enchantment != null && EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack) > 0;
    }

    public static boolean hasEnchantment(LivingEntity entity, Enchantment enchantment) {
        return enchantment != null && EnchantmentHelper.getEnchantmentLevel(enchantment, entity) > 0;
    }

    public static boolean shooterIsLiving(AbstractArrow arrowEntity) {
        return arrowEntity.getOwner() != null && arrowEntity.getOwner() instanceof LivingEntity;
    }

    public static boolean arrowHitLivingEntity(HitResult rayTraceResult) {
        if (rayTraceResult instanceof EntityHitResult) {
            EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
            return entityRayTraceResult.getEntity() instanceof LivingEntity;
        } else {
            return false;
        }
    }

    public static boolean arrowHitMob(HitResult rayTraceResult) {
        if (rayTraceResult instanceof EntityHitResult) {
            EntityHitResult entityRayTraceResult = (EntityHitResult) rayTraceResult;
            return entityRayTraceResult.getEntity() instanceof Mob;
        } else {
            return false;
        }
    }

    public static boolean isNotBlacklistedEnchant(Enchantment enchantment) {
        return !isBlacklistedEnchant(enchantment);
    }

    public static boolean isBlacklistedEnchant(Enchantment enchantment) {
        return DungeonsGearConfig.ENCHANTMENT_BLACKLIST.get().contains(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
    }

    public static boolean isNotTreasureEnchant(Enchantment enchantment) {
        return !isTreasureEnchant(enchantment);
    }

    public static boolean isTreasureEnchant(Enchantment enchantment) {
        return DungeonsGearConfig.TREASURE_ONLY_ENCHANTMENTS.get().contains(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
    }

    public static boolean canEnchantmentTrigger(LivingEntity entity) {
        if (entity == null) return false;
        if(!entity.isEffectiveAi()) return false;
        if(!entity.isAlive()) return false;
        if(entity instanceof Player player) {
            return !player.isCreative() && !player.isSpectator();
        } else {
            return entity.isAlive();
        }
    }
}
