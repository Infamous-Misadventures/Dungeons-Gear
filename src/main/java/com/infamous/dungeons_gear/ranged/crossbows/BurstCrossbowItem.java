package com.infamous.dungeons_gear.ranged.crossbows;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class BurstCrossbowItem extends AbstractDungeonsCrossbowItem implements ISoulGatherer {

    public BurstCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
        this.addPropertyOverride(new ResourceLocation("pull"), (itemStack, world, livingEntity) -> {
            if (livingEntity != null && itemStack.getItem() == this) {
                return isCharged(itemStack) ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / (float)RangedAttackHelper.getModdedCrossbowChargeTime(itemStack);
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
    public boolean shootsFasterArrows(ItemStack stack) {
        return true;
    }

    @Override
    public boolean hasEnigmaResonatorBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.SOUL_HUNTER_CROSSBOW.get();
    }

    @Override
    public boolean hasDynamoBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.CORRUPTED_CROSSBOW.get();
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

        if (stack.getItem() == DeferredItemInit.CORRUPTED_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "This crossbow has a subtle yet corrupting power that is suitable for thieves and nimble warriors alike."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Boosts Next Attack On Jump (Dynamo I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Multiple Projectiles"));
        }
        if (stack.getItem() == DeferredItemInit.SOUL_HUNTER_CROSSBOW.get()) {
            //list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Adding electrical energy to this crossbow changed the firing sound dramatically."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Souls Critical Boost (Enigma Resonator I)"));
            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Multiple Projectiles"));
        }
        if (stack.getItem() == DeferredItemInit.BURST_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "A tactical crossbow favored by warriors and hunters alike, the Burst Crossbow is a powerful tool for any hero."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Fast Multiple Projectiles"));
        }
    }

    @Override
    public int getGatherAmount(ItemStack stack) {
        if(stack.getItem() == DeferredItemInit.BURST_CROSSBOW.get()){
            return 1;
        }
        else return 0;
    }
}
