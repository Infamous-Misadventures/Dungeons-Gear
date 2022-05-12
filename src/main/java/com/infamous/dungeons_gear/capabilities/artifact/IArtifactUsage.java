package com.infamous.dungeons_gear.capabilities.artifact;


import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;

public interface IArtifactUsage {

    boolean isUsingArtifact();

    boolean isSameUsingArtifact(ItemStack itemStack);

    boolean startUsingArtifact(ItemStack itemStack);

    boolean stopUsingArtifact();

    ItemStack getUsingArtifact();

    int getUsingArtifactRemaining();

    void setUsingArtifactRemaining(int usingArtifactRemaining);

    INBT save(CompoundNBT tag, Direction side);

    void load(INBT nbt, Direction side);
}
