package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.BeeEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_libraries.capabilities.summoning.MinionMasterHelper.getMaster;
import static com.infamous.dungeons_gear.goals.GoalUtils.shouldAttackEntity;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class BeeOwnerHurtTargetGoal extends TargetGoal {
    private final BeeEntity beeEntity;
    private LivingEntity attacker;
    private int timestamp;

    public BeeOwnerHurtTargetGoal(BeeEntity beeEntity) {
        super(beeEntity, false);
        this.beeEntity = beeEntity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        LivingEntity owner = getMaster(this.beeEntity);
        if (owner == null) {
            return false;
        } else {
            this.attacker = owner.getLastHurtMob();
            int lastAttackedEntityTime = owner.getLastHurtMobTimestamp();
            return lastAttackedEntityTime != this.timestamp && this.canAttack(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
        }
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity owner = getMaster(this.beeEntity);
        if (owner != null) {
            this.timestamp = owner.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
