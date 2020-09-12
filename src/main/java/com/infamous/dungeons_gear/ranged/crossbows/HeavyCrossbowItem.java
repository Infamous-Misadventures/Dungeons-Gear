package com.infamous.dungeons_gear.ranged.crossbows;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class HeavyCrossbowItem extends AbstractDungeonsCrossbowItem {

    public HeavyCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean shootsHeavyArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasPunchBuiltIn(ItemStack stack) {
        return stack.getItem() == DOOM_CROSSBOW;
    }

    @Override
    public boolean hasRicochetBuiltIn(ItemStack stack) {
        return stack.getItem() == SLAYER_CROSSBOW;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (stack.getItem() == DOOM_CROSSBOW) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Many thought that the Doom Crossbow was just a myth, but this time the rumors turned out to be true."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Shots"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Additional Knockback (Punch I)"));
            list.add(new StringTextComponent(TextFormatting.RED + "Slow Firerate"));
        }
        if (stack.getItem() == SLAYER_CROSSBOW) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Slayer Crossbow is the treasured heirloom of many legendary hunters."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Shots"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Chance To Ricochet (Ricochet I)"));
            list.add(new StringTextComponent(TextFormatting.RED + "Slow Firerate"));
        }
        if (stack.getItem() == HEAVY_CROSSBOW) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The weighted crossbow is a damage-dealing menace and a real threat from a ranged distance."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Powerful Shots"));
            list.add(new StringTextComponent(TextFormatting.RED + "Slow Firerate"));
        }
    }
}
