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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.loot.LootTableType.*;


public class GlobalLootModifier{

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class GlobalLootModifierRegistry {

        @SubscribeEvent
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
            // return early if the user has disabled this feature
            if(!DungeonsGearConfig.ENABLE_DUNGEONS_GEAR_LOOT.get()){
                return generatedLoot;
            }
            ResourceLocation lootTable = determineTable(context.getQueriedLootTableId());
            if(lootTable == null) return generatedLoot;
            List<ItemStack> itemStacks = LootTableHelper.generateItemStacks(context.getLevel(), context, lootTable);
            generatedLoot.addAll(itemStacks);
            return generatedLoot;
        }

        private ResourceLocation determineTable(ResourceLocation lootTable) {
            String lootTablePath = lootTable.toString();
            LootTableRarity rarity = getRarity(lootTablePath);
            LootTableType type = getType(lootTablePath);
            if(rarity == null || type == null) return null;
            return rarity.getTable(type);
        }

        private LootTableType getType(String lootTablePath) {
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.BASIC_LOOT_TABLES.get(), DungeonsGearConfig.BASIC_LOOT_TABLES_BLACKLIST.get())){
                return BASIC;
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.DESERT_LOOT_TABLES.get(), DungeonsGearConfig.DESERT_LOOT_TABLES_BLACKLIST.get())){
                return DESERT;
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.OCEAN_LOOT_TABLES.get(), DungeonsGearConfig.OCEAN_LOOT_TABLES_BLACKLIST.get())){
                return OCEAN;
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.COLD_LOOT_TABLES.get(), DungeonsGearConfig.COLD_LOOT_TABLES_BLACKLIST.get())){
                return COLD;
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.JUNGLE_LOOT_TABLES.get(), DungeonsGearConfig.JUNGLE_LOOT_TABLES_BLACKLIST.get())){
                return JUNGLE;
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.NETHER_LOOT_TABLES.get(), DungeonsGearConfig.NETHER_LOOT_TABLES_BLACKLIST.get())){
                return NETHER;
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.END_LOOT_TABLES.get(), DungeonsGearConfig.END_LOOT_TABLES_BLACKLIST.get())){
                return END;
            }
            return null;
        }

        private LootTableRarity getRarity(String lootTablePath) {
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.OBSIDIAN_LOOT_TABLES.get(), DungeonsGearConfig.OBSIDIAN_LOOT_TABLES_BLACKLIST.get())){
                return LootTableRarity.OBSIDIAN;
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.FANCY_LOOT_TABLES.get(), DungeonsGearConfig.FANCY_LOOT_TABLES_BLACKLIST.get())){
                return LootTableRarity.FANCY;
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.COMMON_LOOT_TABLES.get(), DungeonsGearConfig.COMMON_LOOT_TABLES_BLACKLIST.get())){
                return LootTableRarity.COMMON;
            }
            return null;
        }

        private boolean checkLootTableConfig(String lootTablePath, List<? extends String> whitelist, List<? extends String> blacklist) {
            if(blacklist.contains(lootTablePath)) return false;
            return whitelist.stream().anyMatch(lootTablePath::contains);
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