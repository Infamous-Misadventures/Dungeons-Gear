package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class RushEnchantment extends DungeonsEnchantment {

    public RushEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        int rushLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.RUSH, livingEntity);
        if(rushLevel > 0 && !livingEntity.level.isClientSide){
            MobEffectInstance speedBoost = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, rushLevel - 1);
            livingEntity.addEffect(speedBoost);
        }
    }
}
