package com.infamous.dungeons_gear.ranged.bows;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class ShortbowItem extends AbstractDungeonsBowItem {

    public ShortbowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean hasQuickChargeBuiltIn(ItemStack stack) {
        return stack.getItem() == PURPLE_STORM;
    }

    @Override
    public boolean hasAccelerateBuiltIn(ItemStack stack) {
        return stack.getItem() == MECHANICAL_SHORTBOW;
    }

    @Override
    public boolean hasRadianceShotBuiltIn(ItemStack stack) {
        return stack.getItem() == LOVE_SPELL_BOW;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == MECHANICAL_SHORTBOW){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A brand new development in quick firing technology, this bow packs a swift punch."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Accelerated Fire Rate (Accelerate I)"));
        }if(stack.getItem() == PURPLE_STORM){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Named the Purple Storm after its lovely hue, this legendary bow packs a powerful punch."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Fire Rate (Quick Charge I)"));
        }if(stack.getItem() == SHORTBOW){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The preferred weapon of thieves and rogues, the Shortbow is nimble and lethal at short range."));

        }if(stack.getItem() == LOVE_SPELL_BOW){
        list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Love can be magical, but when it becomes too powerful, it can turn into a frenzy."));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Heals Allies In The Area (Radiance Shot I)"));

    }
    }
}
