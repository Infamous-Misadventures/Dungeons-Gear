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
        soundEmissionTarget.world.playSound(player,
                soundEmissionTarget.getPosition(),
                SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, soundEmissionTarget.getSoundCategory(), volumeLimit, 0.8F + RNG.nextFloat() * 0.2F);
        soundEmissionTarget.world.playSound(player,
                soundEmissionTarget.getPosX(), soundEmissionTarget.getPosY(), soundEmissionTarget.getPosZ(),
                SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, soundEmissionTarget.getSoundCategory(), volumeLimit, 0.5F + RNG.nextFloat() * 0.2F);
    }

    public static void playGenericExplodeSound(Entity soundEmissionTarget){
        soundEmissionTarget.world.playSound(
                soundEmissionTarget.getPosX(), soundEmissionTarget.getPosY(), soundEmissionTarget.getPosZ(),
                SoundEvents.ENTITY_GENERIC_EXPLODE, soundEmissionTarget.getSoundCategory(), volumeLimit, (1.0F + (RNG.nextFloat() - RNG.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public static void playBoltImpactSound(Entity soundEmissionTarget){
        soundEmissionTarget.world.playSound(getPlayerFrom(soundEmissionTarget),
                soundEmissionTarget.getPosition(),
                SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, soundEmissionTarget.getSoundCategory(), volumeLimit, 0.5F + RNG.nextFloat() * 0.2F);
    }

    public static void playAttackSweepSound(Entity soundEmissionTarget){
        soundEmissionTarget.world.playSound(getPlayerFrom(soundEmissionTarget),
                soundEmissionTarget.getPosition(),
                SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, soundEmissionTarget.getSoundCategory(), volumeLimit, standardPitch);
    }

    public static void playBellSound(Entity soundEmissionTarget){
        PlayerEntity player = getPlayerFrom(soundEmissionTarget);
        soundEmissionTarget.world.playSound(player,
                soundEmissionTarget.getPosition(),
                SoundEvents.BLOCK_BELL_USE, soundEmissionTarget.getSoundCategory(), volumeLimit, standardPitch);
        soundEmissionTarget.world.playSound(player,
                soundEmissionTarget.getPosition(),
                SoundEvents.BLOCK_BELL_RESONATE, soundEmissionTarget.getSoundCategory(), volumeLimit, standardPitch);
    }

    public static void playCreatureSound(Entity soundEmissionTarget, SoundEvent soundEvent){
        soundEmissionTarget.world.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.getPosition(), soundEvent, soundEmissionTarget.getSoundCategory(), volumeLimit, standardPitch);

    }

    public static void playBeaconSound(Entity soundEmissionTarget, boolean activate){
        if(activate){
            soundEmissionTarget.world.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.getPosition(), SoundEvents.BLOCK_BEACON_ACTIVATE, soundEmissionTarget.getSoundCategory(), volumeLimit, standardPitch);

        }
        else{
            soundEmissionTarget.world.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.getPosition(), SoundEvents.BLOCK_BEACON_DEACTIVATE, soundEmissionTarget.getSoundCategory(), volumeLimit, standardPitch);

        }
    }

    public static void playHornSound(Entity soundEmissionTarget) {
        soundEmissionTarget.world.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.getPosition(), SoundEvents.EVENT_RAID_HORN, soundEmissionTarget.getSoundCategory(), volumeLimit, standardPitch);

    }

    public static void playKnockbackSound(Entity soundEmissionTarget) {
        soundEmissionTarget.world.playSound(getPlayerFrom(soundEmissionTarget), soundEmissionTarget.getPosition(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, soundEmissionTarget.getSoundCategory(), volumeLimit, standardPitch);
    }

    @Nullable
    private static PlayerEntity getPlayerFrom(Entity soundEmissionTarget) {
        return soundEmissionTarget instanceof PlayerEntity ? (PlayerEntity) soundEmissionTarget : null;
    }
}
