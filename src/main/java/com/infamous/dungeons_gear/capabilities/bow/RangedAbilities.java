package com.infamous.dungeons_gear.capabilities.bow;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class RangedAbilities implements INBTSerializable<CompoundTag> {

    private int fuseShotCounter;
    private float bowChargeTime;
    private int crossbowChargeTime;
    private long lastFiredTime;

    public RangedAbilities() {
        this.fuseShotCounter = 0;
        this.bowChargeTime = 0;
        this.crossbowChargeTime = 0;
        this.lastFiredTime = 0;
    }

    public int getFuseShotCounter() {
        return this.fuseShotCounter;
    }

    public void setFuseShotCounter(int fuseShotCounter) {
        this.fuseShotCounter = fuseShotCounter;
    }

    public float getBowChargeTime() {
        return bowChargeTime;
    }

    public void setBowChargeTime(float bowChargeTime) {
        this.bowChargeTime = bowChargeTime;
    }

    public int getCrossbowChargeTime() {
        return this.crossbowChargeTime;
    }

    public void setCrossbowChargeTime(int crossbowChargeTime) {
        this.crossbowChargeTime = crossbowChargeTime;
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
        tag.putInt("crossbowChargeTime", this.getCrossbowChargeTime());
        tag.putLong("lastFiredTime", this.getLastFiredTime());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.setFuseShotCounter(tag.getInt("fuseShotCounter"));
        this.setCrossbowChargeTime(tag.getInt("crossbowChargeTime"));
        this.setLastFiredTime(tag.getLong("lastFiredTime"));
    }
}
