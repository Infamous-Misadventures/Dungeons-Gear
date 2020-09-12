package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

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
