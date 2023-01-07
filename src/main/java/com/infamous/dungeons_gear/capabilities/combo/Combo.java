package com.infamous.dungeons_gear.capabilities.combo;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraftforge.common.util.INBTSerializable;

public class Combo implements INBTSerializable<CompoundTag> {

    private int comboTimer;
    //private boolean ghostForm;
    private boolean shadowForm;

    private int flamingArrowCount;
    private int tormentArrowCount;
    private int thunderingArrowCount;
    private int harpoonCount;

    private int comboCount;
    private int arrowsInCounter;
    private int jumpCooldownTimer;
    private int offhandCooldown;
    private float cachedCooldown;//no need to be saved, it's stored and used in the span of a tick

    private BlockPos lastExplorerCheckpoint;
    private BlockPos lastLuckyExplorerCheckpoint;
    private boolean artifactSynergy;
    private int painCycleStacks;
    private final int rollChargeTicks;
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

    public int getDualWieldTimer() {
        return this.comboTimer;
    }

    public void setDualWieldTimer(int comboTimer) {
        this.comboTimer = Mth.clamp(comboTimer, 0, 300);
    }

    public boolean getShadowForm() {
        return this.shadowForm;
    }

    public void setShadowForm(boolean shadowForm) {
        this.shadowForm = shadowForm;
    }

    public int getFlamingArrowsCount() {
        return this.flamingArrowCount;
    }

    public void setFlamingArrowsCount(int flamingArrowsCount) {
        this.flamingArrowCount = flamingArrowsCount;
    }

    public int getTormentArrowsCount() {
        return this.tormentArrowCount;
    }

    public int getThunderingArrowsCount() {
        return this.thunderingArrowCount;
    }

    public void setThunderingArrowsCount(int thunderingArrowsCount) {
        this.thunderingArrowCount = thunderingArrowsCount;
    }

    public int getHarpoonCount() {
        return this.harpoonCount;
    }

    public void setHarpoonCount(int harpoonCount) {
        this.harpoonCount = harpoonCount;
    }

    public void setTormentArrowCount(int tormentsArrowsCount) {
        this.tormentArrowCount = tormentsArrowsCount;
    }

    public int getArrowsInCounter() {
        return this.arrowsInCounter;
    }

    public void setArrowsInCounter(int arrowsInCounter) {
        this.arrowsInCounter = arrowsInCounter;
    }

    public int getJumpCooldownTimer() {
        return this.jumpCooldownTimer;
    }

    public void setJumpCooldownTimer(int jumpCooldownTimer) {
        this.jumpCooldownTimer = jumpCooldownTimer;
    }

    public int getComboCount() {
        return comboCount;
    }

    public void setComboCount(int comboCount) {
        this.comboCount = comboCount;
    }

    public int getOffhandCooldown() {
        return offhandCooldown;
    }

    public void setOffhandCooldown(int cooldown) {
        offhandCooldown = comboCount;
    }

    public float getCachedCooldown() {
        return cachedCooldown;
    }

    public void setCachedCooldown(float cooldown) {
        cachedCooldown = cooldown;
    }

    public BlockPos getLastExplorerCheckpoint() {
        return this.lastExplorerCheckpoint;
    }

    public void setLastExplorerCheckpoint(BlockPos blockPos) {
        this.lastExplorerCheckpoint = blockPos;
    }

    public BlockPos getLastLuckyExplorerCheckpoint() {
        return this.lastLuckyExplorerCheckpoint;
    }

    public void setLastLuckyExplorerCheckpoint(BlockPos blockPos) {
        this.lastLuckyExplorerCheckpoint = blockPos;
    }

    public boolean hasArtifactSynergy() {
        return this.artifactSynergy;
    }

    public void setArtifactSynergy(boolean artifactSynergy) {
        this.artifactSynergy = artifactSynergy;
    }

    public int getPainCycleStacks() {
        return this.painCycleStacks;
    }

