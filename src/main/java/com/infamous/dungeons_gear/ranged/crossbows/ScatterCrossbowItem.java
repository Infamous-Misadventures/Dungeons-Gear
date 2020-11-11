package com.infamous.dungeons_gear.ranged.crossbows;

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

public class ScatterCrossbowItem extends AbstractDungeonsCrossbowItem {

    public ScatterCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
        this.addPropertyOverride(new ResourceLocation("pull"), (itemStack, world, livingEntity) -> {
            if (livingEntity != null && itemStack.getItem() == this) {
                return isCharged(itemStack) ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / (float) RangedAttackHelper.getModdedCrossbowChargeTime(itemStack);
            } else {
                return 0.0F;
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"),
                (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack && !isCharged(itemStack) ? 1.0F : 0.0F);
        this.addPropertyOverride(new ResourceLocation("charged"),
                (itemStack, world, livingEntity) -> livingEntity != null && isCharged(itemStack) ? 1.0F : 0.0F);
        this.addPropertyOverride(new ResourceLocation("firework"),
                (itemStack, world, livingEntity) -> livingEntity != null && isCharged(itemStack) && RangedAttackHelper.hasChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
    }

    @Override
    public boolean hasMultishotBuiltIn(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasExtraMultishot(ItemStack stack){
        return stack.getItem() == DeferredItemInit.HARP_CROSSBOW.get();
    }

    @Override
    public boolean hasPiercingBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.LIGHTNING_HARP_CROSSBOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (stack.getItem() == DeferredItemInit.HARP_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Half musical instrument and fully deadly weapon, the Harp Crossbow is the life of the party."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Multiple Projectiles"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Even More Projectiles (Multishot)"));
        }
        if (stack.getItem() == DeferredItemInit.LIGHTNING_HARP_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Adding electrical energy to this crossbow changed the firing sound dramatically."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Multiple Projectiles"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Chance To Ricochet (Ricochet I)"));
        }
        if (stack.getItem() == DeferredItemInit.SCATTER_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This crossbow, modified to hold and fire multiple bolts, is also a half-decent musical instrument."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Multiple Projectiles"));
        }
    }
}
