package com.infamous.dungeons_gear.capabilities.summoning;

import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public class SummoningHelper {

    @Nullable
    public static LivingEntity getOwnerForHorse(AbstractHorseEntity horseEntity){
        try {
            if(horseEntity.getOwnerUniqueId() != null){
                UUID ownerUniqueId = horseEntity.getOwnerUniqueId();
                return ownerUniqueId == null ? null : horseEntity.world.getPlayerByUuid(ownerUniqueId);
            }
            else return null;
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public static boolean isEntitySummonable(Entity target) {
        return target instanceof IronGolemEntity
                || target instanceof WolfEntity
                || target instanceof LlamaEntity
                || target instanceof BatEntity
                || target instanceof BeeEntity
                || target instanceof SheepEntity;
    }

    public static boolean wasSummonedBy(LivingEntity target, UUID ownerUUID) {
        if(isEntitySummonable(target)){
            ISummonable targetSummonableCap = CapabilityHelper.getSummonableCapability(target);
            if(targetSummonableCap == null){
                return false;
            }
            else{
                return targetSummonableCap.getSummoner() != null
                        && targetSummonableCap.getSummoner() == ownerUUID;
            }
        } else{
            return false;
        }
    }

    @Nullable
    public static LivingEntity getSummoner(LivingEntity summonableMob) {
        try {
            ISummonable summonable = CapabilityHelper.getSummonableCapability(summonableMob);
            if(summonable == null) return null;
            if(summonable.getSummoner() != null){
                UUID ownerUniqueId = summonable.getSummoner();
                return ownerUniqueId == null ? null : summonableMob.world.getPlayerByUuid(ownerUniqueId);
            }
            else return null;
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }
}