    public void setPainCycleStacks(int painCycleStacks) {
        this.painCycleStacks = painCycleStacks;
    }

    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }

    public int getRefreshmentCounter() {
        return refreshmentCounter;
    }

    public void setRefreshmentCounter(int refreshmentCounter) {
        this.refreshmentCounter = refreshmentCounter;
    }

    public int getEchoCooldown() {
        return echoCooldown;
    }

    public void setEchoCooldown(int echoCooldown) {
        this.echoCooldown = echoCooldown;
    }

    @Override
    public CompoundTag serializeNBT() {

        CompoundTag tag = new CompoundTag();
        tag.putInt("comboTimer", this.getDualWieldTimer());
        tag.putInt("comboCount", this.getComboCount());
        tag.putBoolean("shadowForm", this.getShadowForm());

        tag.putInt("flamingArrowsCount", this.getFlamingArrowsCount());
        tag.putInt("tormentArrowsCount", this.getTormentArrowsCount());
        tag.putInt("thunderingArrowsCount", this.getThunderingArrowsCount());
        tag.putInt("harpoonCount", this.getHarpoonCount());

        tag.putInt("arrowsInCounter", this.getArrowsInCounter());
        tag.putInt("jumpCooldownTimer", this.getJumpCooldownTimer());

        BlockPos lastExplorerCheckpoint = this.getLastExplorerCheckpoint();
        tag.put("lastExplorerCheckpoint", this.newDoubleNBTList(lastExplorerCheckpoint.getX(), lastExplorerCheckpoint.getY(), lastExplorerCheckpoint.getZ()));

        BlockPos lastLuckyExplorerCheckpoint = this.getLastLuckyExplorerCheckpoint();
        tag.put("lastLuckyExplorerCheckpoint", this.newDoubleNBTList(lastLuckyExplorerCheckpoint.getX(), lastLuckyExplorerCheckpoint.getY(), lastLuckyExplorerCheckpoint.getZ()));

        tag.putBoolean("artifactSynergy", this.hasArtifactSynergy());

        tag.putInt("painCycleStacks", this.getPainCycleStacks());

        tag.putInt("jumpCounter", this.getJumpCounter());
        tag.putInt("refreshmentCounter", this.getRefreshmentCounter());

        tag.putInt("echoCooldown", this.getEchoCooldown());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.setDualWieldTimer(tag.getInt("comboTimer"));
        this.setComboCount(tag.getInt("comboCount"));
        this.setShadowForm(tag.getBoolean("shadowForm"));

        this.setFlamingArrowsCount(tag.getInt("flamingArrowsCount"));
        this.setTormentArrowCount(tag.getInt("tormentArrowsCount"));
        this.setThunderingArrowsCount(tag.getInt("thunderingArrowsCount"));
        this.setHarpoonCount(tag.getInt("harpoonCount"));

        this.setArrowsInCounter(tag.getInt("arrowsInCounter"));
        this.setJumpCooldownTimer(tag.getInt("jumpCooldownTimer"));

        ListTag listnbt = tag.getList("lastExplorerCheckpoint", 6);
        BlockPos lastExplorerCheckpoint = new BlockPos(listnbt.getDouble(0), listnbt.getDouble(1), listnbt.getDouble(2));
        this.setLastExplorerCheckpoint(lastExplorerCheckpoint);

        ListTag listnbt1 = tag.getList("lastLuckyExplorerCheckpoint", 6);
        BlockPos lastLuckyExplorerCheckpoint = new BlockPos(listnbt1.getDouble(0), listnbt1.getDouble(1), listnbt1.getDouble(2));
        this.setLastLuckyExplorerCheckpoint(lastLuckyExplorerCheckpoint);

        this.setArtifactSynergy(tag.getBoolean("artifactSynergy"));

        this.setPainCycleStacks(tag.getInt("painCycleStacks"));

        this.setJumpCounter(tag.getInt("jumpCounter"));
        this.setRefreshmentCounter(tag.getInt("refreshmentCounter"));

        this.setEchoCooldown(tag.getInt("echoCooldown"));
    }

    private ListTag newDoubleNBTList(double... numbers) {
        ListTag listnbt = new ListTag();

        for (double d0 : numbers) {
            listnbt.add(DoubleTag.valueOf(d0));
        }

        return listnbt;
    }
}
