package com.infamous.dungeons_gear.interfaces;

import com.infamous.dungeons_gear.capabilities.offhand.OffhandProvider;
import net.minecraft.client.renderer.FaceDirection;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public interface IDualWieldWeapon<T extends Item> {
    default boolean canAttack(LivingEntity e, ItemStack is) {
        return e.getHeldItemMainhand().getItem() instanceof IDualWieldWeapon && e.getHeldItemOffhand().getItem() instanceof IDualWieldWeapon;
    }

    /*
    logic: if on main hand and offhand is not dummy, wrap offhand into dummy
    dummy tracks and copies the stack tags of the itemstack it was linked with via dual wield capability attached
    damage taken by dummy is forwarded to main
    if dummy detects main hand is not correct itemstack, or is not in the offhand slot, it unwraps itself
     */
    default ItemStack wrapStack(ItemStack wrapper, ItemStack content) {
        wrapper.getCapability(OffhandProvider.OFFHAND_CAPABILITY).ifPresent((a) -> a.setWrappedItemStack(content));
        return wrapper;
    }

    default ItemStack unwrapStack(ItemStack wrapped) {
        if (!wrapped.getCapability(OffhandProvider.OFFHAND_CAPABILITY).isPresent()) return ItemStack.EMPTY;
        return wrapped.getCapability(OffhandProvider.OFFHAND_CAPABILITY).resolve().get().getWrappedItemStack();
    }

    default void copyTag(ItemStack from, ItemStack to) {
        if (from.hasTag())
            to.setTag(from.getTag().copy());
        //to.getCapability(OffhandProvider.OFFHAND_CAPABILITY).ifPresent((a) -> a.setFake(true));
    }

    default ItemStack createDummy(ItemStack of) {
        ItemStack ret = of.copy();
        ret.getCapability(OffhandProvider.OFFHAND_CAPABILITY).ifPresent((a) ->{
            a.setLinkedItemStack(of);
            a.setFake(true);
        });
        return ret;
    }

    default void update(LivingEntity wielder, ItemStack is, int slot) {
        if(wielder.isServerWorld())
        if (wielder.getHeldItemOffhand() == is)
            updateOff(wielder, is);
        else if (wielder.getHeldItemMainhand() == is)
            updateMain(wielder, is);
        else if (wielder instanceof PlayerEntity)
            updateInventory((PlayerEntity) wielder, is, slot);
    }

    default void updateMain(LivingEntity wielder, ItemStack is) {
        //wraps the offhand into a dummy
        if (wielder.getHeldItemOffhand().getItem() != is.getItem()) {
            wielder.setHeldItem(Hand.OFF_HAND, wrapStack(createDummy(is), wielder.getHeldItemOffhand()));
            if(wielder instanceof PlayerEntity){
                ((PlayerEntity) wielder).container.detectAndSendChanges();
            }
        }
    }

    default void updateOff(LivingEntity wielder, ItemStack is) {
        //unwraps the dummy if the main hand doesn't match
        is.getCapability(OffhandProvider.OFFHAND_CAPABILITY).ifPresent((a) -> {
            if(!a.isFake())return;
            if (a.getLinkedItemStack().isEmpty() || wielder.getHeldItemMainhand() != a.getLinkedItemStack())
                wielder.setHeldItem(Hand.OFF_HAND, unwrapStack(is));
            else copyTag(a.getLinkedItemStack(), is);
        });
    }

    default void updateInventory(PlayerEntity e, ItemStack stack, int slot) {
        // unwraps the dummy if it's anywhere except the offhand slot
        // Credits to choonster!
        // Get the entity's main inventory
        stack.getCapability(OffhandProvider.OFFHAND_CAPABILITY).ifPresent((a) -> {
            if (a.isFake() && e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).isPresent()) {
                final IItemHandler mainInventory = e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).resolve().get();
                // If this item is in their main inventory and it can be extracted.
                if (mainInventory.getStackInSlot(slot) == stack && !mainInventory.extractItem(slot, stack.getCount(), true).isEmpty()) {
                    mainInventory.extractItem(slot, stack.getCount(), false);
                    mainInventory.insertItem(slot, unwrapStack(stack), false);
                    e.container.detectAndSendChanges();
                }
            }
        });

    }
}
