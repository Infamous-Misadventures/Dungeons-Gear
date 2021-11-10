package com.infamous.dungeons_gear.datagen;

import com.infamous.dungeons_gear.loot.AddPotionLootFunction;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.ItemTagWrappers.FOOD_PROCESSED;
import static com.infamous.dungeons_gear.registry.ItemRegistry.ARROW_BUNDLE;
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
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(TagLootEntry.expandTag(ARROWS))));
        consumer.accept(new ResourceLocation(MODID, "items/arrow_bundle"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(() -> ARROW).setWeight(3))
                                .add(TableLootEntry.lootTableReference(tagTable).setWeight(1).apply(AddPotionLootFunction.builder()))));
    }

    private void satchelOfSnacksLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "items/satchel_of_snacks"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(TagLootEntry.expandTag(FOOD_PROCESSED))));
    }

    private void satchelOfElixirsLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "items/satchel_of_elixirs"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(() -> POTION).apply(AddPotionLootFunction.builder()))));
    }

    private void foodReservesLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/food_reserves"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(TagLootEntry.expandTag(FOOD_PROCESSED))));
    }

    private void surpriseGiftLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/surprise_gift"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(() -> POTION).setWeight(75).apply(AddPotionLootFunction.builder()))
                                .add(ItemLootEntry.lootTableItem(ARROW_BUNDLE::get).setWeight(25))));
    }

    private void luckyExplorerLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/lucky_explorer"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(() -> EMERALD))));
    }

    private void prospectorLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/overworld"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(() -> EMERALD))));
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/the_nether"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(() -> GOLD_INGOT))));
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/the_end"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(() -> EMERALD))));
    }


}
