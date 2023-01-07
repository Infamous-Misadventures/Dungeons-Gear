package com.infamous.dungeons_gear.entities;

import com.infamous.dungeons_gear.registry.DamageSourceInit;
import com.infamous.dungeons_gear.registry.EntityTypeInit;
import com.infamous.dungeons_gear.registry.ParticleInit;
import com.infamous.dungeons_gear.registry.SoundEventInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Predicate;

public class IceCloudEntity extends Entity implements IAnimatable {

    private static final Predicate<Entity> ALIVE = (p_213685_0_) -> {
        return p_213685_0_.isAlive();
    };

    public LivingEntity target;
    public Entity owner;

    private final AnimationFactory factory = new AnimationFactory(this);

    public int formAnimationTick;
    public int formAnimationLength = 40;

    public int fallAnimationTick;
    public int fallAnimationLength = 17;

    public int landAnimationTick;
    public int landAnimationLength = 22;

    public int lifeTime;
    public boolean falling;
    public boolean hasFormed;

    public int soundLoopTick;

    public IceCloudEntity(Level world) {
        super(EntityTypeInit.ICE_CLOUD.get(), world);
        this.blocksBuilding = true;
    }

    public IceCloudEntity(EntityType<? extends IceCloudEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
        this.blocksBuilding = true;
    }

    public boolean isAttackable() {
        return false;
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean displayFireAnimation() {
        return false;
    }

    protected float getRandomPitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    public void handleEntityEvent(byte p_28844_) {
        if (p_28844_ == 1) {
            this.formAnimationTick = formAnimationLength;
        } else if (p_28844_ == 2) {
            this.fallAnimationTick = fallAnimationLength;
        } else if (p_28844_ == 3) {
            this.landAnimationTick = landAnimationLength;
        } else if (p_28844_ == 4) {
            this.falling = true;
        } else if (p_28844_ == 5) {
            for (int i = 0; i < 50; ++i) {
                double d0 = this.random.nextGaussian() * 0.3D;
                double d1 = this.random.nextGaussian() * 0.2D;
                double d2 = this.random.nextGaussian() * 0.3D;
                this.level.addParticle(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), d0, d1, d2);
            }
        } else if (p_28844_ == 6) {
            this.hasFormed = true;
        } else if (p_28844_ == 7) {
            this.target = null;
        } else {
            super.handleEntityEvent(p_28844_);
        }
    }

    public static void spawn(Entity summoningEntity, LivingEntity target) {
        IceCloudEntity iceChunk = EntityTypeInit.ICE_CLOUD.get().create(summoningEntity.level);
        iceChunk.moveTo(target.getX(), target.getY() + target.getBbHeight() + 3, target.getZ());
        iceChunk.target = target;
        iceChunk.owner = summoningEntity;
        iceChunk.playSound(SoundEventInit.ICE_CHUNK_SUMMONED.get(), 1.0F, iceChunk.getRandomPitch());
        summoningEntity.level.addFreshEntity(iceChunk);
    }

    public void moveToTarget() {
        if (this.target != null && !this.target.isDeadOrDying() && this.distanceToIgnoringY(target) > 1) {
            this.setDeltaMovement(0.0D, 0.0D, 0.0D);

            double x = this.target.getX() - this.getX();
            double y = this.target.getY() + this.target.getBbHeight() + 3 - this.getY();
            double z = this.target.getZ() - this.getZ();
            double d = Math.sqrt(x * x + y * y + z * z);
            this.setDeltaMovement(this.getDeltaMovement()
                    .add(x / d * 0.20000000298023224D, y / d * 0.20000000298023224D, z / d * 0.20000000298023224D)
                    .scale(1.0D));
            this.move(MoverType.SELF, this.getDeltaMovement());
        }
    }

    public float distanceToIgnoringY(Entity p_70032_1_) {
        float f = (float) (this.getX() - p_70032_1_.getX());
        float f2 = (float) (this.getZ() - p_70032_1_.getZ());
        return Mth.sqrt(f * f + f2 * f2);
    }

