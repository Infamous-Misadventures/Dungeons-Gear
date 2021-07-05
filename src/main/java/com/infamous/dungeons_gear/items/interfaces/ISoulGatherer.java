package com.infamous.dungeons_gear.items.interfaces;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ISoulGatherer<T extends Item> {

    int getGatherAmount(ItemStack stack);

    float getActivationCost(ItemStack stack);
}
