package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.*;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class LoverHurtByTargetGoal extends TargetGoal {
    private final MobEntity mobEntity;
    private LivingEntity lover;
    private LivingEntity attacker;
    private int timestamp;

    public LoverHurtByTargetGoal(MobEntity mobEntity, LivingEntity lover) {
        super(mobEntity, false);
        this.mobEntity = mobEntity;
        this.lover = lover;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        if (this.lover != null) {
            this.attacker = this.lover.getLastHurtByMob();
            int revengeTimer = this.lover.getLastHurtByMobTimestamp();
            return revengeTimer != this.timestamp && this.canAttack(this.attacker, ModdedEntityPredicate.DEFAULT)
                    && shouldAttackEntity(this.attacker, this.lover);
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        if (this.lover != null) {
            this.timestamp = this.lover.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
