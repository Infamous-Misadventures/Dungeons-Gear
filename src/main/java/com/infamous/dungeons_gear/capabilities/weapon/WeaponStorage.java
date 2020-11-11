package com.infamous.dungeons_gear.capabilities.weapon;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class WeaponStorage implements Capability.IStorage<IWeapon> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IWeapon> capability, IWeapon instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("fuseShotCounter", instance.getFuseShotCounter());
        tag.putFloat("bowChargeTime", instance.getBowChargeTime());
        tag.putInt("crossbowChargeTime", instance.getCrossbowChargeTime());
        tag.putLong("lastFiredTime", instance.getLastFiredTime());
        return tag;
    }

    @Override
    public void readNBT(Capability<IWeapon> capability, IWeapon instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setFuseShotCounter(tag.getInt("fuseShotCounter"));
        instance.setBowChargeTime(tag.getFloat("bowChargeTime"));
        instance.setCrossbowChargeTime(tag.getInt("crossbowChargeTime"));
        instance.setLastFiredTime(tag.getLong("lastFiredTime"));
    }
}
