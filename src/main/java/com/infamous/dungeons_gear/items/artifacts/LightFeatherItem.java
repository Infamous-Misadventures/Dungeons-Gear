package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.network.PacketBreakItem;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_libraries.utils.PetHelper.isPetOf;

public class LightFeatherItem extends ArtifactItem {
    public LightFeatherItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
        Player playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();
        Level worldIn = c.getLevel();

        // Jump instead of roll
        playerIn.jumpFromGround();

        List<LivingEntity> nearbyEntities = worldIn.getEntitiesOfClass(LivingEntity.class, new AABB(playerIn.getX() - 5, playerIn.getY() - 5, playerIn.getZ() - 5,
                playerIn.getX() + 5, playerIn.getY() + 5, playerIn.getZ() + 5), (nearbyEntity) -> {
            return nearbyEntity != playerIn && !isPetOf(playerIn, nearbyEntity) && nearbyEntity.isAlive();
        });

        PROXY.spawnParticles(playerIn, ParticleTypes.CLOUD);
        for (LivingEntity nearbyEntity : nearbyEntities) {

            // KNOCKBACK
            float knockbackMultiplier = 1.0F;
            double xRatio = playerIn.getX() - nearbyEntity.getX();
            double zRatio;
            for (zRatio = playerIn.getZ() - nearbyEntity.getZ(); xRatio * xRatio + zRatio * zRatio < 1.0E-4D; zRatio = (Math.random() - Math.random()) * 0.01D) {
                xRatio = (Math.random() - Math.random()) * 0.01D;
            }
            nearbyEntity.hurtDir = (float) (Mth.atan2(zRatio, xRatio) * 57.2957763671875D - (double) nearbyEntity.getYRot());
            nearbyEntity.knockback(0.4F * knockbackMultiplier, xRatio, zRatio);
            // END OF KNOCKBACK

            PROXY.spawnParticles(nearbyEntity, ParticleTypes.CLOUD);


            MobEffectInstance stunned = new MobEffectInstance(CustomEffects.STUNNED, this.getDurationInSeconds() * 20);
            MobEffectInstance nausea = new MobEffectInstance(MobEffects.CONFUSION, this.getDurationInSeconds() * 20);
            MobEffectInstance slowness = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, this.getDurationInSeconds() * 20, 4);
            nearbyEntity.addEffect(slowness);
            nearbyEntity.addEffect(nausea);
            nearbyEntity.addEffect(stunned);

        }

        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemstack)));
        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 3;
    }

    @Override
    public int getDurationInSeconds() {
        return 3;
    }
}
