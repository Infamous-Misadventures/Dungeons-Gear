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

public class HuntingBowItem extends AbstractDungeonsBowItem {

    public HuntingBowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
        this.addPropertyOverride(new ResourceLocation("pull"),
                (itemStack, world, livingEntity) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    } else {
                        return !(livingEntity.getActiveItemStack().getItem() instanceof HuntingBowItem) ?
                                0.0F :
                                (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
                    }
                });
        this.addPropertyOverride(new ResourceLocation("pulling"),
                (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);
    }

    @Override
    public boolean setsPetsAttackTarget(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasPowerBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.MASTERS_BOW.get();
    }

    @Override
    public boolean hasReplenishBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.HUNTERS_PROMISE.get();
    }

    @Override
    public boolean hasDynamoBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.ANCIENT_BOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == DeferredItemInit.HUNTERS_PROMISE.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This enchanted bow deepens the bond between the hunter and their trusted animal companion."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pets Attack Targeted Mobs"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Chance To Regain Arrows (Replenish I)"));
        }
        if(stack.getItem() == DeferredItemInit.MASTERS_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The bow preferred by the masters of archery across the Overworld, no matter how simple it appears."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pets Attack Targeted Mobs"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Extra Damage Dealt (Power I)"));
        }
        if(stack.getItem() == DeferredItemInit.HUNTING_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This enchanted bow strengthens the bond between the hunter and their animal companion."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pets Attack Targeted Mobs"));
        }
        if(stack.getItem() == DeferredItemInit.ANCIENT_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Ancient Bow is still as sprightly as the day it was first strung."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Pets Attack Targeted Mobs"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Boosts Next Attack On Jump (Dynamo I)"));
        }
    }
}
