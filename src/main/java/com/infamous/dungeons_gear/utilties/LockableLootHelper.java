package com.infamous.dungeons_gear.utilties;

import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static net.minecraft.loot.LootTable.capStackSizes;

public class LockableLootHelper {

    public static void fillInventory(LootTable lootTable, IInventory iInventory, LootContext lootContext) {
        List<ItemStack> list = lootTable.generate(lootContext);
        Random random = lootContext.getRandom();
        List<Integer> list1 = getEmptySlotsRandomized(iInventory, random);
        shuffleItems(list, list1.size(), random);

        for(ItemStack itemstack : list) {
            if (list1.isEmpty()) {
                DungeonsGear.LOGGER.warn("Tried to over-fill a container");
                return;
            }

            if (itemstack.isEmpty()) {
                iInventory.setInventorySlotContents(list1.remove(list1.size() - 1), ItemStack.EMPTY);
            } else {
                iInventory.setInventorySlotContents(list1.remove(list1.size() - 1), itemstack);
            }
        }

    }

    private static List<Integer> getEmptySlotsRandomized(IInventory inventory, Random rand) {
        List<Integer> list = Lists.newArrayList();

        for(int i = 0; i < inventory.getSizeInventory(); ++i) {
            DungeonsGear.LOGGER.info(inventory);
            DungeonsGear.LOGGER.info(inventory.getStackInSlot(i));
            if (inventory.getStackInSlot(i).isEmpty()) {
                list.add(i);
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }

    private static void shuffleItems(List<ItemStack> stacks, int parameterInt, Random rand) {
        List<ItemStack> list = Lists.newArrayList();
        Iterator<ItemStack> iterator = stacks.iterator();

        while(iterator.hasNext()) {
            ItemStack itemstack = iterator.next();
            if (itemstack.isEmpty()) {
                iterator.remove();
            } else if (itemstack.getCount() > 1) {
                list.add(itemstack);
                iterator.remove();
            }
        }

        while(parameterInt - stacks.size() - list.size() > 0 && !list.isEmpty()) {
            ItemStack itemstack2 = list.remove(MathHelper.nextInt(rand, 0, list.size() - 1));
            int i = MathHelper.nextInt(rand, 1, itemstack2.getCount() / 2);
            ItemStack itemstack1 = itemstack2.split(i);
            if (itemstack2.getCount() > 1 && rand.nextBoolean()) {
                list.add(itemstack2);
            } else {
                stacks.add(itemstack2);
            }

            if (itemstack1.getCount() > 1 && rand.nextBoolean()) {
                list.add(itemstack1);
            } else {
                stacks.add(itemstack1);
            }
        }

        stacks.addAll(list);
        Collections.shuffle(stacks, rand);
    }
}
