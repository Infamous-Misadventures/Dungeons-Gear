package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.items.ItemRegistry;
import com.infamous.dungeons_gear.items.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

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
        return stack.getItem() == ItemRegistry.SOUL_HUNTER_CROSSBOW.get();
    }

    @Override
    public boolean hasDynamoBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.CORRUPTED_CROSSBOW.get();
    }

    @Override
    public void fireCrossbowProjectiles(World world, LivingEntity livingEntity, Hand hand, ItemStack stack, float velocityIn, float inaccuracyIn) {
        List<ItemStack> list = AbstractDungeonsCrossbowItem.getChargedProjectiles(stack);
        float[] randomSoundPitches = AbstractDungeonsCrossbowItem.getRandomSoundPitches(livingEntity.getRNG());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack currentProjectile = list.get(i);
            boolean playerInCreativeMode = livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.isCreativeMode;
            if (!currentProjectile.isEmpty()) {
                // first 3 are always burst projectiles
                if (i == 0) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, 0.0F);
                } else if (i == 1) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, -5.0F);
                } else if (i == 2) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i], playerInCreativeMode, velocityIn, inaccuracyIn, 5.0F);
                } else if (i == 3) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i-2], playerInCreativeMode, velocityIn, inaccuracyIn, -10.0F);
                } else if (i == 4) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i-2], playerInCreativeMode, velocityIn, inaccuracyIn, 10.0F);
                } else if (i == 5) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i-4], playerInCreativeMode, velocityIn, inaccuracyIn, -20.0F);
                } else if (i == 6) {
                    fireProjectile(world, livingEntity, hand, stack, currentProjectile, randomSoundPitches[i-4], playerInCreativeMode, velocityIn, inaccuracyIn, 20.0F);
                }
            }
        }

        AbstractDungeonsCrossbowItem.fireProjectilesAfter(world, livingEntity, stack);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        if(stack.getItem() == ItemRegistry.SOUL_HUNTER_CROSSBOW.get()){
            return 1;
        }
        else return 0;
    }

    @Override
    public int getActivationCost(ItemStack stack) {
        return 0;
    }
}
