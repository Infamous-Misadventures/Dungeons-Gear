package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_gear.utilties.AbilityHelper.isPetOfAttacker;

public class LightFeatherItem extends ArtifactItem {
    public LightFeatherItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItem();
        World worldIn = c.getWorld();

        // Jump instead of roll
        playerIn.jump();

        List<LivingEntity> nearbyEntities = worldIn.getLoadedEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerIn.getPosX() - 5, playerIn.getPosY() - 5, playerIn.getPosZ() - 5,
                playerIn.getPosX() + 5, playerIn.getPosY() + 5, playerIn.getPosZ() + 5), (nearbyEntity) -> {
            return nearbyEntity != playerIn && !isPetOfAttacker(playerIn, nearbyEntity) && nearbyEntity.isAlive();
        });

        PROXY.spawnParticles(playerIn, ParticleTypes.CLOUD);
        for (LivingEntity nearbyEntity : nearbyEntities) {

            // KNOCKBACK
            float knockbackMultiplier = 1.0F;
            double xRatio = playerIn.getPosX() - nearbyEntity.getPosX();
            double zRatio;
            for (zRatio = playerIn.getPosZ() - nearbyEntity.getPosZ(); xRatio * xRatio + zRatio * zRatio < 1.0E-4D; zRatio = (Math.random() - Math.random()) * 0.01D) {
                xRatio = (Math.random() - Math.random()) * 0.01D;
            }
            nearbyEntity.attackedAtYaw = (float) (MathHelper.atan2(zRatio, xRatio) * 57.2957763671875D - (double) nearbyEntity.rotationYaw);
            nearbyEntity.applyKnockback(0.4F * knockbackMultiplier, xRatio, zRatio);
            // END OF KNOCKBACK

            PROXY.spawnParticles(nearbyEntity, ParticleTypes.CLOUD);


            EffectInstance stunned = new EffectInstance(CustomEffects.STUNNED, this.getDurationInSeconds() * 20);
            EffectInstance nausea = new EffectInstance(Effects.NAUSEA, this.getDurationInSeconds() * 20);
            EffectInstance slowness = new EffectInstance(Effects.SLOWNESS, this.getDurationInSeconds() * 20, 4);
            nearbyEntity.addPotionEffect(slowness);
            nearbyEntity.addPotionEffect(nausea);
            nearbyEntity.addPotionEffect(stunned);

        }

        itemstack.damageItem(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), itemstack)));
        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
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
