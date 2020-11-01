package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
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

@Mod.EventBusSubscriber(modid= MODID)
public class GrowingEnchantment extends Enchantment {

    public GrowingEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || enchantment != Enchantments.POWER;
    }

    @SubscribeEvent
    public static void onGrowingImpact(ProjectileImpactEvent.Arrow event){
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        if(!ModEnchantmentHelper.arrowHitLivingEntity(rayTraceResult)) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        LivingEntity shooter = (LivingEntity)arrow.func_234616_v_();
        LivingEntity victim = (LivingEntity) ((EntityRayTraceResult)rayTraceResult).getEntity();
        int growingLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.GROWING);
        boolean uniqueWeaponFlag = arrow.getTags().contains("Bonebow") || arrow.getTags().contains("BabyCrossbow");
        if(growingLevel > 0 || uniqueWeaponFlag){
            double originalDamage = arrow.getDamage();
            double damageModifierCap = 0;
            if(growingLevel == 1) damageModifierCap = 1.25D;
            if(growingLevel == 2) damageModifierCap = 1.5D;
            if(growingLevel == 3) damageModifierCap = 1.75D;
            if(uniqueWeaponFlag) damageModifierCap += 1.25D;
            double squareDistanceTo = shooter.getDistanceSq(victim);
            double distance = Math.sqrt(squareDistanceTo);
            double distanceTraveledModifier = distance * 0.1;
            arrow.setDamage(originalDamage * Math.min(distanceTraveledModifier, damageModifierCap));
        }
    }
}
