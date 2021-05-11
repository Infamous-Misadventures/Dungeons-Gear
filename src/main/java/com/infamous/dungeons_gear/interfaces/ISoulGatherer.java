package com.infamous.dungeons_gear.interfaces;

import net.minecraft.item.ItemStack;

public interface ISoulGatherer {

    int getGatherAmount(ItemStack stack);

    int getActivationCost(ItemStack stack);
}
