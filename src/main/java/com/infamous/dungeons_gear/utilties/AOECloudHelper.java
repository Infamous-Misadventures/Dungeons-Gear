package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.registry.MobEffectInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;

public class AOECloudHelper {
    public static void spawnExplosionCloud(LivingEntity attacker, LivingEntity victim, float radius) {
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(victim.level, victim.getX(), victim.getY(), victim.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(radius);
        areaeffectcloudentity.setDuration(0);
        attacker.level.addFreshEntity(areaeffectcloudentity);
    }

    public static void spawnExplosionCloudAtPos(LivingEntity attacker, boolean arrow, BlockPos blockPos, float radius) {
        int inGroundMitigator = arrow ? 1 : 0;
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(attacker.level, blockPos.getX(), blockPos.getY() + inGroundMitigator, blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(radius);
        areaeffectcloudentity.setDuration(0);
        attacker.level.addFreshEntity(areaeffectcloudentity);
    }

    public static void spawnCritCloud(LivingEntity attacker, LivingEntity victim, float radius) {
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(victim.level, victim.getX(), victim.getY(), victim.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setParticle(ParticleTypes.CRIT);
        areaeffectcloudentity.setRadius(radius);
        areaeffectcloudentity.setDuration(0);
        attacker.level.addFreshEntity(areaeffectcloudentity);
    }

    public static void spawnRegenCloud(LivingEntity attacker, int amplifier) {
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(attacker.level, attacker.getX(), attacker.getY(), attacker.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(100);
        MobEffectInstance regeneration = new MobEffectInstance(MobEffects.REGENERATION, 100, amplifier);
        areaeffectcloudentity.addEffect(regeneration);
        attacker.level.addFreshEntity(areaeffectcloudentity);
    }

    public static void spawnRegenCloudAtPos(LivingEntity attacker, boolean arrow, BlockPos blockPos, int amplifier) {
        int inGroundMitigator = arrow ? 1 : 0;
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(attacker.level, blockPos.getX(), blockPos.getY() + inGroundMitigator, blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(100);
        MobEffectInstance regeneration = new MobEffectInstance(MobEffects.REGENERATION, 100, amplifier);
        areaeffectcloudentity.addEffect(regeneration);
        attacker.level.addFreshEntity(areaeffectcloudentity);
    }

    public static void spawnSoulProtectionCloudAtPos(LivingEntity attacker, BlockPos blockPos, int duration) {
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(attacker.level, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(duration);
        MobEffectInstance shielding = new MobEffectInstance(MobEffectInit.SOUL_PROTECTION.get(), duration);
        areaeffectcloudentity.addEffect(shielding);
        attacker.level.addFreshEntity(areaeffectcloudentity);
    }

    public static void spawnPoisonCloud(LivingEntity attacker, LivingEntity victim, int amplifier) {
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(victim.level, victim.getX(), victim.getY(), victim.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(60);
        MobEffectInstance poison = new MobEffectInstance(MobEffects.POISON, 60, amplifier);
        areaeffectcloudentity.addEffect(poison);
        victim.level.addFreshEntity(areaeffectcloudentity);
    }

    public static void spawnPoisonCloudAtPos(LivingEntity attacker, boolean arrow, BlockPos blockPos, int amplifier) {
        int inGroundMitigator = arrow ? 1 : 0;
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(attacker.level, blockPos.getX(), blockPos.getY() + inGroundMitigator, blockPos.getZ());
        areaeffectcloudentity.setOwner(attacker);
        areaeffectcloudentity.setRadius(5.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setDuration(100);
        MobEffectInstance poison = new MobEffectInstance(MobEffects.POISON, 60, amplifier);
        areaeffectcloudentity.addEffect(poison);
        attacker.level.addFreshEntity(areaeffectcloudentity);
    }
}
