package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IronGolemEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.getOwner;
import static com.infamous.dungeons_gear.goals.GoalUtils.shouldAttackEntity;

public class BeeOwnerHurtTargetGoal extends TargetGoal {
    private final BeeEntity beeEntity;
    private LivingEntity attacker;
    private int timestamp;

    public BeeOwnerHurtTargetGoal(BeeEntity beeEntity) {
        super(beeEntity, false);
        this.beeEntity = beeEntity;
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean shouldExecute() {
        LivingEntity owner = getOwner(this.beeEntity);
        if (owner == null) {
            return false;
        } else {
            this.attacker = owner.getLastAttackedEntity();
            int lastAttackedEntityTime = owner.getLastAttackedEntityTime();
            return lastAttackedEntityTime != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
        }
    }

    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.attacker);
        LivingEntity owner = getOwner(this.beeEntity);
        if (owner != null) {
            this.timestamp = owner.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}
