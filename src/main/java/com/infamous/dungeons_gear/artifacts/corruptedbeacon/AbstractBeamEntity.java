package com.infamous.dungeons_gear.artifacts.corruptedbeacon;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.UUID;

@SuppressWarnings("EntityConstructor")
public abstract class AbstractBeamEntity extends Entity{
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    public int beamShake;
    private int ticksInGround;
    private double damage;
    private SoundEvent hitSound;

    // PROJECTILE
    private UUID shooterUUID;
    private int shooterEntityID;
    private boolean canDamage;

    protected AbstractBeamEntity(EntityType<? extends AbstractBeamEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        this.damage = 1.0D;
        this.hitSound = this.getHitEntitySound();
    }

    protected abstract SoundEvent getHitEntitySound();

    protected AbstractBeamEntity(EntityType<? extends AbstractBeamEntity> entityType, double x, double y, double z, World world) {
        this(entityType, world);
        this.setPosition(x, y, z);
    }

    protected AbstractBeamEntity(EntityType<? extends AbstractBeamEntity> entityType, LivingEntity shooter, World world) {
        this(entityType, shooter.getPosX(), shooter.getPosYEye() - 0.10000000149011612D, shooter.getPosZ(), world);
        this.setShooter(shooter);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double v) {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return v < d0 * d0;
    }

