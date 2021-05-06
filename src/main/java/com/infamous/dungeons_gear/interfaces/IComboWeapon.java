package com.infamous.dungeons_gear.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface IComboWeapon {
    int getComboLength(ItemStack stack, LivingEntity attacker);

    default boolean shouldProcSpecialEffects(ItemStack stack, LivingEntity attacker, int combo) {
        return (combo-1) % getComboLength(stack, attacker) == 0;//due to vanilla weirdness, resetCooldown() is called twice
    }

    default float damageMultiplier(ItemStack stack, LivingEntity attacker, int combo) {
        return 1f;
    }
}
