package com.infamous.dungeons_gear.goals;

import com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;


public class GoalUtils {

    public static boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof Creeper) && !(target instanceof Ghast)) {
            if (target instanceof Wolf) {
                Wolf wolfentity = (Wolf) target;
                if (wolfentity.isTame() && wolfentity.getOwner() == owner) {
                    return false;
                }
            } else if (MinionMasterHelper.isMinionEntity(target)) {
                return !MinionMasterHelper.isMinionOf(target, owner);
            }

            if (target instanceof Player && owner instanceof Player && !((Player) owner).canHarmPlayer((Player) target)) {
                return false;
            } else if (target instanceof AbstractHorse && ((AbstractHorse) target).isTamed()) {
                return false;
            } else {
                return !(target instanceof Cat) || !((Cat) target).isTame();
            }
        } else {
            return false;
        }
    }
}
