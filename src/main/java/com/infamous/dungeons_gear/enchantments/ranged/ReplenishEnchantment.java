package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ReplenishEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_REPLENISH_TAG = "IntrinsicReplenish";

    public ReplenishEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment == Enchantments.INFINITY_ARROWS);
    }


    @SubscribeEvent
    public static void onReplenishImpact(ProjectileImpactEvent event) {
        HitResult rayTraceResult = event.getRayTraceResult();
        if (!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult)) return;
        if (event.getProjectile() instanceof AbstractArrow arrow) {
            if (!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
            LivingEntity shooter = (LivingEntity) arrow.getOwner();
            if (shooter instanceof Player) {
                Player player = (Player) shooter;
                int replenishLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.REPLENISH);
                LivingEntity victim = (LivingEntity) ((EntityHitResult) rayTraceResult).getEntity();
                if (replenishLevel > 0) {
                    float replenishRand = shooter.getRandom().nextFloat();
                    float replenishChance = 0;
                    if (replenishLevel == 1) replenishChance = 0.1F;
                    if (replenishLevel == 2) replenishChance = 0.17F;
                    if (replenishLevel == 3) replenishChance = 0.24F;
                    if (replenishRand <= replenishChance) {
                        ItemEntity arrowDrop = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), new ItemStack(Items.ARROW));
                        shooter.level.addFreshEntity(arrowDrop);
                    }
                }
                if (arrow.getTags().contains(INTRINSIC_REPLENISH_TAG)) {
                    float replenishRand = shooter.getRandom().nextFloat();
                    if (replenishRand <= 0.1F) {
                        ItemEntity arrowDrop = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), new ItemStack(Items.ARROW));
                        shooter.level.addFreshEntity(arrowDrop);
                    }
                }
            }
        }
    }
}
