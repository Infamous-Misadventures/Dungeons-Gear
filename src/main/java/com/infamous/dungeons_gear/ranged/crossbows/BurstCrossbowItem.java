package com.infamous.dungeons_gear.ranged.crossbows;

import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class BurstCrossbowItem extends AbstractDungeonsCrossbowItem implements ISoulGatherer {

    public BurstCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean hasMultishotBuiltIn(ItemStack stack) {
        return true;
    }

    @Override
    public boolean shootsFasterArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasEnigmaResonatorBuiltIn(ItemStack stack) {
        return stack.getItem() == SOUL_HUNTER_CROSSBOW;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (stack.getItem() == CORRUPTED_CROSSBOW) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This crossbow has a subtle yet corrupting power that is suitable for thieves and nimble warriors alike."));

            //list.add(new StringTextComponent(TextFormatting.GREEN + "Boosts Next Attack On Roll (Dynamo I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Multiple Projectiles"));
        }
        if (stack.getItem() == SOUL_HUNTER_CROSSBOW) {
            //list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Adding electrical energy to this crossbow changed the firing sound dramatically."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Souls Critical Boost (Enigma Resonator I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Multiple Projectiles"));
        }
        if (stack.getItem() == BURST_CROSSBOW) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A tactical crossbow favored by warriors and hunters alike, the Burst Crossbow is a powerful tool for any hero."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Multiple Projectiles"));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        if(stack.getItem() == BURST_CROSSBOW){
            return 1;
        }
        else return 0;
    }
}
