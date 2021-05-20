package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.combat.PacketBreakItem;
import com.infamous.dungeons_gear.entities.IceCloudEntity;
import com.infamous.dungeons_gear.items.ItemTagWrappers;
import com.infamous.dungeons_gear.utilties.AbilityHelper;
import com.infamous.dungeons_gear.utilties.AreaOfEffectHelper;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;


public class IceWandItem extends ArtifactItem {
    public IceWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return freezeEntity(stack, playerIn, target);
    }

    @Override
    public ActionResult<ItemStack> procArtifact(ItemUseContext c) {
        PlayerEntity playerIn = c.getPlayer();
        Hand handIn = c.getHand();
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        List<LivingEntity> nearbyEntities = c.getWorld().getLoadedEntitiesWithinAABB(LivingEntity.class, playerIn.getBoundingBox().grow(16), (nearbyEntity) -> AbilityHelper.canApplyToEnemy(playerIn, nearbyEntity));
        LivingEntity target = null;
        double dist = 123456;
        if (!nearbyEntities.isEmpty()) {
            for (LivingEntity e : nearbyEntities) {
                if (target == null || e.getDistanceSq(playerIn) < dist) {
                    target = e;
                    dist = e.getDistanceSq(playerIn);
                }
            }
        }
        return new ActionResult<>(freezeEntity(itemstack, playerIn, target), itemstack);
    }

    private ActionResultType freezeEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target) {
        if (target != null) {
            World world = playerIn.getEntityWorld();
            IceCloudEntity iceCloudEntity = new IceCloudEntity(world, playerIn, target);
            world.addEntity(iceCloudEntity);
            stack.damageItem(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getEntityId(), stack)));
            ArtifactItem.setArtifactCooldown(playerIn, stack.getItem());
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
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
    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        return super.onItemUse(itemUseContext);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
