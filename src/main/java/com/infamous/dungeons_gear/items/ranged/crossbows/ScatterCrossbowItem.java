package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

import net.minecraft.item.Item.Properties;

public class ScatterCrossbowItem extends AbstractDungeonsCrossbowItem {

    public ScatterCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean hasMultishotBuiltIn(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasExtraMultishot(ItemStack stack){
        return stack.getItem() == ItemRegistry.HARP_CROSSBOW.get();
    }

    @Override
    public boolean hasPiercingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.LIGHTNING_HARP_CROSSBOW.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
