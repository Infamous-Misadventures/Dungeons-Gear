package com.infamous.dungeons_gear.goals;

import com.infamous.dungeons_libraries.capabilities.summoning.MinionMasterHelper;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

import static com.infamous.dungeons_gear.goals.GoalUtils.*;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class LlamaOwnerHurtByTargetGoal extends TargetGoal {
    private final LlamaEntity llamaEntity;
    private LivingEntity attacker;
    private int timestamp;

    public LlamaOwnerHurtByTargetGoal(LlamaEntity llamaEntity) {
        super(llamaEntity, false);
        this.llamaEntity = llamaEntity;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    public boolean canUse() {
        if (this.llamaEntity.isTamed()) {
            LivingEntity owner = MinionMasterHelper.getMaster(this.llamaEntity);
            if (owner == null) {
                return false;
            } else {
                this.attacker = owner.getLastHurtByMob();
                int revengeTimer = owner.getLastHurtByMobTimestamp();
                return revengeTimer != this.timestamp && this.canAttack(this.attacker, EntityPredicate.DEFAULT) && shouldAttackEntity(this.attacker, owner);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity owner = MinionMasterHelper.getMaster(this.llamaEntity);
        if (owner != null) {
            this.timestamp = owner.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
