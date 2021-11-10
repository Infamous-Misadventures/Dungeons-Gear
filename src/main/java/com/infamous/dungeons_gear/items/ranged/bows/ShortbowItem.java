package com.infamous.dungeons_gear.items.ranged.bows;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class ShortbowItem extends AbstractDungeonsBowItem {

    public ShortbowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean hasQuickChargeBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.PURPLE_STORM.get();
    }

    @Override
    public boolean hasAccelerateBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.MECHANICAL_SHORTBOW.get();
    }

    @Override
    public boolean hasRadianceShotBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.LOVE_SPELL_BOW.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
