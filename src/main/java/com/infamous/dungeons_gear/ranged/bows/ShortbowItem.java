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

public class ShortbowItem extends AbstractDungeonsBowItem {

    public ShortbowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
        this.addPropertyOverride(new ResourceLocation("pull"),
                (itemStack, world, livingEntity) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    } else {
                        return !(livingEntity.getActiveItemStack().getItem() instanceof ShortbowItem) ?
                                0.0F :
                                (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / RangedAttackHelper.getModdedBowChargeTime(livingEntity.getActiveItemStack());
                    }
                });
        this.addPropertyOverride(new ResourceLocation("pulling"),
                (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);
    }

    @Override
    public boolean hasQuickChargeBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.PURPLE_STORM.get();
    }

    @Override
    public boolean hasAccelerateBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.MECHANICAL_SHORTBOW.get();
    }

    @Override
    public boolean hasRadianceShotBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.LOVE_SPELL_BOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);

        if(stack.getItem() == DeferredItemInit.MECHANICAL_SHORTBOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A brand new development in quick firing technology, this bow packs a swift punch."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Accelerated Fire Rate (Accelerate I)"));
        }if(stack.getItem() == DeferredItemInit.PURPLE_STORM.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Named the Purple Storm after its lovely hue, this legendary bow packs a powerful punch."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Increased Fire Rate (Quick Charge I)"));
        }if(stack.getItem() == DeferredItemInit.SHORTBOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The preferred weapon of thieves and rogues, the Shortbow is nimble and lethal at short range."));

        }if(stack.getItem() == DeferredItemInit.LOVE_SPELL_BOW.get()){
        list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Love can be magical, but when it becomes too powerful, it can turn into a frenzy."));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Heals Allies In The Area (Radiance Shot I)"));

    }
    }
}
