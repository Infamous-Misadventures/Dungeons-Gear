package com.infamous.dungeons_gear.capabilities.artifact;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ArtifactUsageStorage implements Capability.IStorage<IArtifactUsage> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IArtifactUsage> capability, IArtifactUsage instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        return instance.save(tag, side);
    }

    @Override
    public void readNBT(Capability<IArtifactUsage> capability, IArtifactUsage instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.load(tag, side);
    }
}
