package com.infamous.dungeons_gear.items.ranged.bows;

import com.infamous.dungeons_gear.items.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class TrickbowItem extends AbstractDungeonsBowItem {

    public TrickbowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean hasGuaranteedRicochet(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasPoisonCloudBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.THE_GREEN_MENACE.get();
    }

    @Override
    public boolean hasWildRageBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.THE_PINK_SCOUNDREL.get()
                || stack.getItem() == ItemRegistry.SUGAR_RUSH.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
