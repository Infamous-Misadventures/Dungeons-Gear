package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.world.item.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class GrowingEnchantment extends DungeonsEnchantment {

    public GrowingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.POWER_ARROWS;
    }

    @SubscribeEvent
    public static void onGrowingImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult)) return;
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
            int growingLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.GROWING);
            if (growingLevel > 0) {
                double originalDamage = arrow.getBaseDamage();
                double damageModifierCap = 1 + (growingLevel * 0.25D);
                double squareDistanceTo = shooter.distanceToSqr(victim);
                double distance = Math.sqrt(squareDistanceTo);
                double distanceTraveledModifier = Math.max(distance * 0.1D, 1.0D);
                arrow.setBaseDamage(originalDamage * Math.min(distanceTraveledModifier, damageModifierCap));
            }
        }
    }
}
