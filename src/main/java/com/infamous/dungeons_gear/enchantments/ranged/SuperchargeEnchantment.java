package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_libraries.utils.ArrowHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class SuperchargeEnchantment extends DungeonsEnchantment {


    public SuperchargeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (enchantment != Enchantments.PUNCH_ARROWS || enchantment != Enchantments.POWER_ARROWS);
    }

    @SubscribeEvent
    public static void onSuperchargeImpact(ProjectileImpactEvent.Arrow event){
        if(!ModEnchantmentHelper.arrowHitLivingEntity(event.getRayTraceResult())) return;
        AbstractArrowEntity arrow = event.getArrow();
        if(!ModEnchantmentHelper.shooterIsLiving(arrow)) return;
        int superchargeLevel = ArrowHelper.enchantmentTagToLevel(arrow, RangedEnchantmentList.SUPERCHARGE);
        if(superchargeLevel > 0){
            double originalDamage = arrow.getBaseDamage();
            int originalKnockback = arrow.knockback;
            double damageModifier = 0;
            if(superchargeLevel == 1) damageModifier = 1.2D;
            if(superchargeLevel == 2) damageModifier = 1.4D;
            if(superchargeLevel == 3) damageModifier = 1.6D;
            arrow.setBaseDamage(originalDamage * damageModifier);
            arrow.setKnockback(originalKnockback + 1);
        }
    }
}
