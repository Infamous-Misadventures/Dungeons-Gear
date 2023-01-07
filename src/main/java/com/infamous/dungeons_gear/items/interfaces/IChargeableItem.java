package com.infamous.dungeons_gear.items.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IChargeableItem {

    static boolean isCharged(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null && compoundnbt.getBoolean("Charged");
    }

    static void setCharged(ItemStack stack, boolean chargedIn) {
        CompoundTag compoundnbt = stack.getOrCreateTag();
        compoundnbt.putBoolean("Charged", chargedIn);
    }

    default int getChargeTime() {
        return this.getChargeTimeInSeconds() * 20;
    }

    int getChargeTimeInSeconds();
}
