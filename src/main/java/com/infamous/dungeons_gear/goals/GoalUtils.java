package com.infamous.dungeons_gear.goals;

import com.infamous.dungeons_gear.capabilities.summoning.ISummonable;
import com.infamous.dungeons_gear.capabilities.summoning.SummonableProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.UUID;


public class GoalUtils {

    @Nullable
    public static LivingEntity getOwner(AbstractHorseEntity abstractHorseEntity) {
        try {
            UUID ownerUniqueId = abstractHorseEntity.getOwnerUniqueId();
            return ownerUniqueId == null ? null : abstractHorseEntity.world.getPlayerByUuid(ownerUniqueId);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Nullable
    public static LivingEntity getOwner(IronGolemEntity ironGolemEntity) {
        try {
            ISummonable summonable = ironGolemEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonable.getSummoner() != null){
                UUID ownerUniqueId = summonable.getSummoner();
                return ownerUniqueId == null ? null : ironGolemEntity.world.getPlayerByUuid(ownerUniqueId);
            }
            else return null;
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Nullable
    public static LivingEntity getOwner(BatEntity batEntity) {
        try {
            ISummonable summonable = batEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonable.getSummoner() != null){
                UUID ownerUniqueId = summonable.getSummoner();
                return ownerUniqueId == null ? null : batEntity.world.getPlayerByUuid(ownerUniqueId);
            }
            else return null;
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Nullable
    public static LivingEntity getOwner(BeeEntity beeEntity) {
        try {
            ISummonable summonable = beeEntity.getCapability(SummonableProvider.SUMMONABLE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if(summonable.getSummoner() != null){
                UUID ownerUniqueId = summonable.getSummoner();
                return ownerUniqueId == null ? null : beeEntity.world.getPlayerByUuid(ownerUniqueId);
            }
            else return null;
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public static boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof WolfEntity) {
                WolfEntity wolfentity = (WolfEntity)target;
                if (wolfentity.isTamed() && wolfentity.getOwner() == owner) {
                    return false;
                }
            }
            if (target instanceof IronGolemEntity) {
                IronGolemEntity ironGolemEntity = (IronGolemEntity)target;
                if (ironGolemEntity.isPlayerCreated() && getOwner(ironGolemEntity) == owner) {
                    return false;
                }
            }
            if (target instanceof LlamaEntity) {
                LlamaEntity llamaEntity = (LlamaEntity)target;
                if (llamaEntity.isTame() && getOwner(llamaEntity) == owner) {
                    return false;
                }
            }
            if (target instanceof BatEntity) {
                BatEntity llamaEntity = (BatEntity)target;
                if (getOwner(llamaEntity) == owner) {
                    return false;
                }
            }
            if (target instanceof BeeEntity) {
                BeeEntity llamaEntity = (BeeEntity)target;
                if (getOwner(llamaEntity) == owner) {
                    return false;
                }
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).canAttackPlayer((PlayerEntity)target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame()) {
                return false;
            } else {
                return !(target instanceof CatEntity) || !((CatEntity)target).isTamed();
            }
        } else {
            return false;
        }
    }
}
