package com.infamous.dungeons_gear.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class ModdedEntityPredicate extends net.minecraft.entity.EntityPredicate {
    public static final net.minecraft.entity.EntityPredicate DEFAULT = new net.minecraft.entity.EntityPredicate();
    private double distance = -1.0D;
    private boolean allowInvulnerable;
    private boolean friendlyFire;
    private boolean requireLineOfSight;
    private boolean skipAttackChecks;
    private boolean useVisibilityModifier = true;
    private Predicate<LivingEntity> customPredicate;

    public ModdedEntityPredicate() {
    }

    public net.minecraft.entity.EntityPredicate setDistance(double p_221013_1_) {
        this.distance = p_221013_1_;
        return this;
    }

    public net.minecraft.entity.EntityPredicate allowInvulnerable() {
        this.allowInvulnerable = true;
        return this;
    }

    public net.minecraft.entity.EntityPredicate allowFriendlyFire() {
        // changed to allow friendly fire
        this.friendlyFire = true;
        return this;
    }

    public net.minecraft.entity.EntityPredicate setLineOfSiteRequired() {
        this.requireLineOfSight = true;
        return this;
    }

    public net.minecraft.entity.EntityPredicate setSkipAttackChecks() {
        this.skipAttackChecks = true;
        return this;
    }

    public net.minecraft.entity.EntityPredicate setUseInvisibilityCheck() {
        this.useVisibilityModifier = false;
        return this;
    }

    public net.minecraft.entity.EntityPredicate setCustomPredicate(@Nullable Predicate<LivingEntity> p_221012_1_) {
        this.customPredicate = p_221012_1_;
        return this;
    }

    public boolean canTarget(@Nullable LivingEntity goalOwner, LivingEntity targetEntity) {
        if (goalOwner == targetEntity) {
            return false;
        } else if (targetEntity.isSpectator()) {
            return false;
        } else if (!targetEntity.isAlive()) {
            return false;
        } else if (!this.allowInvulnerable && targetEntity.isInvulnerable()) {
            return false;
        } else if (this.customPredicate != null && !this.customPredicate.test(targetEntity)) {
            return false;
        } else {
            if (goalOwner != null) {
                if (!this.skipAttackChecks) {
                    if (!goalOwner.canAttack(targetEntity)) {
                        return false;
                    }

                    if (!goalOwner.canAttack(targetEntity.getType())) {
                        return false;
                    }
                }

                if (!this.friendlyFire && goalOwner.isOnSameTeam(targetEntity)) {
                    return false;
                }

                if (this.distance > 0.0D) {
                    double visibilityModifier = this.useVisibilityModifier ? targetEntity.getVisibilityMultiplier(goalOwner) : 1.0D;
                    double distanceModified = this.distance * visibilityModifier;
                    double squareDistanceTo = goalOwner.getDistanceSq(targetEntity.getPosX(), targetEntity.getPosY(), targetEntity.getPosZ());
                    if (squareDistanceTo > distanceModified * distanceModified) {
                        return false;
                    }
                }

                if (!this.requireLineOfSight && goalOwner instanceof MobEntity && !((MobEntity)goalOwner).getEntitySenses().canSee(targetEntity)) {
                    return false;
                }
            }

            return true;
        }
    }
}
