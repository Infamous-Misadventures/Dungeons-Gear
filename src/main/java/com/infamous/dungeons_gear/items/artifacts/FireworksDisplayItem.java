package com.infamous.dungeons_gear.items.artifacts;

import com.infamous.dungeons_gear.entities.FireworksDisplayEntity;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class FireworksDisplayItem extends ArtifactItem {
    public FireworksDisplayItem(Properties properties) {
        super(properties);
        procOnItemUse=true;
    }


    public ActionResult<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
        World world = itemUseContext.getLevel();
        if (world.isClientSide) {
            return ActionResult.success(itemUseContext.getItemStack());
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItemStack();
            PlayerEntity itemUseContextPlayer = itemUseContext.getPlayer();
            BlockPos itemUseContextPos = itemUseContext.getClickedPos();
            Direction itemUseContextFace = itemUseContext.getClickedFace();
            BlockState blockState = world.getBlockState(itemUseContextPos);

            BlockPos blockPos;
            if (blockState.getCollisionShape(world, itemUseContextPos).isEmpty()) {
                blockPos = itemUseContextPos;
            } else {
                blockPos = itemUseContextPos.relative(itemUseContextFace);
            }
            if(itemUseContextPlayer != null) {
                FireworksDisplayEntity totemEntity = ModEntityTypes.FIREWORKS_DISPLAY.get().create(itemUseContextPlayer.level);
                if(totemEntity != null) {
                    totemEntity.moveTo(blockPos, 0, 0);
                    totemEntity.setOwner(itemUseContextPlayer);
                    itemUseContextPlayer.level.addFreshEntity(totemEntity);
//                    itemUseContextItem.hurtAndBreak(1, itemUseContextPlayer, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PacketBreakItem(entity.getId(), itemUseContextItem)));
                    ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer, itemUseContextItem.getItem());
                }
            }
        }
        return ActionResult.consume(itemUseContext.getItemStack());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 60;
    }

    @Override
    public int getDurationInSeconds() {
        return 5;
    }
}
