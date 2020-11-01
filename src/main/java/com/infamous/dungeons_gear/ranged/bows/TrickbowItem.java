package com.infamous.dungeons_gear.ranged.bows;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

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
        return stack.getItem() == DeferredItemInit.THE_GREEN_MENACE.get();
    }

    @Override
    public boolean hasWildRageBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.THE_PINK_SCOUNDREL.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == DeferredItemInit.THE_GREEN_MENACE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The arrows fired from the Green Menace always hit their intended target, even in the thickest fog."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Spawns Poison Clouds (Poison Cloud I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Hits Multiple Targets"));
        }
        if(stack.getItem() == DeferredItemInit.THE_PINK_SCOUNDREL.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "It would be a deadly mistake to underestimate the power of the Pink Scoundrel."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Chance To Enrage Mobs (Wild Rage I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Hits Multiple Targets"));
        }
        if(stack.getItem() == DeferredItemInit.TRICKBOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A sleek bow that seems to never miss its target."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Hits Multiple Targets"));
        }
    }
}
