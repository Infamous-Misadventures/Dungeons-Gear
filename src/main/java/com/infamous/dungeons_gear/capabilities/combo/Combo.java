package com.infamous.dungeons_gear.capabilities.combo;

public class Combo implements ICombo {

    private int comboTimer;
    //private boolean ghostForm;
    private boolean shadowForm;
    private int flamingArrowCount;
    private int tormentArrowCount;
    private int burnNearbyTimer;
    private int freezeNearbyTimer;
    private int snowballNearbyTimer;
    private int gravityPulseTimer;
    private int comboCount;
    private int arrowsInCounter;
    private int jumpCooldownTimer;
    private int poisonImmunityTimer;
    private double dynamoMultiplier;
    private int lastShoutTimer;
    private float cachedCooldown;//no need to be saved, it's stored and used in the span of a tick

    public Combo() {
        this.comboTimer = 0;
        this.comboCount = 0;
        //this.ghostForm = false;
        this.shadowForm = false;
        this.flamingArrowCount = 0;
        this.tormentArrowCount = 0;
        this.arrowsInCounter = 0;
        this.burnNearbyTimer = 10;
        this.burnNearbyTimer = 40;
        this.gravityPulseTimer = 100;
        this.snowballNearbyTimer = 100;
        this.jumpCooldownTimer = 0;
        this.poisonImmunityTimer = 0;
        this.dynamoMultiplier = 1.0D;
    }

    @Override
    public int getComboTimer() {
        return this.comboTimer;
    }

    @Override
    public void setComboTimer(int comboTimer) {
        this.comboTimer = comboTimer;
    }
/*
    @Override
    public void setGhostForm(boolean ghostForm) {
        this.ghostForm = ghostForm;
    }

    @Override
    public boolean getGhostForm() {
        return this.ghostForm;
    }

 */

    @Override
    public boolean getShadowForm() {
        return this.shadowForm;
    }

    @Override
    public void setShadowForm(boolean shadowForm) {
        this.shadowForm = shadowForm;
    }

    @Override
    public int getFlamingArrowsCount() {
        return this.flamingArrowCount;
    }

    @Override
    public void setFlamingArrowsCount(int flamingArrowsTimer) {
        this.flamingArrowCount = flamingArrowsTimer;
    }

    @Override
    public int getTormentArrowCount() {
        return this.tormentArrowCount;
    }

    @Override
    public void setTormentArrowCount(int tormentsArrowsTimer) {
        this.tormentArrowCount = tormentsArrowsTimer;
    }

    @Override
    public int getArrowsInCounter() {
        return this.arrowsInCounter;
    }

    @Override
    public void setArrowsInCounter(int arrowsInCounter) {
        this.arrowsInCounter = arrowsInCounter;
    }

    @Override
    public int getBurnNearbyTimer() {
        return this.burnNearbyTimer;
    }

    @Override
    public void setBurnNearbyTimer(int burnNearbyTimer) {
        this.burnNearbyTimer = burnNearbyTimer;
    }

    @Override
    public int getFreezeNearbyTimer() {
        return this.freezeNearbyTimer;
    }

    @Override
    public void setFreezeNearbyTimer(int freezeNearbyTimer) {
        this.freezeNearbyTimer = freezeNearbyTimer;
    }

    @Override
    public int getGravityPulseTimer() {
        return gravityPulseTimer;
    }

    @Override
    public void setGravityPulseTimer(int gravityPulseTimer) {
        this.gravityPulseTimer = gravityPulseTimer;
    }

    @Override
    public int getSnowballNearbyTimer() {
        return this.snowballNearbyTimer;
    }

    @Override
    public void setSnowballNearbyTimer(int snowballTimer) {
        this.snowballNearbyTimer = snowballTimer;
    }

    @Override
    public int getJumpCooldownTimer() {
        return this.jumpCooldownTimer;
    }

    @Override
    public void setJumpCooldownTimer(int jumpCooldownTimer) {
        this.jumpCooldownTimer = jumpCooldownTimer;
    }

    @Override
    public int getPoisonImmunityTimer() {
        return this.poisonImmunityTimer;
    }

    @Override
    public void setPoisonImmunityTimer(int poisonImmunityTimer) {
        this.poisonImmunityTimer = poisonImmunityTimer;
    }

    @Override
    public int getLastShoutTimer() {
        return lastShoutTimer;
    }

    @Override
    public void setLastShoutTimer(int lastShoutTimer) {
        this.lastShoutTimer = lastShoutTimer;
    }

    @Override
    public double getDynamoMultiplier() {
        return this.dynamoMultiplier;
    }

    @Override
    public void setDynamoMultiplier(double dynamoMultiplier) {
        this.dynamoMultiplier = dynamoMultiplier;
    }

    @Override
    public void setCachedCooldown(float cooldown) {
        cachedCooldown = cooldown;
    }

    @Override
    public int getComboCount() {
        return comboCount;
    }

    @Override
    public void setComboCount(int comboCount) {
        this.comboCount = comboCount;
    }

    @Override
    public float getCachedCooldown() {
        return cachedCooldown;
    }
}
