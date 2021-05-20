package com.infamous.dungeons_gear.interfaces;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ISoulGatherer<T extends Item> {

    int getGatherAmount(ItemStack stack);

    int getActivationCost(ItemStack stack);
}
