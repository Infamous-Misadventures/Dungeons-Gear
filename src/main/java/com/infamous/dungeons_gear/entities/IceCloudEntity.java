package com.infamous.dungeons_gear.entities;

import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.effects.CustomEffect;
import com.infamous.dungeons_gear.effects.CustomEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class IceCloudEntity extends Entity {
    private int floatTicks = 60;
    public int fallTime = 0;
    private float fallHurtAmount = 3.0F;
    private LivingEntity caster;
    private UUID casterUuid;
    private LivingEntity target;
    private UUID targetUUID;
    private double heightAboveTarget = 2.0D;
    private double heightAdjustment = (1.0F - this.getBbHeight()) / 2.0F;

    public IceCloudEntity(World worldIn) {
        super(ModEntityTypes.ICE_CLOUD.get(), worldIn);
    }

    public IceCloudEntity(EntityType<? extends IceCloudEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public IceCloudEntity(World worldIn, LivingEntity casterIn, LivingEntity targetIn) {
        this(ModEntityTypes.ICE_CLOUD.get(), worldIn);
        this.setCaster(casterIn);
        this.setTarget(targetIn);
        this.setPos(casterIn.getX(),
                casterIn.getY(1.0D) + this.heightAboveTarget + heightAdjustment,
                casterIn.getZ());

        this.blocksBuilding = true;
        this.setDeltaMovement(Vector3d.ZERO);
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
        if (this.caster == null && this.casterUuid != null && this.level instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.level).getEntity(this.casterUuid);
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
        if (this.target == null && this.targetUUID != null && this.level instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.level).getEntity(this.targetUUID);
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
                    this.remove();
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
                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.level.getBlockState(iceCloudPosition);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockstate.is(Blocks.MOVING_PISTON)) {
                        this.spawnIceExplosionCloud();
                        this.remove();
                    }
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
        }
    }

    public void spawnIceExplosionCloud(){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
        areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setDuration(0);
        this.level.addFreshEntity(areaeffectcloudentity);
    }


    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
    int distanceFallen = MathHelper.ceil(distance - 1.0F);
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
        float damageAmount = (float) MathHelper.floor((float)distanceFallen * this.fallHurtAmount);
        if (targetEntity.isAlive() && !targetEntity.isInvulnerable() && targetEntity != caster) {
            if (caster == null) {
                targetEntity.hurt(DamageSource.MAGIC, damageAmount);
                targetEntity.addEffect(new EffectInstance(CustomEffects.STUNNED, 100));
                targetEntity.addEffect(new EffectInstance(Effects.CONFUSION, 100, 4));
                targetEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 4));
            } else {
                if (caster.isAlliedTo(targetEntity)) {
                    return;
                }
                targetEntity.hurt(DamageSource.indirectMagic(this, this.caster), damageAmount);
                targetEntity.addEffect(new EffectInstance(CustomEffects.STUNNED, 100));
                targetEntity.addEffect(new EffectInstance(Effects.CONFUSION, 100, 4));
                targetEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 4));
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
    protected boolean isMovementNoisy() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return !this.removed;
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {
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
    protected void addAdditionalSaveData(CompoundNBT compound) {
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
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    /**
     * Return whether this entity should be rendered as on fire.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean displayFireAnimation() {
        return false;
    }
}
