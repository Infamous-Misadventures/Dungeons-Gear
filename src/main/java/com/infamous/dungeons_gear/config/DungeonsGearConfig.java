package com.infamous.dungeons_gear.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class DungeonsGearConfig {

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DUNGEONS_GEAR_LOOT;
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_SALVAGING;
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_VILLAGER_TRADES;
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR;

        public final ForgeConfigSpec.ConfigValue<Integer> COMMON_ITEM_VALUE;
        public final ForgeConfigSpec.ConfigValue<Integer> UNIQUE_ITEM_VALUE;
        public final ForgeConfigSpec.ConfigValue<Integer> ARTIFACT_VALUE;

        public final ForgeConfigSpec.ConfigValue<Double> COMMON_WEAPON_COMMON_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> UNIQUE_WEAPON_COMMON_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> COMMON_ARMOR_COMMON_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> UNIQUE_ARMOR_COMMON_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> ARTIFACT_COMMON_LOOT;

        public final ForgeConfigSpec.ConfigValue<Double> COMMON_WEAPON_UNCOMMON_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> UNIQUE_WEAPON_UNCOMMON_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> COMMON_ARMOR_UNCOMMON_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> UNIQUE_ARMOR_UNCOMMON_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> ARTIFACT_UNCOMMON_LOOT;

        public final ForgeConfigSpec.ConfigValue<Double> COMMON_WEAPON_RARE_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> UNIQUE_WEAPON_RARE_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> COMMON_ARMOR_RARE_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> UNIQUE_ARMOR_RARE_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> ARTIFACT_RARE_LOOT;

        public final ForgeConfigSpec.ConfigValue<Double> COMMON_WEAPON_SUPER_RARE_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> UNIQUE_WEAPON_SUPER_RARE_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> COMMON_ARMOR_SUPER_RARE_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> UNIQUE_ARMOR_SUPER_RARE_LOOT;
        public final ForgeConfigSpec.ConfigValue<Double> ARTIFACT_SUPER_RARE_LOOT;

        public static ForgeConfigSpec.ConfigValue<List<? extends String>> COMMON_LOOT_TABLES;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> COMMON_LOOT_TABLES_BLACKLIST;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> UNCOMMON_LOOT_TABLES;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> UNCOMMON_LOOT_TABLES_BLACKLIST;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_LOOT_TABLES;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_LOOT_TABLES_BLACKLIST;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> SUPER_RARE_LOOT_TABLES;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> SUPER_RARE_LOOT_TABLES_BLACKLIST;

        public Common(ForgeConfigSpec.Builder builder){

            builder.comment("General Mod Configuration").push("general_mod_configuration");
            ENABLE_DUNGEONS_GEAR_LOOT = builder
                    .comment("Enable the mass addition of Dungeons Gear items to various vanilla loot tables by the mod itself. \n" +
                            "If you prefer to write your own loot pools via datapack or simply don't want it, disable this feature. [true / false]")
                    .define("enableDungeonsGearLoot", true);
            ENABLE_SALVAGING = builder
                    .comment("Enable the salvaging of Dungeons Gear items by shift-right clicking villagers with the item in your hand. \n" +
                            "If you have other mods messing with shift-right clicking villagers or simply don't want it, disable this feature. [true / false]")
                    .define("enableSalvaging", true);
            ENABLE_VILLAGER_TRADES = builder
                    .comment("Enable Weaponsmith, Fletcher, Armorer and Leatherworker Villagers trading Dungeons Gear items. \n" +
                    "If you have other mods messing with the trades of those professions or simply don't want it, disable this feature. [true / false]")
                    .define("enableVillagerTrades", true);
            ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR = builder
                    .comment("Enable applying enchantments from this mod on non-Dungeons gear using the Enchanting Table. \n" +
                            "If you don't want your enchantments to become too cluttered for non-Dungeons gear, or simply don't like it, disable this feature. \n" +
                            "You can still use the anvil to put the enchantments onto them and have them work correctly. [true / false]")
                    .define("enableEnchantsOnNonDungeonsGear", true);
            COMMON_ITEM_VALUE = builder
                    .comment("The emerald value for a common weapon or armor [0-64, default: 12]")
                    .defineInRange("commonItemValue", 12, 0, 64);
            UNIQUE_ITEM_VALUE = builder
                    .comment("The emerald value for a unique weapon or armor [0-64, default: 24]")
                    .defineInRange("uniqueItemValue", 24, 0, 64);
            ARTIFACT_VALUE = builder
                    .comment("The emerald value for an artifact [0-64, default: 24]")
                    .defineInRange("artifactValue", 24, 0, 64);
            builder.pop();

            builder.comment("Common Loot Table Configuration").push("common_loot_table_configuration");
            COMMON_LOOT_TABLES = builder
                    .comment("Add gear from this mod to loot tables considered common. \n"
                            + "To do so, enter the full path of the specific loot table, or the path of the folder containing the loot tables.\n"
                            + "You can also write an incomplete path, and the mod will add to loot tables containing that incomplete path.")
                    .defineList("commonLootTables", Lists.newArrayList(
                            "minecraft:chests/abandoned_mineshaft",
                            "minecraft:chests/shipwreck",
                            "minecraft:chests/desert_pyramid"
                            ),
                            (itemRaw) -> itemRaw instanceof String);
            COMMON_LOOT_TABLES_BLACKLIST = builder
                    .comment("Use this list to prevent specific loot tables from getting common loot. \n"
                            + "To do so, enter the full path of the specific loot table, or the path of the folder containing the loot tables.\n"
                            + "You can also write an incomplete path, and the mod will blacklist loot tables containing that incomplete path.")
                    .defineList("commonLootTablesBlacklist", Lists.newArrayList(

                            ),
                            (itemRaw) -> itemRaw instanceof String);
           COMMON_WEAPON_COMMON_LOOT = builder
                   .comment("The decimal chance for a common weapon to appear in common loot tables [0.0-1.0, default: 1.0]")
                   .defineInRange("commonWeaponCommonLoot", 1.0, 0.0, 1.0);
            UNIQUE_WEAPON_COMMON_LOOT = builder
                    .comment("The decimal chance for a unique weapon to appear in common loot tables [0.0-1.0, default: 0.15]")
                    .defineInRange("uniqueWeaponCommonLoot", 0.15, 0.0, 1.0);
            COMMON_ARMOR_COMMON_LOOT = builder
                    .comment("The decimal chance for a common armor to appear in common loot tables [0.0-1.0, default: 1.0]")
                    .defineInRange("commonArmorCommonLoot", 1.0, 0.0, 1.0);
            UNIQUE_ARMOR_COMMON_LOOT = builder
                    .comment("The decimal chance for a unique armor to appear in common loot tables [0.0-1.0, default: 0.15]")
                    .defineInRange("uniqueArmorCommonLoot", 0.15, 0.0, 1.0);
            ARTIFACT_COMMON_LOOT = builder
                    .comment("The decimal chance for an artifact to appear in common loot tables [0.0-1.0, default: 0.25]")
                    .defineInRange("artifactCommonLoot", 0.25, 0.0, 1.0);
            builder.pop();

            builder.comment("Uncommon Loot Table Configuration").push("uncommon_loot_table_configuration");
            UNCOMMON_LOOT_TABLES = builder
                    .comment("Add gear from this mod to loot tables considered uncommon. \n"
                            + "To do so, enter the full path of the specific loot table, or the path of the folder containing the loot tables.\n"
                            + "You can also write an incomplete path, and the mod will add to loot tables containing that incomplete path.")
                    .defineList("uncommonLootTables", Lists.newArrayList(
                            "minecraft:chests/jungle_temple",
                            "minecraft:chests/nether_bridge",
                            "minecraft:chests/bastion"
                            ),
                            (itemRaw) -> itemRaw instanceof String);
            UNCOMMON_LOOT_TABLES_BLACKLIST = builder
                    .comment("Use this list to prevent specific loot tables from getting uncommon loot. \n"
                            + "To do so, enter the full path of the specific loot table, or the path of the folder containing the loot tables.\n"
                            + "You can also write an incomplete path, and the mod will blacklist loot tables containing that incomplete path.")
                    .defineList("uncommonLootTablesBlacklist", Lists.newArrayList(
                            "minecraft:chests/jungle_temple_dispenser"
                            ),
                            (itemRaw) -> itemRaw instanceof String);
            COMMON_WEAPON_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for a common weapon to appear in uncommon loot tables [0.0-1.0, default: 1.0]")
                    .defineInRange("commonWeaponUncommonLoot", 1.0, 0.0, 1.0);
            UNIQUE_WEAPON_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for a unique weapon to appear in uncommon loot tables [0.0-1.0, default: 0.3]")
                    .defineInRange("uniqueWeaponUncommonLoot", 0.3, 0.0, 1.0);
            COMMON_ARMOR_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for a common armor to appear in uncommon loot tables [0.0-1.0, default: 1.0]")
                    .defineInRange("commonArmorUncommonLoot", 1.0, 0.0, 1.0);
            UNIQUE_ARMOR_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for a unique armor to appear in uncommon loot tables [0.0-1.0, default: 0.3]")
                    .defineInRange("uniqueArmorUncommonLoot", 0.3, 0.0, 1.0);
            ARTIFACT_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for an artifact to appear in uncommon loot tables [0.0-1.0, default: 0.5]")
                    .defineInRange("artifactUncommonLoot", 0.5, 0.0, 1.0);
            builder.pop();

            builder.comment("Rare Loot Table Configuration").push("rare_loot_table_configuration");
            RARE_LOOT_TABLES = builder
                    .comment("Add gear from this mod to loot tables considered rare. \n"
                            + "To do so, enter the full path of the specific loot table, or the path of the folder containing the loot tables.\n"
                            + "You can also write an incomplete path, and the mod will add to loot tables containing that incomplete path.")
                    .defineList("rareLootTables", Lists.newArrayList(
                            "minecraft:chests/stronghold",
                            "minecraft:chests/underwater_ruin",
                            "minecraft:chests/ruined_portal",
                            "minecraft:chests/pillager_outpost",
                            "minecraft:chests/simple_dungeon",
                            "minecraft:chests/end_city_treasure",
                            "minecraft:chests/igloo_chest"
                            ),
                            (itemRaw) -> itemRaw instanceof String);
            RARE_LOOT_TABLES_BLACKLIST = builder
                    .comment("Use this list to prevent specific loot tables from getting rare loot. \n"
                            + "To do so, enter the full path of the specific loot table, or the path of the folder containing the loot tables.\n"
                            + "You can also write an incomplete path, and the mod will blacklist loot tables containing that incomplete path.")
                    .defineList("rareLootTableBlacklist", Lists.newArrayList(

                            ),
                            (itemRaw) -> itemRaw instanceof String);
            COMMON_WEAPON_RARE_LOOT = builder
                    .comment("The decimal chance for a common weapon to appear in rare loot tables [0.0-1.0, default: 1.0]")
                    .defineInRange("commonWeaponRareLoot", 1.0, 0.0, 1.0);
            UNIQUE_WEAPON_RARE_LOOT = builder
                    .comment("The decimal chance for a unique weapon to appear in rare loot tables [0.0-1.0, default: 0.45]")
                    .defineInRange("uniqueWeaponRareLoot", 0.45, 0.0, 1.0);
            COMMON_ARMOR_RARE_LOOT = builder
                    .comment("The decimal chance for a common armor to appear in rare loot tables [0.0-1.0, default: 1.0]")
                    .defineInRange("commonArmorRareLoot", 1.0, 0.0, 1.0);
            UNIQUE_ARMOR_RARE_LOOT = builder
                    .comment("The decimal chance for a unique armor to appear in rare loot tables [0.0-1.0, default: 0.45]")
                    .defineInRange("uniqueArmorRareLoot", 0.45, 0.0, 1.0);
            ARTIFACT_RARE_LOOT = builder
                    .comment("The decimal chance for an artifact to appear in rare loot tables [0.0-1.0, default: 0.75]")
                    .defineInRange("artifactRareLoot", 0.75, 0.0, 1.0);
            builder.pop();

            builder.comment("Super Rare Loot Table Configuration").push("super_rare_loot_table_configuration");
            SUPER_RARE_LOOT_TABLES = builder
                    .comment("Add gear from this mod to loot tables considered super rare. \n"
                            + "To do so, enter the full path of the specific loot table, or the path of the folder containing the loot tables.\n"
                            + "You can also write an incomplete path, and the mod will add to loot tables containing that incomplete path.")
                    .defineList("superRareLootTables", Lists.newArrayList(
                            "minecraft:chests/woodland_mansion",
                            "minecraft:chests/buried_treasure"
                            ),
                            (itemRaw) -> itemRaw instanceof String);
            SUPER_RARE_LOOT_TABLES_BLACKLIST = builder
                    .comment("Use this list to prevent specific loot tables from getting super rare loot. \n"
                            + "To do so, enter the full path of the specific loot table, or the path of the folder containing the loot tables.\n"
                            + "You can also write an incomplete path, and the mod will blacklist loot tables containing that incomplete path.")
                    .defineList("superRareLootTablesBlacklist", Lists.newArrayList(

                            ),
                            (itemRaw) -> itemRaw instanceof String);
            COMMON_WEAPON_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for a common weapon to appear in super rare loot tables [0.0-1.0, default: 1.0]")
                    .defineInRange("commonWeaponSuperRareLoot", 1.0, 0, 1.0);
            UNIQUE_WEAPON_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for a unique weapon to appear in super rare loot tables [0.0-1.0, default: 0.6]")
                    .defineInRange("uniqueWeaponSuperRareLoot", 0.6, 0, 1.0);
            COMMON_ARMOR_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for a common armor to appear in super rare loot tables [0.0-1.0, default: 1.0]")
                    .defineInRange("commonArmorSuperRareLoot", 1.0, 0, 1.0);
            UNIQUE_ARMOR_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for a unique armor to appear in super rare loot tables [0.0-1.0, default: 0.6]")
                    .defineInRange("uniqueArmorSuperRareLoot", 0.6, 0, 1.0);
            ARTIFACT_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for an artifact to appear in super rare loot tables [0.0-1.0, default: 1.0]")
                    .defineInRange("artifactSuperRareLoot", 1.0, 0, 1.0);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();
    }
}