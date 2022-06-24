package com.infamous.dungeons_gear.entities;

import com.infamous.dungeons_gear.items.artifacts.beacon.BeamColor;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.entity.PlayerBeamMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;

public class BeamEntity extends Entity implements IEntityAdditionalSpawnData {
    public static final double MAX_RAYTRACE_DISTANCE = 256;
    public static final float BEAM_DAMAGE_PER_TICK = 0.5F; // 10.0F damage per second
    private LivingEntity owner;
    private UUID ownerUUID;
    private BeamColor beamColor;
    private float beamWidth = 0.2f;


    public BeamEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }


    public BeamEntity(EntityType<?> p_i48580_1_, BeamColor beamColor, World p_i48580_2_, LivingEntity owner) {
        super(p_i48580_1_, p_i48580_2_);
        this.setOwner(owner);
        this.beamColor = beamColor;
    }

    public BeamColor getBeamColor() {
        if(beamColor == null) return new BeamColor((short) 90, (short) 0, (short) 90, (short) 255, (short) 255, (short) 255);
        return beamColor;
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
        if(owner != null){
            this.ownerUUID = owner.getUUID();
            updatePositionAndRotation();
        }
    }

    @Override
    public void tick() {
        LivingEntity owner = getOwner();
        if(!this.level.isClientSide) {
            if (owner == null) {
                this.remove();
                return;
            }
            if (!owner.isAlive()) {
                this.remove();
                return;
            }
        }
        if (this.owner instanceof PlayerEntity && this.level.isClientSide()){
            updatePositionAndRotation();
            NetworkHandler.INSTANCE.sendToServer(new PlayerBeamMessage(this));
        }else if (!(this.owner instanceof PlayerEntity) && !this.level.isClientSide()) {
            updatePositionAndRotation();
        }

        if(!this.level.isClientSide()){
            Set<LivingEntity> entities = new HashSet<>();
            AxisAlignedBB aabb = new AxisAlignedBB(this.position(), this.position()).inflate(beamWidth);
            double distanceToDestination = beamTraceDistance(MAX_RAYTRACE_DISTANCE, 1.0f, false);
            double distanceTraveled = 0;
            while (true) {
                if (this.position().distanceTo(aabb.getCenter()) > distanceToDestination || this.position().distanceTo(aabb.getCenter()) > MAX_RAYTRACE_DISTANCE)
                    break;
                entities.addAll(this.level.getLoadedEntitiesOfClass(LivingEntity.class, aabb, getCanApplyToEnemyPredicate(owner)));
                distanceTraveled += 1.0d;
                Vector3d viewVector = this.getViewVector(1.0F);
                Vector3d targetVector = this.position().add(viewVector.x * distanceTraveled, viewVector.y * distanceTraveled, viewVector.z * distanceTraveled);
                aabb = new AxisAlignedBB(targetVector, targetVector).inflate(1.0D);
            }
            for (LivingEntity entity : entities) {
                entity.invulnerableTime = 0;
                Vector3d deltaMovement = entity.getDeltaMovement();
                entity.hurt(DamageSource.indirectMagic(owner, owner), BEAM_DAMAGE_PER_TICK);
                entity.setDeltaMovement(deltaMovement);
            }
        }
    }

    public void updatePositionAndRotation() {
        LivingEntity owner = this.getOwner();
        Vector3d vec1 = owner.position();
        vec1 = vec1.add(this.getOffsetVector());
        this.setPos(vec1.x, vec1.y, vec1.z);
        this.yRot = boundDegrees(this.owner.yRot);
        this.xRot = boundDegrees(this.owner.xRot);
        this.yRotO = boundDegrees(this.owner.yRotO);
        this.xRotO = boundDegrees(this.owner.xRotO);
    }

    private float boundDegrees(float v){
        return (v % 360 + 360) % 360;
    }

    private Vector3d getOffsetVector() {
        Vector3d viewVector = this.getViewVector(1.0F);
        return new Vector3d(viewVector.x, getOwner().getEyeHeight()*0.8D, viewVector.z);
    }

    public float getBeamWidth() {
        return beamWidth;
    }

    public RayTraceResult beamTraceResult(double distance, float ticks, boolean passesWater) {
        Vector3d vector3d = this.getPosition(ticks);
        Vector3d vector3d1 = this.getViewVector(ticks);
        Vector3d vector3d2 = vector3d.add(vector3d1.x * distance, vector3d1.y * distance, vector3d1.z * distance);
        return level.clip(new RayTraceContext(vector3d, vector3d2, RayTraceContext.BlockMode.COLLIDER, passesWater ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, this));
    }

    public double beamTraceDistance(double distance, float ticks, boolean passesWater) {
        RayTraceResult rayTraceResult = beamTraceResult(distance, ticks, passesWater);
        double distanceToDestination = MAX_RAYTRACE_DISTANCE;
        if(rayTraceResult instanceof BlockRayTraceResult) {
            BlockPos collision = ((BlockRayTraceResult) rayTraceResult).getBlockPos();
            Vector3d destination = new Vector3d(collision.getX(), collision.getY(), collision.getZ());
            distanceToDestination = this.position().distanceTo(destination);
        }
        return distanceToDestination;
    }

    @Nullable
    public LivingEntity getOwner() {
        if(this.owner == null && this.ownerUUID != null){
            if(this.level instanceof ServerWorld) {
                Entity entity = ((ServerWorld) this.level).getEntity(this.ownerUUID);
                if (entity instanceof LivingEntity) {
                    this.owner = (LivingEntity) entity;
                }
            } else if(this.level.isClientSide) {
                this.owner = this.level.getPlayerByUUID(this.ownerUUID);
            }
        }
        return this.owner;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT pCompound) {
        if (pCompound.hasUUID("Owner")) {
            this.ownerUUID = pCompound.getUUID("Owner");
        }
        this.beamColor = BeamColor.load(pCompound.getCompound("BeamColor"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT pCompound) {
        if (this.ownerUUID != null) {
            pCompound.putUUID("Owner", this.ownerUUID);
        }
        pCompound.put("BeamColor", this.getBeamColor().save(new CompoundNBT()));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeUUID(this.ownerUUID);
        buffer.writeShort(this.getBeamColor().getRedValue());
        buffer.writeShort(this.getBeamColor().getGreenValue());
        buffer.writeShort(this.getBeamColor().getBlueValue());
        buffer.writeShort(this.getBeamColor().getInnerRedValue());
        buffer.writeShort(this.getBeamColor().getInnerGreenValue());
        buffer.writeShort(this.getBeamColor().getInnerBlueValue());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
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
