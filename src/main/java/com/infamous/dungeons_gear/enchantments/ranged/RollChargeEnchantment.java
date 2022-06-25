package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.event.BowEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.effects.CustomEffects.BOW_CHARGE;
import static com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList.ROLL_CHARGE;

@Mod.EventBusSubscriber(modid= MODID)
public class RollChargeEnchantment extends DungeonsEnchantment {

    public RollChargeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    public static void activateRollCharge(LivingEntity livingEntity) {
        int rollChargeLevel = EnchantmentHelper.getEnchantmentLevel(ROLL_CHARGE, livingEntity);

        if (rollChargeLevel > 0) {
            EffectInstance effectInstance = new EffectInstance(BOW_CHARGE, 30 * rollChargeLevel, 1);
            livingEntity.addEffect(effectInstance);
        }
    }

    @SubscribeEvent
    public static void onBowChargeTime(BowEvent.ChargeTime event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity == null) return;
        if (livingEntity.hasEffect(BOW_CHARGE) && livingEntity.getEffect(BOW_CHARGE).amplifier > 0) {
            event.setChargeTime(1);
        }
    }

    @SubscribeEvent
    public static void onArrowJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AbstractArrowEntity) {
            AbstractArrowEntity arrowEntity = (AbstractArrowEntity) event.getEntity();
            Entity owner = arrowEntity.getOwner();
            if (owner instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) owner;
                if (livingEntity.hasEffect(BOW_CHARGE)) {
                    livingEntity.removeEffect(BOW_CHARGE);
                }
            }
        }
    }
}