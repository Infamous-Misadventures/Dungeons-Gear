package com.infamous.dungeons_gear.interfaces;

import net.minecraft.item.Item;

public interface IOffhandAttack<T extends Item> {
    float getOffhandAttackReach();
}
