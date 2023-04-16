package com.infamous.dungeons_gear.entities;

import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.BeamColor;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.entity.PlayerBeamMessage;
import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsage;
import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsageHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;

public class ArtifactBeamEntity extends Entity implements IEntityAdditionalSpawnData {
    public static final double MAX_RAYTRACE_DISTANCE = 256;
    public static final float BEAM_DAMAGE_PER_TICK = 0.5F; // 10.0F damage per second
    private LivingEntity owner;
    private UUID ownerUUID;
    private BeamColor beamColor;
    private final float beamWidth = 0.2f;


    public ArtifactBeamEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }


    public ArtifactBeamEntity(EntityType<?> p_i48580_1_, BeamColor beamColor, Level p_i48580_2_, LivingEntity owner) {
        super(p_i48580_1_, p_i48580_2_);
        this.setOwner(owner);
        this.beamColor = beamColor;
    }

    public BeamColor getBeamColor() {
        if (beamColor == null)
            return new BeamColor((short) 90, (short) 0, (short) 90, (short) 255, (short) 255, (short) 255);
        return beamColor;
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
        if (owner != null) {
            this.ownerUUID = owner.getUUID();
            updatePositionAndRotation();
        }
    }

    @Override
    public void tick() {
        LivingEntity owner = getOwner();
        if (!this.level.isClientSide) {
            if (owner == null || !owner.isAlive()) {
                this.remove(RemovalReason.DISCARDED);
                return;
            }
            ArtifactUsage artifactUsage = ArtifactUsageHelper.getArtifactUsageCapability(owner);
            if (!artifactUsage.isUsingArtifact() || !(artifactUsage.getUsingArtifact().getItem() instanceof AbstractBeaconItem)) {
                this.remove(RemovalReason.DISCARDED);
                return;
            }
        }
        if (this.owner instanceof Player && this.level.isClientSide()) {
            updatePositionAndRotation();
            NetworkHandler.INSTANCE.sendToServer(new PlayerBeamMessage(this));
        } else if (!(this.owner instanceof Player) && !this.level.isClientSide()) {
            updatePositionAndRotation();
        }

        if (!this.level.isClientSide()) {
            Set<LivingEntity> entities = new HashSet<>();
            AABB aabb = new AABB(this.position(), this.position()).inflate(beamWidth);
            double distanceToDestination = beamTraceDistance(MAX_RAYTRACE_DISTANCE, 1.0f, false);
            double distanceTraveled = 0;
            while (true) {
                if (this.position().distanceTo(aabb.getCenter()) > distanceToDestination || this.position().distanceTo(aabb.getCenter()) > MAX_RAYTRACE_DISTANCE)
                    break;
                entities.addAll(this.level.getEntitiesOfClass(LivingEntity.class, aabb, getCanApplyToEnemyPredicate(owner)));
                distanceTraveled += 1.0d;
                Vec3 viewVector = this.getViewVector(1.0F);
                Vec3 targetVector = this.position().add(viewVector.x * distanceTraveled, viewVector.y * distanceTraveled, viewVector.z * distanceTraveled);
                aabb = new AABB(targetVector, targetVector).inflate(1.0D);
            }
            for (LivingEntity entity : entities) {
                entity.invulnerableTime = 0;
                Vec3 deltaMovement = entity.getDeltaMovement();
                entity.hurt(DamageSource.indirectMagic(owner, owner), BEAM_DAMAGE_PER_TICK);
                entity.setDeltaMovement(deltaMovement);
            }
        }
    }

    public void updatePositionAndRotation() {
        LivingEntity owner = this.getOwner();
        Vec3 vec1 = owner.position();
        vec1 = vec1.add(this.getOffsetVector());
        this.setPos(vec1.x, vec1.y, vec1.z);
        this.setYRot(boundDegrees(this.owner.getYRot()));
        this.setXRot(boundDegrees(this.owner.getXRot()));
        this.yRotO = boundDegrees(this.owner.yRotO);
        this.xRotO = boundDegrees(this.owner.xRotO);
    }

    private float boundDegrees(float v) {
        return (v % 360 + 360) % 360;
    }

    private Vec3 getOffsetVector() {
        Vec3 viewVector = this.getViewVector(1.0F);
        return new Vec3(viewVector.x, getOwner().getEyeHeight() * 0.8D, viewVector.z);
    }

    public float getBeamWidth() {
        return beamWidth;
    }

    public final Vec3 getWorldPosition(float p_242282_1_) {
        double d0 = Mth.lerp(p_242282_1_, this.xo, this.getX());
        double d1 = Mth.lerp(p_242282_1_, this.yo, this.getY());
        double d2 = Mth.lerp(p_242282_1_, this.zo, this.getZ());
        return new Vec3(d0, d1, d2);
    }

    public HitResult beamTraceResult(double distance, float ticks, boolean passesWater) {
        Vec3 vector3d = this.getWorldPosition(ticks);
        Vec3 vector3d1 = this.getViewVector(ticks);
        Vec3 vector3d2 = vector3d.add(vector3d1.x * distance, vector3d1.y * distance, vector3d1.z * distance);
        return level.clip(new ClipContext(vector3d, vector3d2, ClipContext.Block.COLLIDER, passesWater ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, this));
    }

    public double beamTraceDistance(double distance, float ticks, boolean passesWater) {
        HitResult rayTraceResult = beamTraceResult(distance, ticks, passesWater);
        double distanceToDestination = MAX_RAYTRACE_DISTANCE;
        if (rayTraceResult instanceof BlockHitResult) {
            BlockPos collision = ((BlockHitResult) rayTraceResult).getBlockPos();
            Vec3 destination = new Vec3(collision.getX(), collision.getY(), collision.getZ());
            distanceToDestination = this.position().distanceTo(destination);
        }
        return distanceToDestination;
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null) {
            if (this.level instanceof ServerLevel) {
                Entity entity = ((ServerLevel) this.level).getEntity(this.ownerUUID);
                if (entity instanceof LivingEntity) {
                    this.owner = (LivingEntity) entity;
                }
            } else if (this.level.isClientSide) {
                this.owner = this.level.getPlayerByUUID(this.ownerUUID);
            }
        }
        return this.owner;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.hasUUID("Owner")) {
            this.ownerUUID = pCompound.getUUID("Owner");
        }
        this.beamColor = BeamColor.load(pCompound.getCompound("BeamColor"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        if (this.ownerUUID != null) {
            pCompound.putUUID("Owner", this.ownerUUID);
        }
        pCompound.put("BeamColor", this.getBeamColor().save(new CompoundTag()));
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
    	if (this.ownerUUID != null) {
    		buffer.writeUUID(this.ownerUUID);
    	}
        buffer.writeShort(this.getBeamColor().getRedValue());
        buffer.writeShort(this.getBeamColor().getGreenValue());
        buffer.writeShort(this.getBeamColor().getBlueValue());
        buffer.writeShort(this.getBeamColor().getInnerRedValue());
        buffer.writeShort(this.getBeamColor().getInnerGreenValue());
        buffer.writeShort(this.getBeamColor().getInnerBlueValue());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.ownerUUID = additionalData.readUUID();
        this.beamColor = new BeamColor(
                additionalData.readShort(),
                additionalData.readShort(),
                additionalData.readShort(),
                additionalData.readShort(),
                additionalData.readShort(),
                additionalData.readShort()
        );
    }
}
