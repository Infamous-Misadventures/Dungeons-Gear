package com.infamous.dungeons_gear.artifacts;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.utilties.AOECloudHelper.spawnShieldingCloudAtPos;

public class TotemOfShieldingItem extends ArtifactItem {
    public TotemOfShieldingItem(Properties properties) {
        super(properties);
    }

    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        World world = itemUseContext.getWorld();
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemUseContextItem = itemUseContext.getItem();
            PlayerEntity itemUseContextPlayer = itemUseContext.getPlayer();
            Hand itemUseContextHand = itemUseContext.getHand();
            BlockPos itemUseContextPos = itemUseContext.getPos();
            Direction itemUseContextFace = itemUseContext.getFace();
            BlockState blockState = world.getBlockState(itemUseContextPos);

            BlockPos blockPos;
            if (blockState.getCollisionShape(world, itemUseContextPos).isEmpty()) {
                blockPos = itemUseContextPos;
            } else {
                blockPos = itemUseContextPos.offset(itemUseContextFace);
            }
            if(itemUseContextPlayer != null) {

                spawnShieldingCloudAtPos(itemUseContextPlayer, blockPos, 100);
                if(!itemUseContextPlayer.isCreative()){
                    itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> {
                        entity.sendBreakAnimation(itemUseContextHand);
                    });
                }
                ArtifactItem.setArtifactCooldown(itemUseContextPlayer, itemUseContextItem.getItem(), 400);
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "This totem radiates powerful energy that bursts forth as a protective shield around those near it."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "This totem has mystical powers that shield those around it from projectiles."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "5 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "20 Seconds Cooldown"));
    }
}
