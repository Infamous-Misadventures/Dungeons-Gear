package com.infamous.dungeons_gear.capabilities.artifact;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

public class ArtifactUsage implements INBTSerializable<CompoundTag> {

    private ItemStack usingArtifact = null;
    private int usingArtifactRemaining = 0;

    public boolean isUsingArtifact() {
        return usingArtifact != null;
    }

    public boolean isSameUsingArtifact(ItemStack itemStack) {
        return usingArtifact != null && itemStack != null && itemStack.equals(usingArtifact);
    }

    public boolean startUsingArtifact(ItemStack itemStack) {
        if(usingArtifact != null || !(itemStack.getItem() instanceof ArtifactItem)) return false;
        usingArtifact = itemStack;
        return true;
    }

    public boolean stopUsingArtifact() {
        usingArtifact = null;
        return true;
    }

    public ItemStack getUsingArtifact() {
        return usingArtifact;
    }

    public int getUsingArtifactRemaining() {
        return usingArtifactRemaining;
    }

    public void setUsingArtifactRemaining(int usingArtifactRemaining) {
        this.usingArtifactRemaining = usingArtifactRemaining;
    }
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
    }
}
