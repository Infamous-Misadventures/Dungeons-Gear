package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class SoulCrossbowItem extends AbstractDungeonsCrossbowItem implements ISoulGatherer {

    public SoulCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean hasEnigmaResonatorBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.FERAL_SOUL_CROSSBOW.get();
    }

    @Override
    public boolean hasGravityBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.VOIDCALLER.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 2;
    }

    @Override
    public float getActivationCost(ItemStack stack) {
        return 0;
    }
}
