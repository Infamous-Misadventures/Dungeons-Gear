package com.infamous.dungeons_gear.datagen;

import com.infamous.dungeons_gear.loot.AddPotionLootFunction;
import com.infamous.dungeons_gear.loot.ExperimentalCondition;
import com.infamous.dungeons_gear.loot.LootTableRarity;
import com.infamous.dungeons_gear.loot.LootTableType;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.interfaces.IArmor;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_libraries.items.interfaces.IRangedWeapon;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.ItemTagWrappers.FOOD_PROCESSED;
import static com.infamous.dungeons_gear.loot.LootTableType.ALL;
import static com.infamous.dungeons_gear.registry.ItemInit.*;
import static net.minecraft.tags.ItemTags.ARROWS;
import static net.minecraft.world.item.Items.*;

public class ModChestLootTables extends ChestLoot {

    private final List<Item> EXPERIMENTAL_ITEMS = Arrays.asList(
            DUAL_CROSSBOW.get(),
            BABY_CROSSBOW.get()
    );

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
        for (LootTableType lootTableType : LootTableType.values()) {
            if (lootTableType == ALL) continue;
            createTypeLootTables(consumer, lootTableType);
        }
        createAllLootTables(consumer);
    }

    private void createAllLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        List<Item> lootItems = LOOT_TABLES.entrySet().stream().filter(entry -> !LootTableType.GIFT.equals(entry.getKey())).flatMap(entry -> entry.getValue().stream()).map(RegistryObject::get).toList();
        LootTableType lootTableType = ALL;
        createTableForSubtype(consumer, lootItems.stream().filter(this::isNormalItem).collect(Collectors.toList()), lootTableType.normalTable());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isUniqueItem).collect(Collectors.toList()), lootTableType.uniqueTable());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isArtifactItem).collect(Collectors.toList()), lootTableType.artifactTable());
        createItemLootTables(consumer, lootTableType);
    }

    private void createTypeLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTableType lootTableType) {
        List<Item> lootItems = LOOT_TABLES.getOrDefault(lootTableType, new ArrayList<>()).stream().map(RegistryObject::get).toList();
        createTableForSubtype(consumer, lootItems.stream().filter(this::isNormalItem).collect(Collectors.toList()), lootTableType.normalTable());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isUniqueItem).collect(Collectors.toList()), lootTableType.uniqueTable());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isArtifactItem).collect(Collectors.toList()), lootTableType.artifactTable());
        createItemLootTables(consumer, lootTableType);
    }

    private void createTableForSubtype(BiConsumer<ResourceLocation, LootTable.Builder> consumer, List<Item> items, ResourceLocation lootTable) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantValue.exactly(1));
        if (items.isEmpty()) {
            lootPool.add(EmptyLootItem.emptyItem());
        } else {
            items.forEach(item -> lootPool.add(getItemLootEntry(item)));
        }
        consumer.accept(lootTable, LootTable.lootTable().withPool(lootPool));
    }

    private LootPoolSingletonContainer.Builder<?> getItemLootEntry(Item item) {
        LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(item);
        if (EXPERIMENTAL_ITEMS.contains(item)) {
            builder.when(ExperimentalCondition::new);
        }
        return builder;
    }

    private boolean isArtifactItem(Item item) {
        return item instanceof ArtifactItem;
    }

    private boolean isNormalItem(Item item) {
        if (item instanceof ArtifactItem) {
            return false;
        }
        return !isUniqueItem(item);
    }

    private boolean isUniqueItem(Item item) {
        if (item instanceof IMeleeWeapon) {
            return ((IMeleeWeapon) item).isUnique();
        } else if (item instanceof IArmor) {
            return ((IArmor) item).isUnique();
        } else if (item instanceof IRangedWeapon) {
            return ((IRangedWeapon) item).isUnique();
        }
        return false;
    }

    private void createItemLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTableType lootTableType) {
        consumer.accept(LootTableRarity.COMMON.getTable(lootTableType),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F)).setBonusRolls(UniformGenerator.between(0, 1))
                                .add(LootTableReference.lootTableReference(lootTableType.normalTable()).setWeight(54))
                                .add(LootTableReference.lootTableReference(lootTableType.uniqueTable()).setWeight(1).setQuality(1))
                                .add(EmptyLootItem.emptyItem().setWeight(45).setQuality(-1)))
        );
        consumer.accept(LootTableRarity.FANCY.getTable(lootTableType),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F)).setBonusRolls(UniformGenerator.between(0, 2))
                                .add(LootTableReference.lootTableReference(lootTableType.artifactTable()).setWeight(15))
                                .add(LootTableReference.lootTableReference(lootTableType.normalTable()).setWeight(12).setQuality(-2))
                                .add(LootTableReference.lootTableReference(lootTableType.normalTable()).setWeight(28).setQuality(2).apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(15)).allowTreasure()))
                                .add(LootTableReference.lootTableReference(lootTableType.uniqueTable()).setWeight(5).setQuality(2).apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(15)).allowTreasure()))
                                .add(EmptyLootItem.emptyItem().setWeight(40).setQuality(-2)))
        );
        consumer.accept(LootTableRarity.OBSIDIAN.getTable(lootTableType),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 4.0F)).setBonusRolls(UniformGenerator.between(0, 2))
                                .add(LootTableReference.lootTableReference(lootTableType.artifactTable()).setWeight(15))
                                .add(LootTableReference.lootTableReference(lootTableType.normalTable()).setWeight(17).setQuality(-2).apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(15)).allowTreasure()))
                                .add(LootTableReference.lootTableReference(lootTableType.normalTable()).setWeight(28).setQuality(3).apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30)).allowTreasure()))
                                .add(LootTableReference.lootTableReference(lootTableType.uniqueTable()).setWeight(5).setQuality(3).apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30)).allowTreasure()))
                                .add(EmptyLootItem.emptyItem().setWeight(35).setQuality(-4)))
        );
    }

    private void arrowBundleTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        ResourceLocation tagTable = new ResourceLocation(MODID, "items/arrow_bundle/expand_tag");
        consumer.accept(tagTable,
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(TagEntry.expandTag(ARROWS))));
        consumer.accept(new ResourceLocation(MODID, "items/arrow_bundle"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(() -> ARROW).setWeight(3))
                                .add(LootTableReference.lootTableReference(tagTable).setWeight(1).apply(AddPotionLootFunction.builder()))));
    }

    private void satchelOfSnacksLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "items/satchel_of_snacks"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(TagEntry.expandTag(FOOD_PROCESSED))));
    }

    private void satchelOfElixirsLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "items/satchel_of_elixirs"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(() -> POTION).apply(AddPotionLootFunction.builder()))));
    }

    private void foodReservesLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/food_reserves"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(TagEntry.expandTag(FOOD_PROCESSED))));
    }

    private void surpriseGiftLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/surprise_gift"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(() -> POTION).setWeight(75).apply(AddPotionLootFunction.builder()))
                                .add(LootItem.lootTableItem(ARROW_BUNDLE::get).setWeight(25))));
    }

    private void luckyExplorerLootTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/lucky_explorer"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(() -> EMERALD))));
    }

    private void prospectorLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/overworld"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(() -> EMERALD))));
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/the_nether"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(() -> GOLD_INGOT))));
        consumer.accept(new ResourceLocation(MODID, "enchantments/prospector/the_end"),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(() -> EMERALD))));
    }


}
