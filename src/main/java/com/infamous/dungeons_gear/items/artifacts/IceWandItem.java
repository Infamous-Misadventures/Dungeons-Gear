package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_libraries.network.BreakItemMessage;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactUseContext;
import com.infamous.dungeons_gear.entities.IceCloudEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.lang3.tuple.MutablePair;

import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;

public class IceWandItem extends ArtifactItem {
    public IceWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        return freezeEntity(stack, playerIn, target);
    }

    @Override
    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
        Player playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();
        final MutablePair<LivingEntity, Double> targetDistance = new MutablePair(null, 123456);
        applyToNearbyEntities(playerIn, 16,
                getCanApplyToEnemyPredicate(playerIn),
                (LivingEntity nearbyEntity) -> {
                    if (targetDistance.getLeft() == null || nearbyEntity.distanceToSqr(playerIn) < targetDistance.getRight()) {
                        targetDistance.setLeft(nearbyEntity);
                        targetDistance.setRight(nearbyEntity.distanceToSqr(playerIn));
                    }
                }
        );
        return new InteractionResultHolder<>(freezeEntity(itemstack, playerIn, targetDistance.getLeft()), itemstack);
    }

    private InteractionResult freezeEntity(ItemStack stack, Player playerIn, LivingEntity target) {
        if (target != null) {
            IceCloudEntity.spawn(playerIn, target);
            stack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), stack)));
            ArtifactItem.putArtifactOnCooldown(playerIn, stack.getItem());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }


    @Override
    public int getCooldownInSeconds() {
        return 20;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }

    @Override
    public InteractionResult useOn(UseOnContext itemUseContext) {
        return InteractionResult.PASS;
    }
}
