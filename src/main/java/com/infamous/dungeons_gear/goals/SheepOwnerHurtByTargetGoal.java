package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.SheepEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMaster;
import static com.infamous.dungeons_gear.goals.GoalUtils.shouldAttackEntity;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class SheepOwnerHurtByTargetGoal extends TargetGoal {
    private final SheepEntity sheepEntity;
    private LivingEntity attacker;
    private int timestamp;

    public SheepOwnerHurtByTargetGoal(SheepEntity sheepEntity) {
        super(sheepEntity, false);
        this.sheepEntity = sheepEntity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        //if (this.batEntity.isTame()) {
            LivingEntity owner = getMaster(this.sheepEntity);
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getLastHurtByMob();
                int revengeTimer = owner.getLastHurtByMobTimestamp();
                return revengeTimer != this.timestamp && this.canAttack(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
            }
        //} else {
        //    return false;
        //}
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity owner = getMaster(this.sheepEntity);
        if (owner != null) {
            this.timestamp = owner.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
