package com.infamous.dungeons_gear.goals;

import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.UUID;


public class GoalUtils {

    public static boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof WolfEntity) {
                WolfEntity wolfentity = (WolfEntity)target;
                if (wolfentity.isTame() && wolfentity.getOwner() == owner) {
                    return false;
                }
            } else if(MinionMasterHelper.isMinionEntity(target)){
                return !MinionMasterHelper.isMinionOf(target, owner.getUUID());
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).canHarmPlayer((PlayerEntity)target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTamed()) {
                return false;
            } else {
                return !(target instanceof CatEntity) || !((CatEntity)target).isTame();
            }
        } else {
            return false;
        }
    }
}
