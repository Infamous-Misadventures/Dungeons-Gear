package com.infamous.dungeons_gear.items.ranged.bows;

import com.infamous.dungeons_gear.items.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class SnowBowItem extends AbstractDungeonsBowItem {

    public SnowBowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean shootsFreezingArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasMultishotWhenCharged(ItemStack stack) {
        return stack.getItem() == ItemRegistry.WINTERS_TOUCH.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
