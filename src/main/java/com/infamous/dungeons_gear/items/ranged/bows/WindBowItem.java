package com.infamous.dungeons_gear.items.ranged.bows;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;


public class WindBowItem extends AbstractDungeonsBowItem {

    public WindBowItem(Properties builder, float defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public AbstractArrowEntity createBowArrow(ItemStack stack, World world, PlayerEntity playerentity, ItemStack itemstack, float arrowVelocity, int i, boolean hasInfiniteAmmo, boolean isAdditionalShot) {
        AbstractArrowEntity arrowEntity = super.createBowArrow(stack, world, playerentity, itemstack, arrowVelocity, i, hasInfiniteAmmo, isAdditionalShot);
        if(arrowVelocity == 1.0F){
            arrowEntity.addTag(GALE_ARROW_TAG);
        }
        return arrowEntity;
    }

    @Override
    public boolean shootsGaleArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasRollChargeBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.BURST_GALE_BOW.get();
    }

    @Override
    public boolean hasRicochetBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.ECHO_OF_THE_VALLEY.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }
}
