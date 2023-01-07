package com.infamous.dungeons_gear.utilties;

import com.google.common.collect.Sets;
import com.infamous.dungeons_gear.mixin.LootContextAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LootTableHelper {
    public static ItemStack generateItemStack(ServerLevel world, BlockPos pos, ResourceLocation lootTable, RandomSource random)
    {
        LootContext context = new LootContext.Builder(world)
                .withRandom(random)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)) // positional context
                .create(LootContextParamSets.CHEST);	// chest set requires positional context, has no other mandatory parameters

        LootTable table = world.getServer()
                .getLootTables()
                .get(lootTable);
        List<ItemStack> stacks = table.getRandomItems(context);
        return !stacks.isEmpty()
                ? stacks.get(0)
                : ItemStack.EMPTY;
    }
    public static List<ItemStack> generateItemStacks(ServerLevel world, Vec3 pos, ResourceLocation lootTable, RandomSource random)
    {
        LootContext context = new LootContext.Builder(world)
                .withRandom(random)
                .withParameter(LootContextParams.ORIGIN, pos) // positional context
                .create(LootContextParamSets.CHEST);	// chest set requires positional context, has no other mandatory parameters

        LootTable table = world.getServer()
                .getLootTables()
                .get(lootTable);
        return table.getRandomItems(context);
    }

    public static List<ItemStack> generateItemStacks(ServerLevel world, LootContext originContext, ResourceLocation lootTable) {
        if(!isCompleteParameterSet(originContext)) return new ArrayList<>();
        LootContext newContext = copyLootContextWithNewQueryID(originContext, lootTable);
        return originContext.getLootTable(lootTable).getRandomItems(newContext);
    }

    private static boolean isCompleteParameterSet(LootContext originContext) {
        Map<LootContextParam<?>, Object> originParams = originContext.params;
        Set<LootContextParam<?>> set1 = Sets.difference(LootContextParamSets.CHEST.getRequired(), originParams.keySet());
        return set1.isEmpty();
    }

    public static boolean lootTableExists(ServerLevel world, ResourceLocation lootTable){
        return ! world.getServer()
                .getLootTables()
                .get(lootTable)
                .equals(LootTable.EMPTY);
    }

    protected static LootContext copyLootContextWithNewQueryID(LootContext oldLootContext, ResourceLocation newQueryID){
        LootContext newContext = new LootContext.Builder(oldLootContext).create(LootContextParamSets.CHEST);
        ((LootContextAccessor)newContext).dungeonsgear_setQueriedLootTableId(newQueryID);
        return newContext;
    }
}