    public void baseTick() {
        super.baseTick();

        this.xOld = this.getX();
        this.yOld = this.getY();
        this.zOld = this.getZ();

        if (this.level.isClientSide && this.landAnimationTick <= 0) {
            if (this.hasFormed) {
                this.level.addParticle(ParticleInit.SNOWFLAKE.get(), this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            } else {
                for (int i = 0; i < 2; i++) {
                    this.level.addParticle(ParticleInit.SNOWFLAKE.get(), this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), -(this.random.nextDouble() - 0.5D) * 2.0D, this.random.nextDouble(), -(this.random.nextDouble() - 0.5D) * 2.0D);
                }
            }
        }

        HitResult raytraceresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (raytraceresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult) raytraceresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.level.getBlockEntity(blockpos);
                if (blockEntity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity) blockEntity);
                }

                flag = true;
            }
        }

        if (raytraceresult.getType() != HitResult.Type.MISS && !flag) {
            this.onHit(raytraceresult);
        }

        this.checkInsideBlocks();

        this.tickDownAnimTimers();

        if (this.hasFormed) {
            this.soundLoopTick++;
            this.lifeTime++;
        }

        if (this.soundLoopTick % 80 == 0 && this.hasFormed && !this.falling && this.fallAnimationTick <= 0) {
            this.playSound(SoundEventInit.ICE_CHUNK_IDLE_LOOP.get(), 0.5F, 1.0F);
        }

        if (!this.level.isClientSide) {

            if (this.target != null && !this.canHitEntity(this.target)) {
                this.target = null;
                this.level.broadcastEntityEvent(this, (byte) 7);
            }

            if (!this.hasFormed && this.formAnimationTick <= 0) {
                this.formAnimationTick = formAnimationLength;
                this.level.broadcastEntityEvent(this, (byte) 1);
            }

            if (this.formAnimationTick == 1) {
                this.playSound(SoundEventInit.ICE_CHUNK_IDLE_LOOP.get(), 0.5F, 1.0F);
                this.hasFormed = true;
                this.level.broadcastEntityEvent(this, (byte) 6);
            }

            if ((this.target != null && this.lifeTime > 100 && this.fallAnimationTick <= 0 && this.falling == false) || this.target == null || this.target.isDeadOrDying()) {
                this.playSound(SoundEventInit.ICE_CHUNK_FALL.get(), 1.0F, this.getRandomPitch());
                this.fallAnimationTick = this.fallAnimationLength;
                this.level.broadcastEntityEvent(this, (byte) 2);
            }

            if (this.fallAnimationTick == 1) {
                this.falling = true;
                this.level.broadcastEntityEvent(this, (byte) 4);
            }

            if (this.landAnimationTick == 1 || this.lifeTime > 150) {
                this.remove(RemovalReason.DISCARDED);
            }
        }

        if (this.hasFormed && this.landAnimationTick <= 0 && !this.falling && this.fallAnimationTick <= 0) {
            this.moveToTarget();
        }

        if (this.falling && this.landAnimationTick <= 0) {
            this.setDeltaMovement(this.getDeltaMovement().add(0, -1.25, 0));
            this.move(MoverType.SELF, this.getDeltaMovement());
        }
    }

    protected boolean canHitEntity(Entity p_230298_1_) {
        return !p_230298_1_.isSpectator() && p_230298_1_.isAlive() && p_230298_1_.isPickable();
    }

    protected void onHit(HitResult p_70227_1_) {
        HitResult.Type raytraceresult$type = p_70227_1_.getType();
        if (!this.level.isClientSide) {
            if (this.landAnimationTick <= 0) {
                this.landAnimationTick = landAnimationLength;
                this.level.broadcastEntityEvent(this, (byte) 3);
                this.land();
                this.moveTo(this.getX(), this.getY() - 1, this.getZ());
                this.playSound(SoundEventInit.ICE_CHUNK_LAND.get(), 1.5F, this.getRandomPitch());
            }
        }
        if (raytraceresult$type == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult) p_70227_1_);
        } else if (raytraceresult$type == HitResult.Type.BLOCK) {
            this.onHitBlock((BlockHitResult) p_70227_1_);
        }

    }

    protected void onHitEntity(EntityHitResult p_213868_1_) {
    }

    protected void onHitBlock(BlockHitResult p_230299_1_) {
    }

    private void land() {
        if (this.isAlive()) {
            for (LivingEntity entity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5D), ALIVE)) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
                entity.hurt(DamageSourceInit.iceChunk(this, this.owner), 15.0F);
                this.strongKnockback(entity);
            }

            this.level.broadcastEntityEvent(this, (byte) 5);
        }

    }

    private void strongKnockback(Entity p_213688_1_) {
        p_213688_1_.push(this.random.nextGaussian() * 1.0D, 0.1D, this.random.nextGaussian() * 1.0D);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public void tickDownAnimTimers() {
        if (this.formAnimationTick > 0) {
            this.formAnimationTick--;
        }

        if (this.fallAnimationTick > 0) {
            this.fallAnimationTick--;
        }

        if (this.landAnimationTick > 0) {
            this.landAnimationTick--;
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }


    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.formAnimationTick > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("ice_chunk_form", true));
        } else if (this.landAnimationTick > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("ice_chunk_land", true));
        } else if (this.fallAnimationTick > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("ice_chunk_fall", true));
        } else {
            if (this.hasFormed) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("ice_chunk_idle", true));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("ice_chunk_idle_unformed", true));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
