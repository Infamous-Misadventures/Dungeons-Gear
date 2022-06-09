package com.infamous.dungeons_gear.datagen;

import com.infamous.dungeons_gear.loot.AddPotionLootFunction;
import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.EnchantWithLevels;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.ItemTagWrappers.FOOD_PROCESSED;
import static com.infamous.dungeons_gear.registry.ItemRegistry.*;
import static com.infamous.dungeons_gear.utilties.GeneralHelper.modLoc;
import static net.minecraft.item.Items.*;
import static net.minecraft.tags.ItemTags.ARROWS;

public class ModChestLootTables extends ChestLootTables {

    private static final List<String> TYPES = Arrays.asList("basic", "nether", "sea", "jungle", "snow", "end", "desert");

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        //ChestAdditions
        chestAdditionsLootTables(consumer);
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

    private void chestAdditionsLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        TYPES.forEach(type -> createItemLootTables(consumer, type));
        consumer.accept(modLoc("basic_normal"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(SWORD::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(PICKAXE::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(AXE::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(HUNTERS_ARMOR::get).setWeight(1))
                        )

        );
        consumer.accept(modLoc("basic_unique"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                                .add(ItemLootEntry.lootTableItem(ItemRegistry.DIAMOND_SWORD::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(HAWKBRAND::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(ItemRegistry.DIAMOND_PICKAXE::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(FIREBRAND::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(HIGHLAND_AXE::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(ARCHERS_ARMOR::get).setWeight(1))
                                .add(ItemLootEntry.lootTableItem(ARCHERS_ARMOR_HOOD::get).setWeight(1))
                        )

        );
    }

    private void createItemLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer, String type) {
        ResourceLocation typeNormalTable = modLoc(type + "_normal");
        ResourceLocation typeUniqueTable = modLoc(type + "_unique");
        consumer.accept(modLoc("common_" + type),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(RandomValueRange.between(0.0F, 2.0F)).bonusRolls(0, 1)
                                .add(TableLootEntry.lootTableReference(typeNormalTable).setWeight(54))
                                .add(TableLootEntry.lootTableReference(typeUniqueTable).setWeight(1).setQuality(1))
                                .add(EmptyLootEntry.emptyItem().setWeight(45).setQuality(-1)))
        );
        consumer.accept(modLoc("fancy_" + type),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(RandomValueRange.between(1.0F, 3.0F)).bonusRolls(0, 2)
                                .add(TableLootEntry.lootTableReference(typeNormalTable).setWeight(20).setQuality(-2))
                                .add(TableLootEntry.lootTableReference(typeNormalTable).setWeight(35).setQuality(2).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(15)).allowTreasure()))
                                .add(TableLootEntry.lootTableReference(typeUniqueTable).setWeight(5).setQuality(2).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(15)).allowTreasure()))
                                .add(EmptyLootEntry.emptyItem().setWeight(40).setQuality(-2)))
        );
        consumer.accept(modLoc("obsidian_" + type),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(RandomValueRange.between(1.0F, 4.0F)).bonusRolls(0, 2)
                                .add(TableLootEntry.lootTableReference(typeNormalTable).setWeight(25).setQuality(-3).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(15)).allowTreasure()))
                                .add(TableLootEntry.lootTableReference(typeNormalTable).setWeight(35).setQuality(3).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(30)).allowTreasure()))
                                .add(TableLootEntry.lootTableReference(typeUniqueTable).setWeight(5).setQuality(3).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(30)).allowTreasure()))
                                .add(EmptyLootEntry.emptyItem().setWeight(35).setQuality(-3)))
        );
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
