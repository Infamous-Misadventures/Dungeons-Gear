package com.infamous.dungeons_gear.capabilities.artifact;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;

import java.util.UUID;

public class ArtifactUsage implements IArtifactUsage {

    private ItemStack usingArtifact = null;
    private int usingArtifactRemaining = 0;

    @Override
    public boolean isUsingArtifact() {
        return usingArtifact != null;
    }

    @Override
    public boolean isSameUsingArtifact(ItemStack itemStack) {
        return usingArtifact != null && itemStack != null && itemStack.equals(usingArtifact);
    }

    @Override
    public boolean startUsingArtifact(ItemStack itemStack) {
        if(usingArtifact != null || !(itemStack.getItem() instanceof ArtifactItem)) return false;
        usingArtifact = itemStack;
        return true;
    }

    @Override
    public boolean stopUsingArtifact() {
        usingArtifact = null;
        return true;
    }

    @Override
    public ItemStack getUsingArtifact() {
        return usingArtifact;
    }

    @Override
    public INBT save(CompoundNBT tag, Direction side) {
        return tag;
    }

    @Override
    public void load(INBT nbt, Direction side) {
    }

    @Override
    public int getUsingArtifactRemaining() {
        return usingArtifactRemaining;
    }

    @Override
    public void setUsingArtifactRemaining(int usingArtifactRemaining) {
        this.usingArtifactRemaining = usingArtifactRemaining;
    }
}
