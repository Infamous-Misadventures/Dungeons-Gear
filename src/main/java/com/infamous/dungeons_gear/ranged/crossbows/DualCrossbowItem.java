package com.infamous.dungeons_gear.ranged.crossbows;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.infamous.dungeons_gear.items.RangedWeaponList.*;

public class DualCrossbowItem extends AbstractDungeonsCrossbowItem {

    public DualCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand handIn) {
        ItemStack heldItem = playerEntity.getHeldItem(handIn);
        ItemStack offhandHeldItem = playerEntity.getHeldItemOffhand();
        boolean offhandCrossbowFlag = offhandHeldItem.getItem() instanceof DualCrossbowItem && handIn == Hand.MAIN_HAND;
        if (isCharged(heldItem)) {
            fireProjectiles(world, playerEntity, handIn, heldItem, getProjectileVelocity(heldItem), 5.0F);
            if(offhandCrossbowFlag && isCharged(offhandHeldItem)){
                fireProjectiles(world, playerEntity, Hand.OFF_HAND, offhandHeldItem, getProjectileVelocity(heldItem), 5.0F);
            }
            setCharged(heldItem, false);
            if(offhandCrossbowFlag){
                setCharged(offhandHeldItem, false);
            }

            return ActionResult.resultConsume(heldItem);
        } else if (!playerEntity.findAmmo(heldItem).isEmpty()) {

            if (!isCharged(heldItem)) {

                this.isLoadingStart = false;
                this.isLoadingMiddle = false;
                playerEntity.setActiveHand(handIn);
            }

            return ActionResult.resultConsume(heldItem);
        } else {
            return ActionResult.resultFail(heldItem);
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity livingEntity, int timeLeft) {
        int chargeIn = this.getUseDuration(stack) - timeLeft;
        float charge = getCrossbowCharge(chargeIn, stack);

        ItemStack offhandHeldItem = livingEntity.getHeldItemOffhand();
        boolean offhandCrossbowFlag = offhandHeldItem.getItem() instanceof DualCrossbowItem && livingEntity.getHeldItemMainhand() == stack;

        if (charge >= 1.0F
                && !isCharged(stack)
                && this.hasAmmo(livingEntity, stack)) {
            setCharged(stack, true);
            if(offhandCrossbowFlag && this.hasAmmo(livingEntity, offhandHeldItem)){
                setCharged(offhandHeldItem, true);
            }
            SoundCategory lvt_7_1_ = livingEntity instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound((PlayerEntity)null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, lvt_7_1_, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    @Override
    public boolean hasGrowingBuiltIn(ItemStack stack) {
        return stack.getItem() == DeferredItemInit.BABY_CROSSBOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);

        if (stack.getItem() == DeferredItemInit.BABY_CROSSBOW.get()) {
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "While some discuss the cute Baby Crossbow, this deadly weapon grows into a heavy hitter."));

            list.add(new StringTextComponent(TextFormatting.GREEN + "Arrows Grow Size (Growing I)"));
        }
        if(stack.getItem() == DeferredItemInit.DUAL_CROSSBOW.get()){
            list.add(new StringTextComponent(TextFormatting.WHITE + "" + TextFormatting.ITALIC + "Dual crossbows are the perfect choice for a warrior with quick reflexes in a fast-paced battle."));
        }
        list.add(new StringTextComponent(TextFormatting.GREEN + "Double Projectiles"));
    }
}
