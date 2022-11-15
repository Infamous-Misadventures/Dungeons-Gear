package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.entities.ArtifactBeamEntity;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsage;
import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_libraries.integration.curios.client.message.CuriosArtifactStopMessage;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_libraries.network.NetworkHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_gear.entities.ModEntityTypes.BEAM_ENTITY;

public abstract class AbstractBeaconItem extends ArtifactItem {

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
        if(!canFire(playerIn, itemstack) || (!worldIn.isClientSide && cap.isUsingArtifact() && cap.getUsingArtifact().getItem().equals(itemstack.getItem()))){
            stopUsingArtifact(playerIn);
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
        }

        SoundHelper.playBeaconSound(playerIn, true);
        ArtifactUsageHelper.startUsingArtifact(playerIn, cap, itemstack);
        if (!worldIn.isClientSide) {
            ArtifactItem.triggerSynergy(playerIn, itemstack);
            ArtifactBeamEntity artifactBeamEntity = new ArtifactBeamEntity(BEAM_ENTITY.get(), this.getBeamColor(), worldIn, playerIn);
            artifactBeamEntity.moveTo(playerIn.position().x, playerIn.position().y + 0.7D, playerIn.position().z, playerIn.getYRot(), playerIn.getXRot());
            artifactBeamEntity.setOwner(playerIn);
            MobEffectInstance slowdown = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 20);
            playerIn.addEffect(slowdown);
            worldIn.addFreshEntity(artifactBeamEntity);
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int count) {
        if (!world.isClientSide() && livingEntity instanceof Player player) {
            if(player.isCreative()) return;

            if (this.consumeTick(player, stack)) {
                MobEffectInstance slowdown = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 20);
                player.addEffect(slowdown);
                if(count % 20 == 0){ // damage the stack every second used, not every tick used
                    stack.hurtAndBreak(1, player, entity -> entity.broadcastBreakEvent(player.getUsedItemHand()));
                }
                if(stack.getDamageValue() >= stack.getMaxDamage()){
                    stopUsingArtifact(player);
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
        if(!livingEntity.level.isClientSide()) {
            ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(livingEntity);
            cap.stopUsingArtifact();
            List<ArtifactBeamEntity> beams = livingEntity.level.getEntitiesOfClass(ArtifactBeamEntity.class, livingEntity.getBoundingBox().inflate(1), artifactBeamEntity -> artifactBeamEntity.getOwner() == livingEntity);
            beams.forEach(artifactBeamEntity -> artifactBeamEntity.remove(Entity.RemovalReason.DISCARDED));
            SoundHelper.playBeaconSound(livingEntity, false);
            if (livingEntity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                livingEntity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            }
            NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity), new CuriosArtifactStopMessage());
        }
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