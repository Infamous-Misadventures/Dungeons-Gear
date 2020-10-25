package com.infamous.dungeons_gear.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.pathfinding.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;

import static com.infamous.dungeons_gear.goals.GoalUtils.getOwner;

public class SheepFollowOwnerGoal extends Goal {
    private final SheepEntity sheepEntity;
    private LivingEntity owner;
    private final IWorldReader world;
    private final double followSpeed;
    private final PathNavigator navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;
    private final boolean passesThroughLeaves;

    public SheepFollowOwnerGoal(SheepEntity sheepEntity, double followSpeed, float minDist, float maxDist, boolean passesThroughLeaves) {
        this.sheepEntity = sheepEntity;
        this.world = sheepEntity.world;
        this.followSpeed = followSpeed;
        this.navigator = sheepEntity.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.passesThroughLeaves = passesThroughLeaves;
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        if (!(sheepEntity.getNavigator() instanceof GroundPathNavigator) && !(sheepEntity.getNavigator() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        LivingEntity livingentity = getOwner(this.sheepEntity);
        if (livingentity == null) {
            return false;
        } else if (livingentity.isSpectator()) {
            return false;
        } else if (this.sheepEntity.getLeashed()) {
            return false;
        } else if (this.sheepEntity.getDistanceSq(livingentity) < (double)(this.minDist * this.minDist)) {
            return false;
        } else {
            this.owner = livingentity;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if (this.navigator.noPath()) {
            return false;
        } else if (this.sheepEntity.getLeashed()) {
            return false;
        } else {
            return !(this.sheepEntity.getDistanceSq(this.owner) <= (double)(this.maxDist * this.maxDist));
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.sheepEntity.getPathPriority(PathNodeType.WATER);
        this.sheepEntity.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.owner = null;
        this.navigator.clearPath();
        this.sheepEntity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.sheepEntity.getLookController().setLookPositionWithEntity(this.owner, 10.0F, (float)this.sheepEntity.getVerticalFaceSpeed());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.sheepEntity.getLeashed() && !this.sheepEntity.isPassenger()) {
                if (this.sheepEntity.getDistanceSq(this.owner) >= 144.0D) {
                    this.func_226330_g_();
                } else {
                    this.navigator.tryMoveToEntityLiving(this.owner, this.followSpeed);
                }

            }
        }
    }

    private void func_226330_g_() {
        BlockPos blockpos = this.owner.getPosition();

        for(int i = 0; i < 10; ++i) {
            int j = this.func_226327_a_(-3, 3);
            int k = this.func_226327_a_(-1, 1);
            int l = this.func_226327_a_(-3, 3);
            boolean flag = this.func_226328_a_(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }

    }

    private boolean func_226328_a_(int p_226328_1_, int p_226328_2_, int p_226328_3_) {
        if (Math.abs((double)p_226328_1_ - this.owner.getPosX()) < 2.0D && Math.abs((double)p_226328_3_ - this.owner.getPosZ()) < 2.0D) {
            return false;
        } else if (!this.func_226329_a_(new BlockPos(p_226328_1_, p_226328_2_, p_226328_3_))) {
            return false;
        } else {
            this.sheepEntity.setLocationAndAngles((double)p_226328_1_ + 0.5D, (double)p_226328_2_, (double)p_226328_3_ + 0.5D, this.sheepEntity.rotationYaw, this.sheepEntity.rotationPitch);
            this.navigator.clearPath();
            return true;
        }
    }

    private boolean func_226329_a_(BlockPos p_226329_1_) {
        PathNodeType pathnodetype = WalkNodeProcessor.func_237231_a_(this.world, p_226329_1_.toMutable());
        if (pathnodetype != PathNodeType.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.world.getBlockState(p_226329_1_.down());
            if (!this.passesThroughLeaves && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = p_226329_1_.subtract(this.sheepEntity.getPosition());
                return this.world.hasNoCollisions(this.sheepEntity, this.sheepEntity.getBoundingBox().offset(blockpos));
            }
        }
    }

    private int func_226327_a_(int p_226327_1_, int p_226327_2_) {
        return this.sheepEntity.getRNG().nextInt(p_226327_2_ - p_226327_1_ + 1) + p_226327_1_;
    }
}
