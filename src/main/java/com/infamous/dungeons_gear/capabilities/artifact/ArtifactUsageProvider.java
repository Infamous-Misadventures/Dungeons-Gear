package com.infamous.dungeons_gear.capabilities.artifact;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArtifactUsageProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IArtifactUsage.class)
    public static final Capability<IArtifactUsage> ARTIFACT_USAGE_CAPABILITY = null;

    private LazyOptional<IArtifactUsage> instance = LazyOptional.of(ARTIFACT_USAGE_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ARTIFACT_USAGE_CAPABILITY ? instance.cast() : LazyOptional.empty();    }

    @Override
    public INBT serializeNBT() {
        return ARTIFACT_USAGE_CAPABILITY.getStorage().writeNBT(ARTIFACT_USAGE_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        ARTIFACT_USAGE_CAPABILITY.getStorage().readNBT(ARTIFACT_USAGE_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}