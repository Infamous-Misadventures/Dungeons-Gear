package com.infamous.dungeons_gear.capabilities.summoning;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SummonableProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(ISummonable.class)
    public static final Capability<ISummonable> SUMMONABLE_CAPABILITY = null;

    private LazyOptional<ISummonable> instance = LazyOptional.of(SUMMONABLE_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == SUMMONABLE_CAPABILITY ? instance.cast() : LazyOptional.empty();    }

    @Override
    public INBT serializeNBT() {
        return SUMMONABLE_CAPABILITY.getStorage().writeNBT(SUMMONABLE_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        SUMMONABLE_CAPABILITY.getStorage().readNBT(SUMMONABLE_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}