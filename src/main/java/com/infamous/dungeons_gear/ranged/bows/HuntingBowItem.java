package com.infamous.dungeons_gear.ranged.bows;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

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
        return stack.getItem() == MASTERS_BOW;
    }

    @Override
    public boolean hasReplenishBuiltIn(ItemStack stack) {
        return stack.getItem() == HUNTERS_PROMISE;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == HUNTERS_PROMISE){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This enchanted bow deepens the bond between the hunter and their trusted animal companion."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pets Attack Targeted Mobs"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Chance To Regain Arrows (Replenish I)"));
        }
        if(stack.getItem() == MASTERS_BOW){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The bow preferred by the masters of archery across the Overworld, no matter how simple it appears."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pets Attack Targeted Mobs"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage Dealt (Power I)"));
        }
        if(stack.getItem() == HUNTING_BOW){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This enchanted bow strengthens the bond between the hunter and their animal companion."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pets Attack Targeted Mobs"));
        }
        if(stack.getItem() == ANCIENT_BOW){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Ancient Bow is still as sprightly as the day it was first strung."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pets Attack Targeted Mobs"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Boosts Next Attack On Jump (Dynamo I)"));
        }
    }
}
