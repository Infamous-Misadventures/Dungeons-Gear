package com.infamous.dungeons_gear.items.ranged.bows;

import com.infamous.dungeons_gear.items.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class SoulBowItem extends AbstractDungeonsBowItem implements ISoulGatherer {

    public SoulBowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean hasTempoTheftBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.NOCTURNAL_BOW.get()
                || stack.getItem() == ItemRegistry.SHIVERING_BOW.get();
    }

    @Override
    public boolean hasMultishotBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.BOW_OF_LOST_SOULS.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 2;
    }

    @Override
    public int getActivationCost(ItemStack stack) {
        return 0;
    }
}
