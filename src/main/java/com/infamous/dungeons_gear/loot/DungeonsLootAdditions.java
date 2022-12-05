package com.infamous.dungeons_gear.loot;

import com.google.common.base.Suppliers;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.loot.LootTableType.*;
import static net.minecraft.world.level.storage.loot.BuiltInLootTables.SPAWN_BONUS_CHEST;

public class DungeonsLootAdditions extends LootModifier {

    public static final Supplier<Codec<DungeonsLootAdditions>> CODEC =  Suppliers.memoize(()
            ->RecordCodecBuilder.create(instance -> codecStart(instance).apply(instance, DungeonsLootAdditions::new)));

    public DungeonsLootAdditions(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ObjectArrayList<ItemStack> modifiedLoot = generatedLoot;
        // return early if the user has disabled this feature
        if (!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT.get()) {
            return generatedLoot;
        }
        if (!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT_ON_BONUS_CHEST.get() && context.getQueriedLootTableId().equals(SPAWN_BONUS_CHEST)) {
            return generatedLoot;
        }
        modifiedLoot = modExceptions(modifiedLoot, context);
        ResourceLocation lootTable = determineTable(context.getQueriedLootTableId());
        if (lootTable == null) return generatedLoot;
        List<ItemStack> itemStacks = LootTableHelper.generateItemStacks(context.getLevel(), context, lootTable);
        modifiedLoot.addAll(itemStacks);
        return modifiedLoot;
    }

    private ObjectArrayList<ItemStack> modExceptions(ObjectArrayList<ItemStack> modifiedLoot, LootContext context) {
        if (context.getQueriedLootTableId().getNamespace().contains("repurposed_structures") ||
                context.getQueriedLootTableId().getNamespace().contains("betterstrongholds")) {
            modifiedLoot = new ObjectArrayList<>(modifiedLoot.stream().filter(itemStack -> !ForgeRegistries.ITEMS.getKey(itemStack.getItem()).getNamespace().equals(MODID)).collect(Collectors.toList()));
        }
        return modifiedLoot;
    }

    private ResourceLocation determineTable(ResourceLocation lootTable) {
        LootTableRarity rarity = getRarity(lootTable);
        LootTableType type = getType(lootTable);
        if (rarity == null || type == null) return null;
        return rarity.getTable(type);
    }

    private LootTableType getType(ResourceLocation lootTable) {
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.DESERT_LOOT_TABLES.get(), DungeonsGearConfig.DESERT_LOOT_TABLES_BLACKLIST.get())) {
            return DESERT;
        }
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.OCEAN_LOOT_TABLES.get(), DungeonsGearConfig.OCEAN_LOOT_TABLES_BLACKLIST.get())) {
            return OCEAN;
        }
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.COLD_LOOT_TABLES.get(), DungeonsGearConfig.COLD_LOOT_TABLES_BLACKLIST.get())) {
            return COLD;
        }
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.JUNGLE_LOOT_TABLES.get(), DungeonsGearConfig.JUNGLE_LOOT_TABLES_BLACKLIST.get())) {
            return JUNGLE;
        }
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.NETHER_LOOT_TABLES.get(), DungeonsGearConfig.NETHER_LOOT_TABLES_BLACKLIST.get())) {
            return NETHER;
        }
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.END_LOOT_TABLES.get(), DungeonsGearConfig.END_LOOT_TABLES_BLACKLIST.get())) {
            return END;
        }
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.BASIC_LOOT_TABLES.get(), DungeonsGearConfig.BASIC_LOOT_TABLES_BLACKLIST.get())) {
            return BASIC;
        }
        return null;
    }

    private LootTableRarity getRarity(ResourceLocation lootTable) {
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.OBSIDIAN_LOOT_TABLES.get(), DungeonsGearConfig.OBSIDIAN_LOOT_TABLES_BLACKLIST.get())) {
            return LootTableRarity.OBSIDIAN;
        }
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.FANCY_LOOT_TABLES.get(), DungeonsGearConfig.FANCY_LOOT_TABLES_BLACKLIST.get())) {
            return LootTableRarity.FANCY;
        }
        if (checkLootTableConfig(lootTable, DungeonsGearConfig.COMMON_LOOT_TABLES.get(), DungeonsGearConfig.COMMON_LOOT_TABLES_BLACKLIST.get())) {
            return LootTableRarity.COMMON;
        }
        return null;
    }

    private boolean checkLootTableConfig(ResourceLocation lootTable, List<? extends String> whitelist, List<? extends String> blacklist) {
        if (partialContains(lootTable, blacklist)) return false;
        return partialContains(lootTable, whitelist);
    }

    private boolean partialContains(ResourceLocation lootTable, List<? extends String> configItemList) {
        return configItemList.stream().anyMatch(configItem -> partialMatch(lootTable, configItem));
    }

    private boolean partialMatch(ResourceLocation lootTable, String configItem) {
        ResourceLocation configAsResourceLocation = new ResourceLocation(configItem);
        if (configAsResourceLocation.getNamespace().equals("minecraft")) {
            return lootTable.getPath().contains(configAsResourceLocation.getPath());
        } else {
            return lootTable.getNamespace().contains(configAsResourceLocation.getNamespace()) && lootTable.getPath().contains(configAsResourceLocation.getPath());
        }
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
