package com.infamous.dungeons_gear.goals;

import com.infamous.dungeons_libraries.capabilities.summoning.MinionMasterHelper;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.IronGolemEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.*;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class GolemOwnerHurtTargetGoal extends TargetGoal {
    private final IronGolemEntity ironGolemEntity;
    private LivingEntity attacker;
    private int timestamp;

    public GolemOwnerHurtTargetGoal(IronGolemEntity ironGolemEntity) {
        super(ironGolemEntity, false);
        this.ironGolemEntity = ironGolemEntity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        if (this.ironGolemEntity.isPlayerCreated()) {
            LivingEntity owner = MinionMasterHelper.getMaster(this.ironGolemEntity);
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getLastHurtMob();
                int lastAttackedEntityTime = owner.getLastHurtMobTimestamp();
                return lastAttackedEntityTime != this.timestamp && this.canAttack(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity owner = MinionMasterHelper.getMaster(this.ironGolemEntity);
        if (owner != null) {
            this.timestamp = owner.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
