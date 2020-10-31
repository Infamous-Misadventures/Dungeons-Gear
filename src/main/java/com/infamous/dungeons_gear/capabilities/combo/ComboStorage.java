package com.infamous.dungeons_gear.capabilities.combo;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ComboStorage implements Capability.IStorage<ICombo> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ICombo> capability, ICombo instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("comboTimer", instance.getComboTimer());
        //tag.putBoolean("ghostForm", instance.getGhostForm());
        tag.putBoolean("shadowForm", instance.getShadowForm());
        tag.putInt("flamingArrowsCount", instance.getFlamingArrowsCount());
        tag.putInt("tormentArrowsCount", instance.getTormentArrowCount());
        tag.putInt("arrowsInCounter", instance.getArrowsInCounter());
        tag.putInt("burnNearbyTimer", instance.getBurnNearbyTimer());
        tag.putInt("freezeNearbyTimer", instance.getFreezeNearbyTimer());
        tag.putInt("gravityPulseTimer", instance.getGravityPulseTimer());
        tag.putInt("snowballNearbyTimer", instance.getSnowballNearbyTimer());
        tag.putInt("jumpCooldownTimer", instance.getJumpCooldownTimer());
        tag.putInt("poisonImmunityTimer", instance.getPoisonImmunityTimer());
        tag.putDouble("dynamoMultiplier", instance.getDynamoMultiplier());
        return tag;
    }

    @Override
    public void readNBT(Capability<ICombo> capability, ICombo instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setComboTimer(tag.getInt("comboTimer"));
        //instance.setGhostForm(tag.getBoolean("ghostForm"));
        instance.setShadowForm(tag.getBoolean("shadowForm"));
        instance.setFlamingArrowsCount(tag.getInt("flamingArrowsCount"));
        instance.setTormentArrowCount(tag.getInt("tormentArrowsCount"));
        instance.setArrowsInCounter(tag.getInt("arrowsInCounter"));
        instance.setBurnNearbyTimer(tag.getInt("burnNearbyTimer"));
        instance.setFreezeNearbyTimer(tag.getInt("freezeNearbyTimer"));
        instance.setGravityPulseTimer(tag.getInt("gravityPulseTimer"));
        instance.setSnowballNearbyTimer(tag.getInt("snowballNearbyTimer"));
        instance.setJumpCooldownTimer(tag.getInt("jumpCooldownTimer"));
        instance.setPoisonImmunityTimer(tag.getInt("poisonImmunityTimer"));
        instance.setDynamoMultiplier(tag.getInt("dynamoMultiplier"));
    }
}
