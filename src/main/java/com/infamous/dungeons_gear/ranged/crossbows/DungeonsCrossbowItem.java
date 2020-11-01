package com.infamous.dungeons_gear.ranged.crossbows;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class DungeonsCrossbowItem extends AbstractDungeonsCrossbowItem {

    public DungeonsCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean shootsFasterArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasQuickChargeBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.AZURE_SEEKER.get();
    }

    @Override
    public boolean hasPiercingBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.THE_SLICER.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (stack.getItem() == DeferredItemInit.AZURE_SEEKER.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "If the color blue had a sound, it would be shots fired by the Azure Seeker."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Fire Rate (Quick Charge I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Faster Projectiles"));
        }
        if (stack.getItem() == DeferredItemInit.THE_SLICER.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The peak of mischievous Illager-engineering, the famous Slicer was designed to fire bolts that pierce through even the thickest of armor."));

            // Chance to fire piercing bolts
            list.add(new StringTextComponent(TextFormatting.GREEN + "Fires Piercing Bolts (Piercing I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Faster Projectiles"));
        }
    }
}
