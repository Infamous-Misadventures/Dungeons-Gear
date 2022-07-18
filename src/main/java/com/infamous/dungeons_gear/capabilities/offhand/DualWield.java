package com.infamous.dungeons_gear.capabilities.offhand;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public class DualWield implements INBTSerializable<CompoundTag> {

    private ItemStack linked=ItemStack.EMPTY, wrapping=ItemStack.EMPTY;
    private boolean fake;

    public void setLinkedItemStack(ItemStack stack) {
        linked =stack;
    }

    public ItemStack getLinkedItemStack() {
        return linked;
    }

    public void setWrappedItemStack(ItemStack stack) {
        wrapping=stack;
    }

    public ItemStack getWrappedItemStack() {
        return wrapping;
    }

    public void setFake(boolean isFake) {
        fake=isFake;
    }

    public boolean isFake() {
        return fake;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag save=new CompoundTag();
        save.put("wrapped", this.getWrappedItemStack().save(new CompoundTag()));
        save.putBoolean("fake", this.isFake());
        return save;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.setWrappedItemStack(ItemStack.of(nbt.getCompound("wrapped")));
        this.setFake(nbt.getBoolean("fake"));
    }
}
