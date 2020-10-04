package com.infamous.dungeons_gear.artifacts.fallingblocks;

import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.damagesources.SummonedFallingBlockDamageSource;
import com.infamous.dungeons_gear.effects.CustomEffect;
import com.infamous.dungeons_gear.effects.CustomEffects;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SummonedFallingBlockEntity extends FallingBlockEntity {
    private LivingEntity caster;
    private UUID casterUuid;
    private BlockState fallTile;
    private boolean dontSetBlock;
    private boolean hurtEntities;
    private int fallHurtMax;
    private float fallHurtAmount;

    public SummonedFallingBlockEntity(World world) {
        super(EntityType.FALLING_BLOCK, world);
    }

    public SummonedFallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public SummonedFallingBlockEntity(World worldIn, double x, double y, double z, LivingEntity casterIn, BlockState fallingBlockState) {
        super(worldIn, x, y, z, fallingBlockState);
        this.dontSetBlock = true; // TODO: Make a config option for this
        this.fallTile = fallingBlockState;
        this.fallHurtMax = 40;
        this.fallHurtAmount = 2.0F;
        //this.warmupDelayTicks = warmupDelayTicks;
        this.setCaster(casterIn);
        //this.rotationYaw = rotationYawIn * (180F / (float)Math.PI);
        //this.setPosition(x, y, z);
    }

    public void setCaster(@Nullable LivingEntity p_190549_1_) {
        this.caster = p_190549_1_;
        this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.getUniqueID();
    }

    @Nullable
    public LivingEntity getCaster() {
        if (this.caster == null && this.casterUuid != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.world).getEntityByUuid(this.casterUuid);
            if (entity instanceof LivingEntity) {
                this.caster = (LivingEntity)entity;
            }
        }

        return this.caster;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */


    @Override
    protected void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        //this.warmupDelayTicks = compound.getInt("Warmup");
        if (compound.hasUniqueId("Owner")) {
            this.casterUuid = compound.getUniqueId("Owner");
        }

    }



    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.casterUuid != null) {
            compound.putUniqueId("Owner", this.casterUuid);
        }

    }

    /**
     * Called to update the entity's position/logic.
     */

    @Override
    public void tick() {
        if (this.fallTile.isAir()) {
            this.remove();
        } else {
            Block block = this.fallTile.getBlock();
            if (this.fallTime++ == 0) {
                BlockPos blockpos = this.func_233580_cy_();
                if (this.world.getBlockState(blockpos).isIn(block)) {
                    this.world.removeBlock(blockpos, false);
                } else if (!this.world.isRemote) {
                    this.remove();
                    return;
                }
            }

            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MoverType.SELF, this.getMotion());
            if (!this.world.isRemote) {
                BlockPos blockpos1 = this.func_233580_cy_();
                boolean flag = this.fallTile.getBlock() instanceof ConcretePowderBlock;
                boolean flag1 = flag && this.world.getFluidState(blockpos1).isTagged(FluidTags.WATER);
                double d0 = this.getMotion().lengthSquared();
                if (flag && d0 > 1.0D) {
                    BlockRayTraceResult blockraytraceresult = this.world.rayTraceBlocks(new RayTraceContext(new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ), this.getPositionVec(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this));
                    if (blockraytraceresult.getType() != RayTraceResult.Type.MISS && this.world.getFluidState(blockraytraceresult.getPos()).isTagged(FluidTags.WATER)) {
                        blockpos1 = blockraytraceresult.getPos();
                        flag1 = true;
                    }
                }

                if (!this.onGround && !flag1) {
                    if (!this.world.isRemote && (this.fallTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600)) {
                        if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.entityDropItem(block);
                        }

                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.world.getBlockState(blockpos1);
                    this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
                    if (!blockstate.isIn(Blocks.MOVING_PISTON)) {
                        this.remove();
                        if (!this.dontSetBlock) {
                            boolean flag2 = blockstate.isReplaceable(new DirectionalPlaceContext(this.world, blockpos1, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean flag3 = FallingBlock.canFallThrough(this.world.getBlockState(blockpos1.down())) && (!flag || !flag1);
                            boolean flag4 = this.fallTile.isValidPosition(this.world, blockpos1) && !flag3;
                            if (flag2 && flag4) {
                                if (this.fallTile.func_235901_b_(BlockStateProperties.WATERLOGGED) && this.world.getFluidState(blockpos1).getFluid() == Fluids.WATER) {
                                    this.fallTile = this.fallTile.with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                                }

                                if (this.world.setBlockState(blockpos1, this.fallTile, 3)) {
                                    if (block instanceof FallingBlock) {
                                        ((FallingBlock)block).onEndFalling(this.world, blockpos1, this.fallTile, blockstate, this);
                                    }

                                    if (this.tileEntityData != null && this.fallTile.hasTileEntity()) {
                                        TileEntity tileentity = this.world.getTileEntity(blockpos1);
                                        if (tileentity != null) {
                                            CompoundNBT compoundnbt = tileentity.write(new CompoundNBT());

                                            for(String s : this.tileEntityData.keySet()) {
                                                INBT inbt = this.tileEntityData.get(s);
                                                if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
                                                    compoundnbt.put(s, inbt.copy());
                                                }
                                            }

                                            tileentity.func_230337_a_(this.fallTile, compoundnbt);
                                            tileentity.markDirty();
                                        }
                                    }
                                } else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                    this.entityDropItem(block);
                                }
                            } else if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.entityDropItem(block);
                            }
                        } else if (block instanceof FallingBlock) {
                            ((FallingBlock)block).onBroken(this.world, blockpos1, this);
                        }
                    }
                }
            }

            this.setMotion(this.getMotion().scale(0.98D));
        }
    }



    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        if (this.hurtEntities) {
            int distanceFallen = MathHelper.ceil(distance - 1.0F);
            if (distanceFallen > 0) {
                List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox()));
                for(Entity entity : list) {
                    if(entity instanceof LivingEntity){
                        LivingEntity livingEntity = (LivingEntity)entity;
                        damage(livingEntity, distanceFallen);
                    }
                }
            }
        }

        return false;
    }

    private void damage(LivingEntity targetEntity, int distanceFallen) {
        LivingEntity caster = this.getCaster();
        DamageSource summonedFallingBlockDamageSource = new IndirectEntityDamageSource("summonedFallingBlock", this, caster);
        float damageAmount = (float)Math.min(MathHelper.floor((float)distanceFallen * this.fallHurtAmount), this.fallHurtMax);
        if (targetEntity.isAlive() && !targetEntity.isInvulnerable() && targetEntity != caster) {
            if (caster == null) {
                targetEntity.attackEntityFrom(summonedFallingBlockDamageSource, damageAmount);
            } else {
                if (caster.isOnSameTeam(targetEntity)) {
                    return;
                }
                targetEntity.attackEntityFrom(summonedFallingBlockDamageSource, damageAmount);
                // simulate stun
                targetEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 4));
                targetEntity.addPotionEffect(new EffectInstance(CustomEffects.STUNNED, 100));
            }

        }
    }

    @Override
    public void setHurtEntities(boolean b) {
        this.hurtEntities = b;
    }
}
