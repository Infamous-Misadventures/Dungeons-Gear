package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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

import static com.infamous.dungeons_gear.utilties.AOECloudHelper.spawnSoulProtectionCloudAtPos;

public class TotemOfSoulProtectionItem extends ArtifactItem implements ISoulGatherer {
    public TotemOfSoulProtectionItem(Item.Properties properties) {
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
                if(itemUseContextPlayer.experienceTotal >= 5 || itemUseContextPlayer.isCreative()){
                    if(!itemUseContextPlayer.isCreative()){
                        itemUseContextPlayer.giveExperiencePoints(-5);
                    }
                    spawnSoulProtectionCloudAtPos(itemUseContextPlayer, blockPos, 100);
                    if(!itemUseContextPlayer.isCreative()){
                        itemUseContextItem.damageItem(1, itemUseContextPlayer, (entity) -> {
                            entity.sendBreakAnimation(itemUseContextHand);
                        });
                    }
                    ArtifactItem.setArtifactCooldown(itemUseContextPlayer, itemUseContextItem.getItem(), 20);
                }
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "This totem radiates powerful protective magic."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Creates a space around the totem which, when you or allies die within it, revives the player."));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "5 Seconds Duration"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "1 Second Cooldown"));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE +
                    "+1 XP Gathering"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "Requires 5 XP"));
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 1;
    }
}
