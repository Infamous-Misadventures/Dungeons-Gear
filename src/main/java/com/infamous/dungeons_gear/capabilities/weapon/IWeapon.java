package com.infamous.dungeons_gear.capabilities.weapon;


public interface IWeapon {

    void setFuseShotCounter(int comboTimer);
    int getFuseShotCounter();

    float getBowChargeTime();
    void setBowChargeTime(float bowChargeTime);

    int getCrossbowChargeTime();
    void setCrossbowChargeTime(int crossbowChargeTime);

    long getLastFiredTime();
    void setLastFiredTime(long lastFiredTime);

}
