package com.infamous.dungeons_gear.capabilities.offhand;

import net.minecraft.item.ItemStack;

public class Offhand implements IOffhand {

    private ItemStack linked=ItemStack.EMPTY, wrapping=ItemStack.EMPTY;
    private boolean fake;

    @Override
    public void setLinkedItemStack(ItemStack stack) {
        linked =stack;
    }

    @Override
    public ItemStack getLinkedItemStack() {
        return linked;
    }

    @Override
    public void setWrappedItemStack(ItemStack stack) {
        wrapping=stack;
    }

    @Override
    public ItemStack getWrappedItemStack() {
        return wrapping;
    }

    @Override
    public void setFake(boolean isFake) {
        fake=isFake;
    }

    @Override
    public boolean isFake() {
        return fake;
    }
}
