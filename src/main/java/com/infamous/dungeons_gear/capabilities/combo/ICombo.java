package com.infamous.dungeons_gear.capabilities.combo;


import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

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
