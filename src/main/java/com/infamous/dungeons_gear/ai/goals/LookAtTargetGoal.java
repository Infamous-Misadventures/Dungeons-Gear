package com.infamous.dungeons_gear.ai.goals;

import java.util.EnumSet;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

public class LookAtTargetGoal extends Goal {
   protected final MobEntity mob;

   public LookAtTargetGoal(MobEntity mob) {
	  this.setFlags(EnumSet.of(Goal.Flag.LOOK));
      this.mob = mob;
   }
   
   @Override
	public boolean isInterruptable() {
		return true;
	}

   public boolean canUse() {
         return mob.getTarget() != null && mob.getTarget().isAlive();
   }

   public boolean canContinueToUse() {
       return mob.getTarget() != null && mob.getTarget().isAlive();
   }

   public void tick() {
      if (mob.getTarget() != null && mob.getTarget().isAlive()) {
         this.mob.getLookControl().setLookAt(mob.getTarget().getX(), mob.getTarget().getEyeY(), mob.getTarget().getZ());
      }
   }
}