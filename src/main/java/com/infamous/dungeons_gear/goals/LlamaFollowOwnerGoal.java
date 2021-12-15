package com.infamous.dungeons_gear.goals;

import com.infamous.dungeons_libraries.capabilities.summoning.MinionMasterHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.*;

public class LlamaFollowOwnerGoal extends Goal {
    private final LlamaEntity llamaEntity;
    private LivingEntity owner;
    private final IWorldReader world;
    private final double followSpeed;
    private final PathNavigator navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;
    private final boolean passesThroughLeaves;

    public LlamaFollowOwnerGoal(LlamaEntity llamaEntity, double followSpeed, float minDist, float maxDist, boolean passesThroughLeaves) {
        this.llamaEntity = llamaEntity;
        this.world = llamaEntity.level;
        this.followSpeed = followSpeed;
        this.navigator = llamaEntity.getNavigation();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.passesThroughLeaves = passesThroughLeaves;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(llamaEntity.getNavigation() instanceof GroundPathNavigator) && !(llamaEntity.getNavigation() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        LivingEntity livingentity = MinionMasterHelper.getMaster(this.llamaEntity);
        if (livingentity == null) {
            return false;
        } else if (livingentity.isSpectator()) {
            return false;
        } else if (this.llamaEntity.isLeashed()) {
            return false;
        } else if (this.llamaEntity.distanceToSqr(livingentity) < (double)(this.minDist * this.minDist)) {
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
        } else if (this.llamaEntity.isLeashed()) {
            return false;
        } else {
            return !(this.llamaEntity.distanceToSqr(this.owner) <= (double)(this.maxDist * this.maxDist));
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.llamaEntity.getPathfindingMalus(PathNodeType.WATER);
        this.llamaEntity.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.owner = null;
        this.navigator.stop();
        this.llamaEntity.setPathfindingMalus(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.llamaEntity.getLookControl().setLookAt(this.owner, 10.0F, (float)this.llamaEntity.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.llamaEntity.isLeashed() && !this.llamaEntity.isPassenger()) {
                if (this.llamaEntity.distanceToSqr(this.owner) >= 144.0D) {
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
            this.llamaEntity.moveTo((double)p_226328_1_ + 0.5D, (double)p_226328_2_, (double)p_226328_3_ + 0.5D, this.llamaEntity.yRot, this.llamaEntity.xRot);
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
                BlockPos blockpos = p_226329_1_.subtract(this.llamaEntity.blockPosition());
                return this.world.noCollision(this.llamaEntity, this.llamaEntity.getBoundingBox().move(blockpos));
            }
        }
    }

    private int randomIntInclusive(int p_226327_1_, int p_226327_2_) {
        return this.llamaEntity.getRandom().nextInt(p_226327_2_ - p_226327_1_ + 1) + p_226327_1_;
    }
}
