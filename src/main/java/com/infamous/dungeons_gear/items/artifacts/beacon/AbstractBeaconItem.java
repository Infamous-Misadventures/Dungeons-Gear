package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsage;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.entities.BeamEntity;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

import static com.infamous.dungeons_gear.entities.ModEntityTypes.BEAM_ENTITY;

public abstract class AbstractBeaconItem extends ArtifactItem{

    public static final double RAYTRACE_DISTANCE = 256;
    public static final float BEAM_DAMAGE_PER_TICK = 0.5F; // 10.0F damage per second
    public static final float SOUL_COST_PER_TICK = 0.625F;

    public AbstractBeaconItem(Properties properties) {
        super(properties);
    }

    public abstract boolean canFire(Player playerEntity, ItemStack stack);

    public abstract BeamColor getBeamColor();

    @Override
    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext iuc) {
        ItemStack itemstack = iuc.getItemStack();
        Player playerIn = iuc.getPlayer();
        Level worldIn = iuc.getLevel();

        ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(playerIn);
        if(!canFire(playerIn, itemstack) || cap.isUsingArtifact()){
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }

        SoundHelper.playBeaconSound(playerIn, true);
        ArtifactUsageHelper.startUsingArtifact(playerIn, cap, itemstack);
        if (!worldIn.isClientSide) {
            ArtifactItem.triggerSynergy(playerIn, itemstack);
            BeamEntity beamEntity = new BeamEntity(BEAM_ENTITY.get(), this.getBeamColor(), worldIn, playerIn);
            beamEntity.moveTo(playerIn.position().x, playerIn.position().y + 0.7D, playerIn.position().z, playerIn.getYRot(), playerIn.getXRot());
            beamEntity.setOwner(playerIn);
            worldIn.addFreshEntity(beamEntity);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        SoundHelper.playBeaconSound(entityLiving, false);
    }

    @Override
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int count) {
        if (livingEntity instanceof Player) {
            Player playerEntity = (Player) livingEntity;
            if(playerEntity.isCreative()) return;

            if (this.consumeTick(playerEntity, stack)) {
                if(count % 20 == 0){ // damage the stack every second used, not every tick used
                    stack.hurtAndBreak(1, playerEntity, entity -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
                }
            }else{
                ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(livingEntity);
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
        beams.forEach(beamEntity -> beamEntity.remove(Entity.RemovalReason.DISCARDED));
    }


    protected abstract boolean consumeTick(Player playerEntity, ItemStack stack);

    @Override
    public int getCooldownInSeconds() {
        return 0;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}