package com.infamous.dungeons_gear.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;

import static com.infamous.dungeons_libraries.capabilities.summoning.SummoningHelper.getSummoner;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class BeeFollowOwnerGoal extends Goal {
    private final BeeEntity beeEntity;
    private LivingEntity owner;
    private final IWorldReader world;
    private final double followSpeed;
    private final PathNavigator navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;
    private final boolean passesThroughLeaves;

    public BeeFollowOwnerGoal(BeeEntity beeEntity, double followSpeed, float minDist, float maxDist, boolean passesThroughLeaves) {
        this.beeEntity = beeEntity;
        this.world = beeEntity.level;
        this.followSpeed = followSpeed;
        this.navigator = beeEntity.getNavigation();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.passesThroughLeaves = passesThroughLeaves;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        if (!(beeEntity.getNavigation() instanceof GroundPathNavigator) && !(beeEntity.getNavigation() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        LivingEntity livingentity = getSummoner(this.beeEntity);
        if (livingentity == null) {
            return false;
        } else if (livingentity.isSpectator()) {
            return false;
        } else if (this.beeEntity.isLeashed()) {
            return false;
        } else if (this.beeEntity.distanceToSqr(livingentity) < (double)(this.minDist * this.minDist)) {
            return false;
        } else {
            this.owner = livingentity;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        if (this.navigator.isDone()) {
            return false;
        } else if (this.beeEntity.isLeashed()) {
            return false;
        } else {
            return !(this.beeEntity.distanceToSqr(this.owner) <= (double)(this.maxDist * this.maxDist));
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.beeEntity.getPathfindingMalus(PathNodeType.WATER);
        this.beeEntity.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.owner = null;
        this.navigator.stop();
        this.beeEntity.setPathfindingMalus(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.beeEntity.getLookControl().setLookAt(this.owner, 10.0F, (float)this.beeEntity.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.beeEntity.isLeashed() && !this.beeEntity.isPassenger()) {
                if (this.beeEntity.distanceToSqr(this.owner) >= 144.0D) {
                    this.teleportToOwner();
                } else {
                    this.navigator.moveTo(this.owner, this.followSpeed);
                }

            }
        }
    }

    private void teleportToOwner() {
        BlockPos blockpos = this.owner.blockPosition();

        for(int i = 0; i < 10; ++i) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }

    }

    private boolean maybeTeleportTo(int p_226328_1_, int p_226328_2_, int p_226328_3_) {
        if (Math.abs((double)p_226328_1_ - this.owner.getX()) < 2.0D && Math.abs((double)p_226328_3_ - this.owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(p_226328_1_, p_226328_2_, p_226328_3_))) {
            return false;
        } else {
            this.beeEntity.moveTo((double)p_226328_1_ + 0.5D, (double)p_226328_2_, (double)p_226328_3_ + 0.5D, this.beeEntity.yRot, this.beeEntity.xRot);
            this.navigator.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos p_226329_1_) {
        PathNodeType pathnodetype = WalkNodeProcessor.getBlockPathTypeStatic(this.world, p_226329_1_.mutable());
        if (pathnodetype != PathNodeType.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.world.getBlockState(p_226329_1_.below());
            if (!this.passesThroughLeaves && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = p_226329_1_.subtract(this.beeEntity.blockPosition());
                return this.world.noCollision(this.beeEntity, this.beeEntity.getBoundingBox().move(blockpos));
            }
        }
    }

    private int randomIntInclusive(int p_226327_1_, int p_226327_2_) {
        return this.beeEntity.getRandom().nextInt(p_226327_2_ - p_226327_1_ + 1) + p_226327_1_;
    }
}
