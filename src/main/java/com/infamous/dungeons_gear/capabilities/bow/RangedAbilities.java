package com.infamous.dungeons_gear.capabilities.bow;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class RangedAbilities implements INBTSerializable<CompoundTag> {

    private int fuseShotCounter;
    private float dynamicChargeTime;
    private long lastFiredTime;

    public RangedAbilities() {
        this.fuseShotCounter = 0;
        this.dynamicChargeTime = 0;
        this.lastFiredTime = 0;
    }

    public int getFuseShotCounter() {
        return this.fuseShotCounter;
    }

    public void setFuseShotCounter(int fuseShotCounter) {
        this.fuseShotCounter = fuseShotCounter;
    }

    public float getDynamicChargeTime() {
        return this.dynamicChargeTime;
    }

    public void setDynamicChargeTime(float dynamicChargeTime) {
        this.dynamicChargeTime = dynamicChargeTime;
    }

    public long getLastFiredTime() {
        return this.lastFiredTime;
    }

    public void setLastFiredTime(long lastFiredTime) {
        this.lastFiredTime = lastFiredTime;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("fuseShotCounter", this.getFuseShotCounter());
        tag.putFloat("dynamicChargeTime", this.getDynamicChargeTime());
        tag.putLong("lastFiredTime", this.getLastFiredTime());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.setFuseShotCounter(tag.getInt("fuseShotCounter"));
        this.setDynamicChargeTime(tag.getFloat("dynamicChargeTime"));
        this.setLastFiredTime(tag.getLong("lastFiredTime"));
    }
}
