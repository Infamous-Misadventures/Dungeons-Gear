package com.infamous.dungeons_gear.loot;

import com.google.gson.JsonObject;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.utilties.LootTableHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

import static com.infamous.dungeons_gear.DungeonsGear.LOGGER;
import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.loot.LootTableType.*;
import static net.minecraft.loot.LootTables.SPAWN_BONUS_CHEST;


public class GlobalLootModifier{

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class GlobalLootModifierRegistry {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
            event.getRegistry().register(
                    new DungeonsLootAdditions.Serializer().setRegistryName(new ResourceLocation(MODID,"dungeons_loot_additions"))
            );
        }
    }

    public static class DungeonsLootAdditions extends LootModifier {

        public DungeonsLootAdditions(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            List<ItemStack> modifiedLoot = generatedLoot;
            LOGGER.info("DungeonsLootAdditions: " + context.getQueriedLootTableId());
            // return early if the user has disabled this feature
            if(!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            if(!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT_ON_BONUS_CHEST.get() && context.getQueriedLootTableId().equals(SPAWN_BONUS_CHEST)){
                return generatedLoot;
            }
            modifiedLoot = modExceptions(modifiedLoot, context);
            ResourceLocation lootTable = determineTable(context.getQueriedLootTableId());
            LOGGER.info("DungeonsLootAdditions: " + lootTable);
            if(lootTable == null) return generatedLoot;
            List<ItemStack> itemStacks = LootTableHelper.generateItemStacks(context.getLevel(), context, lootTable);
            itemStacks.stream().map(itemStack -> itemStack.getItem().getRegistryName()).collect(Collectors.toList()).forEach(LOGGER::info);
            modifiedLoot.addAll(itemStacks);
            return modifiedLoot;
        }

        private List<ItemStack> modExceptions(List<ItemStack> modifiedLoot, LootContext context) {
            if(context.getQueriedLootTableId().getNamespace().contains("repurposed_structures") ||
                    context.getQueriedLootTableId().getNamespace().contains("betterstrongholds")){
                modifiedLoot = modifiedLoot.stream().filter(itemStack -> !itemStack.getItem().getRegistryName().getNamespace().equals(MODID)).collect(Collectors.toList());
            }
            return modifiedLoot;
        }

        private ResourceLocation determineTable(ResourceLocation lootTable) {
            LootTableRarity rarity = getRarity(lootTable);
            LootTableType type = getType(lootTable);
            if(rarity == null || type == null) return null;
            return rarity.getTable(type);
        }

        private LootTableType getType(ResourceLocation lootTable) {
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.DESERT_LOOT_TABLES.get(), DungeonsGearConfig.DESERT_LOOT_TABLES_BLACKLIST.get())){
                return DESERT;
            }
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.OCEAN_LOOT_TABLES.get(), DungeonsGearConfig.OCEAN_LOOT_TABLES_BLACKLIST.get())){
                return OCEAN;
            }
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.COLD_LOOT_TABLES.get(), DungeonsGearConfig.COLD_LOOT_TABLES_BLACKLIST.get())){
                return COLD;
            }
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.JUNGLE_LOOT_TABLES.get(), DungeonsGearConfig.JUNGLE_LOOT_TABLES_BLACKLIST.get())){
                return JUNGLE;
            }
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.NETHER_LOOT_TABLES.get(), DungeonsGearConfig.NETHER_LOOT_TABLES_BLACKLIST.get())){
                return NETHER;
            }
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.END_LOOT_TABLES.get(), DungeonsGearConfig.END_LOOT_TABLES_BLACKLIST.get())){
                return END;
            }
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.BASIC_LOOT_TABLES.get(), DungeonsGearConfig.BASIC_LOOT_TABLES_BLACKLIST.get())){
                return BASIC;
            }
            return null;
        }

        private LootTableRarity getRarity(ResourceLocation lootTable) {
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.OBSIDIAN_LOOT_TABLES.get(), DungeonsGearConfig.OBSIDIAN_LOOT_TABLES_BLACKLIST.get())){
                return LootTableRarity.OBSIDIAN;
            }
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.FANCY_LOOT_TABLES.get(), DungeonsGearConfig.FANCY_LOOT_TABLES_BLACKLIST.get())){
                return LootTableRarity.FANCY;
            }
            if(checkLootTableConfig(lootTable, DungeonsGearConfig.COMMON_LOOT_TABLES.get(), DungeonsGearConfig.COMMON_LOOT_TABLES_BLACKLIST.get())){
                return LootTableRarity.COMMON;
            }
            return null;
        }

        private boolean checkLootTableConfig(ResourceLocation lootTable, List<? extends String> whitelist, List<? extends String> blacklist) {
            if(partialContains(lootTable, blacklist)) return false;
            return partialContains(lootTable, whitelist);
        }

        private boolean partialContains(ResourceLocation lootTable, List<? extends String> configItemList) {
            return configItemList.stream().anyMatch(configItem -> partialMatch(lootTable, configItem));
        }

        private boolean partialMatch(ResourceLocation lootTable, String configItem) {
            ResourceLocation configAsResourceLocation = new ResourceLocation(configItem);
            if(configAsResourceLocation.getNamespace().equals("minecraft")){
                return lootTable.getPath().contains(configAsResourceLocation.getPath());
            }else{
                return lootTable.getNamespace().contains(configAsResourceLocation.getNamespace()) && lootTable.getPath().contains(configAsResourceLocation.getPath());
            }
        }

        public static class Serializer extends GlobalLootModifierSerializer<DungeonsLootAdditions> {

            @Override
            public DungeonsLootAdditions read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
                return new DungeonsLootAdditions(conditionsIn);
            }

            @Override
            public JsonObject write(DungeonsLootAdditions instance) {
                return this.makeConditions(instance.conditions);
            }
        }
    }
}