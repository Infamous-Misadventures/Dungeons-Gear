package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.init.ParticleInit;
import com.infamous.dungeons_gear.ranged.crossbows.AbstractDungeonsCrossbowItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.*;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.items.RangedWeaponList.FERAL_SOUL_CROSSBOW;
import static net.minecraft.entity.Entity.horizontalMag;

public class AbilityUtils {

    public static void summonOrTeleportBat(PlayerEntity playerEntity, World world) {
        ISummoner summonerCap = playerEntity.getCapability(SummonerProvider.SUMMONER_CAPABILITY).orElseThrow(IllegalStateException::new);

        if(summonerCap.getSummonedBat() == null){
            BatEntity batEntity = EntityType.BAT.create(world);
            if (batEntity!= null) {
                batEntity.setLocationAndAngles((double)playerEntity.getPosX() + playerEntity.getEyeHeight(), (double)playerEntity.getPosY() + playerEntity.getEyeHeight(), (double)playerEntity.getPosZ() + playerEntity.getEyeHeight(), 0.0F, 0.0F);

                batEntity.goalSelector.addGoal(1, new BatFollowOwnerGoal(batEntity, 2.1D, 10.0F, 2.0F, false));
                batEntity.goalSelector.addGoal(2, new BatMeleeAttackGoal(batEntity, 1.0D, true));


                batEntity.targetSelector.addGoal(1, new BatOwnerHurtByTargetGoal(batEntity));
                batEntity.targetSelector.addGoal(2, new BatOwnerHurtTargetGoal(batEntity));
                batEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(batEntity, LivingEntity.class, 5, false, false, (entityIterator) -> {
                    return entityIterator instanceof IMob && !(entityIterator instanceof CreeperEntity);
                }));

                world.playSound((PlayerEntity)null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_BAT_AMBIENT, SoundCategory.AMBIENT, 64.0F, 1.0F);
                world.addEntity(batEntity);
                ISummonable summonable = batEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);

                summonable.setSummoner(playerEntity.getUniqueID());

                summonerCap.setSummonedBat(batEntity.getUniqueID());
            }
        } else{
            Entity entity = ((ServerWorld)world).getEntityByUuid(summonerCap.getSummonedBat());
            if(entity instanceof BatEntity){
                BatEntity batEntity = (BatEntity) entity;
                batEntity.teleportKeepLoaded(playerEntity.getPosX() + playerEntity.getEyeHeight(), playerEntity.getPosY() + playerEntity.getEyeHeight(), playerEntity.getPosZ() + playerEntity.getEyeHeight());
            }
        }
    }

    public static void teleportOnHit(LivingEntity livingEntity){
        World world = livingEntity.getEntityWorld();
        if (!world.isRemote) {
            double lvt_5_1_ = livingEntity.getPosX();
            double lvt_7_1_ = livingEntity.getPosY();
            double lvt_9_1_ = livingEntity.getPosZ();

            for(int i = 0; i < 16; ++i) {
                double teleportX = livingEntity.getPosX() + (livingEntity.getRNG().nextDouble() - 0.5D) * 16.0D;
                double teleportY = MathHelper.clamp(livingEntity.getPosY() + (double)(livingEntity.getRNG().nextInt(16) - 8), 0.0D, (double)(world.func_234938_ad_() - 1));
                double teleportZ = livingEntity.getPosZ() + (livingEntity.getRNG().nextDouble() - 0.5D) * 16.0D;
                if (livingEntity.isPassenger()) {
                    livingEntity.stopRiding();
                }

                if (livingEntity.attemptTeleport(teleportX, teleportY, teleportZ, true)) {
                    SoundEvent lvt_18_1_ = livingEntity instanceof FoxEntity ? SoundEvents.field_232710_ez_ : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    world.playSound((PlayerEntity)null, lvt_5_1_, lvt_7_1_, lvt_9_1_, lvt_18_1_, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    livingEntity.playSound(lvt_18_1_, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }

    public static void stealSpeedFromTarget(LivingEntity attacker, LivingEntity target, int amplifer){
        EffectInstance speed = new EffectInstance(Effects.SPEED, 80, amplifer);
        EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 80, amplifer);
        attacker.addPotionEffect(speed);
        target.addPotionEffect(slowness);
    }

    public static void makePetsAttackTarget(LivingEntity target, LivingEntity owner){
        List<LivingEntity> nearbyEntities = owner.getEntityWorld().getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(owner.getPosX() - 12, owner.getPosY() - 12, owner.getPosZ() - 12,
                owner.getPosX() + 12, owner.getPosY() + 12, owner.getPosZ() + 12), (nearbyEntity) -> {
            return nearbyEntity != owner && isPetOfAttacker(owner, nearbyEntity) && nearbyEntity.isAlive();
        });
        for(LivingEntity nearbyEntity : nearbyEntities){
            if(nearbyEntity instanceof TameableEntity){
                TameableEntity tameableEntity = (TameableEntity) nearbyEntity;
                tameableEntity.setAttackTarget(target);
            }
            if(nearbyEntity instanceof LlamaEntity){
                LlamaEntity llamaEntity = (LlamaEntity) nearbyEntity;
                llamaEntity.setAttackTarget(target);
            }
            if(nearbyEntity instanceof IronGolemEntity){
                IronGolemEntity ironGolemEntity = (IronGolemEntity) nearbyEntity;
                ironGolemEntity.setAttackTarget(target);
            }
        }
    }

    public static boolean isPetOfAttacker(LivingEntity possibleOwner, LivingEntity possiblePet){
        if(possiblePet instanceof TameableEntity){
            TameableEntity pet = (TameableEntity) possiblePet;
            return pet.getOwner() == possibleOwner;
        }
        if(possiblePet instanceof AbstractHorseEntity){
            AbstractHorseEntity abstractHorse = (AbstractHorseEntity) possiblePet;
            return GoalUtils.getOwner(abstractHorse) == possibleOwner;
        }
        if(possiblePet instanceof IronGolemEntity){
            IronGolemEntity ironGolem = (IronGolemEntity) possiblePet;
            return GoalUtils.getOwner(ironGolem) == possibleOwner;
        }
        return false;
    }



    public static void pullInNearbyEntities(LivingEntity attacker, LivingEntity target, float distance){
        World world = target.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(target.getPosX() - distance, target.getPosY() - distance, target.getPosZ() - distance,
                target.getPosX() + distance, target.getPosY() + distance, target.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != target && nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        PROXY.spawnParticles(target, ParticleTypes.PORTAL);
        for(LivingEntity nearbyEntity : nearbyEntities){
            double motionX = target.getPosX() - (nearbyEntity.getPosX());
            double motionY = target.getPosY() - (nearbyEntity.getPosY());
            double motionZ = target.getPosZ() - (nearbyEntity.getPosZ());
            Vector3d vector3d = new Vector3d(motionX, motionY, motionZ);

            nearbyEntity.setMotion(vector3d);
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.PORTAL);
        }
    }

    public static void healNearbyAllies(LivingEntity healer, EffectInstance potionEffect, float distance){
        World world = healer.getEntityWorld();
        PlayerEntity playerentity = healer instanceof PlayerEntity ? (PlayerEntity)healer : null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(healer.getPosX() - distance, healer.getPosY() - distance, healer.getPosZ() - distance,
                healer.getPosX() + distance, healer.getPosY() + distance, healer.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != healer
                    && (isPetOfAttacker(healer, nearbyEntity)
                    || (nearbyEntity instanceof AbstractVillagerEntity)
                    || healer.isOnSameTeam(nearbyEntity))
                    && nearbyEntity.isAlive();
        });
        for(LivingEntity nearbyEntity : nearbyEntities){
            if(nearbyEntity.getHealth() < nearbyEntity.getMaxHealth()){
                if (potionEffect.getPotion().isInstant()) {
                    potionEffect.getPotion().affectEntity(playerentity, playerentity, nearbyEntity, potionEffect.getAmplifier(), 1.0D);
                } else {
                    nearbyEntity.addPotionEffect(new EffectInstance(potionEffect));
                }
                PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
            }
        }
    }

    public static void healNearbyAllies(LivingEntity healer, float amount, float distance){
        World world = healer.getEntityWorld();
        PlayerEntity playerentity = healer instanceof PlayerEntity ? (PlayerEntity)healer : null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(healer.getPosX() - distance, healer.getPosY() - distance, healer.getPosZ() - distance,
                healer.getPosX() + distance, healer.getPosY() + distance, healer.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != healer
                    && (isPetOfAttacker(healer, nearbyEntity)
                    || (nearbyEntity instanceof AbstractVillagerEntity)
                    || healer.isOnSameTeam(nearbyEntity))
                    && nearbyEntity.isAlive();
        });
        for(LivingEntity nearbyEntity : nearbyEntities){
            if(nearbyEntity.getHealth() < nearbyEntity.getMaxHealth()){
                nearbyEntity.heal(amount);
                PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
            }
        }
    }

    public static void pullInNearbyEntitiesAtPos(LivingEntity attacker, BlockPos blockPos, int distance){
        World world = attacker.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockPos.getX() - distance, blockPos.getY() - distance, blockPos.getZ() - distance,
                blockPos.getX() + distance, blockPos.getY() + distance, blockPos.getZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        for(LivingEntity nearbyEntity : nearbyEntities){
            double motionX = blockPos.getX() - (nearbyEntity.getPosX());
            double motionY = blockPos.getY() - (nearbyEntity.getPosY());
            double motionZ = blockPos.getZ() - (nearbyEntity.getPosZ());
            Vector3d vector3d = new Vector3d(motionX, motionY, motionZ);

            nearbyEntity.setMotion(vector3d);
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.PORTAL);
        }
    }

    public static void chainNearbyEntities(LivingEntity attacker, LivingEntity target, float distance, int timeMultiplier){
        World world = target.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(target.getPosX() - distance, target.getPosY() - distance, target.getPosZ() - distance,
                target.getPosX() + distance, target.getPosY() + distance, target.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != target && nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        PROXY.spawnParticles(target, ParticleTypes.PORTAL);
        EffectInstance chained = new EffectInstance(Effects.SLOWNESS, 20 * timeMultiplier, 5);
        target.addPotionEffect(chained);
        for(LivingEntity nearbyEntity : nearbyEntities){

            double motionX = target.getPosX() - (nearbyEntity.getPosX());
            double motionY = target.getPosY() - (nearbyEntity.getPosY());
            double motionZ = target.getPosZ() - (nearbyEntity.getPosZ());
            Vector3d vector3d = new Vector3d(motionX, motionY, motionZ);

            nearbyEntity.setMotion(vector3d);
            nearbyEntity.addPotionEffect(chained);
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.PORTAL);
        }
    }

    public static void weakenNearbyEntities(LivingEntity attacker, LivingEntity target, int distance, int amplifier){
        World world = target.getEntityWorld();
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(target.getPosX() - distance, target.getPosY() - distance, target.getPosZ() - distance,
                target.getPosX() + distance, target.getPosY() + distance, target.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != target && nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        for(LivingEntity nearbyEntity : nearbyEntities){
            EffectInstance weakness = new EffectInstance(Effects.WEAKNESS, 100, amplifier);
            nearbyEntity.addPotionEffect(weakness);
        }
    }

    public static void setProjectileTowards(ProjectileEntity projectileEntity, double x, double y, double z, float inaccuracy){
        Random random = new Random();
        Vector3d vector3d = (new Vector3d(x, y, z))
                .normalize()
                .add(random.nextGaussian() * (double)0.0075F * (double)inaccuracy,
                        random.nextGaussian() * (double)0.0075F * (double)inaccuracy,
                        random.nextGaussian() * (double)0.0075F * (double)inaccuracy);
        projectileEntity.setMotion(vector3d);
        float f = MathHelper.sqrt(horizontalMag(vector3d));
        projectileEntity.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
        projectileEntity.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
        projectileEntity.prevRotationYaw = projectileEntity.rotationYaw;
        projectileEntity.prevRotationPitch = projectileEntity.rotationPitch;
    }

    public static void ricochetArrowTowardsOtherEntity(LivingEntity attacker, LivingEntity victim, AbstractArrowEntity arrowEntity, int distance){
        World world = attacker.getEntityWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(victim.getPosX() - distance, victim.getPosY() - distance, victim.getPosZ() - distance,
                victim.getPosX() + distance, victim.getPosY() + distance, victim.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker
                    && nearbyEntity != victim
                    && !isPetOfAttacker(attacker, nearbyEntity)
                    && nearbyEntity.isAlive()
                    && victim.canEntityBeSeen(nearbyEntity) && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.getDistanceSq(victim)));
        LivingEntity target =  nearbyEntities.get(0);
        if(target != null){
            byte pierceLevel = arrowEntity.getPierceLevel();
            pierceLevel++;
            arrowEntity.setPierceLevel(pierceLevel);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getPosX() - victim.getPosX();
            double towardsZ = target.getPosZ() - victim.getPosZ();
            double euclideanDist = (double)MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
            double towardsY = target.getPosYHeight(0.3333333333333333D) - arrowEntity.getPosY() + euclideanDist * (double)0.2F;
            setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
            //
        }
    }

    public static void fireBonusShotTowardsOtherEntity(LivingEntity attacker, int distance, double bonusShotDamageMultiplier, float arrowVelocity){
        World world = attacker.getEntityWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(attacker.getPosX() - distance, attacker.getPosY() - distance, attacker.getPosZ() - distance,
                attacker.getPosX() + distance, attacker.getPosY() + distance, attacker.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker
                    && !isPetOfAttacker(attacker, nearbyEntity)
                    && nearbyEntity.isAlive()
                    && attacker.canEntityBeSeen(nearbyEntity) && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.size() < 2) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.getDistanceSq(attacker)));
        LivingEntity target =  nearbyEntities.get(0);
        if(target != null){
            ArrowItem arrowItem = (ArrowItem)((ArrowItem)(Items.ARROW));
            AbstractArrowEntity arrowEntity = arrowItem.createArrow(world, new ItemStack(Items.ARROW), attacker);
            arrowEntity.setDamage(arrowEntity.getDamage() * bonusShotDamageMultiplier);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getPosX() - attacker.getPosX();
            double towardsZ = target.getPosZ() - attacker.getPosZ();
            double euclideanDist = (double)MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
            double towardsY = target.getPosYHeight(0.3333333333333333D) - arrowEntity.getPosY() + euclideanDist * (double)0.2F;
            arrowEntity.func_234612_a_(attacker, attacker.rotationPitch, attacker.rotationYaw, 0.0F, arrowVelocity * 3.0F, 1.0F);
            setProjectileTowards(arrowEntity, towardsX, towardsY, towardsZ, 0);
            //
            arrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            arrowEntity.addTag("BonusProjectile");
            attacker.world.addEntity(arrowEntity);
        }
    }

    public static void fireSnowballAtNearbyEnemy(LivingEntity attacker, int distance){
        World world = attacker.getEntityWorld();
        //boolean nullListFlag = arrowEntity.hitEntities == null;
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(attacker.getPosX() - distance, attacker.getPosY() - distance, attacker.getPosZ() - distance,
                attacker.getPosX() + distance, attacker.getPosY() + distance, attacker.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker
                    && !isPetOfAttacker(attacker, nearbyEntity)
                    && nearbyEntity.isAlive()
                    && attacker.canEntityBeSeen(nearbyEntity) && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.size() < 2) return;
        nearbyEntities.sort(Comparator.comparingDouble(livingEntity -> livingEntity.getDistanceSq(attacker)));
        LivingEntity target =  nearbyEntities.get(0);
        if(target != null){
            SnowballEntity snowballEntity = new SnowballEntity(world, attacker);
            // borrowed from AbstractSkeletonEntity
            double towardsX = target.getPosX() - attacker.getPosX();
            double towardsZ = target.getPosZ() - attacker.getPosZ();
            double euclideanDist = (double)MathHelper.sqrt(towardsX * towardsX + towardsZ * towardsZ);
            double towardsY = target.getPosYHeight(0.3333333333333333D) - snowballEntity.getPosY() + euclideanDist * (double)0.2F;
            snowballEntity.func_234612_a_(attacker, attacker.rotationPitch, attacker.rotationYaw, 0.0F, 1.5F, 1.0F);
            setProjectileTowards(snowballEntity, towardsX, towardsY, towardsZ, 0);
            //
            attacker.world.addEntity(snowballEntity);
        }
    }

    public static void causeShockwave(LivingEntity attacker, LivingEntity target, float damageAmount, float distance){
        World world = target.getEntityWorld();
        DamageSource shockwave = DamageSource.causeExplosionDamage(attacker);

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(target.getPosX() - distance, target.getPosY() - distance, target.getPosZ() - distance,
                target.getPosX() + distance, target.getPosY() + distance, target.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != target && nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        for(LivingEntity nearbyEntity : nearbyEntities){
            nearbyEntity.attackEntityFrom(shockwave, damageAmount);
        }
    }

    public static void causeExplosionAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance){
        World world = target.getEntityWorld();
        DamageSource explosion = DamageSource.causeExplosionDamage(attacker);

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(target.getPosX() - distance, target.getPosY() - distance, target.getPosZ() - distance,
                target.getPosX() + distance, target.getPosY() + distance, target.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        for(LivingEntity nearbyEntity : nearbyEntities){
            nearbyEntity.attackEntityFrom(explosion, damageAmount);
        }
    }

    public static void causeMagicExplosionAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance){
        World world = target.getEntityWorld();
        DamageSource magicExplosion = DamageSource.causeExplosionDamage(attacker).setDamageBypassesArmor().setMagicDamage();

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(target.getPosX() - distance, target.getPosY() - distance, target.getPosZ() - distance,
                target.getPosX() + distance, target.getPosY() + distance, target.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        for(LivingEntity nearbyEntity : nearbyEntities){
            nearbyEntity.attackEntityFrom(magicExplosion, damageAmount);
        }
    }

    public static void burnNearbyEnemies(LivingEntity attacker, float damage, float distance){
        World world = attacker.getEntityWorld();

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(attacker.getPosX() - distance, attacker.getPosY() - distance, attacker.getPosZ() - distance,
                attacker.getPosX() + distance, attacker.getPosY() + distance, attacker.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        for(LivingEntity nearbyEntity : nearbyEntities){
            nearbyEntity.attackEntityFrom(DamageSource.ON_FIRE, damage);
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.FLAME);
        }
    }

    public static void freezeNearbyEnemies(LivingEntity attacker, int amplifier, float distance){
        World world = attacker.getEntityWorld();

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(attacker.getPosX() - distance, attacker.getPosY() - distance, attacker.getPosZ() - distance,
                attacker.getPosX() + distance, attacker.getPosY() + distance, attacker.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        for(LivingEntity nearbyEntity : nearbyEntities){
            EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 20, amplifier);
            EffectInstance fatigue = new EffectInstance(Effects.MINING_FATIGUE, 20, amplifier * 2 - 1);
            nearbyEntity.addPotionEffect(slowness);
            nearbyEntity.addPotionEffect(fatigue);
            PROXY.spawnParticles(nearbyEntity, ParticleTypes.ITEM_SNOWBALL);
        }
    }

    public static void causeExplosionAttackAtPos(LivingEntity attacker, boolean arrow, BlockPos blockPos, float damageAmount, float distance){
        int inGroundMitigator = arrow ? 1 : 0;
        World world = attacker.getEntityWorld();
        DamageSource explosion;
        explosion = DamageSource.causeExplosionDamage(attacker);

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockPos.getX() - distance, blockPos.getY() + inGroundMitigator - distance, blockPos.getZ() - distance,
                blockPos.getX() + distance, blockPos.getY() + inGroundMitigator + distance, blockPos.getZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        for(LivingEntity nearbyEntity : nearbyEntities){
            nearbyEntity.attackEntityFrom(explosion, damageAmount);
        }
    }

    public static void causeSwirlingAttack(PlayerEntity attacker, LivingEntity target, float damageAmount, float distance){
        World world = target.getEntityWorld();
        DamageSource swirling = DamageSource.causePlayerDamage((PlayerEntity) attacker);

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(attacker.getPosX() - distance, attacker.getPosY() - distance, attacker.getPosZ() - distance,
                attacker.getPosX() + distance, attacker.getPosY() + distance, attacker.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != target && nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        for(LivingEntity nearbyEntity : nearbyEntities){
            nearbyEntity.attackEntityFrom(swirling, damageAmount);
        }
    }

    public static void causeEchoAttack(LivingEntity attacker, LivingEntity target, float damageAmount, float distance){
        World world = target.getEntityWorld();
        DamageSource echo = DamageSource.causeMobDamage(attacker);
        if(attacker instanceof PlayerEntity){
            echo = DamageSource.causePlayerDamage((PlayerEntity) attacker);
        }
        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(target.getPosX() - distance, target.getPosY() - distance, target.getPosZ() - distance,
                target.getPosX() + distance, target.getPosY() + distance, target.getPosZ() + distance), (nearbyEntity) -> nearbyEntity != target && nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity));
        if(nearbyEntities.isEmpty()) return;
        for(LivingEntity nearbyEntity : nearbyEntities){
            if(nearbyEntity == null) return;
            nearbyEntity.attackEntityFrom(echo, damageAmount);
            return;
        }
    }

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

    public static void ricochetArrowLikeShield(AbstractArrowEntity arrowEntity, LivingEntity entity){
        //int k = entity.getFireTimer();
        // set fire timer
        //entity.func_241209_g_(k);
        arrowEntity.setMotion(arrowEntity.getMotion().scale(-0.1D));
        arrowEntity.rotationYaw += 180.0F;
        arrowEntity.prevRotationYaw += 180.0F;
        if (!arrowEntity.world.isRemote && arrowEntity.getMotion().lengthSquared() < 1.0E-7D) {
            if (arrowEntity.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
                // arrowEntity.getArrowStack() => new ItemStack(Items.ARROW)
                arrowEntity.entityDropItem(new ItemStack(Items.ARROW), 0.1F);
            }

            arrowEntity.remove();
        }
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

    public static void electrify(LivingEntity attacker, LivingEntity victim, float damageAmount){
        ElectricShockDamageSource lightning = (ElectricShockDamageSource) new ElectricShockDamageSource(attacker).setMagicDamage().setDamageBypassesArmor();
        PROXY.spawnParticles(victim, ParticleInit.ELECTRIC_SHOCK.get());
        victim.attackEntityFrom(lightning, damageAmount);
    }

    public static void electrifyNearbyEnemies(LivingEntity attacker, float distance, float damageAmount, int limit){
        World world = attacker.getEntityWorld();

        List<LivingEntity> nearbyEntities = world.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(attacker.getPosX() - distance, attacker.getPosY() - distance, attacker.getPosZ() - distance,
                attacker.getPosX() + distance, attacker.getPosY() + distance, attacker.getPosZ() + distance), (nearbyEntity) -> {
            return nearbyEntity != attacker && !isPetOfAttacker(attacker, nearbyEntity) && nearbyEntity.isAlive() && !(nearbyEntity instanceof AbstractVillagerEntity);
        });
        if(nearbyEntities.isEmpty()) return;
        if(limit > nearbyEntities.size()) limit = nearbyEntities.size();
        attacker.world.playSound((PlayerEntity)null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 64.0F, 1.0F);
        attacker.world.playSound((PlayerEntity)null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.WEATHER, 64.0F, 1.0F);
        for(int i = 0; i < limit; i++){
            if(nearbyEntities.size() >= i + 1){
                LivingEntity nearbyEntity = nearbyEntities.get(i);
                //castLightningBolt(attacker, nearbyEntity);
                electrify(attacker, nearbyEntity, damageAmount);
            }
        }
    }

    public static void sendIntoWildRage(MobEntity mobEntity){
        mobEntity.targetSelector.addGoal(0, new WildRageAttackGoal(mobEntity));
        PROXY.spawnParticles(mobEntity, ParticleTypes.ANGRY_VILLAGER);
    }

    private static AbstractArrowEntity createChainReactionProjectile(World world, LivingEntity attacker, ItemStack ammoStack, AbstractArrowEntity originalArrow) {
        ArrowItem arrowItem = (ArrowItem)((ArrowItem)(ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW));
        AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(world, ammoStack, attacker);
        if (attacker instanceof PlayerEntity) {
            abstractArrowEntity.setIsCritical(true);
        }

        abstractArrowEntity.setHitSound(SoundEvents.ITEM_CROSSBOW_HIT);
        abstractArrowEntity.setShotFromCrossbow(true);
        abstractArrowEntity.addTag("ChainReactionProjectile");
        Set<String> originalArrowTags = originalArrow.getTags();
        for(String tag : originalArrowTags){
            abstractArrowEntity.addTag(tag);
        }
        return abstractArrowEntity;
    }

    public static void fireChainReactionProjectiles(World world, LivingEntity attacker, LivingEntity victim, float v, float v1, AbstractArrowEntity originalArrow) {
        float[] randomSoundPitches = AbstractDungeonsCrossbowItem.getRandomSoundPitches(victim.getRNG());
        for(int i = 0; i < 4; ++i) {
            ItemStack currentProjectile = new ItemStack(Items.ARROW);
            if (!currentProjectile.isEmpty()) {
                if (i == 0) {
                    fireChainReactionProjectileFromVictim(world, attacker, victim,  currentProjectile, randomSoundPitches[1], v, v1, 45.0F, originalArrow);
                } else if (i == 1) {
                    fireChainReactionProjectileFromVictim(world, attacker,  victim, currentProjectile, randomSoundPitches[2], v, v1, -45.0F, originalArrow);
                } else if (i == 2) {
                    fireChainReactionProjectileFromVictim(world, attacker,  victim, currentProjectile, randomSoundPitches[1], v, v1, 135.0F, originalArrow);
                } else if (i == 3) {
                    fireChainReactionProjectileFromVictim(world, attacker,  victim, currentProjectile, randomSoundPitches[2], v, v1, -135.0F, originalArrow);
                }
            }
        }
    }

    private static void fireChainReactionProjectileFromVictim(World world, LivingEntity attacker, LivingEntity victim, ItemStack projectileStack, float soundPitch, float v1, float v2, float centerOffset, AbstractArrowEntity originalArrow) {
        if (!world.isRemote) {
            AbstractArrowEntity projectile;
            projectile = createChainReactionProjectile(world, attacker, projectileStack, originalArrow);
            projectile.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
            Vector3d upVector = victim.getUpVector(1.0F);
            Quaternion quaternion = new Quaternion(new Vector3f(upVector), centerOffset, true);
            Vector3d lookVector = victim.getLook(1.0F);
            Vector3f vector3f = new Vector3f(lookVector);
            vector3f.transform(quaternion);
            projectile.shoot((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ(), v1, v2);
            world.addEntity(projectile);
            world.playSound((PlayerEntity)null, victim.getPosX(), victim.getPosY(), victim.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
        }
    }

    public static boolean soulsCriticalBoost(PlayerEntity attacker, ItemStack mainhand){
        int numSouls = Math.min(attacker.experienceTotal, 50);
        boolean uniqueWeaponFlag = mainhand.getItem() == FERAL_SOUL_CROSSBOW;
        if(EnchantUtils.hasEnchantment(mainhand, MeleeRangedEnchantmentList.ENIGMA_RESONATOR)){
            int enigmaResonatorLevel = EnchantmentHelper.getEnchantmentLevel(MeleeRangedEnchantmentList.ENIGMA_RESONATOR, mainhand);
            float soulsCriticalBoostChanceCap = 0;
            if(enigmaResonatorLevel == 1) soulsCriticalBoostChanceCap = 0.15F;
            if(enigmaResonatorLevel == 2) soulsCriticalBoostChanceCap = 0.2F;
            if(enigmaResonatorLevel == 3) soulsCriticalBoostChanceCap = 0.25F;
            float soulsCriticalBoostRand = attacker.getRNG().nextFloat();
            if(soulsCriticalBoostRand <= Math.min(numSouls/50.0, soulsCriticalBoostChanceCap)){
                return true;
            }
        }
        if(uniqueWeaponFlag){
            float soulsCriticalBoostRand = attacker.getRNG().nextFloat();
            return soulsCriticalBoostRand <= Math.min(numSouls / 50.0, 0.15F);
        }
        return false;
    }
}
