package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.items.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class HarpoonCrossbowItem extends AbstractDungeonsCrossbowItem{
    public HarpoonCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean firesHarpoons(ItemStack itemStack) {
        return itemStack.getItem() instanceof HarpoonCrossbowItem;
    }

    @Override
    public boolean hasPiercingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.NAUTICAL_CROSSBOW.get();
    }
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
