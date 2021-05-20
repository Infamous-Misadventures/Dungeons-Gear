package com.infamous.dungeons_gear.ranged.crossbows;

import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

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
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
