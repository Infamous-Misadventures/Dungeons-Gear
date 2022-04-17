package com.infamous.dungeons_gear.capabilities.combo;


import net.minecraft.util.math.BlockPos;

public interface ICombo {

    void setComboTimer(int comboTimer);
    int getComboTimer();

    //void setGhostForm(boolean ghostForm);
    //boolean getGhostForm();

    void setShadowForm(boolean shadowForm);
    boolean getShadowForm();

    void setFlamingArrowsCount(int flamingArrowsCount);
    void setTormentArrowCount(int tormentsArrowsCount);
    void setThunderingArrowsCount(int thunderingArrowsCount);
    void setHarpoonCount(int harpoonCount);

    int getFlamingArrowsCount();
    int getTormentArrowsCount();
    int getThunderingArrowsCount();
    int getHarpoonCount();

    int getArrowsInCounter();
    void setArrowsInCounter(int arrowsInCounter);

    int getSnowballNearbyTimer();
    void setSnowballNearbyTimer(int snowballTimer);

    int getJumpCooldownTimer();
    void setJumpCooldownTimer(int jumpCooldownTimer);

    int getComboCount();
    void setComboCount(int comboCount);

    int getOffhandCooldown();
    void setOffhandCooldown(int cooldown);

    float getCachedCooldown();
    void setCachedCooldown(float cooldown);

    BlockPos getLastExplorerCheckpoint();
    void setLastExplorerCheckpoint(BlockPos blockPos);

    BlockPos getLastLuckyExplorerCheckpoint();
    void setLastLuckyExplorerCheckpoint(BlockPos blockPos);

    boolean hasArtifactSynergy();
    void setArtifactSynergy(boolean artifactSynergy);

    int getPainCycleStacks();
    void setPainCycleStacks(int painCycleStacks);

    int getRollChargeTicks();
    void setRollChargeTicks(int rollChargeTicks);

    int getJumpCounter();
    void setJumpCounter(int jumpCounter);

    int getRefreshmentCounter();
    void setRefreshmentCounter(int refreshmentCounter);

    int getEchoCooldown();
    void setEchoCooldown(int echoCooldown);
}
