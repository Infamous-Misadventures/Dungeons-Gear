package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.entities.ArtifactBeamEntity;
import com.infamous.dungeons_libraries.network.NetworkHandler;
import com.infamous.dungeons_gear.utilties.SoundHelper;
import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_libraries.capabilities.artifact.IArtifactUsage;
import com.infamous.dungeons_libraries.integration.curios.client.message.CuriosArtifactStopMessage;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.infamous.dungeons_gear.registry.ModEntityTypes.BEAM_ENTITY;

import java.util.List;

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
        ItemStack itemstack = iuc.getItemStack();
        PlayerEntity playerIn = iuc.getPlayer();
        World worldIn = iuc.getLevel();

        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(playerIn);

        if(!canFire(playerIn, itemstack) || (!worldIn.isClientSide && cap.isUsingArtifact() && cap.getUsingArtifact().getItem().equals(itemstack.getItem()))){
            stopUsingArtifact(playerIn);
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        }

        SoundHelper.playBeaconSound(playerIn, true);
        ArtifactUsageHelper.startUsingArtifact(playerIn, cap, itemstack);
        if (!worldIn.isClientSide) {
            ArtifactItem.triggerSynergy(playerIn, itemstack);
            ArtifactBeamEntity artifactBeamEntity = new ArtifactBeamEntity(BEAM_ENTITY.get(), this.getBeamColor(), worldIn, playerIn);
            artifactBeamEntity.moveTo(playerIn.position().x, playerIn.position().y + 0.7D, playerIn.position().z, playerIn.yRot, playerIn.xRot);
            artifactBeamEntity.setOwner(playerIn);
            EffectInstance slowdown = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 20);
            playerIn.addEffect(slowdown);
            worldIn.addFreshEntity(artifactBeamEntity);
        }
        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onUseTick(World world, LivingEntity livingEntity, ItemStack stack, int count) {
        if (livingEntity instanceof PlayerEntity && !world.isClientSide()) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            if(playerEntity.isCreative()) return;

            if (this.consumeTick(playerEntity, stack)) {
                EffectInstance slowdown = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 20);
                playerEntity.addEffect(slowdown);
                if(count % 20 == 0){ // damage the stack every second used, not every tick used
                    stack.hurtAndBreak(1, playerEntity, entity -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
                    if(stack.getDamageValue() >= stack.getMaxDamage()){
                        stopUsingArtifact(playerEntity);
                    }
                }
            }else{
                IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(livingEntity);
                if(cap != null && cap.isUsingArtifact()){
                    this.stopUsingArtifact(livingEntity);
                }
            }
        }
    }

    @Override
    public void stopUsingArtifact(LivingEntity livingEntity) {
        super.stopUsingArtifact(livingEntity);
        if(!livingEntity.level.isClientSide()) {
            IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(livingEntity);
            cap.stopUsingArtifact();
            List<ArtifactBeamEntity> beams = livingEntity.level.getEntitiesOfClass(ArtifactBeamEntity.class, livingEntity.getBoundingBox().inflate(1), artifactBeamEntity -> artifactBeamEntity.getOwner() == livingEntity);
            beams.forEach(Entity::remove);
            SoundHelper.playBeaconSound(livingEntity, false);
            if(livingEntity.hasEffect(Effects.MOVEMENT_SLOWDOWN)){
                livingEntity.removeEffect(Effects.MOVEMENT_SLOWDOWN);
            }
            NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity), new CuriosArtifactStopMessage());
        }
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