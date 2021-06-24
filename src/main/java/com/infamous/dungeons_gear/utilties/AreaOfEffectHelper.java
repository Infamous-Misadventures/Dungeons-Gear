package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.goals.LoverHurtByTargetGoal;
import com.infamous.dungeons_gear.goals.LoverHurtTargetGoal;
import com.infamous.dungeons_gear.init.ParticleInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.utilties.AbilityHelper.canApplyToEnemy;
import static com.infamous.dungeons_gear.utilties.AbilityHelper.isPetOfAttacker;

public class AreaOfEffectHelper {

    public static final double PULL_IN_SPEED_FACTOR = 0.15;
    public static final Random RANDOM = new Random();


    public static boolean applyElementalEffectsToNearbyEnemies(PlayerEntity playerIn, int limit, float distance) {
        World world = playerIn.getEntityWorld();

        List<LivingEntity> nearbyEnemies = getNearbyEnemies(playerIn, distance, world);
        if (nearbyEnemies.isEmpty()) return false;
        if (limit > nearbyEnemies.size()) limit = nearbyEnemies.size();
        for (int i = 0; i < limit; i++) {
            if (nearbyEnemies.size() >= i + 1) {
                LivingEntity nearbyEntity = nearbyEnemies.get(i);
                int randomEffectId = RANDOM.nextInt(4);
                switch (randomEffectId){
                    case 1:
                        electrify(playerIn, nearbyEntity, 5);
                    case 2:
                        freezeEnemy(0, nearbyEntity, 8);
                    case 3:
                        nearbyEntity.setFire(8);
                }
            }
        }
        return true;
    }