    protected void registerData() {
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vector3d lvt_9_1_ = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy, this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy, this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy).scale((double)velocity);
        this.setMotion(lvt_9_1_);
        float lvt_10_1_ = MathHelper.sqrt(horizontalMag(lvt_9_1_));
        this.rotationYaw = (float)(MathHelper.atan2(lvt_9_1_.x, lvt_9_1_.z) * 57.2957763671875D);
        this.rotationPitch = (float)(MathHelper.atan2(lvt_9_1_.y, (double)lvt_10_1_) * 57.2957763671875D);
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.ticksInGround = 0;
    }

    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_) {
        this.setPosition(x, y, z);
        this.setRotation(p_180426_7_, p_180426_8_);
    }

    @OnlyIn(Dist.CLIENT)
    public void setVelocity(double x, double y, double z) {
        this.setMotion(x, y, z);
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float lvt_7_1_ = MathHelper.sqrt(x * x + z * z);
            this.rotationPitch = (float)(MathHelper.atan2(y, (double)lvt_7_1_) * 57.2957763671875D);
            this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.2957763671875D);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
        }
        this.ticksInGround = 0;
    }

    public void tick() {
        if (!this.canDamage) {
            this.canDamage = this.allowedToDamageEntity();
        }

        super.tick();

        this.noClip = true;
        Vector3d vector3d = this.getMotion();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(horizontalMag(vector3d));
            this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * 57.2957763671875D);
            this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * 57.2957763671875D);
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }

        BlockPos blockpos = this.getPosition();
        BlockState blockstate = this.world.getBlockState(blockpos);
        Vector3d vector3d3;
        if (!blockstate.isAir(this.world, blockpos) && !this.noClip) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
            if (!voxelshape.isEmpty()) {
                vector3d3 = this.getPositionVec();
                Iterator var7 = voxelshape.toBoundingBoxList().iterator();

                while(var7.hasNext()) {
                    AxisAlignedBB axisalignedbb = (AxisAlignedBB)var7.next();
                    if (axisalignedbb.offset(blockpos).contains(vector3d3)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.beamShake > 0) {
            --this.beamShake;
        }

        if (this.isWet()) {
            this.extinguish();
        }

        if (this.inGround && !this.noClip) {
            if (this.inBlockState != blockstate && this.isInGroundButNotColliding()) {
                this.resetTicksInGround();
            } else if (!this.world.isRemote) {
                this.updateTicksInGround();
            }

            ++this.timeInGround;
        } else {
            this.timeInGround = 0;
            Vector3d vector3d2 = this.getPositionVec();
            vector3d3 = vector3d2.add(vector3d);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (((RayTraceResult)raytraceresult).getType() != RayTraceResult.Type.MISS) {
                vector3d3 = ((RayTraceResult)raytraceresult).getHitVec();
            }

            while(!this.removed) {
                EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(vector3d2, vector3d3);
                if (entityraytraceresult != null) {
                    raytraceresult = entityraytraceresult;
                }

                if (raytraceresult != null && ((RayTraceResult)raytraceresult).getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    Entity entity1 = this.getShooter();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }

                if (raytraceresult != null && ((RayTraceResult)raytraceresult).getType() != RayTraceResult.Type.MISS && !this.noClip && !ForgeEventFactory.onProjectileImpact(this, (RayTraceResult)raytraceresult)) {
                    this.onImpact((RayTraceResult)raytraceresult);
                    this.isAirBorne = true;
                }

                if (entityraytraceresult == null) {
                    break;
                }

                raytraceresult = null;
            }

            vector3d = this.getMotion();
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;

            double d5 = this.getPosX() + d3;
            double d1 = this.getPosY() + d4;
            double d2 = this.getPosZ() + d0;
            float f1 = MathHelper.sqrt(horizontalMag(vector3d));
            if (this.noClip) {
                this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * 57.2957763671875D);
            } else {
                this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * 57.2957763671875D);
            }

            this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * 57.2957763671875D);
            this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
            this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
            float f2 = 0.99F;

            this.setMotion(vector3d.scale((double)f2));
            if (!this.hasNoGravity() && !this.noClip) {
                Vector3d vector3d4 = this.getMotion();
                this.setMotion(vector3d4.x, vector3d4.y - 0.05000000074505806D, vector3d4.z);
            }

            this.setPosition(d5, d1, d2);
            this.doBlockCollisions();
        }
    }

    private boolean isInGroundButNotColliding() {
        return this.inGround && this.world.hasNoCollisions((new AxisAlignedBB(this.getPositionVec(), this.getPositionVec())).grow(0.06D));
    }

    private void resetTicksInGround() {
        this.inGround = false;
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.mul((double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F), (double)(this.rand.nextFloat() * 0.2F)));
        this.ticksInGround = 0;
    }

    public void move(MoverType moverType, Vector3d vector3d) {
        super.move(moverType, vector3d);
        if (moverType != MoverType.SELF && this.isInGroundButNotColliding()) {
            this.resetTicksInGround();
        }

    }

    protected void updateTicksInGround() {
        ++this.ticksInGround;
        if (this.ticksInGround >= 20) {
            this.remove();
        }

    }

    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        float f = (float)this.getMotion().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.damage, 0.0D, 2.147483647E9D));

        Entity entity1 = this.getShooter();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.causeIndirectMagicDamage(this, this);
        } else {
            damagesource = DamageSource.causeIndirectMagicDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastAttackedEntity(entity);
            }
        }

        if (entity.attackEntityFrom(damagesource, (float)i)) {

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;

                if (!this.world.isRemote && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity);
                }

                if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241770_g_, 0.0F));
                }
            }
            // if the entity is not living, then remove the beam entity
            else{
                this.remove();
            }

            this.playSound(this.hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.remove();
        } else {
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {

                this.remove();
            }
        }

    }

    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d vector3d, Vector3d vector3d1) {
        return ProjectileHelper.rayTraceEntities(this.world, this, vector3d, vector3d1, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), this::canCollideWith);
    }
    protected boolean canCollideWith(Entity entity) {
        if (!entity.isSpectator() && entity.isAlive() && entity.canBeCollidedWith()) {
            Entity shooter = this.getShooter();
            return shooter == null || this.canDamage || !shooter.isRidingSameEntity(entity);
        } else {
            return false;
        }
    }

    public void writeAdditional(CompoundNBT compoundNBT) {
        if (this.shooterUUID != null) {
            compoundNBT.putUniqueId("Owner", this.shooterUUID);
        }

        if (this.canDamage) {
            compoundNBT.putBoolean("LeftOwner", true);
        }
        compoundNBT.putShort("life", (short)this.ticksInGround);
        if (this.inBlockState != null) {
            compoundNBT.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
        }

        compoundNBT.putByte("shake", (byte)this.beamShake);
        compoundNBT.putBoolean("inGround", this.inGround);
        compoundNBT.putDouble("damage", this.damage);
        compoundNBT.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.hitSound).toString());
    }

    public void readAdditional(CompoundNBT compoundNBT) {
        if (compoundNBT.hasUniqueId("Owner")) {
            this.shooterUUID = compoundNBT.getUniqueId("Owner");
        }

        this.canDamage = compoundNBT.getBoolean("LeftOwner");
        this.ticksInGround = compoundNBT.getShort("life");
        if (compoundNBT.contains("inBlockState", 10)) {
            this.inBlockState = NBTUtil.readBlockState(compoundNBT.getCompound("inBlockState"));
        }

        this.beamShake = compoundNBT.getByte("shake") & 255;
        this.inGround = compoundNBT.getBoolean("inGround");
        if (compoundNBT.contains("damage", 99)) {
            this.damage = compoundNBT.getDouble("damage");
        }
        if (compoundNBT.contains("SoundEvent", 8)) {
            this.hitSound = (SoundEvent)Registry.SOUND_EVENT.getOptional(new ResourceLocation(compoundNBT.getString("SoundEvent"))).orElse(this.getHitEntitySound());
        }
    }

    public void setShooter(@Nullable Entity entity) {
        if (entity != null) {
            this.shooterUUID = entity.getUniqueID();
            this.shooterEntityID = entity.getEntityId();
        }

    }

    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        this.remove();
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDamage() {
        return this.damage;
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    protected float getEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.13F;
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // PROJECTILE ENTITY
    @Nullable
    public Entity getShooter() {
        if (this.shooterUUID != null && this.world instanceof ServerWorld) {
            return ((ServerWorld)this.world).getEntityByUuid(this.shooterUUID);
        } else {
            return this.shooterEntityID != 0 ? this.world.getEntityByID(this.shooterEntityID) : null;
        }
    }

    private boolean allowedToDamageEntity() {
        Entity shooter = this.getShooter();
        if (shooter != null) {
            Iterator var2 = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (entity) -> !entity.isSpectator() && entity.canBeCollidedWith()).iterator();

            while(var2.hasNext()) {
                Entity entity = (Entity)var2.next();
                if (entity.getLowestRidingEntity() == shooter.getLowestRidingEntity()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setArrowMotion(Entity entity, float rotationPitch, float yaw, float p_234612_4_, float p_234612_5_, float p_234612_6_) {
        float x = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(rotationPitch * 0.017453292F);
        float y = -MathHelper.sin((rotationPitch + p_234612_4_) * 0.017453292F);
        float z = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(rotationPitch * 0.017453292F);
        this.shoot((double)x, (double)y, (double)z, p_234612_5_, p_234612_6_);
        Vector3d entityMotion = entity.getMotion();
        this.setMotion(this.getMotion().add(entityMotion.x, entity.isOnGround() ? 0.0D : entityMotion.y, entityMotion.z));
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        RayTraceResult.Type traceResultType = rayTraceResult.getType();
        if (traceResultType == RayTraceResult.Type.ENTITY) {
            this.onEntityHit((EntityRayTraceResult)rayTraceResult);
        } else if (traceResultType == RayTraceResult.Type.BLOCK) {
            this.remove();
            //this.hitGround((BlockRayTraceResult)rayTraceResult);
        }

    }

    protected void func_234617_x_() {
        Vector3d lvt_1_1_ = this.getMotion();
        float lvt_2_1_ = MathHelper.sqrt(horizontalMag(lvt_1_1_));
        this.rotationPitch = func_234614_e_(this.prevRotationPitch, (float)(MathHelper.atan2(lvt_1_1_.y, (double)lvt_2_1_) * 57.2957763671875D));
        this.rotationYaw = func_234614_e_(this.prevRotationYaw, (float)(MathHelper.atan2(lvt_1_1_.x, lvt_1_1_.z) * 57.2957763671875D));
    }

    protected static float func_234614_e_(float v, float v1) {
        while(v1 - v < -180.0F) {
            v -= 360.0F;
        }

        while(v1 - v >= 180.0F) {
            v += 360.0F;
        }

        return MathHelper.lerp(0.2F, v, v1);
    }
}

