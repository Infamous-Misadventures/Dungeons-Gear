package com.infamous.dungeons_gear.items.ranged.bows;

import com.infamous.dungeons_gear.items.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class HuntingBowItem extends AbstractDungeonsBowItem {

    public HuntingBowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean setsPetsAttackTarget(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasPowerBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.MASTERS_BOW.get();
    }

    @Override
    public boolean hasReplenishBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.HUNTERS_PROMISE.get();
    }

    @Override
    public boolean hasDynamoBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.ANCIENT_BOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
