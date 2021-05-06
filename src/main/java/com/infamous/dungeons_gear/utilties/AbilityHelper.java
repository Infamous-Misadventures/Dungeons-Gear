package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.goals.GoalUtils;
import com.infamous.dungeons_gear.goals.WildRageAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;

public class AbilityHelper {

    public static void stealSpeedFromTarget(LivingEntity attacker, LivingEntity target, int amplifer){
        EffectInstance speed = new EffectInstance(Effects.SPEED, 80, amplifer);
        EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 80, amplifer);
        attacker.addPotionEffect(speed);
        target.addPotionEffect(slowness);
    }

    public static void makeNearbyPetsAttackTarget(LivingEntity target, LivingEntity owner){
        List<LivingEntity> nearbyEntities = owner.getEntityWorld().getLoadedEntitiesWithinAABB(LivingEntity.class, owner.getBoundingBox().grow(12), (nearbyEntity) -> {
            return canPetAttackEntity(owner, nearbyEntity);
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

    private static boolean canPetAttackEntity(LivingEntity owner, LivingEntity nearbyEntity) {
        return nearbyEntity != owner && isPetOfAttacker(owner, nearbyEntity) && nearbyEntity.isAlive();
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
        if(possiblePet instanceof BatEntity){
            BatEntity batEntity = (BatEntity) possiblePet;
            return GoalUtils.getOwner(batEntity) == possibleOwner;
        }
        if(possiblePet instanceof BeeEntity){
            BeeEntity beeEntity = (BeeEntity) possiblePet;
            return GoalUtils.getOwner(beeEntity) == possibleOwner;
        }
        if(possiblePet instanceof SheepEntity){
            SheepEntity sheepEntity = (SheepEntity) possiblePet;
            return GoalUtils.getOwner(sheepEntity) == possibleOwner;
        }
        return false;
    }


    private static boolean isAVillagerOrIronGolem(LivingEntity nearbyEntity) {
        return (nearbyEntity instanceof AbstractVillagerEntity) || (nearbyEntity instanceof IronGolemEntity);
    }

    private static boolean isNotTheTargetOrAttacker(LivingEntity attacker, LivingEntity target, LivingEntity nearbyEntity) {
        return nearbyEntity != target
                && nearbyEntity != attacker;
    }

    private static boolean isNotAPlayerOrCanApplyToPlayers(LivingEntity nearbyEntity) {
        if(!(nearbyEntity instanceof PlayerEntity)){
            return true;
        }
        else{
            return DungeonsGearConfig.ENABLE_AREA_OF_EFFECT_ON_PLAYERS.get();
        }
    }

    public static boolean canHealEntity(LivingEntity healer, LivingEntity nearbyEntity) {
        return nearbyEntity != healer
                && isAlly(healer, nearbyEntity)
                && isAliveAndCanBeSeen(nearbyEntity, healer);
    }

    public static boolean isAlly(LivingEntity healer, LivingEntity nearbyEntity) {
        return isPetOfAttacker(healer, nearbyEntity)
        || isAVillagerOrIronGolem(nearbyEntity)
        || healer.isOnSameTeam(nearbyEntity);
    }

    private static boolean isEntityBlacklisted(LivingEntity entity){
        return (ForgeRegistries.ENTITIES.getKey(entity.getType()) != null && DungeonsGearConfig.ENEMY_BLACKLIST.get().contains(ForgeRegistries.ENTITIES.getKey(entity.getType()).toString()));
    }

    private static boolean isAliveAndCanBeSeen(LivingEntity nearbyEntity, LivingEntity attacker) {
        return nearbyEntity.isAlive() && attacker.canEntityBeSeen(nearbyEntity);
    }

    public static boolean canApplyToEnemy(LivingEntity attacker, LivingEntity target, LivingEntity nearbyEntity) {
        return isNotTheTargetOrAttacker(attacker, target, nearbyEntity)
                && isAliveAndCanBeSeen(nearbyEntity, attacker)
                && !isAlly(attacker, nearbyEntity)
                && isNotAPlayerOrCanApplyToPlayers(nearbyEntity)
                && !isEntityBlacklisted(nearbyEntity);
    }

    public static boolean canApplyToEnemy(LivingEntity attacker, LivingEntity nearbyEntity) {
        return nearbyEntity != attacker
                && isAliveAndCanBeSeen(nearbyEntity, attacker)
                && !isAlly(attacker, nearbyEntity)
                && isNotAPlayerOrCanApplyToPlayers(nearbyEntity)
                && !isEntityBlacklisted(nearbyEntity);
    }

    public static void sendIntoWildRage(MobEntity mobEntity){
        mobEntity.targetSelector.addGoal(0, new WildRageAttackGoal(mobEntity));
        PROXY.spawnParticles(mobEntity, ParticleTypes.ANGRY_VILLAGER);
    }

}
