package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.IronGolemEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.getOwner;
import static com.infamous.dungeons_gear.goals.GoalUtils.shouldAttackEntity;

public class BatOwnerHurtTargetGoal extends TargetGoal {
    private final BatEntity batEntity;
    private LivingEntity attacker;
    private int timestamp;

    public BatOwnerHurtTargetGoal(BatEntity batEntity) {
        super(batEntity, false);
        this.batEntity = batEntity;
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean shouldExecute() {
        //if (this.batEntity.isPlayerCreated()) {
            LivingEntity owner = getOwner(this.batEntity);
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getLastAttackedEntity();
                int lastAttackedEntityTime = owner.getLastAttackedEntityTime();
                return lastAttackedEntityTime != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
            }
        //} else {
        //    return false;
        //}
    }

    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.attacker);
        LivingEntity owner = getOwner(this.batEntity);
        if (owner != null) {
            this.timestamp = owner.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}
