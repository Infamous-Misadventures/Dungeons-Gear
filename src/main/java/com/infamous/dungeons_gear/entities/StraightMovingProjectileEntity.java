package com.infamous.dungeons_gear.entities;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class StraightMovingProjectileEntity extends ProjectileEntity {
    public double xPower;
    public double yPower;
    public double zPower;

    public boolean stuckInBlock;
    private final Predicate<Entity> CAN_HIT = (entity) -> {
        return this.canHitEntity(entity);
    };
    public int lifeTime;
    public float lockedXRot;
    public float lockedYRot;

    protected StraightMovingProjectileEntity(EntityType<? extends StraightMovingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public StraightMovingProjectileEntity(EntityType<? extends StraightMovingProjectileEntity> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        this(p_i50174_1_, p_i50174_14_);
        this.moveTo(p_i50174_2_, p_i50174_4_, p_i50174_6_, this.yRot, this.xRot);
        this.reapplyPosition();
        double d0 = (double) MathHelper.sqrt(p_i50174_8_ * p_i50174_8_ + p_i50174_10_ * p_i50174_10_ + p_i50174_12_ * p_i50174_12_);
        if (d0 != 0.0D) {
            this.xPower = p_i50174_8_ / d0 * 0.1D;
            this.yPower = p_i50174_10_ / d0 * 0.1D;
            this.zPower = p_i50174_12_ / d0 * 0.1D;
        }

    }

    public StraightMovingProjectileEntity(EntityType<? extends StraightMovingProjectileEntity> p_i50175_1_, LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World p_i50175_9_) {
        this(p_i50175_1_, p_i50175_2_.getX(), p_i50175_2_.getY(), p_i50175_2_.getZ(), p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
        this.setOwner(p_i50175_2_);
        this.setRot(p_i50175_2_.yRot, p_i50175_2_.xRot);
    }

    protected void defineSynchedData() {
    }

    public void setPower(double powerX, double powerY, double powerZ) {
        double d0 = (double) MathHelper.sqrt(powerX * powerX + powerY * powerY + powerZ * powerZ);
        if (d0 != 0.0D) {
            this.xPower = powerX / d0 * 0.1D;
            this.yPower = powerY / d0 * 0.1D;
            this.zPower = powerZ / d0 * 0.1D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double p_70112_1_) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return p_70112_1_ < d0 * d0;
    }

    public void tryToDealDamage() {
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox(), CAN_HIT);
        if (!list.isEmpty() && !this.level.isClientSide) {
            for (Entity entity : list) {
                this.onHitEntity(entity);
            }
        }
    }

    public void rotateToMatchMovement() {
        this.updateRotation();
    }

    public boolean shouldUpdateRotation() {
        return true;
    }

    @Override
    protected void updateRotation() {
        if (this.stuckInBlock) {
            this.xRot = this.lockedXRot;
            this.yRot = this.lockedYRot;
        } else {
            super.updateRotation();
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (this.shouldUpdateRotation()) {
            this.updateRotation();
        }

        if (!this.level.isClientSide && this.vanishesAfterTime()) {
            if (this.lifeTime < this.vanishAfterTime() + this.getVanishAnimationLength()) {
                this.lifeTime++;
            } else {
                this.remove();
            }
        }

        if (this.level.isClientSide ) {
            if (this.lifeTime < this.vanishAfterTime() + this.getVanishAnimationLength()) {
                this.lifeTime++;
            }
        }

        this.tryToDealDamage();
    }

    public void tick() {
        Entity entity = this.getOwner();

        if (this.stuckInBlock) {
            this.noPhysics = false;
        }

        if (this.level.isClientSide || (entity == null || !entity.removed) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onHit(raytraceresult);
            }

            this.checkInsideBlocks();
            Vector3d vector3d = this.getDeltaMovement();
            double d0 = this.getX() + vector3d.x;
            double d1 = this.getY() + vector3d.y;
            double d2 = this.getZ() + vector3d.z;
            float f = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    if (this.getUnderWaterTrailParticle() != null && this.shouldSpawnParticles()) {
                        this.spawnUnderWaterTrailParticle();
                    }
                }

                if (this.slowedDownInWater()) {
                    f = 0.8F;
                }
            }

            this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale((double) f));
            if (this.getTrailParticle() != null && this.shouldSpawnParticles()) {
                this.spawnTrailParticle();
            }
            this.setPos(d0, d1, d2);
        } else {
            this.remove();
        }
    }

    public boolean vanishesAfterTime() {
        return true;
    }

    public int vanishAfterTime() {
        return 200;
    }

    public int getVanishAnimationLength() {
        return 0;
    }

    public void onHitEntity(Entity entity) {
        this.playImpactSound();
    }

    public boolean getsStuckInBlocks() {
        return false;
    }

    public boolean keepsHittingAfterStuck() {
        return false;
    }

    public abstract SoundEvent getImpactSound();

    public void playImpactSound() {
        if (this.getImpactSound() != null) {
            this.playSound(this.getImpactSound(), 1.0F, 1.0F);
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        if (!this.stuckInBlock) {
            this.playImpactSound();
        }
        if (!this.getsStuckInBlocks()) {
            if (!this.level.isClientSide) {
                this.remove();
            }
        } else {
            this.lockedXRot = this.xRot;
            this.lockedYRot = this.yRot;
            this.stuckInBlock = true;
            this.setPower(0, 0, 0);
            this.setDeltaMovement(0, 0, 0);
        }
    }

    public boolean slowedDownInWater() {
        return true;
    }

    public void spawnTrailParticle() {
        this.level.addParticle(this.getTrailParticle(), this.getX(), this.getY() + this.getSpawnParticlesY(), this.getZ(), 0, 0, 0);
    }

    public void spawnUnderWaterTrailParticle() {
        this.level.addParticle(this.getUnderWaterTrailParticle(), this.getX(), this.getY() + this.getSpawnParticlesY(), this.getZ(), 0, 0, 0);
    }

    public double getSpawnParticlesY() {
        return 0.5D;
    }

    public boolean shouldSpawnParticles() {
        return true;
    }

    protected boolean canHitEntity(Entity p_230298_1_) {
        boolean foundOwnerPartInEntity = false;
        if (this.getOwner() != null && this.getOwner().isMultipartEntity()) {
            for (PartEntity<?> entity : this.getOwner().getParts()) {
                if (p_230298_1_ == entity) {
                    foundOwnerPartInEntity = true;
                }
            }
        }
        return super.canHitEntity(p_230298_1_) && ((this.keepsHittingAfterStuck() && this.stuckInBlock) || !this.stuckInBlock) && !p_230298_1_.noPhysics && foundOwnerPartInEntity == false;
    }

    protected boolean shouldBurn() {
        return true;
    }

    protected IParticleData getTrailParticle() {
        return ParticleTypes.SMOKE;
    }

    protected IParticleData getUnderWaterTrailParticle() {
        return ParticleTypes.BUBBLE;
    }

    protected float getInertia() {
        return 0.95F;
    }

    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.put("power", this.newDoubleList(new double[]{this.xPower, this.yPower, this.zPower}));
    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        if (p_70037_1_.contains("power", 9)) {
            ListNBT listnbt = p_70037_1_.getList("power", 6);
            if (listnbt.size() == 3) {
                this.xPower = listnbt.getDouble(0);
                this.yPower = listnbt.getDouble(1);
                this.zPower = listnbt.getDouble(2);
            }
        }

    }

    public boolean isPickable() {
        return true;
    }

    public float getPickRadius() {
        return 1.0F;
    }

    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isInvulnerableTo(p_70097_1_)) {
            return false;
        } else {
            this.markHurt();
            Entity entity = p_70097_1_.getEntity();
            if (entity != null) {
                Vector3d vector3d = entity.getLookAngle();
                this.setDeltaMovement(vector3d);
                this.xPower = vector3d.x * 0.1D;
                this.yPower = vector3d.y * 0.1D;
                this.zPower = vector3d.z * 0.1D;
                this.setOwner(entity);
                return true;
            } else {
                return false;
            }
        }
    }

    public float getBrightness() {
        return 1.0F;
    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}