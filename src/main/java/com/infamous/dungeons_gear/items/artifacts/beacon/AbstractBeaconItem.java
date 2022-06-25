package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.IArtifactUsage;
import com.infamous.dungeons_gear.entities.BeamEntity;
import com.infamous.dungeons_gear.entities.BuzzyNestEntity;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.infamous.dungeons_gear.entities.ModEntityTypes.BEAM_ENTITY;

public abstract class AbstractBeaconItem extends ArtifactItem{

    public static final double RAYTRACE_DISTANCE = 256;
    public static final float BEAM_DAMAGE_PER_TICK = 0.5F; // 10.0F damage per second
    public static final float SOUL_COST_PER_TICK = 0.625F;

    public AbstractBeaconItem(Properties properties) {
        super(properties);
    }

    public abstract boolean canFire(PlayerEntity playerEntity, ItemStack stack);

    public abstract BeamColor getBeamColor();

    @Override
    public ActionResult<ItemStack> procArtifact(ArtifactUseContext iuc) {
        ItemStack itemstack = iuc.getItemInHand();
        PlayerEntity playerIn = iuc.getPlayer();
        World worldIn = iuc.getLevel();

        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(playerIn);
        if(!canFire(playerIn, itemstack) || cap.isUsingArtifact()){
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }

        SoundHelper.playBeaconSound(playerIn, true);
        ArtifactUsageHelper.startUsingArtifact(playerIn, cap, itemstack);
        if (!worldIn.isClientSide) {
            ArtifactItem.triggerSynergy(playerIn, itemstack);
            BeamEntity beamEntity = new BeamEntity(BEAM_ENTITY.get(), this.getBeamColor(), worldIn, playerIn);
            beamEntity.moveTo(playerIn.position().x, playerIn.position().y + 0.7D, playerIn.position().z, playerIn.yRot, playerIn.xRot);
            beamEntity.setOwner(playerIn);
            worldIn.addFreshEntity(beamEntity);
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
            if(playerEntity.isCreative()) return;

            if (this.consumeTick(playerEntity, stack)) {
                if(count % 20 == 0){ // damage the stack every second used, not every tick used
                    stack.hurtAndBreak(1, playerEntity, entity -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
                }
            }else{
                IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(livingEntity);
                if(cap != null && cap.isUsingArtifact()){
                    this.stopUsingArtifact(livingEntity);
                    cap.stopUsingArtifact();
                }
            }
        }
    }

    @Override
    public void stopUsingArtifact(LivingEntity livingEntity) {
        super.stopUsingArtifact(livingEntity);
        List<BeamEntity> beams = livingEntity.level.getEntitiesOfClass(BeamEntity.class, livingEntity.getBoundingBox().inflate(1), beamEntity -> beamEntity.getOwner() == livingEntity);
        beams.forEach(Entity::remove);
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