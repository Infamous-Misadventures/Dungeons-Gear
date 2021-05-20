package com.infamous.dungeons_gear.ranged.crossbows;

import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class DungeonsCrossbowItem extends AbstractDungeonsCrossbowItem {

    public DungeonsCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean shootsFasterArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasQuickChargeBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.AZURE_SEEKER.get();
    }

    @Override
    public boolean hasPiercingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.THE_SLICER.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
