package com.infamous.dungeons_gear.utilties;

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
                .withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(pos)) // positional context
                .build(LootParameterSets.CHEST);	// chest set requires positional context, has no other mandatory parameters

        LootTable table = world.getServer()
                .getLootTableManager()
                .getLootTableFromLocation(lootTable);
        List<ItemStack> stacks = table.generate(context);
        return !stacks.isEmpty()
                ? stacks.get(0)
                : ItemStack.EMPTY;
    }
    public static List<ItemStack> generateItemStacks(ServerWorld world, BlockPos pos, ResourceLocation lootTable, Random random)
    {
        LootContext context = new LootContext.Builder(world)
                .withRandom(random)
                .withParameter(LootParameters.field_237457_g_, Vector3d.copyCentered(pos)) // positional context
                .build(LootParameterSets.CHEST);	// chest set requires positional context, has no other mandatory parameters

        LootTable table = world.getServer()
                .getLootTableManager()
                .getLootTableFromLocation(lootTable);
        return table.generate(context);
    }

    public static boolean lootTableExists(ServerWorld world, ResourceLocation lootTable){
        return ! world.getServer()
                .getLootTableManager()
                .getLootTableFromLocation(lootTable)
                .equals(LootTable.EMPTY_LOOT_TABLE);
    }
}
