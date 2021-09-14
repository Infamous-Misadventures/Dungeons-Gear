package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
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

import javax.annotation.Nullable;

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

        if(canFire(playerIn, itemstack)){
            SoundHelper.playBeaconSound(playerIn, true);
        } else{
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }

        if (!worldIn.isRemote) {
            playerIn.setActiveHand(handIn);
            ArtifactItem.triggerSynergy(playerIn, itemstack);
        }
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
        SoundHelper.playBeaconSound(entityLiving, false);
    }

    @Override
    public void onUse(World world, LivingEntity livingEntity, ItemStack stack, int count) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;

            if (playerEntity.isCreative()|| this.consumeTick(playerEntity)) {
                RayTraceResult result = playerEntity.pick(RAYTRACE_DISTANCE, 1.0f, false);
                Vector3d eyeVector = playerEntity.getEyePosition(1.0f);
                Vector3d lookVector = playerEntity.getLook(1.0F);
                Vector3d targetVector = eyeVector.add(lookVector.x * RAYTRACE_DISTANCE, lookVector.y * RAYTRACE_DISTANCE, lookVector.z * RAYTRACE_DISTANCE);
                AxisAlignedBB axisalignedbb = playerEntity.getBoundingBox().expand(lookVector.scale(RAYTRACE_DISTANCE)).grow(1.0D, 1.0D, 1.0D);
                EntityRayTraceResult entityraytraceresult =
                        ProjectileHelper.rayTraceEntities(world, playerEntity, eyeVector, targetVector, axisalignedbb,
                                entity -> entity instanceof LivingEntity && !entity.isSpectator() && entity.canBeCollidedWith());
                if (entityraytraceresult != null && result.getHitVec().squareDistanceTo(eyeVector) > entityraytraceresult.getHitVec().squareDistanceTo(eyeVector)) {
                    if (!world.isRemote()) {
                        Entity entity = entityraytraceresult.getEntity();
                        entity.hurtResistantTime = 0;
                        entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(playerEntity, playerEntity), BEAM_DAMAGE_PER_TICK);
                    }
                }
                if(count % 20 == 0){ // damage the stack every second used, not every tick used
                    stack.damageItem(1, playerEntity, entity -> entity.sendBreakAnimation(playerEntity.getActiveHand()));
                }
            }
        }
    }

    protected abstract boolean consumeTick(PlayerEntity playerEntity);

    @Override
    public int getCooldownInSeconds() {
        return 0;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}