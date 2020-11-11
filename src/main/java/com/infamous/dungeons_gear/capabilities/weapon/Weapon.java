package com.infamous.dungeons_gear.capabilities.weapon;

public class Weapon implements IWeapon {

    private int fuseShotCounter;
    private float bowChargeTime;
    private int crossbowChargeTime;
    private long lastFiredTime;

    public Weapon(){
        this.fuseShotCounter = 0;
        this.bowChargeTime = 0;
        this.crossbowChargeTime = 0;
        this.lastFiredTime = 0;
    }

    @Override
    public int getFuseShotCounter() {
        return this.fuseShotCounter;
    }

    @Override
    public void setFuseShotCounter(int fuseShotCounter) {
        this.fuseShotCounter = fuseShotCounter;
    }

    @Override
    public float getBowChargeTime() {
        return this.bowChargeTime;
    }

    @Override
    public void setBowChargeTime(float bowChargeTime) {
        this.bowChargeTime = bowChargeTime;
    }

    @Override
    public int getCrossbowChargeTime() {
        return this.crossbowChargeTime;
    }

    @Override
    public void setCrossbowChargeTime(int crossbowChargeTime) {
        this.crossbowChargeTime = crossbowChargeTime;
    }

    @Override
    public long getLastFiredTime() {
        return this.lastFiredTime;
    }

    @Override
    public void setLastFiredTime(long lastFiredTime) {
        this.lastFiredTime = lastFiredTime;
    }
}
