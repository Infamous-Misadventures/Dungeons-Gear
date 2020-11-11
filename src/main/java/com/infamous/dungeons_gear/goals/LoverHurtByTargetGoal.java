package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.shouldAttackEntity;

public class LoverHurtByTargetGoal extends TargetGoal {
    private final MobEntity mobEntity;
    private LivingEntity lover;
    private LivingEntity attacker;
    private int timestamp;

    public LoverHurtByTargetGoal(MobEntity mobEntity, LivingEntity lover) {
        super(mobEntity, false);
        this.mobEntity = mobEntity;
        this.lover = lover;
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean shouldExecute() {
        if (this.lover != null) {
            this.attacker = this.lover.getRevengeTarget();
            int revengeTimer = this.lover.getRevengeTimer();
            return revengeTimer != this.timestamp && this.isSuitableTarget(this.attacker, ModdedEntityPredicate.DEFAULT)
                    && shouldAttackEntity(this.attacker, this.lover);
        } else {
            return false;
        }
    }

    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.attacker);
        if (this.lover != null) {
            this.timestamp = this.lover.getRevengeTimer();
        }

        super.startExecuting();
    }
}
