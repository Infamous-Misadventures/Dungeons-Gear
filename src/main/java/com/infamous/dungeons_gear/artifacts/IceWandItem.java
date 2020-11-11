package com.infamous.dungeons_gear.artifacts;

import com.infamous.dungeons_gear.entities.IceCloudEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;


public class IceWandItem extends ArtifactItem {
    public IceWandItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if(target != null){
            World world = playerIn.getEntityWorld();
            IceCloudEntity iceCloudEntity = new IceCloudEntity(world, playerIn, target);
            world.addEntity(iceCloudEntity);
            stack.damageItem(1, playerIn, playerEntity -> playerEntity.sendBreakAnimation(hand));
            return ActionResultType.SUCCESS;
        }
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC +
                    "The Ice Wand was trapped in a tomb of ice for ages, sealed away by those who feared its power."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Creates large ice blocks that can crush your foes."));
            list.add(new StringTextComponent(TextFormatting.GREEN +
                    "Stuns Mobs"));
            list.add(new StringTextComponent(TextFormatting.BLUE +
                    "20 Seconds Cooldown"));
    }
}
