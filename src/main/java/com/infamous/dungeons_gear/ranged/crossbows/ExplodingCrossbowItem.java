package com.infamous.dungeons_gear.ranged.crossbows;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class ExplodingCrossbowItem extends AbstractDungeonsCrossbowItem {

    public ExplodingCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean shootsExplosiveArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasChainReactionBuiltIn(ItemStack stack) {
        return stack.getItem() == FIREBOLT_THROWER;
    }

    @Override
    public boolean hasGravityBuiltIn(ItemStack stack) {
        return stack.getItem() == IMPLODING_CROSSBOW;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (stack.getItem() == FIREBOLT_THROWER) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Firebolt Thrower launches chaos in a chain reaction of exploding arrows."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Explodes On Impact"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Chance Of Chain Reaction (Chain Reaction I)"));
        }if (stack.getItem() == EXPLODING_CROSSBOW) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The power of TNT fused with the latest in archery design resulted in this devastating crossbow."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Explodes On Impact"));
        }
        if (stack.getItem() == IMPLODING_CROSSBOW) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Imploding Crossbow has been magically fine-tuned to maximize the impact of the explosion."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pulls Enemies In (Gravity I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Explodes On Impact"));
        }
    }
}
