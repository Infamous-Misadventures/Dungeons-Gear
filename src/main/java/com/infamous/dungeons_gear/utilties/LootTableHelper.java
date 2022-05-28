package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.mixin.LootContextAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class LootTableHelper {
    public static ItemStack generateItemStack(ServerWorld world, BlockPos pos, ResourceLocation lootTable, Random random)
    {
        LootContext context = new LootContext.Builder(world)
                .withRandom(random)
                .withParameter(LootParameters.ORIGIN, Vector3d.atCenterOf(pos)) // positional context
                .create(LootParameterSets.CHEST);	// chest set requires positional context, has no other mandatory parameters

        LootTable table = world.getServer()
                .getLootTables()
                .get(lootTable);
        List<ItemStack> stacks = table.getRandomItems(context);
        return !stacks.isEmpty()
                ? stacks.get(0)
                : ItemStack.EMPTY;
    }
    public static List<ItemStack> generateItemStacks(ServerWorld world, Vector3d pos, ResourceLocation lootTable, Random random)
    {
        LootContext context = new LootContext.Builder(world)
                .withRandom(random)
                .withParameter(LootParameters.ORIGIN, pos) // positional context
                .create(LootParameterSets.CHEST);	// chest set requires positional context, has no other mandatory parameters

        LootTable table = world.getServer()
                .getLootTables()
                .get(lootTable);
        return table.getRandomItems(context);
    }

    public static List<ItemStack> generateItemStacks(ServerWorld world, LootContext originContext, ResourceLocation lootTable) {
        LootContext newContext = copyLootContextWithNewQueryID(originContext, lootTable);
        List<ItemStack> newlyGeneratedLoot = originContext.getLootTable(lootTable).getRandomItems(newContext);
        return newlyGeneratedLoot;
    }

    public static boolean lootTableExists(ServerWorld world, ResourceLocation lootTable){
        return ! world.getServer()
                .getLootTables()
                .get(lootTable)
                .equals(LootTable.EMPTY);
    }

    protected static LootContext copyLootContextWithNewQueryID(LootContext oldLootContext, ResourceLocation newQueryID){
        LootContext newContext = new LootContext.Builder(oldLootContext).create(LootParameterSets.CHEST);
        ((LootContextAccessor)newContext).dungeonsgear_setQueriedLootTableId(newQueryID);
        return newContext;
    }
}
