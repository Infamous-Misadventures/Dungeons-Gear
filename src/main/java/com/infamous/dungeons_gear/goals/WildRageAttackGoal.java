package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.VindicatorEntity;

public class WildRageAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
    public WildRageAttackGoal(MobEntity mob) {
        super(mob, LivingEntity.class, 0, true, true, LivingEntity::attackable);
    }

    public boolean shouldExecute() {
        return (super.shouldExecute());
    }

    public void startExecuting() {
        super.startExecuting();
        this.goalOwner.setIdleTime(0);
    }
}
