package com.infamous.dungeons_gear.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface IComboWeapon {
    int getComboLength(ItemStack stack, LivingEntity attacker);

    default boolean shouldProcSpecialEffects(ItemStack stack, LivingEntity attacker, int combo) {
        return combo % getComboLength(stack, attacker) == 0;
    }

//    default float damageMultiplier(ItemStack stack, LivingEntity attacker, int combo) {
//        return 1.5f;
//    }
}
