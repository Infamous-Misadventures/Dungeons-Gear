package com.infamous.dungeons_gear.capabilities.offhand;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class OffhandStorage implements Capability.IStorage<IOffhand> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IOffhand> capability, IOffhand instance, Direction side) {
        CompoundNBT save=new CompoundNBT();
        save.put("wrapped", instance.getWrappedItemStack().write(new CompoundNBT()));
        save.putBoolean("fake", instance.isFake());
        return save;
    }

    @Override
    public void readNBT(Capability<IOffhand> capability, IOffhand instance, Direction side, INBT nbt) {
        CompoundNBT from=(CompoundNBT)nbt;
        instance.setWrappedItemStack(ItemStack.read(from.getCompound("wrapped")));
        instance.setFake(from.getBoolean("fake"));
    }
}
