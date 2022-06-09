package com.infamous.dungeons_gear.capabilities.combo;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Combo implements ICombo {

    private int comboTimer;
    //private boolean ghostForm;
    private boolean shadowForm;

    private int flamingArrowCount;
    private int tormentArrowCount;
    private int thunderingArrowCount;
    private int harpoonCount;

    private int snowballNearbyTimer;
    private int gravityPulseTimer;
    private int comboCount;
    private int arrowsInCounter;
    private int jumpCooldownTimer;
    private int lastShoutTimer;
    private int offhandCooldown;
    private float cachedCooldown;//no need to be saved, it's stored and used in the span of a tick

    private BlockPos lastExplorerCheckpoint;
    private BlockPos lastLuckyExplorerCheckpoint;
    private boolean artifactSynergy;
    private int painCycleStacks;
    private int rollChargeTicks;
    private int jumpCounter;
    private int refreshmentCounter;
    private int echoCooldown;

    public Combo() {
        this.comboTimer = 0;
        this.comboCount = 0;
        //this.ghostForm = false;
        this.shadowForm = false;

        this.flamingArrowCount = 0;
        this.tormentArrowCount = 0;
        this.thunderingArrowCount = 0;
        this.harpoonCount = 0;

        this.arrowsInCounter = 0;
        this.gravityPulseTimer = 100;
        this.snowballNearbyTimer = 100;
        this.jumpCooldownTimer = 0;
        this.lastExplorerCheckpoint = BlockPos.ZERO;
        this.lastLuckyExplorerCheckpoint = BlockPos.ZERO;

        this.artifactSynergy = false;
        this.painCycleStacks = 0;
        this.rollChargeTicks = 0;
        this.jumpCounter = 0;
        this.refreshmentCounter = 0;
        this.echoCooldown = 0;
    }

    @Override
    public int getComboTimer() {
        return this.comboTimer;
    }

    @Override
    public void setComboTimer(int comboTimer) {
        this.comboTimer = MathHelper.clamp(comboTimer, 0, 300);
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
    public void setFlamingArrowsCount(int flamingArrowsCount) {
        this.flamingArrowCount = flamingArrowsCount;
    }

    @Override
    public int getTormentArrowsCount() {
        return this.tormentArrowCount;
    }

    @Override
    public int getThunderingArrowsCount() {
        return this.thunderingArrowCount;
    }

    @Override
    public void setThunderingArrowsCount(int thunderingArrowsCount) {
        this.thunderingArrowCount = thunderingArrowsCount;
    }

    @Override
    public int getHarpoonCount() {
        return this.harpoonCount;
    }

    @Override
    public void setHarpoonCount(int harpoonCount) {
        this.harpoonCount = harpoonCount;
    }

    @Override
    public void setTormentArrowCount(int tormentsArrowsCount) {
        this.tormentArrowCount = tormentsArrowsCount;
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
    public int getJumpCooldownTimer() {
        return this.jumpCooldownTimer;
    }

    @Override
    public void setJumpCooldownTimer(int jumpCooldownTimer) {
        this.jumpCooldownTimer = jumpCooldownTimer;
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
    public int getOffhandCooldown() {
        return offhandCooldown;
    }

    @Override
    public void setOffhandCooldown(int cooldown) {
        offhandCooldown = comboCount;
    }

    @Override
    public float getCachedCooldown() {
        return cachedCooldown;
    }

    @Override
    public void setCachedCooldown(float cooldown) {
        cachedCooldown = cooldown;
    }

    @Override
    public BlockPos getLastExplorerCheckpoint() {
        return this.lastExplorerCheckpoint;
    }

    @Override
    public void setLastExplorerCheckpoint(BlockPos blockPos) {
        this.lastExplorerCheckpoint = blockPos;
    }

    @Override
    public BlockPos getLastLuckyExplorerCheckpoint() {
        return this.lastLuckyExplorerCheckpoint;
    }

    @Override
    public void setLastLuckyExplorerCheckpoint(BlockPos blockPos) {
        this.lastLuckyExplorerCheckpoint = blockPos;
    }

    @Override
    public boolean hasArtifactSynergy() {
        return this.artifactSynergy;
    }

    @Override
    public void setArtifactSynergy(boolean artifactSynergy) {
        this.artifactSynergy = artifactSynergy;
    }

    @Override
    public int getPainCycleStacks() {
        return this.painCycleStacks;
    }

    @Override
    public void setPainCycleStacks(int painCycleStacks) {
        this.painCycleStacks = painCycleStacks;
    }

    @Override
    public int getRollChargeTicks() {
        return this.rollChargeTicks;
    }

    @Override
    public void setRollChargeTicks(int rollChargeTicks) {
        this.rollChargeTicks = rollChargeTicks;
    }

    @Override
    public int getJumpCounter() {
        return jumpCounter;
    }

    @Override
    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }

    @Override
    public int getRefreshmentCounter() {
        return refreshmentCounter;
    }

    @Override
    public void setRefreshmentCounter(int refreshmentCounter) {
        this.refreshmentCounter = refreshmentCounter;
    }

    @Override
    public int getEchoCooldown() {
        return echoCooldown;
    }

    @Override
    public void setEchoCooldown(int echoCooldown) {
        this.echoCooldown=echoCooldown;
    }
}
