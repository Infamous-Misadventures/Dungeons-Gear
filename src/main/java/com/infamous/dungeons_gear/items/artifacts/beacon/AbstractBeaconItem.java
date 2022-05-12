package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.IArtifactUsage;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBeaconItem extends ArtifactItem{

    public static final double RAYTRACE_DISTANCE = 256;
    public static final float BEAM_DAMAGE_PER_TICK = 0.5F; // 10.0F damage per second
    public static final float SOUL_COST_PER_TICK = 0.625F;

    public AbstractBeaconItem(Properties properties) {
        super(properties);
    }

    @Nullable
    public static BeaconBeamColor getBeaconBeamColor(ItemStack stack) {
        Item stackItem = stack.getItem();
        return stackItem instanceof AbstractBeaconItem ? ((AbstractBeaconItem) stackItem).getBeamColor() : null;
    }

    public abstract boolean canFire(PlayerEntity playerEntity, ItemStack stack);

    public abstract BeaconBeamColor getBeamColor();

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        if(canFire(playerIn, itemstack)){
            SoundHelper.playBeaconSound(playerIn, true);
        } else{
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }

        if (!worldIn.isClientSide) {
            playerIn.startUsingItem(handIn);
            ArtifactItem.triggerSynergy(playerIn, itemstack);
        }
        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }

    @Override
    public ActionResult<ItemStack> procArtifact(ArtifactUseContext iuc) {
        ItemStack itemstack = iuc.getItemInHand();

        PlayerEntity playerIn = iuc.getPlayer();
        World worldIn = playerIn.level;
        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(playerIn);
        if(!canFire(playerIn, itemstack) || cap.isUsingArtifact()){
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }

        SoundHelper.playBeaconSound(playerIn, true);
        ArtifactUsageHelper.startUsingArtifact(playerIn, cap, itemstack);
        if (!worldIn.isClientSide) {
            ArtifactItem.triggerSynergy(playerIn, itemstack);
        }
        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        SoundHelper.playBeaconSound(entityLiving, false);
    }

    @Override
    public void onUseTick(World world, LivingEntity livingEntity, ItemStack stack, int count) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;

            if (playerEntity.isCreative()|| this.consumeTick(playerEntity, stack)) {
                RayTraceResult result = beamTrace(playerEntity, RAYTRACE_DISTANCE, 1.0f, false);
                Vector3d eyeVector = playerEntity.getEyePosition(1.0f);
                Vector3d lookVector = playerEntity.getViewVector(1.0F);

                Set<LivingEntity> entities = new HashSet<>();
                AxisAlignedBB aabb = new AxisAlignedBB(eyeVector, eyeVector).inflate(1.0D);
                double distance = 0;
                while(eyeVector.distanceTo(aabb.getCenter()) < eyeVector.distanceTo(result.getLocation()) || eyeVector.distanceTo(aabb.getCenter()) < RAYTRACE_DISTANCE){
                    entities.addAll(world.getEntitiesOfClass(LivingEntity.class, aabb));
                    distance += 1.0d;
                    Vector3d targetVector = eyeVector.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
                    aabb = new AxisAlignedBB(targetVector, targetVector).inflate(1.0D);

                }
                if (!world.isClientSide()) {
                    for (LivingEntity entity : entities) {
                        entity.invulnerableTime = 0;
                        entity.hurt(DamageSource.indirectMagic(playerEntity, playerEntity), BEAM_DAMAGE_PER_TICK);
                    }
                }
//                AxisAlignedBB axisalignedbb = playerEntity.getBoundingBox().expandTowards(lookVector.scale(RAYTRACE_DISTANCE)).inflate(1.0D, 1.0D, 1.0D);
//                EntityRayTraceResult entityraytraceresult =
//                        ProjectileHelper.getEntityHitResult(world, playerEntity, eyeVector, targetVector, axisalignedbb,
//                                entity -> entity instanceof LivingEntity && !entity.isSpectator() && entity.isPickable());
//                if (entityraytraceresult != null && result.getLocation().distanceToSqr(eyeVector) > entityraytraceresult.getLocation().distanceToSqr(eyeVector)) {
//                    if (!world.isClientSide()) {
//                        Entity entity = entityraytraceresult.getEntity();
//                        entity.invulnerableTime = 0;
//                        entity.hurt(DamageSource.indirectMagic(playerEntity, playerEntity), BEAM_DAMAGE_PER_TICK);
//                    }
//                }
                if(count % 20 == 0){ // damage the stack every second used, not every tick used
                    stack.hurtAndBreak(1, playerEntity, entity -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
                }
            }
        }
    }

    public static RayTraceResult beamTrace(PlayerEntity playerEntity, double distance, float p_213324_3_, boolean p_213324_4_) {
        Vector3d vector3d = playerEntity.getEyePosition(p_213324_3_);
        Vector3d vector3d1 = playerEntity.getViewVector(p_213324_3_);
        Vector3d vector3d2 = vector3d.add(vector3d1.x * distance, vector3d1.y * distance, vector3d1.z * distance);
        return playerEntity.level.clip(new RayTraceContext(vector3d, vector3d2, RayTraceContext.BlockMode.COLLIDER, p_213324_4_ ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, playerEntity));
    }

    protected abstract boolean consumeTick(PlayerEntity playerEntity, ItemStack stack);

    @Override
    public int getCooldownInSeconds() {
        return 0;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}