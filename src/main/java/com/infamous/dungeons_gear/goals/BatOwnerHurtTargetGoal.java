package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.BatEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMaster;
import static com.infamous.dungeons_gear.goals.GoalUtils.shouldAttackEntity;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class BatOwnerHurtTargetGoal extends TargetGoal {
    private final BatEntity batEntity;
    private LivingEntity attacker;
    private int timestamp;

    public BatOwnerHurtTargetGoal(BatEntity batEntity) {
        super(batEntity, false);
        this.batEntity = batEntity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        //if (this.batEntity.isPlayerCreated()) {
            LivingEntity owner = getMaster(this.batEntity);
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getLastHurtMob();
                int lastAttackedEntityTime = owner.getLastHurtMobTimestamp();
                return lastAttackedEntityTime != this.timestamp && this.canAttack(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
            }
        //} else {
        //    return false;
        //}
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity owner = getMaster(this.batEntity);
        if (owner != null) {
            this.timestamp = owner.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
