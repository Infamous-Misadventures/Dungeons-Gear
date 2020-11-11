package com.infamous.dungeons_gear.capabilities.summoning;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SummonerProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(ISummoner.class)
    public static final Capability<ISummoner> SUMMONER_CAPABILITY = null;

    private LazyOptional<ISummoner> instance = LazyOptional.of(SUMMONER_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == SUMMONER_CAPABILITY ? instance.cast() : LazyOptional.empty();    }

    @Override
    public INBT serializeNBT() {
        return SUMMONER_CAPABILITY.getStorage().writeNBT(SUMMONER_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        SUMMONER_CAPABILITY.getStorage().readNBT(SUMMONER_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}