package com.infamous.dungeons_gear.entities;

import com.google.common.collect.Lists;
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
    private double heightAdjustment = (1.0F - this.getHeight()) / 2.0F;

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
        this.setPosition(casterIn.getPosX(),
                casterIn.getPosYHeight(1.0D) + this.heightAboveTarget + heightAdjustment,
                casterIn.getPosZ());

        this.preventEntitySpawning = true;
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = targetIn.getPosX();
        this.prevPosY = targetIn.getPosYHeight(1.0D) + heightAboveTarget + heightAdjustment;
        this.prevPosZ = targetIn.getPosZ();

    }


    protected void registerData() {
    }

    private void tryToFloatAboveTarget(LivingEntity targetIn) {
        List<IceCloudEntity> nearbyIceClouds = this.world.getEntitiesWithinAABB(
                ModEntityTypes.ICE_CLOUD.get(),
                this.getBoundingBox().grow(0.2, 0, 0.2),
                (nearbyEntity) -> nearbyEntity != this);

        if(nearbyIceClouds.isEmpty()){
            this.setPosition(targetIn.getPosX(),
                    targetIn.getPosYHeight(1.0D) + this.heightAboveTarget + heightAdjustment,
                    targetIn.getPosZ());
        }
    }

    public void setCaster(@Nullable LivingEntity caster) {
        this.caster = caster;
        this.casterUuid = caster == null ? null : caster.getUniqueID();
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

    public void setTarget(@Nullable LivingEntity target) {
        this.target = target;
        this.targetUUID = target == null ? null : target.getUniqueID();
    }

    @Nullable
    public LivingEntity getTarget() {
        if (this.target == null && this.targetUUID != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.world).getEntityByUuid(this.targetUUID);
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
            if(this.target != null && !this.world.isRemote){
                this.tryToFloatAboveTarget(this.target);
            }
        }
        else{
            if (this.fallTime < 0) {
                if (!this.world.isRemote) {
                    this.remove();
                    return;
                }
            }
            this.fallTime++;

            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0D, -0.04D * 4.0D, 0.0D));
            }

            this.move(MoverType.SELF, this.getMotion());
            if (!this.world.isRemote) {
                BlockPos iceCloudPosition = this.getPosition();

                if (!this.onGround) {
                    if (!this.world.isRemote
                            && (this.fallTime > 100
                            && (iceCloudPosition.getY() < 1
                            || iceCloudPosition.getY() > 256)
                            || this.fallTime > 600)) {
                        this.remove();
                    }
                } else {
                    BlockState blockstate = this.world.getBlockState(iceCloudPosition);
                    this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
                    if (!blockstate.isIn(Blocks.MOVING_PISTON)) {
                        this.spawnIceExplosionCloud();
                        this.remove();
                    }
                }
            }

            this.setMotion(this.getMotion().scale(0.98D));
        }
    }

    public void spawnIceExplosionCloud(){
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
        areaeffectcloudentity.setParticleData(ParticleTypes.EXPLOSION);
        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setDuration(0);
        this.world.addEntity(areaeffectcloudentity);
    }


    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
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
        return false;
    }

    private void damage(LivingEntity targetEntity, int distanceFallen) {
        LivingEntity caster = this.getCaster();
        float damageAmount = (float) MathHelper.floor((float)distanceFallen * this.fallHurtAmount);
        if (targetEntity.isAlive() && !targetEntity.isInvulnerable() && targetEntity != caster) {
            if (caster == null) {
                targetEntity.attackEntityFrom(DamageSource.MAGIC, damageAmount);
                targetEntity.addPotionEffect(new EffectInstance(CustomEffects.STUNNED, 100));
                targetEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 4));
                targetEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 4));
            } else {
                if (caster.isOnSameTeam(targetEntity)) {
                    return;
                }
                targetEntity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.caster), damageAmount);
                targetEntity.addPotionEffect(new EffectInstance(CustomEffects.STUNNED, 100));
                targetEntity.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 4));
                targetEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 4));
            }

        }
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean canBeAttackedWithItem() {
        return false;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.fallTime = compound.getInt("Time");
        this.fallHurtAmount = compound.getFloat("FallHurtAmount");
        this.setFloatTicks(compound.getInt("FloatTicks"));
        if (compound.hasUniqueId("Owner")) {
            this.casterUuid = compound.getUniqueId("Owner");
        }
        if (compound.hasUniqueId("Target")) {
            this.casterUuid = compound.getUniqueId("Target");
        }

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Time", this.fallTime);
        compound.putFloat("FallHurtAmount", this.fallHurtAmount);
        compound.putInt("FloatTicks", this.getFloatTicks());
        if (this.casterUuid != null) {
            compound.putUniqueId("Owner", this.casterUuid);
        }
        if (this.targetUUID != null) {
            compound.putUniqueId("Target", this.targetUUID);
        }
    }

    public int getFloatTicks() {
        return this.floatTicks;
    }

    public void setFloatTicks(int floatTicksIn){
        this.floatTicks = floatTicksIn;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    /**
     * Return whether this entity should be rendered as on fire.
     */
    @OnlyIn(Dist.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }
}
