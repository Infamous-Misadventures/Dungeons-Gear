package com.infamous.dungeons_gear.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class WildRageAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
    public WildRageAttackGoal(Mob mob) {
        super(mob, LivingEntity.class, 0, true, true, LivingEntity::attackable);
    }

    public boolean canUse() {
        return (super.canUse());
    }

    public void start() {
        super.start();
        this.mob.setNoActionTime(0);
    }
}
