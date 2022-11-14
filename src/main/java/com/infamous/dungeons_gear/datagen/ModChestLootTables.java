package com.infamous.dungeons_gear.datagen;

import com.infamous.dungeons_gear.loot.AddPotionLootFunction;
import com.infamous.dungeons_gear.loot.ExperimentalCondition;
import com.infamous.dungeons_gear.loot.LootTableRarity;
import com.infamous.dungeons_gear.loot.LootTableType;
import com.infamous.dungeons_libraries.items.artifacts.ArtifactItem;
import com.infamous.dungeons_libraries.items.interfaces.IArmor;
import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_libraries.items.interfaces.IRangedWeapon;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.EnchantWithLevels;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.items.ItemTagWrappers.FOOD_PROCESSED;
import static com.infamous.dungeons_gear.loot.LootTableType.ALL;
import static com.infamous.dungeons_gear.registry.ItemRegistry.*;
import static net.minecraft.item.Items.*;
import static net.minecraft.tags.ItemTags.ARROWS;

public class ModChestLootTables extends ChestLootTables {

    private List<Item> EXPERIMENTAL_ITEMS = Arrays.asList(
            DAGGER.get(),
            FANG_OF_FROST.get(),
            MOON_DAGGER.get(),
            SHEAR_DAGGER.get(),
            SICKLE.get(),
            NIGHTMARES_BITE.get(),
            THE_LAST_LAUGH.get(),
            GAUNTLET.get(),
            FIGHTERS_BINDING.get(),
            MAULER.get(),
            SOUL_FIST.get(),
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
            if(lootTableType == ALL) continue;
            createTypeLootTables(consumer, lootTableType);
        }
        createAllLootTables(consumer);
    }

    private void createAllLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        List<Item> lootItems = LOOT_TABLES.entrySet().stream().filter(entry -> !LootTableType.GIFT.equals(entry.getKey())).flatMap(entry -> entry.getValue().stream()).map(RegistryObject::get).collect(Collectors.toList());
        LootTableType lootTableType = ALL;
        createTableForSubtype(consumer, lootItems.stream().filter(this::isNormalItem).collect(Collectors.toList()), lootTableType.normalTable());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isUniqueItem).collect(Collectors.toList()), lootTableType.uniqueTable());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isArtifactItem).collect(Collectors.toList()), lootTableType.artifactTable());
        createItemLootTables(consumer, lootTableType);
    }

    private void createTypeLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTableType lootTableType) {
        List<Item> lootItems = LOOT_TABLES.getOrDefault(lootTableType, new ArrayList<>()).stream().map(RegistryObject::get).collect(Collectors.toList());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isNormalItem).collect(Collectors.toList()), lootTableType.normalTable());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isUniqueItem).collect(Collectors.toList()), lootTableType.uniqueTable());
        createTableForSubtype(consumer, lootItems.stream().filter(this::isArtifactItem).collect(Collectors.toList()), lootTableType.artifactTable());
        createItemLootTables(consumer, lootTableType);
    }

    private void createTableForSubtype(BiConsumer<ResourceLocation, LootTable.Builder> consumer, List<Item> items, ResourceLocation lootTable) {
        LootPool.Builder lootPool = LootPool.lootPool().setRolls(ConstantRange.exactly(1));
        if(items.isEmpty()){
            lootPool.add(EmptyLootEntry.emptyItem());
        }else{
            items.forEach(item -> lootPool.add(getItemLootEntry(item)));
        }
        consumer.accept(lootTable, LootTable.lootTable().withPool(lootPool));
    }

    private StandaloneLootEntry.Builder<?> getItemLootEntry(Item item) {
        StandaloneLootEntry.Builder<?> builder = ItemLootEntry.lootTableItem(item);
        if(EXPERIMENTAL_ITEMS.contains(item)){
            builder.when(ExperimentalCondition::new);
        }
        return builder;
    }

    private boolean isArtifactItem(Item item) {
        return item instanceof ArtifactItem;
    }

    private boolean isNormalItem(Item item) {
        if(item instanceof ArtifactItem){
            return false;
        }
        return !isUniqueItem(item);
    }

    private boolean isUniqueItem(Item item) {
        if(item instanceof IMeleeWeapon){
            return ((IMeleeWeapon) item).isUnique();
        }else if(item instanceof IArmor){
            return ((IArmor) item).isUnique();
        }else if(item instanceof IRangedWeapon){
            return ((IRangedWeapon)item).isUnique();
        }
        return false;
    }

    private void createItemLootTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTableType lootTableType) {
        consumer.accept(LootTableRarity.COMMON.getTable(lootTableType),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(RandomValueRange.between(0.0F, 2.0F)).bonusRolls(0, 1)
                                .add(TableLootEntry.lootTableReference(lootTableType.normalTable()).setWeight(54))
                                .add(TableLootEntry.lootTableReference(lootTableType.uniqueTable()).setWeight(1).setQuality(1))
                                .add(EmptyLootEntry.emptyItem().setWeight(45).setQuality(-1)))
        );
        consumer.accept(LootTableRarity.FANCY.getTable(lootTableType),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(RandomValueRange.between(1.0F, 2.0F)).bonusRolls(0, 2)
                                .add(TableLootEntry.lootTableReference(lootTableType.artifactTable()).setWeight(15))
                                .add(TableLootEntry.lootTableReference(lootTableType.normalTable()).setWeight(12).setQuality(-2))
                                .add(TableLootEntry.lootTableReference(lootTableType.normalTable()).setWeight(28).setQuality(2).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(15)).allowTreasure()))
                                .add(TableLootEntry.lootTableReference(lootTableType.uniqueTable()).setWeight(5).setQuality(2).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(15)).allowTreasure()))
                                .add(EmptyLootEntry.emptyItem().setWeight(40).setQuality(-2)))
        );
        consumer.accept(LootTableRarity.OBSIDIAN.getTable(lootTableType),
                LootTable.lootTable().
                        withPool(LootPool.lootPool().setRolls(RandomValueRange.between(1.0F, 3.0F)).bonusRolls(0, 2)
                                .add(TableLootEntry.lootTableReference(lootTableType.artifactTable()).setWeight(15))
                                .add(TableLootEntry.lootTableReference(lootTableType.normalTable()).setWeight(17).setQuality(-2).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(15)).allowTreasure()))
                                .add(TableLootEntry.lootTableReference(lootTableType.normalTable()).setWeight(28).setQuality(3).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(30)).allowTreasure()))
                                .add(TableLootEntry.lootTableReference(lootTableType.uniqueTable()).setWeight(5).setQuality(3).apply(EnchantWithLevels.enchantWithLevels(ConstantRange.exactly(30)).allowTreasure()))
                                .add(EmptyLootEntry.emptyItem().setWeight(35).setQuality(-4)))
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
