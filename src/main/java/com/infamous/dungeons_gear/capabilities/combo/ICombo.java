package com.infamous.dungeons_gear.capabilities.combo;


public interface ICombo {

    void setSouls(float soul);
    void addSouls(float amount, float until);
    boolean consumeSouls(float amount);
    float getSouls();

    void setComboTimer(int comboTimer);
    int getComboTimer();

    //void setGhostForm(boolean ghostForm);
    //boolean getGhostForm();

    void setShadowForm(boolean shadowForm);
    boolean getShadowForm();

    void setFlamingArrowsCount(int flamingArrowsTimer);
    void setTormentArrowCount(int tormentsArrowsTimer);

    int getFlamingArrowsCount();
    int getTormentArrowCount();

    int getArrowsInCounter();
    void setArrowsInCounter(int arrowsInCounter);

    int getBurnNearbyTimer();
    void setBurnNearbyTimer(int burnNearbyTimer);

    int getFreezeNearbyTimer();
    void setFreezeNearbyTimer(int freezeNearbyTimer);

    int getGravityPulseTimer();
    void setGravityPulseTimer(int gravityPulseTimer);

    int getSnowballNearbyTimer();
    void setSnowballNearbyTimer(int snowballTimer);

    int getJumpCooldownTimer();
    void setJumpCooldownTimer(int jumpCooldownTimer);

    int getPoisonImmunityTimer();
    void setPoisonImmunityTimer(int poisoncloudimmunitytimer);

    int getLastShoutTimer();
    void setLastShoutTimer(int lastShoutTimer);

    double getDynamoMultiplier();
    void setDynamoMultiplier(double dynamoMultiplier);

    int getComboCount();
    void setComboCount(int comboCount);

    int getOffhandCooldown();
    void setOffhandCooldown(int cooldown);

    float getCachedCooldown();
    void setCachedCooldown(float cooldown);
}
