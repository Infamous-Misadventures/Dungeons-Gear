package com.infamous.dungeons_gear.ranged.bows;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class SnowBowItem extends AbstractDungeonsBowItem {

    public SnowBowItem(Item.Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean shootsFreezingArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasMultishotWhenCharged(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.WINTERS_TOUCH.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == DeferredItemInit.SNOW_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Those who face the Snow Bow in battle must also prepare to face the chill of freezing wintry winds."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Freezes On Impact"));
        }
        if(stack.getItem() == DeferredItemInit.WINTERS_TOUCH.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Arrows fired from this legendary bow are said to be carried by the winter winds themselves."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Fires 3 Arrows When Charged"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Freezes On Impact"));
        }
    }
}
