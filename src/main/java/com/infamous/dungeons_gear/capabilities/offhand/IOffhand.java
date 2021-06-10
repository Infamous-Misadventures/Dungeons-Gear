package com.infamous.dungeons_gear.capabilities.offhand;


import net.minecraft.item.ItemStack;

public interface IOffhand {

    void setLinkedItemStack(ItemStack stack);
    ItemStack getLinkedItemStack();

    void setWrappedItemStack(ItemStack stack);
    ItemStack getWrappedItemStack();

    void setFake(boolean isFake);
    boolean isFake();
}
