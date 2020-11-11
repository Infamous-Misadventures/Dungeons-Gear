package com.infamous.dungeons_gear.artifacts.beacon;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
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
    public static final int EXPERIENCE_COST_PER_TICK = -1;

    public AbstractBeaconItem(Item.Properties properties) {
        super(properties);
    }

    public abstract BeaconBeamColor getBeamColor();


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote) {
            if (canFire(playerIn)){
                worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        }

        if (!canFire(playerIn)){
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }

        playerIn.setActiveHand(handIn);
        return new ActionResult<>(ActionResultType.PASS, itemstack);
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
        if (entityLiving instanceof PlayerEntity){
            worldIn.playSound((PlayerEntity) entityLiving, entityLiving.getPosition(), SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.PLAYERS, 1.0f, 1.0f);
            entityLiving.resetActiveHand();
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity livingEntity, int count) {
        if(livingEntity instanceof PlayerEntity){
            World world = livingEntity.getEntityWorld();
            PlayerEntity playerEntity = (PlayerEntity)livingEntity;
            if (canFire(playerEntity)) {
                if(!playerEntity.isCreative()){
                    playerEntity.giveExperiencePoints(EXPERIENCE_COST_PER_TICK);
                }
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
                        if (!resetHurtResistantTime(entity)) return;
                        entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(playerEntity, playerEntity), BEAM_DAMAGE_PER_TICK);
                    }
                }
            }
        }
    }

    private boolean resetHurtResistantTime(Entity entity) {
        String hurtResistantTime = "field_70172_ad";
        Integer hurtResistanceTimer = ObfuscationReflectionHelper.getPrivateValue(Entity.class, entity, hurtResistantTime);
        if(hurtResistanceTimer == null){
            DungeonsGear.LOGGER.error("Reflection error for hurtResistantTime!");
            return false;
        }
        if(hurtResistanceTimer > 0){
            ObfuscationReflectionHelper.setPrivateValue(Entity.class, entity, 0, hurtResistantTime);
            return true;
        }
        return true;
    }

    @Nullable
    public static BeaconBeamColor getBeaconBeamColor(ItemStack stack){
        Item stackItem = stack.getItem();
        return stackItem instanceof AbstractBeaconItem ? ((AbstractBeaconItem)stackItem).getBeamColor() :  null;
    }

    public static boolean canFire(PlayerEntity playerEntity) {
        return playerEntity.experienceTotal >= 1 || playerEntity.isCreative();
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

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }
}