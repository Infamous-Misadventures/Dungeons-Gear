package com.infamous.dungeons_gear.utilties;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.Random;

public class SoundHelper {

    public static final Random RNG = new Random();

    public static void playLightningStrikeSounds(Entity soundEmissionTarget){
        soundEmissionTarget.world.playSound((PlayerEntity)null,
                soundEmissionTarget.getPosition(),
                SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, soundEmissionTarget.getSoundCategory(), 10000.0F, 0.8F + RNG.nextFloat() * 0.2F);
        soundEmissionTarget.world.playSound((PlayerEntity)null,
                soundEmissionTarget.getPosX(), soundEmissionTarget.getPosY(), soundEmissionTarget.getPosZ(),
                SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, soundEmissionTarget.getSoundCategory(), 2.0F, 0.5F + RNG.nextFloat() * 0.2F);
    }

    public static void playGenericExplodeSound(Entity soundEmissionTarget){
        soundEmissionTarget.world.playSound(
                soundEmissionTarget.getPosX(), soundEmissionTarget.getPosY(), soundEmissionTarget.getPosZ(),
                SoundEvents.ENTITY_GENERIC_EXPLODE, soundEmissionTarget.getSoundCategory(), 4.0F, (1.0F + (RNG.nextFloat() - RNG.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public static void playBoltImpactSound(Entity soundEmissionTarget){
        soundEmissionTarget.world.playSound((PlayerEntity) null,
                soundEmissionTarget.getPosition(),
                SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, soundEmissionTarget.getSoundCategory(), 2.0F, 0.5F + RNG.nextFloat() * 0.2F);
    }

    public static void playAttackSweepSound(Entity soundEmissionTarget){
        soundEmissionTarget.world.playSound((PlayerEntity) null,
                soundEmissionTarget.getPosition(),
                SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, soundEmissionTarget.getSoundCategory(), 1.0F, 1.0F);
    }

    public static void playBellSound(Entity soundEmissionTarget){
        soundEmissionTarget.world.playSound((PlayerEntity)null,
                soundEmissionTarget.getPosition(),
                SoundEvents.BLOCK_BELL_USE, soundEmissionTarget.getSoundCategory(), 2.0F, 1.0F);
        soundEmissionTarget.world.playSound((PlayerEntity)null,
                soundEmissionTarget.getPosition(),
                SoundEvents.BLOCK_BELL_RESONATE, soundEmissionTarget.getSoundCategory(), 1.0F, 1.0F);
    }

    public static void playCreatureSound(Entity soundEmissionTarget, SoundEvent soundEvent){
        soundEmissionTarget.world.playSound((PlayerEntity) null, soundEmissionTarget.getPosition(), soundEvent, SoundCategory.AMBIENT, 64.0F, 1.0F);

    }

    public static void playBeaconSound(Entity soundEmissionTarget, boolean activate){
        if(activate){
            soundEmissionTarget.world.playSound((PlayerEntity)null, soundEmissionTarget.getPosition(), SoundEvents.BLOCK_BEACON_ACTIVATE, soundEmissionTarget.getSoundCategory(), 1.0f, 1.0f);

        }
        else{
            soundEmissionTarget.world.playSound((PlayerEntity)null, soundEmissionTarget.getPosition(), SoundEvents.BLOCK_BEACON_DEACTIVATE, soundEmissionTarget.getSoundCategory(), 1.0f, 1.0f);

        }
    }

    public static void playHornSound(Entity soundEmissionTarget) {
        soundEmissionTarget.world.playSound((PlayerEntity) null, soundEmissionTarget.getPosition(), SoundEvents.EVENT_RAID_HORN, soundEmissionTarget.getSoundCategory(), 64.0F, 1.0F);

    }

    public static void playKnockbackSound(Entity soundEmissionTarget) {
        soundEmissionTarget.world.playSound((PlayerEntity)null, soundEmissionTarget.getPosition(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, soundEmissionTarget.getSoundCategory(), 1.0F, 1.0F);
    }
}
