package com.infamous.dungeons_gear.ranged.crossbows;

import com.infamous.dungeons_gear.capabilities.offhand.OffhandProvider;
import com.infamous.dungeons_gear.init.ItemRegistry;
import com.infamous.dungeons_gear.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_gear.utilties.DescriptionHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class DualCrossbowItem extends AbstractDungeonsCrossbowItem implements IDualWieldWeapon {

    public DualCrossbowItem(Properties builder, int defaultChargeTimeIn, boolean isUniqueIn) {
        super(builder, defaultChargeTimeIn, isUniqueIn);
    }

    @Override
    public boolean canDualWield(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand handIn) {
        if (isCharged(playerEntity.getHeldItemMainhand()) && playerEntity.getHeldItemOffhand().getItem() instanceof DualCrossbowItem && isCharged(playerEntity.getHeldItemOffhand()) && handIn == Hand.MAIN_HAND)
            super.onItemRightClick(world, playerEntity, Hand.OFF_HAND);
        return super.onItemRightClick(world, playerEntity, handIn);
    }


    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity livingEntity, int timeLeft) {
        int chargeIn = this.getUseDuration(stack) - timeLeft;
        float charge = getCrossbowCharge(chargeIn, stack);

        ItemStack offhandHeldItem = livingEntity.getHeldItemOffhand();
        boolean offhandCrossbowFlag = offhandHeldItem.getItem() instanceof DualCrossbowItem && livingEntity.getHeldItemMainhand() == stack;

        if (charge >= 1.0F
                && !isCharged(stack)
                && AbstractDungeonsCrossbowItem.hasAmmo(livingEntity, stack)) {
            setCharged(stack, true);
            if (offhandCrossbowFlag && AbstractDungeonsCrossbowItem.hasAmmo(livingEntity, offhandHeldItem)) {
                setCharged(offhandHeldItem, true);
            }
            SoundCategory lvt_7_1_ = livingEntity instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound((PlayerEntity) null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, lvt_7_1_, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    @Override
    public boolean hasGrowingBuiltIn(ItemStack stack) {
        return stack.getItem() == ItemRegistry.BABY_CROSSBOW.get();
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag) {
        super.addInformation(stack, world, list, flag);
        DescriptionHelper.addFullDescription(list, stack);
    }

    @Override
    void fireProjectile(World worldIn, LivingEntity shooter, Hand handIn, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle) {
        super.fireProjectile(worldIn, shooter, handIn, crossbow, projectile, soundPitch, isCreativeMode, velocity, inaccuracy, projectileAngle);
    }

    @Override
    protected void damageItem(int amount, ItemStack stack, LivingEntity shooter, Hand handIn){
        if (stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).isPresent()) {
            if (!stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).resolve().get().getLinkedItemStack().isEmpty())
                stack = stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).resolve().get().getLinkedItemStack();
        }
        super.damageItem(amount, stack, shooter, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity && !worldIn.isRemote)
            update((LivingEntity) entityIn, stack, itemSlot);
    }
}
