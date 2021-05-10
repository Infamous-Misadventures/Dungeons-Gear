package com.infamous.dungeons_gear.ranged.bows;

import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class LongbowItem extends AbstractDungeonsBowItem {

    public LongbowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean shootsStrongChargedArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasFuseShotBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.RED_SNAKE.get();
    }

    @Override
    public boolean hasSuperChargedBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.GUARDIAN_BOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
