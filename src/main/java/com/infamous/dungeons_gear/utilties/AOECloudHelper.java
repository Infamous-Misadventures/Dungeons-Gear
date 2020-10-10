package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.effects.CustomEffects;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;

public class AOECloudHelper {
    public static void spawnExplosionCloud(LivingEntity attacker, LivingEntity victim, float radius){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setParticleData(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(radius);
        areaeffectcloudentity.setDuration(0);
        attacker.world.addEntity(areaeffectcloudentity);
    }

    public static void spawnExplosionCloudAtPos(LivingEntity attacker, boolean arrow, BlockPos blockPos, float radius){
        int inGroundMitigator = arrow ? 1 : 0;
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(attacker.world, blockPos.getX(), blockPos.getY() + inGroundMitigator, blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setParticleData(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(radius);
        areaeffectcloudentity.setDuration(0);
        attacker.world.addEntity(areaeffectcloudentity);
    }

    public static void spawnCritCloud(LivingEntity attacker, LivingEntity victim, float radius){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setParticleData(ParticleTypes.CRIT);
        areaeffectcloudentity.setRadius(radius);
        areaeffectcloudentity.setDuration(0);
        attacker.world.addEntity(areaeffectcloudentity);
    }

    public static void spawnRegenCloud(LivingEntity attacker, int amplifier){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(attacker.world, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(100);
        EffectInstance regeneration = new EffectInstance(Effects.REGENERATION, 100, amplifier);
        areaeffectcloudentity.addEffect(regeneration);
        attacker.world.addEntity(areaeffectcloudentity);
    }

    public static void spawnRegenCloudAtPos(LivingEntity attacker, boolean arrow, BlockPos blockPos, int amplifier){
        int inGroundMitigator = arrow ? 1 : 0;
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(attacker.world, blockPos.getX(), blockPos.getY() + inGroundMitigator, blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(100);
        EffectInstance regeneration = new EffectInstance(Effects.REGENERATION, 100, amplifier);
        areaeffectcloudentity.addEffect(regeneration);
        attacker.world.addEntity(areaeffectcloudentity);
    }

    public static void spawnShieldingCloudAtPos(LivingEntity attacker, BlockPos blockPos, int duration){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(attacker.world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(duration);
        EffectInstance shielding = new EffectInstance(CustomEffects.SHIELDING, duration);
        areaeffectcloudentity.addEffect(shielding);
        attacker.world.addEntity(areaeffectcloudentity);
    }

    public static void spawnSoulProtectionCloudAtPos(LivingEntity attacker, BlockPos blockPos, int duration){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(attacker.world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(duration);
        EffectInstance shielding = new EffectInstance(CustomEffects.SOUL_PROTECTION, duration);
        areaeffectcloudentity.addEffect(shielding);
        attacker.world.addEntity(areaeffectcloudentity);
    }

    public static void spawnPoisonCloud(LivingEntity attacker, LivingEntity victim, int amplifier){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(victim.world, victim.getPosX(), victim.getPosY(), victim.getPosZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(60);
        EffectInstance poison = new EffectInstance(Effects.POISON, 60, amplifier);
        areaeffectcloudentity.addEffect(poison);
        victim.world.addEntity(areaeffectcloudentity);
    }

    public static void spawnPoisonCloudAtPos(LivingEntity attacker, boolean arrow, BlockPos blockPos, int amplifier){
        int inGroundMitigator = arrow ? 1 : 0;
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(attacker.world, blockPos.getX(), blockPos.getY() + inGroundMitigator, blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(100);
        EffectInstance poison = new EffectInstance(Effects.POISON, 60, amplifier);
        areaeffectcloudentity.addEffect(poison);
        attacker.world.addEntity(areaeffectcloudentity);
    }
}
