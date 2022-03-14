package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class GrowingEnchantment extends DungeonsEnchantment {

    public static final String INTRINSIC_GROWING_TAG = "IntrinsicGrowing";

    public GrowingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.POWER_ARROWS;
    }

    @SubscribeEvent
    public static void onGrowingImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        if(!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity)arrow.getOwner();
        LivingEntity victim = (LivingEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
        int growingLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.GROWING);
        boolean uniqueWeaponFlag = arrow.getTags().contains(INTRINSIC_GROWING_TAG);
        if(growingLevel > 0 || uniqueWeaponFlag){
            double originalDamage = arrow.getBaseDamage();
            double damageModifierCap = 0;
            if(growingLevel == 1) damageModifierCap = 1.25D;
            if(growingLevel == 2) damageModifierCap = 1.5D;
            if(growingLevel == 3) damageModifierCap = 1.75D;
            if(uniqueWeaponFlag) damageModifierCap += 1.25D;
            double squareDistanceTo = shooter.distanceToSqr(victim);
            double distance = Math.sqrt(squareDistanceTo);
            double distanceTraveledModifier = distance * 0.1;
            arrow.setBaseDamage(originalDamage * Math.min(distanceTraveledModifier, damageModifierCap));
        }
    }
}
