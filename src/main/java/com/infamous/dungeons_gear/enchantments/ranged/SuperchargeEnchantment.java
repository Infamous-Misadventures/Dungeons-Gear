package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class SuperchargeEnchantment extends Enchantment {

    public SuperchargeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return enchantment != Enchantments.PUNCH || enchantment != Enchantments.POWER;
    }

    @SubscribeEvent
    public static void onSuperchargeImpact(ProjectileImpactEvent.Arrow event){
        if(!ModEnchantmentHelper.arrowHitLivingEntity(event.getRayTraceResult())) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        int superchargeLevel = ModEnchantmentHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.SUPERCHARGE);
        boolean uniqueWeaponFlag = arrow.getTags().contains("GuardianBow");
        if(superchargeLevel > 0 || uniqueWeaponFlag){
            double originalDamage = arrow.getDamage();
            int originalKnockback = ObfuscationReflectionHelper.getPrivateValue(AbstractArrowEntity.class, arrow, "field_70256_ap");
            double damageModifier = 0;
            if(superchargeLevel == 1) damageModifier = 1.2D;
            if(superchargeLevel == 2) damageModifier = 1.4D;
            if(superchargeLevel == 3) damageModifier = 1.6D;
            if(uniqueWeaponFlag) damageModifier += 1.2D;
            arrow.setDamage(originalDamage * damageModifier);
            arrow.setKnockbackStrength(originalKnockback + 1);
        }
    }
}
