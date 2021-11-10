package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class CogCrossbowItem extends AbstractDungeonsCrossbowItem {

    public CogCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean hasWindUpAttack(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasPiercingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.PRIDE_OF_THE_PIGLINS.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
