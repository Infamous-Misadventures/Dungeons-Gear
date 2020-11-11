package com.infamous.dungeons_gear.ranged.bows;

import com.infamous.dungeons_gear.init.DeferredItemInit;
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

public class LongbowItem extends AbstractDungeonsBowItem {

    public LongbowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
        this.addPropertyOverride(new ResourceLocation("pull"),
                (itemStack, world, livingEntity) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    } else {
                        return !(livingEntity.getActiveItemStack().getItem() instanceof LongbowItem) ?
                                0.0F :
                                (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
                    }
                });
        this.addPropertyOverride(new ResourceLocation("pulling"),
                (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);
    }

    @Override
    public boolean shootsStrongChargedArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasFuseShotBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.RED_SNAKE.get();
    }

    @Override
    public boolean hasSuperChargedBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.GUARDIAN_BOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == DeferredItemInit.GUARDIAN_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Forged from fossilized coral, the Guardian Bow is a remnant from sunken civilizations of lost ages."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Strong Charged Attacks"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Super Charged Arrows (Supercharge I)"));
        }
        if(stack.getItem() == DeferredItemInit.RED_SNAKE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Red Snake radiates an explosive heat, making it a deadly fire risk in the dry, desert lands."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Strong Charged Attacks"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Chance For Arrows To Explode (Fuse Shot I)"));
        }if(stack.getItem() == DeferredItemInit.LONGBOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Longbow, crafted for hunting rather than battle, is still useful in a fight."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Strong Charged Attacks"));
        }
    }
}
