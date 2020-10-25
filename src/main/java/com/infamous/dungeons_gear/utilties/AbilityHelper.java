package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.artifacts.fallingblocks.SummonedFallingBlockEntity;
import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.ISummoner;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import com.infamous.dungeons_gear.capabilities.summoning.SummonerProvider;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.damagesources.ElectricShockDamageSource;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.goals.*;
import com.infamous.dungeons_gear.init.ParticleInit;
import com.infamous.dungeons_gear.ranged.crossbows.AbstractDungeonsCrossbowItem;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class AbilityHelper {

    public static void stealSpeedFromTarget(LivingEntity attacker, LivingEntity target, int amplifer){
        EffectInstance speed = new EffectInstance(Effects.SPEED, 80, amplifer);
        EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, 80, amplifer);
        attacker.addPotionEffect(speed);
        target.addPotionEffect(slowness);
    }

    public static void makeNearbyPetsAttackTarget(LivingEntity target, LivingEntity owner){
        List<LivingEntity> nearbyEntities = owner.getEntityWorld().getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(owner.getPosX() - 12, owner.getPosY() - 12, owner.getPosZ() - 12,
                owner.getPosX() + 12, owner.getPosY() + 12, owner.getPosZ() + 12), (nearbyEntity) -> {
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
        return false;
    }


    private static boolean isNotAVillager(LivingEntity nearbyEntity) {
        return !(nearbyEntity instanceof AbstractVillagerEntity);
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
            return DungeonsGearConfig.COMMON.ENABLE_AREA_OF_EFFECT_ON_PLAYERS.get();
        }
    }

    public static boolean canHealEntity(LivingEntity healer, LivingEntity nearbyEntity) {
        return nearbyEntity != healer
                && isAlly(healer, nearbyEntity)
                && isAliveAndCanBeSeen(nearbyEntity, healer);
    }

    private static boolean isAlly(LivingEntity healer, LivingEntity nearbyEntity) {
        return isPetOfAttacker(healer, nearbyEntity)
        || (nearbyEntity instanceof AbstractVillagerEntity)
        || healer.isOnSameTeam(nearbyEntity);
    }

    private static boolean isAliveAndCanBeSeen(LivingEntity nearbyEntity, LivingEntity attacker) {
        return nearbyEntity.isAlive() && attacker.canEntityBeSeen(nearbyEntity);
    }

    public static boolean canBeAppliedToEntity(LivingEntity attacker, LivingEntity target, LivingEntity nearbyEntity) {
        return isNotTheTargetOrAttacker(attacker, target, nearbyEntity)
                && !isPetOfAttacker(attacker, nearbyEntity)
                && isAliveAndCanBeSeen(nearbyEntity, attacker)
                && isNotAVillager(nearbyEntity)
                && isNotAPlayerOrCanApplyToPlayers(nearbyEntity);
    }

    public static boolean canBeAppliedToEntity(LivingEntity attacker, LivingEntity nearbyEntity) {
        return nearbyEntity != attacker
                && !isPetOfAttacker(attacker, nearbyEntity)
                && isAliveAndCanBeSeen(nearbyEntity, attacker)
                && isNotAVillager(nearbyEntity)
                && isNotAPlayerOrCanApplyToPlayers(nearbyEntity);
    }

    public static void sendIntoWildRage(MobEntity mobEntity){
        mobEntity.targetSelector.addGoal(0, new WildRageAttackGoal(mobEntity));
        PROXY.spawnParticles(mobEntity, ParticleTypes.ANGRY_VILLAGER);
    }

    public static void summonIceBlocks(World world, PlayerEntity itemUseContextPlayer, BlockPos blockPos) {
        for(int i = 0; i < 9; i++){
            double xshift = 0;
            double zshift = 0;

            // positive x shift
            if(i == 1 || i == 2 || i == 8){
                xshift = 1.0D;
            }
            // negative x shift
            if(i == 4 || i == 5 || i == 6){
                xshift = -1.0D;
            }
            // positive z shift
            if(i == 2 || i == 3 || i == 4){
                zshift = 1.0D;
            }
            // negative z shift
            if(i == 6 || i == 7 || i == 8){
                zshift = -1.0D;
            }
            SummonedFallingBlockEntity fallingBlockEntity =
                    new SummonedFallingBlockEntity(world,
                            (double)blockPos.getX() + 0.5D + xshift,
                            (double)blockPos.getY() + 4.0D,
                            (double)blockPos.getZ() + 0.5D + zshift,
                            itemUseContextPlayer,
                            Blocks.ICE.getDefaultState());
            fallingBlockEntity.fallTime = 1;
            fallingBlockEntity.setHurtEntities(true);
            world.addEntity(fallingBlockEntity);
        }
    }

}
