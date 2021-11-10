package com.infamous.dungeons_gear.utilties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nullable;
import java.util.Random;

public class SoundHelper {

    // this gets multiplied by 16.0F, so the volume indicates from how many chunks away the sound can be heard
    private static final float volumeLimit = 2.0F;
    private static final float standardPitch = 1.0F;

    public static final Random RNG = new Random();

    public static void playLightningStrikeSounds(Entity soundEmissionTarget){
        PlayerEntity player = getPlayerFrom(soundEmissionTarget);
        soundEmissionTarget.level.playSound(player,
                soundEmissionTarget.blockPosition(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, soundEmissionTarget.getSoundSource(), volumeLimit, 0.8F + RNG.nextFloat() * 0.2F);
        soundEmissionTarget.level.playSound(player,
                soundEmissionTarget.getX(), soundEmissionTarget.getY(), soundEmissionTarget.getZ(),
                SoundEvents.LIGHTNING_BOLT_IMPACT, soundEmissionTarget.getSoundSource(), volumeLimit, 0.5F + RNG.nextFloat() * 0.2F);
    }

    public static void playGenericExplodeSound(Entity soundEmissionTarget){
        soundEmissionTarget.level.playLocalSound(
                soundEmissionTarget.getX(), soundEmissionTarget.getY(), soundEmissionTarget.getZ(),
                SoundEvents.GENERIC_EXPLODE, soundEmissionTarget.getSoundSource(), volumeLimit, (1.0F + (RNG.nextFloat() - RNG.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public static void playBoltImpactSound(Entity soundEmissionTarget){
        soundEmissionTarget.level.playSound(getPlayerFrom(soundEmissionTarget),
                soundEmissionTarget.blockPosition(),
                SoundEvents.LIGHTNING_BOLT_IMPACT, soundEmissionTarget.getSoundSource(), volumeLimit, 0.5F + RNG.nextFloat() * 0.2F);
    }

    public static void playAttackSweepSound(Entity soundEmissionTarget){
        soundEmissionTarget.level.playSound(getPlayerFrom(soundEmissionTarget),
                soundEmissionTarget.blockPosition(),
                SoundEvents.PLAYER_ATTACK_SWEEP, soundEmissionTarget.getSoundSource(), volumeLimit, standardPitch);
    }

    public static void playBellSound(Entity soundEmissionTarget){
        PlayerEntity player = getPlayerFrom(soundEmissionTarget);
        soundEmissionTarget.level.playSound(player,
                soundEmissionTarget.blockPosition(),
                SoundEvents.BELL_BLOCK, soundEmissionTarget.getSoundSource(), volumeLimit, standardPitch);
        soundEmissionTarget.level.playSound(player,
                soundEmissionTarget.blockPosition(),
                SoundEvents.BELL_RESONATE, soundEmissionTarget.getSoundSource(), volumeLimit, standardPitch);
    }

    public static void playCreatureSound(Entity soundEmissionTarget, SoundEvent soundEvent){
        soundEmissionTarget.level.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.blockPosition(), soundEvent, soundEmissionTarget.getSoundSource(), volumeLimit, standardPitch);

    }

    public static void playBeaconSound(Entity soundEmissionTarget, boolean activate){
        if(activate){
            soundEmissionTarget.level.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.blockPosition(), SoundEvents.BEACON_ACTIVATE, soundEmissionTarget.getSoundSource(), volumeLimit, standardPitch);

        }
        else{
            soundEmissionTarget.level.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.blockPosition(), SoundEvents.BEACON_DEACTIVATE, soundEmissionTarget.getSoundSource(), volumeLimit, standardPitch);

        }
    }

    public static void playHornSound(Entity soundEmissionTarget) {
        soundEmissionTarget.level.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.blockPosition(), SoundEvents.RAID_HORN, soundEmissionTarget.getSoundSource(), volumeLimit, standardPitch);

    }

    public static void playKnockbackSound(Entity soundEmissionTarget) {
        soundEmissionTarget.level.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.blockPosition(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, soundEmissionTarget.getSoundSource(), volumeLimit, standardPitch);
    }

    @Nullable
    private static PlayerEntity getPlayerFrom(Entity soundEmissionTarget) {
        return soundEmissionTarget instanceof PlayerEntity ? (PlayerEntity) soundEmissionTarget : null;
    }
}
