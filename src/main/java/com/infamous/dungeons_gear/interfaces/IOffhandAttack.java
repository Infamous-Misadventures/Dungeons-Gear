package com.infamous.dungeons_gear.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IOffhandAttack<T extends Item> {
    default boolean canAttack(LivingEntity e, ItemStack is) {
        return e.getHeldItemMainhand().getItem() instanceof IOffhandAttack && e.getHeldItemOffhand().getItem() instanceof IOffhandAttack;
    }
}
