package com.infamous.dungeons_gear.entities;

import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.effects.CustomEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class IceCloudEntity extends Entity implements IAnimatable {
    private int floatTicks = 60;
    public int fallTime = 0;
    private float fallHurtAmount = 3.0F;
    private LivingEntity caster;
    private UUID casterUuid;
    private LivingEntity target;
    private UUID targetUUID;
    private double heightAboveTarget = 2.0D;
    private double heightAdjustment = (1.0F - this.getBbHeight()) / 2.0F;

    AnimationFactory factory = new AnimationFactory(this);

    public IceCloudEntity(Level worldIn) {
        super(ModEntityTypes.ICE_CLOUD.get(), worldIn);
    }

    public IceCloudEntity(EntityType<? extends IceCloudEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public IceCloudEntity(Level worldIn, LivingEntity casterIn, LivingEntity targetIn) {
        this(ModEntityTypes.ICE_CLOUD.get(), worldIn);
        this.setCaster(casterIn);
        this.setTarget(targetIn);
        this.setPos(casterIn.getX(),
                casterIn.getY(1.0D) + this.heightAboveTarget + heightAdjustment,
                casterIn.getZ());

        this.blocksBuilding = true;
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = targetIn.getX();
        this.yo = targetIn.getY(1.0D) + heightAboveTarget + heightAdjustment;
        this.zo = targetIn.getZ();

    }


    protected void defineSynchedData() {
    }

    private void tryToFloatAboveTarget(LivingEntity targetIn) {
        List<IceCloudEntity> nearbyIceClouds = this.level.getEntities(
                ModEntityTypes.ICE_CLOUD.get(),
                this.getBoundingBox().inflate(0.2, 0, 0.2),
                (nearbyEntity) -> nearbyEntity != this);

        if(nearbyIceClouds.isEmpty()){
            this.setPos(targetIn.getX(),
                    targetIn.getY(1.0D) + this.heightAboveTarget + heightAdjustment,
                    targetIn.getZ());
        }
    }

    public void setCaster(@Nullable LivingEntity caster) {
        this.caster = caster;
        this.casterUuid = caster == null ? null : caster.getUUID();
    }

    @Nullable
    public LivingEntity getCaster() {
        if (this.caster == null && this.casterUuid != null && this.level instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level).getEntity(this.casterUuid);
            if (entity instanceof LivingEntity) {
                this.caster = (LivingEntity)entity;
            }
        }

        return this.caster;
    }

    public void setTarget(@Nullable LivingEntity target) {
        this.target = target;
        this.targetUUID = target == null ? null : target.getUUID();
    }

    @Nullable
    public LivingEntity getTarget() {
        if (this.target == null && this.targetUUID != null && this.level instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level).getEntity(this.targetUUID);
            if (entity instanceof LivingEntity) {
                this.target = (LivingEntity)entity;
            }
        }

        return this.caster;
    }

    @Override
    public void tick() {
        if(this.floatTicks > 0){
            this.floatTicks--;
            if(this.target != null && !this.level.isClientSide){
                this.tryToFloatAboveTarget(this.target);
            }
        }
        else{
            if (this.fallTime < 0) {
                if (!this.level.isClientSide) {
                    this.remove(RemovalReason.DISCARDED);
                    return;
                }
            }
            this.fallTime++;

            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D * 4.0D, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.level.isClientSide) {
                BlockPos iceCloudPosition = this.blockPosition();

                if (!this.onGround) {
                    if (!this.level.isClientSide
                            && (this.fallTime > 100
                            && (iceCloudPosition.getY() < 1
                            || iceCloudPosition.getY() > 256)
                            || this.fallTime > 600)) {
                        this.remove(RemovalReason.DISCARDED);
                    }
                } else {
                    BlockState blockstate = this.level.getBlockState(iceCloudPosition);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        this.spawnIceExplosionCloud();
                        this.remove(RemovalReason.DISCARDED);
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }
    }

    public void spawnIceExplosionCloud(){
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setDuration(0);
        this.level.addFreshEntity(areaeffectcloudentity);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource) {
        int distanceFallen = Mth.ceil(distance - 1.0F);
        if (distanceFallen > 0) {
            List<Entity> list = Lists.newArrayList(this.level.getEntities(this, this.getBoundingBox()));
            for(Entity entity : list) {
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity)entity;
                    damage(livingEntity, distanceFallen);
                }
            }
        }
        return false;
    }

    private void damage(LivingEntity targetEntity, int distanceFallen) {
        LivingEntity caster = this.getCaster();
        float damageAmount = (float) Mth.floor((float)distanceFallen * this.fallHurtAmount);
        if (targetEntity.isAlive() && !targetEntity.isInvulnerable() && targetEntity != caster) {
            if (caster == null) {
                targetEntity.hurt(DamageSource.MAGIC, damageAmount);
                targetEntity.addEffect(new MobEffectInstance(CustomEffects.STUNNED, 100));
                targetEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 4));
                targetEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 4));
            } else {
                if (caster.isAlliedTo(targetEntity)) {
                    return;
                }
                targetEntity.hurt(DamageSource.indirectMagic(this, this.caster), damageAmount);
                targetEntity.addEffect(new MobEffectInstance(CustomEffects.STUNNED, 100));
                targetEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 4));
                targetEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 4));
            }

        }
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
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

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.fallTime = compound.getInt("Time");
        this.fallHurtAmount = compound.getFloat("FallHurtAmount");
        this.setFloatTicks(compound.getInt("FloatTicks"));
        if (compound.hasUUID("Owner")) {
            this.casterUuid = compound.getUUID("Owner");
        }
        if (compound.hasUUID("Target")) {
            this.casterUuid = compound.getUUID("Target");
        }

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("Time", this.fallTime);
        compound.putFloat("FallHurtAmount", this.fallHurtAmount);
        compound.putInt("FloatTicks", this.getFloatTicks());
        if (this.casterUuid != null) {
            compound.putUUID("Owner", this.casterUuid);
        }
        if (this.targetUUID != null) {
            compound.putUUID("Target", this.targetUUID);
        }
    }

    public int getFloatTicks() {
        return this.floatTicks;
    }

    public void setFloatTicks(int floatTicksIn){
        this.floatTicks = floatTicksIn;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    /**
     * Return whether this entity should be rendered as on fire.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean displayFireAnimation() {
        return false;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.icecloud.summoned", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
