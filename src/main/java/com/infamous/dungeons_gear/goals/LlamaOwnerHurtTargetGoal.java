package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.horse.LlamaEntity;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.*;

public class LlamaOwnerHurtTargetGoal extends TargetGoal {
    private final LlamaEntity llamaEntity;
    private LivingEntity attacker;
    private int timestamp;

    public LlamaOwnerHurtTargetGoal(LlamaEntity llamaEntity) {
        super(llamaEntity, false);
        this.llamaEntity = llamaEntity;
        this.setMutexFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean shouldExecute() {
        if (this.llamaEntity.isTame()) {
            LivingEntity owner = getOwner(this.llamaEntity);
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getLastAttackedEntity();
                int lastAttackedEntityTime = owner.getLastAttackedEntityTime();
                return lastAttackedEntityTime != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
            }
        } else {
            return false;
        }
    }

    public void startExecuting() {
        this.goalOwner.setAttackTarget(this.attacker);
        LivingEntity owner = getOwner(this.llamaEntity);
        if (owner != null) {
            this.timestamp = owner.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}
