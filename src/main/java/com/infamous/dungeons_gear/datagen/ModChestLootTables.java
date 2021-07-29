package com.infamous.dungeons_gear.datagen;

import com.infamous.dungeons_gear.loot.AddPotionLootFunction;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.ItemTagWrappers.FOOD_PROCESSED;
import static net.minecraft.item.Items.*;
import static net.minecraft.tags.ItemTags.ARROWS;

public class ModChestLootTables extends ChestLootTables {

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        //Enchantments
        prospectorLootTables(consumer);
        luckyExplorerLootTable(consumer);
        surpriseGiftLootTable(consumer);
        foodReservesLootTable(consumer);
        //Items
        satchelOfSnacksLootTable(consumer);
        satchelOfElixirsLootTable(consumer);
        arrowBundleTable(consumer);
    }

    private void arrowBundleTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        ResourceLocation tagTable = new ResourceLocation(MODID, "items/arrow_bundle/expand_tag");
        consumer.accept(tagTable,
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(TagLootEntry.getBuilder(ARROWS))));
        consumer.accept(new ResourceLocation(MODID, "items/arrow_bundle"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(() -> ARROW).weight(3))
                                .addEntry(TableLootEntry.builder(tagTable).weight(1).acceptFunction(AddPotionLootFunction.builder()))));
    }

    private void satchelOfSnacksLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "items/satchel_of_snacks"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(TagLootEntry.getBuilder(FOOD_PROCESSED))));
    }

    private void satchelOfElixirsLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "items/satchel_of_elixirs"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(() -> POTION).acceptFunction(AddPotionLootFunction.builder()))));
    }

    private void foodReservesLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/food_reserves"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(TagLootEntry.getBuilder(FOOD_PROCESSED))));
    }

    private void surpriseGiftLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/surprise_gift"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(() -> POTION).acceptFunction(AddPotionLootFunction.builder()))));
    }

    private void luckyExplorerLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/lucky_explorer"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(() -> EMERALD))));
    }

    private void prospectorLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/overworld"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(() -> EMERALD))));
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/the_nether"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(() -> GOLD_INGOT))));
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/the_end"),
                LootTable.builder().
                        addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                                .addEntry(ItemLootEntry.builder(() -> EMERALD))));
    }


}