    public static void pullInNearbyEntities(LivingEntity attacker, LivingEntity target, float distance, BasicParticleType particleType) {
        World world = target.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, target, nearbyEntity));
        PROXY.spawnParticles(target, ParticleTypes.PORTAL);
        for (LivingEntity nearbyEntity : nearbyEntities) {
            pullVicitimTowardsTarget(target, nearbyEntity, particleType);
        }
    }

    public static void pullVicitimTowardsTarget(LivingEntity target, LivingEntity nearbyEntity, BasicParticleType particleType) {
        double motionX = target.getPosX() - (nearbyEntity.getPosX());
        double motionY = target.getPosY() - (nearbyEntity.getPosY());
        double motionZ = target.getPosZ() - (nearbyEntity.getPosZ());
        Vector3d vector3d = new Vector3d(motionX, motionY, motionZ).scale(PULL_IN_SPEED_FACTOR);

        nearbyEntity.setMotion(vector3d);
        PROXY.spawnParticles(nearbyEntity, particleType);
    }

    public static void pullInNearbyEntitiesAtPos(LivingEntity attacker, BlockPos blockPos, int distance, BasicParticleType particleType) {
        World world = attacker.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockPos).grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        for (LivingEntity nearbyEntity : nearbyEntities) {
            pullVictimTowardsPos(blockPos, nearbyEntity, particleType);
        }
    }

    private static void pullVictimTowardsPos(BlockPos targetPos, LivingEntity nearbyEntity, BasicParticleType particleType) {
        double motionX = targetPos.getX() - (nearbyEntity.getPosX());
        double motionY = targetPos.getY() - (nearbyEntity.getPosY());
        double motionZ = targetPos.getZ() - (nearbyEntity.getPosZ());
        Vector3d vector3d = new Vector3d(motionX, motionY, motionZ).scale(PULL_IN_SPEED_FACTOR);

        nearbyEntity.setMotion(vector3d);
        PROXY.spawnParticles(nearbyEntity, particleType);
    }

    public static void chainNearbyEntities(LivingEntity attacker, LivingEntity target, float distance, int timeMultiplier) {
        World world = target.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, target, nearbyEntity));
        PROXY.spawnParticles(target, ParticleTypes.PORTAL);
        EffectInstance chained = new EffectInstance(Effects.SLOWNESS, 20 * timeMultiplier, 5);
        target.addPotionEffect(chained);
        for (LivingEntity nearbyEntity : nearbyEntities) {

            double motionX = target.getPosX() - (nearbyEntity.getPosX());
            double motionY = target.getPosY() - (nearbyEntity.getPosY());
            double motionZ = target.getPosZ() - (nearbyEntity.getPosZ());
            Vector3d vector3d = new Vector3d(motionX, motionY, motionZ).scale(PULL_IN_SPEED_FACTOR);

            nearbyEntity.setMotion(vector3d);
            nearbyEntity.addPotionEffect(chained);
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.PORTAL);
        }
    }

    public static void healNearbyAllies(LivingEntity healer, EffectInstance potionEffect, float distance) {
        World world = healer.getEntityWorld();
        PlayerEntity playerentity = healer instanceof PlayerEntity ? (PlayerEntity) healer : null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, healer.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canHealEntity(healer, nearbyEntity));
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity.getHealth() < nearbyEntity.getMaxHealth()) {
                if (potionEffect.getPotion().isInstant()) {
                    potionEffect.getPotion().affectEntity(playerentity, playerentity, nearbyEntity, potionEffect.getAmplifier(), 1.0D);
                } else {
                    nearbyEntity.addPotionEffect(new EffectInstance(potionEffect));
                }
                PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
            }
        }
    }

    public static void healNearbyAllies(LivingEntity healer, float amount, float distance) {
        World world = healer.getEntityWorld();
        PlayerEntity playerentity = healer instanceof PlayerEntity ? (PlayerEntity) healer : null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, healer.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canHealEntity(healer, nearbyEntity));
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity.getHealth() < nearbyEntity.getMaxHealth()) {
                nearbyEntity.heal(amount);
                PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
            }
        }
    }

    public static float healMostInjuredAlly(LivingEntity healer, float distance) {
        World world = healer.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, healer.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canHealEntity(healer, nearbyEntity));
        if (!nearbyEntities.isEmpty()) {
            float lostHealth = 0;
            LivingEntity mostInjuredAlly = null;
            for (LivingEntity injure : nearbyEntities) {
                if (mostInjuredAlly == null || injure.getMaxHealth() - injure.getHealth() > lostHealth) {
                    mostInjuredAlly = injure;
                    lostHealth = injure.getMaxHealth() - injure.getHealth();
                }
            }
            float heal = Math.min(lostHealth, Math.min(mostInjuredAlly.getMaxHealth() / 5, CapabilityHelper.getComboCapability(healer).getSouls() * 0.01f));
            mostInjuredAlly.heal(heal);
            PROXY.spawnParticles(mostInjuredAlly, ParticleTypes.HEART);
            return heal;
        } else return 0;
    }

    public static void weakenNearbyEntities(LivingEntity attacker, LivingEntity target, int distance, int amplifier) {
        World world = target.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, target, nearbyEntity));
        for (LivingEntity nearbyEntity : nearbyEntities) {
            EffectInstance weakness = new EffectInstance(Effects.WEAKNESS, 100, amplifier);
            nearbyEntity.addPotionEffect(weakness);
        }
    }

    public static void causeShockwave(LivingEntity attacker, LivingEntity target, float damageAmount, float distance) {
        World world = target.getEntityWorld();
        DamageSource shockwave = DamageSource.causeExplosionDamage(attacker);
        Vector3d vec1 = target.getPositionVec();
        Vector3d vec2 = attacker.getPositionVec();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.isFacingEntity(attacker, nearbyEntity, vec1.subtract(vec2), 60) && AbilityHelper.canApplyToEnemy(attacker, target, nearbyEntity));
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            nearbyEntity.attackEntityFrom(shockwave, damageAmount);
        }
    }

    public static void causeExplosionAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance) {
        World world = target.getEntityWorld();
        DamageSource explosion = DamageSource.causeExplosionDamage(attacker);

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            nearbyEntity.attackEntityFrom(explosion, damageAmount);
        }
    }

    public static void causeMagicExplosionAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance) {
        World world = target.getEntityWorld();
        DamageSource magicExplosion = DamageSource.causeExplosionDamage(attacker).setDamageBypassesArmor().setMagicDamage();

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            nearbyEntity.attackEntityFrom(magicExplosion, damageAmount);
        }
    }

    public static void burnNearbyEnemies(LivingEntity attacker, float damage, float distance) {
        World world = attacker.getEntityWorld();

        List<LivingEntity> nearbyEntities = getNearbyEnemies(attacker, distance, world);
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            nearbyEntity.attackEntityFrom(DamageSource.ON_FIRE, damage);
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.FLAME);
        }
    }

    public static void setNearbyEnemiesOnFire(LivingEntity attacker, float distance, int duration) {
        World world = attacker.getEntityWorld();

        List<LivingEntity> nearbyEntities = getNearbyEnemies(attacker, distance, world);
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            nearbyEntity.setFire(duration);
        }
    }

    public static void freezeNearbyEnemies(LivingEntity attacker, int amplifier, float distance, int durationInSeconds) {
        World world = attacker.getEntityWorld();

        List<LivingEntity> nearbyEntities = getNearbyEnemies(attacker, distance, world);
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            freezeEnemy(amplifier, nearbyEntity, durationInSeconds);
        }
    }

    public static void freezeEnemy(int amplifier, LivingEntity nearbyEntity, int durationInSeconds) {
        EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, durationInSeconds * 20, amplifier);
        EffectInstance fatigue = new EffectInstance(Effects.MINING_FATIGUE, durationInSeconds * 20, Math.max(0, amplifier * 2 - 1));
        nearbyEntity.addPotionEffect(slowness);
        nearbyEntity.addPotionEffect(fatigue);
        PROXY.spawnParticles(nearbyEntity, ParticleTypes.ITEM_SNOWBALL);
    }

    public static void causeExplosionAttackAtPos(LivingEntity attacker, boolean arrow, BlockPos blockPos, float damageAmount, float distance) {
        int inGroundMitigator = arrow ? 1 : 0;
        World world = attacker.getEntityWorld();
        DamageSource explosion;
        explosion = DamageSource.causeExplosionDamage(attacker);

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockPos).grow(distance).offset(0, inGroundMitigator, 0), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            nearbyEntity.attackEntityFrom(explosion, damageAmount);
        }
    }

    public static void causeSwirlingAttack(PlayerEntity attacker, LivingEntity target, float damageAmount, float distance) {
        World world = target.getEntityWorld();
        DamageSource swirling = DamageSource.causePlayerDamage((PlayerEntity) attacker);

        List<LivingEntity> nearbyEntities = getNearbyEnemies(attacker, target, distance, world);
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            nearbyEntity.attackEntityFrom(swirling, damageAmount);
        }
    }

    public static void causeEchoAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance, int echoLevel) {
        World world = target.getEntityWorld();
        DamageSource echo = DamageSource.causeMobDamage(attacker);
        if (attacker instanceof PlayerEntity) {
            echo = DamageSource.causePlayerDamage((PlayerEntity) attacker);
        }
        List<LivingEntity> nearbyEntities = getNearbyEnemies(attacker, target, distance, world);
        if (nearbyEntities.isEmpty()) return;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity == null) return;
            nearbyEntity.attackEntityFrom(echo, damageAmount);
            echoLevel--;
            if (echoLevel <= 0) return;
        }
    }

    public static void createVisualLightningBoltOnEntity(Entity target) {
        World world = target.getEntityWorld();
        LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
        if (lightningboltentity != null) {
            lightningboltentity.moveForced(target.getPosX(), target.getPosY(), target.getPosZ());
            lightningboltentity.setEffectOnly(true);
            world.addEntity(lightningboltentity);
        }
    }

    public static void electrify(LivingEntity attacker, LivingEntity victim, float damageAmount) {
        createVisualLightningBoltOnEntity(victim);
        ElectricShockDamageSource lightning = (ElectricShockDamageSource) new ElectricShockDamageSource(attacker).setMagicDamage().setDamageBypassesArmor();
        PROXY.spawnParticles(victim, ParticleInit.ELECTRIC_SHOCK.get());
        victim.attackEntityFrom(lightning, damageAmount);
    }

    public static void electrifyNearbyEnemies(LivingEntity attacker, float distance, float damageAmount, int limit) {
        World world = attacker.getEntityWorld();

        List<LivingEntity> nearbyEntities = getNearbyEnemies(attacker, distance, world);
        if (nearbyEntities.isEmpty()) return;
        if (limit > nearbyEntities.size()) limit = nearbyEntities.size();
        for (int i = 0; i < limit; i++) {
            if (nearbyEntities.size() >= i + 1) {
                LivingEntity nearbyEntity = nearbyEntities.get(i);
                //castLightningBolt(attacker, nearbyEntity);
                electrify(attacker, nearbyEntity, damageAmount);
            }
        }
    }

    public static void electrifyNearbyEnemies(AbstractArrowEntity arrow, float distance, float damageAmount, int limit) {
        World world = arrow.getEntityWorld();
        Entity shooter = arrow.func_234616_v_();
        if(shooter instanceof LivingEntity){
            LivingEntity livingShooter = (LivingEntity) shooter;
            List<LivingEntity> nearbyEntities = getNearbyEnemies(arrow, distance, world, livingShooter);
            if (nearbyEntities.isEmpty()) return;
            if (limit > nearbyEntities.size()) limit = nearbyEntities.size();
            for (int i = 0; i < limit; i++) {
                if (nearbyEntities.size() >= i + 1) {
                    LivingEntity nearbyEntity = nearbyEntities.get(i);
                    //castLightningBolt(arrow, nearbyEntity);
                    electrify(livingShooter, nearbyEntity, damageAmount);
                }
            }
        }
    }

    public static void poisonAndSlowNearbyEnemies(World worldIn, PlayerEntity playerIn) {
        List<LivingEntity> nearbyEntities = getNearbyEnemies(playerIn, 5, worldIn);
        for (LivingEntity nearbyEntity : nearbyEntities) {

            EffectInstance entangled = new EffectInstance(Effects.SLOWNESS, 140, 4);
            EffectInstance poison = new EffectInstance(Effects.POISON, 140, 1);
            nearbyEntity.addPotionEffect(entangled);
            nearbyEntity.addPotionEffect(poison);

        }
    }

    public static void weakenAndMakeNearbyEnemiesVulnerable(PlayerEntity playerIn, World world) {
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, playerIn.getBoundingBox().grow(5), (nearbyEntity) -> {
            return nearbyEntity != playerIn && !isPetOfAttacker(playerIn, nearbyEntity) && nearbyEntity.isAlive();
        });
        for (LivingEntity nearbyEntity : nearbyEntities) {
            EffectInstance weakness = new EffectInstance(Effects.WEAKNESS, 140);
            EffectInstance vulnerability = new EffectInstance(Effects.RESISTANCE, 140, -2);
            nearbyEntity.addPotionEffect(weakness);
            nearbyEntity.addPotionEffect(vulnerability);
        }
    }

    public static void stunNearbyEnemies(World worldIn, PlayerEntity playerIn) {
        List<LivingEntity> nearbyEntities = worldIn.getLoadedEntitiesWithinAABB(LivingEntity.class, playerIn.getBoundingBox().grow(5), (nearbyEntity) -> {
            return nearbyEntity != playerIn && !isPetOfAttacker(playerIn, nearbyEntity) && nearbyEntity.isAlive();
        });
        for (LivingEntity nearbyEntity : nearbyEntities) {


            EffectInstance stunned = new EffectInstance(CustomEffects.STUNNED, 100);
            EffectInstance nausea = new EffectInstance(Effects.NAUSEA, 100);
            EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 100, 4);
            nearbyEntity.addPotionEffect(slowness);
            nearbyEntity.addPotionEffect(nausea);
            nearbyEntity.addPotionEffect(stunned);

        }
    }

    public static void makeLoversOutOfNearbyEnemies(PlayerEntity playerIn, World world) {
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, playerIn.getBoundingBox().grow(5), (nearbyEntity) -> {
            return nearbyEntity instanceof IMob
                    && !isPetOfAttacker(playerIn, nearbyEntity)
                    && nearbyEntity.isAlive()
                    && nearbyEntity.isNonBoss();
        });

        int maxLovers = 3;
        int loverCount = 0;
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof MonsterEntity) {
                MonsterEntity mobEntity = (MonsterEntity) nearbyEntity;
                PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
                mobEntity.targetSelector.addGoal(0, new LoverHurtByTargetGoal(mobEntity, playerIn));
                mobEntity.targetSelector.addGoal(1, new LoverHurtTargetGoal(mobEntity, playerIn));
                loverCount++;
                if (loverCount == maxLovers) break;
            }
        }
    }

    public static void knockbackNearbyEnemies(World worldIn, PlayerEntity playerIn) {
        List<LivingEntity> nearbyEntities = worldIn.getLoadedEntitiesWithinAABB(LivingEntity.class, playerIn.getBoundingBox().grow(5), (nearbyEntity) -> {
            return nearbyEntity != playerIn && !isPetOfAttacker(playerIn, nearbyEntity) && nearbyEntity.isAlive();
        });

        PROXY.spawnParticles(playerIn, ParticleTypes.CLOUD);
        for (LivingEntity nearbyEntity : nearbyEntities) {


            // KNOCKBACK
            float knockbackMultiplier = 3.0F;
            double xRatio = playerIn.getPosX() - nearbyEntity.getPosX();
            double zRatio;
            for (zRatio = playerIn.getPosZ() - nearbyEntity.getPosZ(); xRatio * xRatio + zRatio * zRatio < 1.0E-4D; zRatio = (Math.random() - Math.random()) * 0.01D) {
                xRatio = (Math.random() - Math.random()) * 0.01D;
            }
            nearbyEntity.attackedAtYaw = (float) (MathHelper.atan2(zRatio, xRatio) * 57.2957763671875D - (double) nearbyEntity.rotationYaw);
            nearbyEntity.applyKnockback(0.4F * knockbackMultiplier, xRatio, zRatio);
            // END OF KNOCKBACK

            PROXY.spawnParticles(nearbyEntity, ParticleTypes.CLOUD);

            EffectInstance stun = new EffectInstance(Effects.SLOWNESS, 60, 4);
            nearbyEntity.addPotionEffect(stun);

        }
    }

    public static List<LivingEntity> getNearbyEnemies(LivingEntity attacker, float distance, World world) {
        return world.getLoadedEntitiesWithinAABB(LivingEntity.class, attacker.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, nearbyEntity));
    }

    public static List<LivingEntity> getNearbyEnemies(PlayerEntity attacker, LivingEntity target, float distance, World world) {
        return world.getLoadedEntitiesWithinAABB(LivingEntity.class, attacker.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, target, nearbyEntity));
    }

    public static List<LivingEntity> getNearbyEnemies(LivingEntity attacker, LivingEntity target, float distance, World world) {
        return world.getLoadedEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(distance),
                (nearbyEntity) -> AbilityHelper.canApplyToEnemy(attacker, target, nearbyEntity));
    }

    public static List<LivingEntity> getNearbyEnemies(AbstractArrowEntity arrow, float distance, World world, LivingEntity livingShooter) {
        return world.getLoadedEntitiesWithinAABB(LivingEntity.class, arrow.getBoundingBox().grow(distance), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(livingShooter, nearbyEntity));
    }
}
