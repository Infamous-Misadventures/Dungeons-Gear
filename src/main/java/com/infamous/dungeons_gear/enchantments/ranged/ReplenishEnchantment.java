package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ReplenishEnchantment extends Enchantment {

    public ReplenishEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.COMMON.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment == Enchantments.INFINITY);
    }


    @SubscribeEvent
    public static void onReplenishImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        if(!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity)arrow.func_234616_v_();
        if(shooter instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) shooter;
            int replenishLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.REPLENISH);
            LivingEntity victim = (LivingEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
            if(replenishLevel > 0){
                float replenishRand = shooter.getRNG().nextFloat();
                float replenishChance = 0;
                if(replenishLevel == 1) replenishChance = 0.1F;
                if(replenishLevel == 2) replenishChance = 0.17F;
                if(replenishLevel == 3) replenishChance = 0.24F;
                if(replenishRand <=  replenishChance){
                    ItemEntity arrowDrop = new ItemEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(Items.ARROW));
                    shooter.world.addEntity(arrowDrop);
                }
            }
            if(arrow.getTags().contains("HuntersPromise")){
                float replenishRand = shooter.getRNG().nextFloat();
                if(replenishRand <=  0.1F){
                    ItemEntity arrowDrop = new ItemEntity(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(Items.ARROW));
                    shooter.world.addEntity(arrowDrop);
                }
            }
        }
    }
}
