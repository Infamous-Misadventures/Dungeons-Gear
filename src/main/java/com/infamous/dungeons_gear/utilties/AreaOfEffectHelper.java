package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.registry.ParticleInit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_libraries.utils.AbilityHelper.isFacingEntity;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.*;

public class AreaOfEffectHelper {

    public static final double PULL_IN_SPEED_FACTOR = 0.15;
    public static final Random RANDOM = new Random();

    public static boolean applyElementalEffectsToNearbyEnemies(Player playerIn, int limit, float distance) {
        applyToNearbyEntities(playerIn, distance, limit,
                getCanApplyToEnemyPredicate(playerIn),
                (LivingEntity nearbyEntity) -> {
                    int randomEffectId = RANDOM.nextInt(4);
                    switch (randomEffectId) {
                        case 1:
                            electrify(playerIn, nearbyEntity, 5);
                            break;
                        case 2:
                            freezeEnemy(0, nearbyEntity, 8);
                            break;
                        case 3:
                            nearbyEntity.setSecondsOnFire(8);
                            break;
                    }
                }
        );
        return true;
    }

    public static void pullInNearbyEntities(LivingEntity attacker, LivingEntity target, float distance, SimpleParticleType particleType) {
        PROXY.spawnParticles(target, ParticleTypes.PORTAL);
        applyToNearbyEntities(target, distance,
                getCanApplyToSecondEnemyPredicate(attacker, target),
                (LivingEntity nearbyEntity) -> pullVictimTowardsTarget(target, nearbyEntity, particleType, PULL_IN_SPEED_FACTOR)
        );
    }

    public static void pullVictimTowardsTarget(LivingEntity target, LivingEntity nearbyEntity, SimpleParticleType particleType, double pullInSpeedFactor) {
        double motionX = target.getX() - (nearbyEntity.getX());
        double motionY = target.getY() - (nearbyEntity.getY());
        double motionZ = target.getZ() - (nearbyEntity.getZ());
        Vec3 vector3d = new Vec3(motionX, motionY, motionZ).scale(pullInSpeedFactor);

        nearbyEntity.setDeltaMovement(vector3d);
        PROXY.spawnParticles(nearbyEntity, particleType);
    }

    public static void pullInNearbyEntitiesAtPos(LivingEntity attacker, BlockPos blockPos, int distance, SimpleParticleType particleType) {
        Level world = attacker.getCommandSenderWorld();
        applyToNearbyEntitiesAtPos(blockPos, world, distance,
                getCanApplyToEnemyPredicate(attacker),
                (LivingEntity nearbyEntity) -> pullVictimTowardsPos(blockPos, nearbyEntity, particleType)
        );
    }

    private static void pullVictimTowardsPos(BlockPos targetPos, LivingEntity nearbyEntity, SimpleParticleType particleType) {
        double motionX = targetPos.getX() - (nearbyEntity.getX());
        double motionY = targetPos.getY() - (nearbyEntity.getY());
        double motionZ = targetPos.getZ() - (nearbyEntity.getZ());
        Vec3 vector3d = new Vec3(motionX, motionY, motionZ).scale(PULL_IN_SPEED_FACTOR);

        nearbyEntity.setDeltaMovement(vector3d);
        PROXY.spawnParticles(nearbyEntity, particleType);
    }

