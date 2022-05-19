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
import static net.minecraft.loot.LootParameters.ORIGIN;


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
            List<ItemStack> itemStacks = LootTableHelper.generateItemStacks(context.getLevel(), context.getParamOrNull(ORIGIN), lootTable, context.getRandom());
            generatedLoot.addAll(itemStacks);
            return generatedLoot;
        }

        private ResourceLocation determineTable(ResourceLocation lootTable) {
            String lootTablePath = lootTable.toString();
            String rarity = getRarity(lootTablePath);
            String type = getType(lootTablePath);
            if(rarity.isEmpty() || type.isEmpty()) return null;
            return new ResourceLocation(MODID, rarity + "_" + type);
        }

        private String getType(String lootTablePath) {
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.BASIC_LOOT_TABLES.get(), DungeonsGearConfig.BASIC_LOOT_TABLES_BLACKLIST.get())){
                return "basic";
            }
            return "basic";
        }

        private String getRarity(String lootTablePath) {
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.OBSIDIAN_LOOT_TABLES.get(), DungeonsGearConfig.OBSIDIAN_LOOT_TABLES_BLACKLIST.get())){
                return "obsidian";
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.FANCY_LOOT_TABLES.get(), DungeonsGearConfig.FANCY_LOOT_TABLES_BLACKLIST.get())){
                return "fancy";
            }
            if(checkLootTableConfig(lootTablePath, DungeonsGearConfig.COMMON_LOOT_TABLES.get(), DungeonsGearConfig.COMMON_LOOT_TABLES_BLACKLIST.get())){
                return "common";
            }
            return "";
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
                return null;
            }
        }
    }
}