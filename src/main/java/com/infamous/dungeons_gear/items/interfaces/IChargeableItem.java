package com.infamous.dungeons_gear.items.interfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IChargeableItem {

    static boolean isCharged(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("Charged");
    }

    static void setCharged(ItemStack stack, boolean chargedIn) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();
        compoundnbt.putBoolean("Charged", chargedIn);
    }

    default int getChargeTime(){
        return this.getChargeTimeInSeconds() * 20;
    }

    int getChargeTimeInSeconds();
}