    public static void chainNearbyEntities(LivingEntity attacker, LivingEntity target, float distance, int timeMultiplier) {
        PROXY.spawnParticles(target, ParticleTypes.PORTAL);
        MobEffectInstance chained = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * timeMultiplier, 5);
        target.addEffect(chained);
        applyToNearbyEntities(target, distance,
                getCanApplyToSecondEnemyPredicate(attacker, target),
                (LivingEntity nearbyEntity) -> {
                    double motionX = target.getX() - (nearbyEntity.getX());
                    double motionY = target.getY() - (nearbyEntity.getY());
                    double motionZ = target.getZ() - (nearbyEntity.getZ());
                    Vec3 vector3d = new Vec3(motionX, motionY, motionZ).scale(PULL_IN_SPEED_FACTOR);

                    nearbyEntity.setDeltaMovement(vector3d);
                    nearbyEntity.addEffect(chained);
                    PROXY.spawnParticles(nearbyEntity, ParticleTypes.PORTAL);
                }
        );
    }

    public static void healNearbyAllies(LivingEntity healer, MobEffectInstance potionEffect, float distance) {
        Player playerentity = healer instanceof Player ? (Player) healer : null;
        applyToNearbyEntities(healer, distance,
                getCanHealPredicate(healer),
                (LivingEntity nearbyEntity) -> {
                    if (nearbyEntity.getHealth() < nearbyEntity.getMaxHealth()) {
                        if (potionEffect.getEffect().isInstantenous()) {
                            potionEffect.getEffect().applyInstantenousEffect(playerentity, playerentity, nearbyEntity, potionEffect.getAmplifier(), 1.0D);
                        } else {
                            nearbyEntity.addEffect(new MobEffectInstance(potionEffect));
                        }
                        PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
                    }
                }
        );
    }

    public static void healNearbyAllies(LivingEntity healer, float amount, float distance) {
        applyToNearbyEntities(healer, distance,
                getCanHealPredicate(healer),
                (LivingEntity nearbyEntity) -> {
                    if (nearbyEntity.getHealth() < nearbyEntity.getMaxHealth()) {
                        nearbyEntity.heal(amount);
                        PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
                    }
                }
        );
    }

    public static LivingEntity findMostInjuredAlly(LivingEntity healer, float distance) {
        Level world = healer.getCommandSenderWorld();
        List<LivingEntity> nearbyEntities = getNearbyEnemies(healer,distance, world, getCanHealPredicate(healer));
        if (!nearbyEntities.isEmpty()) {
            float lostHealth = 0;
            LivingEntity mostInjuredAlly = null;
            for (LivingEntity injure : nearbyEntities) {
                if (mostInjuredAlly == null || injure.getMaxHealth() - injure.getHealth() > lostHealth) {
                    mostInjuredAlly = injure;
                    lostHealth = injure.getMaxHealth() - injure.getHealth();
                }
            }
            return mostInjuredAlly;
        } else return null;
    }

    public static void causeShockwave(LivingEntity attacker, LivingEntity target, float damageAmount, float distance) {
        DamageSource shockwave = DamageSource.explosion(attacker);
        Vec3 vec1 = target.position();
        Vec3 vec2 = attacker.position();
        applyToNearbyEntities(target, distance,
                (nearbyEntity) -> isFacingEntity(attacker, nearbyEntity, vec1.subtract(vec2), 60) && getCanApplyToSecondEnemyPredicate(attacker, target).test(nearbyEntity), (LivingEntity nearbyEntity) -> nearbyEntity.hurt(shockwave, damageAmount)
        );
    }

    public static void causeExplosionAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance) {
        DamageSource explosion = DamageSource.explosion(attacker);
        applyToNearbyEntities(target, distance,
                getCanApplyToEnemyPredicate(attacker), (LivingEntity nearbyEntity) -> nearbyEntity.hurt(explosion, damageAmount)
        );
    }

    public static void causeMagicExplosionAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance) {
        DamageSource magicExplosion = DamageSource.explosion(attacker).bypassArmor().setMagic();
        applyToNearbyEntities(target, distance,
                getCanApplyToEnemyPredicate(attacker), (LivingEntity nearbyEntity) -> {
                    nearbyEntity.hurt(magicExplosion, damageAmount);
                }
        );
    }

    public static void burnNearbyEnemies(LivingEntity attacker, float damage, float distance) {
        applyToNearbyEntities(attacker, distance,
                getCanApplyToEnemyPredicate(attacker), (LivingEntity nearbyEntity) -> {
                    nearbyEntity.hurt(DamageSource.ON_FIRE, damage);
                    PROXY.spawnParticles(nearbyEntity, ParticleTypes.FLAME);
                }
        );
    }

    public static void setNearbyEnemiesOnFire(LivingEntity attacker, float distance, int duration) {
        applyToNearbyEntities(attacker, distance,
                getCanApplyToEnemyPredicate(attacker), (LivingEntity nearbyEntity) -> {
                    nearbyEntity.setSecondsOnFire(duration);
                }
        );
    }

    public static void freezeNearbyEnemies(LivingEntity attacker, int amplifier, float distance, int durationInSeconds) {
        applyToNearbyEntities(attacker, distance,
                getCanApplyToEnemyPredicate(attacker), (LivingEntity nearbyEntity) -> {
                    freezeEnemy(amplifier, nearbyEntity, durationInSeconds);
                }
        );
    }

    public static void freezeEnemy(int amplifier, LivingEntity nearbyEntity, int durationInSeconds) {
        MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, durationInSeconds * 20, amplifier);
        MobEffectInstance fatigue = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, durationInSeconds * 20, Math.max(0, amplifier * 2 - 1));
        nearbyEntity.addEffect(slowness);
        nearbyEntity.addEffect(fatigue);
        PROXY.spawnParticles(nearbyEntity, ParticleTypes.ITEM_SNOWBALL);
    }

    public static void causeExplosionAttackAtPos(LivingEntity attacker, boolean inGround, BlockPos blockPos, float damageAmount, float distance) {
        Level world = attacker.getCommandSenderWorld();
        DamageSource explosion = DamageSource.explosion(attacker);
        BlockPos origin = blockPos;
        if(inGround) {
            origin = origin.above();
        }
        applyToNearbyEntitiesAtPos(origin, world, distance,
                getCanApplyToEnemyPredicate(attacker),
                (LivingEntity nearbyEntity) -> nearbyEntity.hurt(explosion, damageAmount)
        );
    }

    public static void causeSwirlingAttack(Player attacker, LivingEntity target, float damageAmount, float distance) {
        DamageSource swirling = DamageSource.playerAttack(attacker);
        applyToNearbyEntities(attacker, distance,
                getCanApplyToSecondEnemyPredicate(attacker, target), (LivingEntity nearbyEntity) -> {
                    nearbyEntity.hurt(swirling, damageAmount);
                }
        );
    }

    public static void causeEchoAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance, final int echoLevel) {
        applyToNearbyEntities(target, distance, echoLevel,
                getCanApplyToSecondEnemyPredicate(attacker, target),
                (LivingEntity nearbyEntity) -> {
                    DamageSource echo = DamageSource.mobAttack(attacker);
                    if (attacker instanceof Player) {
                        echo = DamageSource.playerAttack((Player) attacker);
                    }
                    nearbyEntity.hurt(echo, damageAmount);
                });
    }

    public static void createVisualLightningBoltOnEntity(Entity target) {
        Level world = target.getCommandSenderWorld();
        LightningBolt lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
        if (lightningboltentity != null) {
            lightningboltentity.moveTo(target.getX(), target.getY(), target.getZ());
            lightningboltentity.setVisualOnly(true);
            world.addFreshEntity(lightningboltentity);
        }
    }

    public static void electrify(LivingEntity attacker, LivingEntity victim, float damageAmount) {
        createVisualLightningBoltOnEntity(victim);
        ElectricShockDamageSource lightning = (ElectricShockDamageSource) new ElectricShockDamageSource(attacker).setMagic().bypassArmor();
        PROXY.spawnParticles(victim, ParticleInit.ELECTRIC_SHOCK.get());
        victim.hurt(lightning, damageAmount);
    }

    public static void levitate(int amplifier, LivingEntity nearbyEntity, int durationInSeconds) {
        MobEffectInstance levitation = new MobEffectInstance(MobEffects.LEVITATION, durationInSeconds * 20, amplifier);
        nearbyEntity.addEffect(levitation);
    }

    public static void electrifyNearbyEnemies(LivingEntity attacker, float distance, float damageAmount, int limit) {
        applyToNearbyEntities(attacker, distance, limit,
                getCanApplyToEnemyPredicate(attacker),
                (LivingEntity nearbyEntity) -> electrify(attacker, nearbyEntity, damageAmount));
    }

    public static void levitateNearbyEnemies(LivingEntity attacker, float distance, int limit, int amplifier, int durationInSeconds) {
        applyToNearbyEntities(attacker, distance, limit,
                getCanApplyToEnemyPredicate(attacker),
                (LivingEntity nearbyEntity) -> levitate(amplifier, nearbyEntity, durationInSeconds));
    }

    public static void electrifyNearbyEnemies(AbstractArrow arrow, float distance, float damageAmount, int limit) {
        Level world = arrow.getCommandSenderWorld();
        Entity shooter = arrow.getOwner();
        if(shooter instanceof LivingEntity){
            LivingEntity livingShooter = (LivingEntity) shooter;
            applyToNearbyEntities(arrow, world, distance, limit,
                    getCanApplyToEnemyPredicate(livingShooter),
                    (LivingEntity nearbyEntity) -> electrify(livingShooter, nearbyEntity, damageAmount));
        }
    }

    public static void poisonAndSlowNearbyEnemies(Level worldIn, Player playerIn, int distance) {
        applyToNearbyEntities(playerIn, worldIn, distance,
                getCanApplyToEnemyPredicate(playerIn),
                (LivingEntity nearbyEntity) -> {
                    MobEffectInstance entangled = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 4);
                    MobEffectInstance poison = new MobEffectInstance(MobEffects.POISON, 140, 1);
                    nearbyEntity.addEffect(entangled);
                    nearbyEntity.addEffect(poison);

                });
    }

    public static void weakenAndMakeNearbyEnemiesVulnerable(Player playerIn, Level world, int distance) {
        applyToNearbyEntities(playerIn, world, distance,
                getCanApplyToEnemyPredicate(playerIn),
                (LivingEntity nearbyEntity) -> {
                    MobEffectInstance weakness = new MobEffectInstance(MobEffects.WEAKNESS, 140);
                    MobEffectInstance vulnerability = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 140, -2);
                    nearbyEntity.addEffect(weakness);
                    nearbyEntity.addEffect(vulnerability);
                });
    }

    public static void stunNearbyEnemies(Level worldIn, Player playerIn, int distance) {
        applyToNearbyEntities(playerIn, distance,
                getCanApplyToEnemyPredicate(playerIn),
                (LivingEntity nearbyEntity) -> {
                    MobEffectInstance stunned = new MobEffectInstance(CustomEffects.STUNNED, 100);
                    MobEffectInstance nausea = new MobEffectInstance(MobEffects.CONFUSION, 100);
                    MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 4);
                    nearbyEntity.addEffect(slowness);
                    nearbyEntity.addEffect(nausea);
                    nearbyEntity.addEffect(stunned);
                });
    }

    public static void knockbackNearbyEnemies(Level worldIn, Player playerIn, int distance) {
        applyToNearbyEntities(playerIn, distance,
                getCanApplyToEnemyPredicate(playerIn),
                (LivingEntity nearbyEntity) -> {
                    // KNOCKBACK
                    float knockbackMultiplier = 3.0F;
                    double xRatio = playerIn.getX() - nearbyEntity.getX();
                    double zRatio;
                    for (zRatio = playerIn.getZ() - nearbyEntity.getZ(); xRatio * xRatio + zRatio * zRatio < 1.0E-4D; zRatio = (Math.random() - Math.random()) * 0.01D) {
                        xRatio = (Math.random() - Math.random()) * 0.01D;
                    }
                    nearbyEntity.hurtDir = (float) (Mth.atan2(zRatio, xRatio) * 57.2957763671875D - (double) nearbyEntity.getYRot());
                    nearbyEntity.knockback(0.4F * knockbackMultiplier, xRatio, zRatio);
                    // END OF KNOCKBACK

                    PROXY.spawnParticles(nearbyEntity, ParticleTypes.CLOUD);

                    MobEffectInstance stun = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4);
                    nearbyEntity.addEffect(stun);
                });
    }
}
