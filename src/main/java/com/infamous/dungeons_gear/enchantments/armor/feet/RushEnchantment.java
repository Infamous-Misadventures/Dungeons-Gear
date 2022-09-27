package com.infamous.dungeons_gear.enchantments.armor.feet;


import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid= MODID)
public class RushEnchantment extends DungeonsEnchantment {

    public RushEnchantment() {
        super(Rarity.RARE, EnchantmentType.ARMOR_FEET, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        int rushLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.RUSH, livingEntity);
        if(rushLevel > 0 && !livingEntity.level.isClientSide){
            EffectInstance speedBoost = new EffectInstance(Effects.MOVEMENT_SPEED, 20, rushLevel - 1);
            livingEntity.addEffect(speedBoost);
        }
    }
}
