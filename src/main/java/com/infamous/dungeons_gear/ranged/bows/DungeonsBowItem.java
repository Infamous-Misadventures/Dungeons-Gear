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
public class DungeonsBowItem extends AbstractDungeonsBowItem {

    public DungeonsBowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
        this.addPropertyOverride(new ResourceLocation("pull"),
                (itemStack, world, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return !(livingEntity.getActiveItemStack().getItem() instanceof DungeonsBowItem) ?
                        0.0F :
                        (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"),
                (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);
    }

    @Override
    public boolean hasGrowingBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.BONEBOW.get();
    }

    @Override
    public boolean hasBonusShotBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.TWIN_BOW.get() || stack.getItem() == DeferredItemInit.HAUNTED_BOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == DeferredItemInit.BONEBOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Bonebow is the pride of Pumpkin Pastures, crafted within the walls of their humble village."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Arrows Grow Size (Growing I)"));
        }
        if(stack.getItem() == DeferredItemInit.TWIN_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Twin Bow is the champion of the hero who finds themselves outnumbered and alone."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Shoots Two Enemies At Once (Bonus Shot I)"));
        }
        if(stack.getItem() == DeferredItemInit.HAUNTED_BOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "What indescribable horror! The creeping tentacles of this bow reach for the unknowable Void."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Shoots Two Enemies At Once (Bonus Shot I)"));
        }
    }
}
