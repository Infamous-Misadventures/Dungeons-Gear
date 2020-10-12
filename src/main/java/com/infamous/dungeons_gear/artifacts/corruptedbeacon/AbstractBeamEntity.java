package com.infamous.dungeons_gear.artifacts.corruptedbeacon;

import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class AbstractBeamEntity extends Entity{
    private static final DataParameter<Byte> CRITICAL;
    private static final DataParameter<Byte> PIERCE_LEVEL;
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    public AbstractBeamEntity.PickupStatus pickupStatus;
    public int beamShake;
    private int ticksInGround;
    private double damage;
    private int knockbackStrength;
    private SoundEvent hitSound;
    private IntOpenHashSet piercedEntities;
    private List<Entity> hitEntities;

    // PROJECTILE
    private UUID shooterUUID;
    private int shooterEntityID;
    private boolean field_234611_d_;

    protected AbstractBeamEntity(EntityType<? extends AbstractBeamEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        this.pickupStatus = AbstractBeamEntity.PickupStatus.DISALLOWED;
        this.damage = 1.0D;
        this.hitSound = this.getHitEntitySound();
    }

    protected AbstractBeamEntity(EntityType<? extends AbstractBeamEntity> entityType, double x, double y, double z, World world) {
        this(entityType, world);
        this.setPosition(x, y, z);
    }

    protected AbstractBeamEntity(EntityType<? extends AbstractBeamEntity> entityType, LivingEntity shooter, World world) {
        this(entityType, shooter.getPosX(), shooter.getPosYEye() - 0.10000000149011612D, shooter.getPosZ(), world);
        this.setShooter(shooter);
        if (shooter instanceof PlayerEntity) {
            this.pickupStatus = AbstractBeamEntity.PickupStatus.ALLOWED;
        }

    }

    protected AbstractBeamEntity(World world){
        super(ModEntityTypes.BEAM.get(), world);
    }

    public void setHitSound(SoundEvent soundEvent) {
        this.hitSound = soundEvent;
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
        this.dataManager.register(CRITICAL, (byte)0);
        this.dataManager.register(PIERCE_LEVEL, (byte)0);
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
        if (!this.field_234611_d_) {
            this.field_234611_d_ = this.func_234615_h_();
        }

        super.tick();

        boolean isNoClip = this.getNoClip();
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
        if (!blockstate.isAir(this.world, blockpos) && !isNoClip) {
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

        if (this.inGround && !isNoClip) {
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

                if (raytraceresult != null && ((RayTraceResult)raytraceresult).getType() != RayTraceResult.Type.MISS && !isNoClip && !ForgeEventFactory.onProjectileImpact(this, (RayTraceResult)raytraceresult)) {
                    this.onImpact((RayTraceResult)raytraceresult);
                    this.isAirBorne = true;
                }

                if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                raytraceresult = null;
            }

            vector3d = this.getMotion();
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;
            if (this.getIsCritical()) {
                for(int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.CRIT, this.getPosX() + d3 * (double)i / 4.0D, this.getPosY() + d4 * (double)i / 4.0D, this.getPosZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
                }
            }

            double d5 = this.getPosX() + d3;
            double d1 = this.getPosY() + d4;
            double d2 = this.getPosZ() + d0;
            float f1 = MathHelper.sqrt(horizontalMag(vector3d));
            if (isNoClip) {
                this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * 57.2957763671875D);
            } else {
                this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * 57.2957763671875D);
            }

            this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * 57.2957763671875D);
            this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
            this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
            float f2 = 0.99F;
            float f3 = 0.05F;
            if (this.isInWater()) {
                /*
                for(int j = 0; j < 4; ++j) {
                    float f4 = 0.25F;
                    this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }

                f2 = this.getWaterDrag();

                 */
            }

            this.setMotion(vector3d.scale((double)f2));
            if (!this.hasNoGravity() && !isNoClip) {
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

    private void func_213870_w() {
        if (this.hitEntities != null) {
            this.hitEntities.clear();
        }

        if (this.piercedEntities != null) {
            this.piercedEntities.clear();
        }

    }

    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        float f = (float)this.getMotion().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.damage, 0.0D, 2.147483647E9D));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.hitEntities == null) {
                this.hitEntities = Lists.newArrayListWithCapacity(5);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }

            this.piercedEntities.add(entity.getEntityId());
        }

        if (this.getIsCritical()) {
            long j = (long)this.rand.nextInt(i / 2 + 2);
            i = (int)Math.min(j + (long)i, 2147483647L);
        }

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

        //boolean flag = entity.getType() == EntityType.ENDERMAN;
        //int k = entity.getFireTimer();
        //if (this.isBurning() && !flag) {
            //entity.setFire(5);
        //}

        if (entity.attackEntityFrom(damagesource, (float)i)) {
            //if (flag) {
            //    return;
            //}

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (!this.world.isRemote && this.getPierceLevel() <= 0) {
                    //livingentity.setArrowCountInEntity(livingentity.getArrowCountInEntity() + 1);
                }

                if (this.knockbackStrength > 0) {
                    Vector3d vector3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                    if (vector3d.lengthSquared() > 0.0D) {
                        livingentity.addVelocity(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.world.isRemote && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity)entity1, livingentity);
                }

                this.beamHit(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity1).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241770_g_, 0.0F));
                }

                if (!entity.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingentity);
                }

                if (!this.world.isRemote && entity1 instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity1;
                    if (this.hitEntities != null && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.test(serverplayerentity, this.hitEntities);
                    } else if (!entity.isAlive() && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.test(serverplayerentity, Arrays.asList(entity));
                    }
                }
            }
            // if the entity is not living, then remove the beam entity
            else{
                this.remove();
            }

            this.playSound(this.hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            //entity.func_241209_g_(k);
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                if (this.pickupStatus == AbstractBeamEntity.PickupStatus.ALLOWED) {
                    this.entityDropItem(this.getArrowStack(), 0.1F);
                }

                this.remove();
            }
        }

    }

    protected void hitGround(BlockRayTraceResult p_230299_1_) {
        this.inBlockState = this.world.getBlockState(p_230299_1_.getPos());
        BlockState lvt_3_1_ = this.world.getBlockState(p_230299_1_.getPos());
        //lvt_3_1_.onProjectileCollision(this.world, lvt_3_1_, p_230299_1_, this);
        Vector3d vector3d = p_230299_1_.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
        this.setMotion(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale(0.05000000074505806D);
        this.setRawPosition(this.getPosX() - vector3d1.x, this.getPosY() - vector3d1.y, this.getPosZ() - vector3d1.z);
        this.playSound(this.getHitGroundSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.beamShake = 7;
        this.setIsCritical(false);
        this.setPierceLevel((byte)0);
        this.setHitSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL);
        this.setShotFromCrossbow(false);
        this.func_213870_w();
    }

    protected SoundEvent getHitEntitySound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    protected final SoundEvent getHitGroundSound() {
        return this.hitSound;
    }

    protected void beamHit(LivingEntity livingEntity) {
    }

    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d vector3d, Vector3d vector3d1) {
        return ProjectileHelper.rayTraceEntities(this.world, this, vector3d, vector3d1, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), this::func_230298_a_);
    }

    protected boolean func_230298_a_(Entity entity) {
        return canCollideWith(entity)
                && (this.piercedEntities == null || !this.piercedEntities.contains(entity.getEntityId()));
    }
    protected boolean canCollideWith(Entity entity) {
        if (!entity.isSpectator() && entity.isAlive() && entity.canBeCollidedWith()) {
            Entity shooter = this.getShooter();
            return shooter == null || this.field_234611_d_ || !shooter.isRidingSameEntity(entity);
        } else {
            return false;
        }
    }

    public void writeAdditional(CompoundNBT compoundNBT) {
        if (this.shooterUUID != null) {
            compoundNBT.putUniqueId("Owner", this.shooterUUID);
        }

        if (this.field_234611_d_) {
            compoundNBT.putBoolean("LeftOwner", true);
        }
        compoundNBT.putShort("life", (short)this.ticksInGround);
        if (this.inBlockState != null) {
            compoundNBT.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
        }

        compoundNBT.putByte("shake", (byte)this.beamShake);
        compoundNBT.putBoolean("inGround", this.inGround);
        compoundNBT.putByte("pickup", (byte)this.pickupStatus.ordinal());
        compoundNBT.putDouble("damage", this.damage);
        compoundNBT.putBoolean("crit", this.getIsCritical());
        compoundNBT.putByte("PierceLevel", this.getPierceLevel());
        compoundNBT.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.hitSound).toString());
        compoundNBT.putBoolean("ShotFromCrossbow", this.getShotFromCrossbow());
    }

    public void readAdditional(CompoundNBT compoundNBT) {
        if (compoundNBT.hasUniqueId("Owner")) {
            this.shooterUUID = compoundNBT.getUniqueId("Owner");
        }

        this.field_234611_d_ = compoundNBT.getBoolean("LeftOwner");
        this.ticksInGround = compoundNBT.getShort("life");
        if (compoundNBT.contains("inBlockState", 10)) {
            this.inBlockState = NBTUtil.readBlockState(compoundNBT.getCompound("inBlockState"));
        }

        this.beamShake = compoundNBT.getByte("shake") & 255;
        this.inGround = compoundNBT.getBoolean("inGround");
        if (compoundNBT.contains("damage", 99)) {
            this.damage = compoundNBT.getDouble("damage");
        }

        if (compoundNBT.contains("pickup", 99)) {
            this.pickupStatus = AbstractBeamEntity.PickupStatus.getByOrdinal(compoundNBT.getByte("pickup"));
        } else if (compoundNBT.contains("player", 99)) {
            this.pickupStatus = compoundNBT.getBoolean("player") ? AbstractBeamEntity.PickupStatus.ALLOWED : AbstractBeamEntity.PickupStatus.DISALLOWED;
        }

        this.setIsCritical(compoundNBT.getBoolean("crit"));
        this.setPierceLevel(compoundNBT.getByte("PierceLevel"));
        if (compoundNBT.contains("SoundEvent", 8)) {
            this.hitSound = (SoundEvent)Registry.SOUND_EVENT.getOptional(new ResourceLocation(compoundNBT.getString("SoundEvent"))).orElse(this.getHitEntitySound());
        }

        this.setShotFromCrossbow(compoundNBT.getBoolean("ShotFromCrossbow"));
    }

    public void setShooter(@Nullable Entity entity) {
        if (entity != null) {
            this.shooterUUID = entity.getUniqueID();
            this.shooterEntityID = entity.getEntityId();
        }
        if (entity instanceof PlayerEntity) {
            this.pickupStatus = ((PlayerEntity)entity).abilities.isCreativeMode ? AbstractBeamEntity.PickupStatus.CREATIVE_ONLY : AbstractBeamEntity.PickupStatus.ALLOWED;
        }

    }

    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (!this.world.isRemote && (this.inGround || this.getNoClip()) && this.beamShake <= 0) {
            boolean flag = this.pickupStatus == AbstractBeamEntity.PickupStatus.ALLOWED || this.pickupStatus == AbstractBeamEntity.PickupStatus.CREATIVE_ONLY && playerEntity.abilities.isCreativeMode || this.getNoClip() && this.getShooter().getUniqueID() == playerEntity.getUniqueID();
            if (this.pickupStatus == AbstractBeamEntity.PickupStatus.ALLOWED
                    //&& !p_70100_1_.inventory.addItemStackToInventory(this.getArrowStack())
            ) {
                flag = false;
            }

            if (flag) {
                //p_70100_1_.onItemPickup(this, 1);
                this.remove();
            }
        }
        if(this.ticksExisted > 0){
            this.remove();
        }

    }

    protected abstract ItemStack getArrowStack();

    protected boolean canTriggerWalking() {
        return false;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setKnockbackStrength(int knockbackStrength) {
        this.knockbackStrength = knockbackStrength;
    }

    public boolean canBeAttackedWithItem() {
        return false;
    }

    protected float getEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.13F;
    }

    public void setIsCritical(boolean isCritical) {
        this.setArrowFlag(1, isCritical);
    }

    public void setPierceLevel(byte pierceLevel) {
        this.dataManager.set(PIERCE_LEVEL, pierceLevel);
    }

    private void setArrowFlag(int i, boolean b) {
        byte b0 = (Byte)this.dataManager.get(CRITICAL);
        if (b) {
            this.dataManager.set(CRITICAL, (byte)(b0 | i));
        } else {
            this.dataManager.set(CRITICAL, (byte)(b0 & ~i));
        }

    }

    public boolean getIsCritical() {
        byte b0 = (Byte)this.dataManager.get(CRITICAL);
        return (b0 & 1) != 0;
    }

    public boolean getShotFromCrossbow() {
        byte b0 = (Byte)this.dataManager.get(CRITICAL);
        return (b0 & 4) != 0;
    }

    public byte getPierceLevel() {
        return (Byte)this.dataManager.get(PIERCE_LEVEL);
    }

    public void setEnchantmentEffectsFromEntity(LivingEntity livingEntity, float v) {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, livingEntity);
        int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, livingEntity);
        this.setDamage((double)(v * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getId() * 0.11F));
        if (i > 0) {
            this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0) {
            this.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, livingEntity) > 0) {
            this.setFire(100);
        }

    }

    protected float getWaterDrag() {
        return 0.6F;
    }

    public void setNoClip(boolean noClip) {
        this.noClip = noClip;
        this.setArrowFlag(2, noClip);
    }

    public boolean getNoClip() {
        if (!this.world.isRemote) {
            return this.noClip;
        } else {
            return ((Byte)this.dataManager.get(CRITICAL) & 2) != 0;
        }
    }

    public void setShotFromCrossbow(boolean shotFromCrossbow) {
        this.setArrowFlag(4, shotFromCrossbow);
    }

    public IPacket<?> createSpawnPacket() {
        Entity entity = this.getShooter();
        return new SSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId());
    }

    static {
        CRITICAL = EntityDataManager.createKey(net.minecraft.entity.projectile.AbstractArrowEntity.class, DataSerializers.BYTE);
        PIERCE_LEVEL = EntityDataManager.createKey(net.minecraft.entity.projectile.AbstractArrowEntity.class, DataSerializers.BYTE);
    }

    public static enum PickupStatus {
        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY;

        private PickupStatus() {
        }

        public static AbstractBeamEntity.PickupStatus getByOrdinal(int i) {
            if (i < 0 || i > values().length) {
                i = 0;
            }

            return values()[i];
        }
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

    private boolean func_234615_h_() {
        Entity shooter = this.getShooter();
        if (shooter != null) {
            Iterator var2 = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (entity) -> {
                return !entity.isSpectator() && entity.canBeCollidedWith();
            }).iterator();

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

