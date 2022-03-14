package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.capabilities.offhand.OffhandProvider;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_libraries.items.gearconfig.CrossbowGear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DualCrossbowGear extends CrossbowGear implements IDualWieldWeapon {

    public DualCrossbowGear(Properties builder) {
        super(builder);
    }

    @Override
    public boolean canDualWield(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand handIn) {
        if (isCharged(playerEntity.getMainHandItem()) && playerEntity.getOffhandItem().getItem() instanceof DualCrossbowGear && isCharged(playerEntity.getOffhandItem()) && handIn == Hand.MAIN_HAND)
            super.use(world, playerEntity, Hand.OFF_HAND);
        return super.use(world, playerEntity, handIn);
    }


    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity livingEntity, int timeLeft) {
        int chargeIn = this.getUseDuration(stack) - timeLeft;
        float charge = getCrossbowCharge(livingEntity, chargeIn, stack);

        ItemStack offhandHeldItem = livingEntity.getOffhandItem();
        boolean offhandCrossbowFlag = offhandHeldItem.getItem() instanceof DualCrossbowGear && livingEntity.getMainHandItem() == stack;

        if (charge >= 1.0F
                && !isCharged(stack)
                && CrossbowGear.hasAmmo(livingEntity, stack)) {
            setCharged(stack, true);
            if (offhandCrossbowFlag && CrossbowGear.hasAmmo(livingEntity, offhandHeldItem)) {
                setCharged(offhandHeldItem, true);
            }
            SoundCategory lvt_7_1_ = livingEntity instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound((PlayerEntity) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, lvt_7_1_, 1.0F, 1.0F / (random.nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

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
        if (entityIn instanceof LivingEntity && !worldIn.isClientSide)
            update((LivingEntity) entityIn, stack, itemSlot);
    }
}
