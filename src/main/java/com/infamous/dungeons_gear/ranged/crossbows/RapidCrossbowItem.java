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

public class RapidCrossbowItem extends AbstractDungeonsCrossbowItem {

    public RapidCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
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
    public boolean hasAccelerateBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.AUTO_CROSSBOW.get();
    }

    @Override
    public boolean hasBonusShotBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.BUTTERFLY_CROSSBOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (stack.getItem() == DeferredItemInit.AUTO_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "The Arch-Illager said the design for this powerful crossbow came to him in a vision."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "High Firerate"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Firerate Increases Upon Firing (Accelerate I)"));
        }
        if (stack.getItem() == DeferredItemInit.BUTTERFLY_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This crossbow moves faster than wings in flight and strikes down enemies before they see the bolt."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "High Firerate"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Shoots Two Enemies At Once (Bonus Shot I)"));
        }
        if (stack.getItem() == DeferredItemInit.RAPID_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "New reloading technology helps this crossbow fire more rapidly than those that came before it."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "High Firerate"));
        }
    }
}
