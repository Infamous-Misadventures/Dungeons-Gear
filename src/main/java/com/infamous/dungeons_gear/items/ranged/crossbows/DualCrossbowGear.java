package com.infamous.dungeons_gear.items.ranged.crossbows;

import com.infamous.dungeons_gear.capabilities.ModCapabilities;
import com.infamous.dungeons_gear.items.interfaces.IDualWieldWeapon;
import com.infamous.dungeons_libraries.items.gearconfig.CrossbowGear;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DualCrossbowGear extends CrossbowGear implements IDualWieldWeapon {

    public DualCrossbowGear(Properties builder) {
        super(builder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand handIn) {
        if (isCharged(playerEntity.getMainHandItem()) && playerEntity.getOffhandItem().getItem() instanceof DualCrossbowGear && isCharged(playerEntity.getOffhandItem()) && handIn == InteractionHand.MAIN_HAND)
            super.use(world, playerEntity, InteractionHand.OFF_HAND);
        return super.use(world, playerEntity, handIn);
    }


    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity livingEntity, int timeLeft) {
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
            SoundSource lvt_7_1_ = livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            world.playSound((Player) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, lvt_7_1_, 1.0F, 1.0F / (livingEntity.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    @Override
    protected void damageItem(int amount, ItemStack stack, LivingEntity shooter, InteractionHand handIn){
        if (stack.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).isPresent()) {
            if (!stack.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).resolve().get().getLinkedItemStack().isEmpty())
                stack = stack.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).resolve().get().getLinkedItemStack();
        }
        super.damageItem(amount, stack, shooter, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof LivingEntity && !worldIn.isClientSide)
            update((LivingEntity) entityIn, stack, itemSlot);
    }
}
