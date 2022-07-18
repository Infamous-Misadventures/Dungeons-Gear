package com.infamous.dungeons_gear.items.interfaces;

import com.infamous.dungeons_gear.capabilities.ModCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public interface IDualWieldWeapon<T extends Item> {
    default boolean canAttack(LivingEntity e, ItemStack is) {
        return e.getMainHandItem().getItem() instanceof IDualWieldWeapon && e.getOffhandItem().getItem() instanceof IDualWieldWeapon;
    }

    /*
    logic: if on main hand and offhand is not dummy, wrap offhand into dummy
    dummy tracks and copies the stack tags of the itemstack it was linked with via dual wield capability attached
    damage taken by dummy is forwarded to main
    if dummy detects main hand is not correct itemstack, or is not in the offhand slot, it unwraps itself
     */
    default ItemStack wrapStack(ItemStack wrapper, ItemStack content) {
        wrapper.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).ifPresent((a) -> a.setWrappedItemStack(content));
        return wrapper;
    }

    default ItemStack unwrapStack(ItemStack wrapped) {
        if (!wrapped.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).isPresent()) return ItemStack.EMPTY;
        return wrapped.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).resolve().get().getWrappedItemStack();
    }

    default void copyTag(ItemStack from, ItemStack to) {
        if (from.hasTag())
            to.setTag(from.getTag().copy());
        //to.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).ifPresent((a) -> a.setFake(true));
    }

    default ItemStack createDummy(ItemStack of) {
        ItemStack ret = of.copy();
        ret.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).ifPresent((a) ->{
            a.setLinkedItemStack(of);
            a.setFake(true);
        });
        return ret;
    }

    default void update(LivingEntity wielder, ItemStack is, int slot) {
        if(wielder.isEffectiveAi())
        if (wielder.getOffhandItem() == is)
            updateOff(wielder, is);
        else if (wielder.getMainHandItem() == is)
            updateMain(wielder, is);
        else if (wielder instanceof Player)
            updateInventory((Player) wielder, is, slot);
    }

    default void updateMain(LivingEntity wielder, ItemStack is) {
        //wraps the offhand into a dummy
        if (wielder.getOffhandItem().getItem() != is.getItem()) {
            wielder.setItemInHand(InteractionHand.OFF_HAND, wrapStack(createDummy(is), wielder.getOffhandItem()));
            if(wielder instanceof Player){
                ((Player) wielder).inventoryMenu.broadcastChanges();
            }
        }
    }

    default void updateOff(LivingEntity wielder, ItemStack is) {
        //unwraps the dummy if the main hand doesn't match
        is.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).ifPresent((a) -> {
            if(!a.isFake())return;
            if (a.getLinkedItemStack().isEmpty() || wielder.getMainHandItem() != a.getLinkedItemStack())
                wielder.setItemInHand(InteractionHand.OFF_HAND, unwrapStack(is));
            else copyTag(a.getLinkedItemStack(), is);
        });
    }

    default void updateInventory(Player e, ItemStack stack, int slot) {
        // unwraps the dummy if it's anywhere except the offhand slot
        // Credits to choonster!
        // Get the entity's main inventory
        stack.getCapability(ModCapabilities.DUAL_WIELD_CAPABILITY).ifPresent((a) -> {
            if (a.isFake() && e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).isPresent()) {
                final IItemHandler mainInventory = e.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).resolve().get();
                // If this item is in their main inventory and it can be extracted.
                if (mainInventory.getStackInSlot(slot) == stack && !mainInventory.extractItem(slot, stack.getCount(), true).isEmpty()) {
                    mainInventory.extractItem(slot, stack.getCount(), false);
                    mainInventory.insertItem(slot, unwrapStack(stack), false);
                    e.inventoryMenu.broadcastChanges();
                }
            }
        });

    }
}
