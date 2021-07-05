package com.infamous.dungeons_gear.goals;

import com.infamous.dungeons_gear.capabilities.summoning.SummoningHelper;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.IronGolemEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.*;

public class GolemOwnerHurtByTargetGoal extends TargetGoal {
    private final IronGolemEntity ironGolemEntity;
    private LivingEntity attacker;
    private int timestamp;

    public GolemOwnerHurtByTargetGoal(IronGolemEntity ironGolemEntity) {
        super(ironGolemEntity, false);
        this.ironGolemEntity = ironGolemEntity;
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean shouldExecute() {
        if (this.ironGolemEntity.isPlayerCreated()) {
            LivingEntity owner = SummoningHelper.getSummoner(this.ironGolemEntity);
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getRevengeTarget();
                int revengeTimer = owner.getRevengeTimer();
                return revengeTimer != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
            }
        } else {
            return false;
        }
    }

    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.attacker);
        LivingEntity owner = SummoningHelper.getSummoner(this.ironGolemEntity);
        if (owner != null) {
            this.timestamp = owner.getRevengeTimer();
        }

        super.startExecuting();
    }
}
