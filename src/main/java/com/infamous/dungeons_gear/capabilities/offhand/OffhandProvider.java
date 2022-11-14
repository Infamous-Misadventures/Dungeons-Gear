package com.infamous.dungeons_gear.capabilities.offhand;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OffhandProvider implements ICapabilitySerializable<INBT> {

    @CapabilityInject(IOffhand.class)
    public static final Capability<IOffhand> OFFHAND_CAPABILITY = null;

    private LazyOptional<IOffhand> instance = LazyOptional.of(OFFHAND_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == OFFHAND_CAPABILITY ? instance.cast() : LazyOptional.empty();    }

    @Override
    public INBT serializeNBT() {
        return OFFHAND_CAPABILITY.getStorage().writeNBT(OFFHAND_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        OFFHAND_CAPABILITY.getStorage().readNBT(OFFHAND_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}