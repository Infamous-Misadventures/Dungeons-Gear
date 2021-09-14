package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.SheepEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.capabilities.summoning.SummoningHelper.getSummoner;
import static com.infamous.dungeons_gear.goals.GoalUtils.shouldAttackEntity;

public class SheepOwnerHurtByTargetGoal extends TargetGoal {
    private final SheepEntity sheepEntity;
    private LivingEntity attacker;
    private int timestamp;

    public SheepOwnerHurtByTargetGoal(SheepEntity sheepEntity) {
        super(sheepEntity, false);
        this.sheepEntity = sheepEntity;
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean shouldExecute() {
        //if (this.batEntity.isTame()) {
            LivingEntity owner = getSummoner(this.sheepEntity);
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getRevengeTarget();
                int revengeTimer = owner.getRevengeTimer();
                return revengeTimer != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
            }
        //} else {
        //    return false;
        //}
    }

    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.attacker);
        LivingEntity owner = getSummoner(this.sheepEntity);
        if (owner != null) {
            this.timestamp = owner.getRevengeTimer();
        }

        super.startExecuting();
    }
}
