package com.infamous.dungeons_gear.artifacts.beacon;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;

public abstract class AbstractBeaconItem extends ArtifactItem implements ISoulGatherer {

    public static final double RAYTRACE_DISTANCE = 256;
    public static final float BEAM_DAMAGE_PER_TICK = 0.5F; // 10.0F damage per second
    public static final float EXPERIENCE_COST_PER_TICK = 0.625f;

    public AbstractBeaconItem(Properties properties) {
        super(properties);
    }

    @Nullable
    public static BeaconBeamColor getBeaconBeamColor(ItemStack stack) {
        Item stackItem = stack.getItem();
        return stackItem instanceof AbstractBeaconItem ? ((AbstractBeaconItem) stackItem).getBeamColor() : null;
    }

    public static boolean canFire(PlayerEntity playerEntity, ItemStack stack) {
        ISoulGatherer soulGatherer = stack.getItem() instanceof ISoulGatherer ? ((ISoulGatherer) stack.getItem()) : null;
        if (soulGatherer != null) {
            return CapabilityHelper.getComboCapability(playerEntity).getSouls() >= soulGatherer.getActivationCost(stack) || playerEntity.isCreative();
        }
        return false;
    }

    public static ItemStack getBeacon(PlayerEntity player) {
        ItemStack heldItem = player.getHeldItemMainhand();
        if (!(heldItem.getItem() instanceof AbstractBeaconItem)) {
            heldItem = player.getHeldItemOffhand();
            if (!(heldItem.getItem() instanceof AbstractBeaconItem)) {
                return ItemStack.EMPTY;
            }
        }
        return heldItem;
    }

    public abstract BeaconBeamColor getBeamColor();

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote) {
            if (canFire(playerIn, itemstack)) {
                SoundHelper.playBeaconSound(playerIn, true);
            }
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        }

        if (!canFire(playerIn, itemstack)) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }

        playerIn.setActiveHand(handIn);
        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }

    @Override
    public ActionResult<ItemStack> procArtifact(ItemUseContext iuc) {
        return new ActionResult<>(ActionResultType.PASS, iuc.getItem());
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            SoundHelper.playBeaconSound(entityLiving, false);
            entityLiving.resetActiveHand();
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity livingEntity, int count) {
        if (livingEntity instanceof PlayerEntity) {
            World world = livingEntity.getEntityWorld();
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            if (playerEntity.isCreative()||CapabilityHelper.getComboCapability(playerEntity).consumeSouls(EXPERIENCE_COST_PER_TICK)) {
                RayTraceResult result = playerEntity.pick(RAYTRACE_DISTANCE, 1.0f, false);
                Vector3d eyeVector = playerEntity.getEyePosition(1.0f);
                Vector3d lookVector = playerEntity.getLook(1.0F);
                Vector3d targetVector = eyeVector.add(lookVector.x * RAYTRACE_DISTANCE, lookVector.y * RAYTRACE_DISTANCE, lookVector.z * RAYTRACE_DISTANCE);
                AxisAlignedBB axisalignedbb = playerEntity.getBoundingBox().expand(lookVector.scale(RAYTRACE_DISTANCE)).grow(1.0D, 1.0D, 1.0D);
                EntityRayTraceResult entityraytraceresult =
                        ProjectileHelper.rayTraceEntities(world, playerEntity, eyeVector, targetVector, axisalignedbb,
                                entity -> entity instanceof LivingEntity && !entity.isSpectator() && entity.canBeCollidedWith());
                if (entityraytraceresult != null && result.getHitVec().squareDistanceTo(eyeVector) > entityraytraceresult.getHitVec().squareDistanceTo(eyeVector)) {
                    stack.damageItem(1, playerEntity, entity -> entity.sendBreakAnimation(playerEntity.getActiveHand()));
                    if (!world.isRemote()) {
                        Entity entity = entityraytraceresult.getEntity();
                        //if (!resetHurtResistantTime(entity)) return;
                        entity.hurtResistantTime = 0;
                        entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(playerEntity, playerEntity), BEAM_DAMAGE_PER_TICK);
                    }
                }
            }
        }
    }

    private boolean resetHurtResistantTime(Entity entity) {
        String hurtResistantTime = "field_70172_ad";
        Integer hurtResistanceTimer = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, hurtResistantTime);
        if (hurtResistanceTimer == null) {
            DungeonsGear.LOGGER.error("Reflection error for hurtResistantTime!");
            return false;
        }
        if (hurtResistanceTimer > 0) {
            ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, 0, hurtResistantTime);
            return true;
        }
        return true;
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }

    @Override
    public int getCooldownInSeconds() {
        return 0;
    }

    @Override
    public int getActivationCost(ItemStack stack) {
        return 1;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}