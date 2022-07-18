package com.infamous.dungeons_gear.enchantments.ranged;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import com.infamous.dungeons_libraries.event.BowEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.effects.CustomEffects.BOW_CHARGE;
import static com.infamous.dungeons_gear.enchantments.lists.RangedEnchantmentList.ROLL_CHARGE;

@Mod.EventBusSubscriber(modid= MODID)
public class RollChargeEnchantment extends DungeonsEnchantment {

    public RollChargeEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    public int getMaxLevel() {
        return 3;
    }

    public static void activateRollCharge(LivingEntity livingEntity) {
        int rollChargeLevel = EnchantmentHelper.getEnchantmentLevel(ROLL_CHARGE, livingEntity);

        if (rollChargeLevel > 0) {
            MobEffectInstance effectInstance = new MobEffectInstance(BOW_CHARGE, 30 * rollChargeLevel, 1);
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
        if (event.getEntity() instanceof AbstractArrow) {
            AbstractArrow arrowEntity = (AbstractArrow) event.getEntity();
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