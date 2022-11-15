package com.infamous.dungeons_gear.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import java.util.EnumSet;

public class ApproachTargetGoal extends MeleeAttackGoal {
	
	public double distanceToApproachTo;
	
	      public ApproachTargetGoal(PathfinderMob attackingMob, double distanceToApproachTo, double moveSpeed, boolean shouldFollowUnseenTarget) {
	         super(attackingMob, moveSpeed, shouldFollowUnseenTarget);
	         this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	         this.distanceToApproachTo = distanceToApproachTo;
	      }
	      
	    @Override
	    public boolean isInterruptable() {
	    	return true;
	    }
	      
	    public boolean canUse() {
	    	return super.canUse() && (this.mob.distanceTo(this.mob.getTarget()) >= distanceToApproachTo || !this.mob.hasLineOfSight(this.mob.getTarget()));
	    }
	    
	    public boolean canContinueToUse() {
	    	return super.canContinueToUse() && (this.mob.distanceTo(this.mob.getTarget()) >= distanceToApproachTo || !this.mob.hasLineOfSight(this.mob.getTarget()));
	    }

	      protected double getAttackReachSqr(LivingEntity p_179512_1_) {
	          return 0;
	       }
	      
	      @Override
	      protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_) {
		            this.resetAttackCooldown();
		      }
	   }