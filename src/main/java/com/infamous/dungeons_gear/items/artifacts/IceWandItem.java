package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.entities.IceCloudEntity;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import javafx.util.Pair;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;

import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;

public class IceWandItem extends ArtifactItem {
    public IceWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return freezeEntity(stack, playerIn, target);
    }

    @Override
    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemInHand();
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
        return new ActionResult<>(freezeEntity(itemstack, playerIn, targetDistance.getLeft()), itemstack);
    }

    private ActionResultType freezeEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target) {
        if (target != null) {
            World world = playerIn.getCommandSenderWorld();
            IceCloudEntity iceCloudEntity = new IceCloudEntity(world, playerIn, target);
            world.addFreshEntity(iceCloudEntity);
            stack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), stack)));
            ArtifactItem.putArtifactOnCooldown(playerIn, stack.getItem());
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
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
    public ActionResultType useOn(ItemUseContext itemUseContext) {
        return ActionResultType.PASS;
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return new ActionResult<>(ActionResultType.PASS, playerIn.getItemInHand(handIn));
    }
}
