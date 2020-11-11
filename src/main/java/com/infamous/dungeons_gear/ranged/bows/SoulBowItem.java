package com.infamous.dungeons_gear.ranged.bows;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class SoulBowItem extends AbstractDungeonsBowItem implements ISoulGatherer {

    public SoulBowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
        this.addPropertyOverride(new ResourceLocation("pull"),
                (itemStack, world, livingEntity) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    } else {
                        return !(livingEntity.getActiveItemStack().getItem() instanceof SoulBowItem) ?
                                0.0F :
                                (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
                    }
                });
        this.addPropertyOverride(new ResourceLocation("pulling"),
                (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);
    }

    @Override
    public boolean gathersSouls(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasTempoTheftBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.NOCTURNAL_BOW.get();
    }

    @Override
    public boolean hasMultishotBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.BOW_OF_LOST_SOULS.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == DeferredItemInit.BOW_OF_LOST_SOULS.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This bow, made of cursed bones, strips the living of their very souls."));

            // Chance for Multishot
            list.add(new StringTextComponent(TextFormatting.GREEN + "Fires 3 Arrows (Multishot)"));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "+2 XP Gathering"));
        }
        if(stack.getItem() == DeferredItemInit.NOCTURNAL_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The souls bound to the bow guide the arrows to their targets and cause it to glow slightly."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Steals Speed (Tempo Theft I)"));
            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "+2 XP Gathering"));
        }
        if(stack.getItem() == DeferredItemInit.SOUL_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Soul Bow shimmers with all the beauty and fury of an attacking Vex."));

            list.add(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "+2 XP Gathering"));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        return 2;
    }
}
