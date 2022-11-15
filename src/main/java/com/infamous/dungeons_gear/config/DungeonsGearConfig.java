package com.infamous.dungeons_gear.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.loot.LootTableRarity;
import com.infamous.dungeons_gear.loot.LootTableType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.util.List;

public class DungeonsGearConfig {
    private final LootTablesConfigHelper lootTables = new LootTablesConfigHelper();
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec.ConfigValue<Integer> METAL_MELEE_WEAPON_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DUNGEONS_GEAR_LOOT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DUNGEONS_GEAR_LOOT_ON_BONUS_CHEST;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_EXPERIMENTAL;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_SALVAGING;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_VILLAGER_TRADES;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_FRIENDLY_PET_FIRE;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ELENAI_DODGE_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_WAR_DANCE_COMPAT;

    public static ForgeConfigSpec.ConfigValue<Integer> COMMON_ITEM_VALUE;
    public static ForgeConfigSpec.ConfigValue<Integer> UNIQUE_ITEM_VALUE;
    public static ForgeConfigSpec.ConfigValue<Integer> ARTIFACT_VALUE;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> COMMON_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> COMMON_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> FANCY_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> FANCY_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> OBSIDIAN_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> OBSIDIAN_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BASIC_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DESERT_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> OCEAN_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> COLD_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> JUNGLE_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> NETHER_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> END_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BASIC_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> DESERT_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> OCEAN_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> COLD_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> JUNGLE_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> NETHER_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> END_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENEMY_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_AREA_OF_EFFECT_ON_PLAYERS;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENCHANTMENT_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> TREASURE_ONLY_ENCHANTMENTS;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENCHANTMENT_TRADES;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENCHANTMENT_LOOT;

    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_MELEE_WEAPON_LOOT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_RANGED_WEAPON_LOOT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ARMOR_LOOT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ARTIFACT_LOOT;

    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_MELEE_WEAPON_TAB;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_RANGED_WEAPON_TAB;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ARMOR_TAB;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ARTIFACT_TAB;

    //Effect Values
    public static ForgeConfigSpec.ConfigValue<Integer> PARTY_STARTER_DAMAGE;

    // Enchanting specific values
    public static ForgeConfigSpec.ConfigValue<Double> BUSY_BEE_BASE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> BUSY_BEE_CHANCE_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> TUMBLE_BEE_CHANCE_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> RAMPAGING_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> RAMPAGING_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> RUSHDOWN_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> WEAKENING_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> WEAKENING_DISTANCE;
    public static ForgeConfigSpec.ConfigValue<Double> LEECHING_BASE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> LEECHING_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> COMMITTED_BASE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> COMMITTED_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> DYNAMO_MAX_STACKS;
    public static ForgeConfigSpec.ConfigValue<Double> DYNAMO_DAMAGE_MULTIPLIER_PER_STACK;
    public static ForgeConfigSpec.ConfigValue<Integer> FREEZING_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> SOUL_SIPHON_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> SOUL_SIPHON_SOULS_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> CHAINS_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> RADIANCE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> THUNDERING_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Integer> THUNDERING_BASE_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<Double> ALTRUISTIC_DAMAGE_TO_HEALING_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> BEAST_BOSS_BASE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> BEAST_BOSS_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> BEAST_BURST_DAMAGE_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> BEAST_SURGE_DURATION;
    public static ForgeConfigSpec.ConfigValue<Double> COWARDICE_BASE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> COWARDICE_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> DEFLECT_CHANCE_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> FOCUS_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> FRENZIED_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> GRAVITY_PULSE_BASE_STRENGTH;
    public static ForgeConfigSpec.ConfigValue<Double> GRAVITY_PULSE_STRENGTH_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Integer> POTION_BARRIER_BASE_DURATION;
    public static ForgeConfigSpec.ConfigValue<Integer> POTION_BARRIER_DURATION_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> RECKLESS_MAX_HEALTH_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> RECKLESS_ATTACK_DAMAGE_BASE_MULTIPLIER;
    public static ForgeConfigSpec.ConfigValue<Double> RECKLESS_ATTACK_DAMAGE_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> EXPLODING_MULTIPLIER_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> PROSPECTOR_CHANCE_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> POISON_CLOUD_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> DODGE_CHANCE_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> VOID_DODGE_CHANCE_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> BEEHIVE_CHANCE_PER_LEVEL;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> LOVE_MEDALLION_BLACKLIST;

    private static CommentedFileConfig cfg;

    public DungeonsGearConfig() {
        cfg = CommentedFileConfig
                .builder(new File(FMLPaths.CONFIGDIR.get().toString(), DungeonsGear.MODID + "-common.toml")).sync()
                .autosave().build();
        cfg.load();
        initConfig();
        ForgeConfigSpec spec = builder.build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec, cfg.getFile().getName());
        spec.setConfig(cfg);
    }

    private void initConfig() {
        lootTables.init();
        builder.comment("General Mod Configuration").push("general_mod_configuration");
        ENABLE_DUNGEONS_GEAR_LOOT = builder
                .comment("Enable the mass addition of Dungeons Gear items to various vanilla loot tables by the mod itself. \n" +
                        "If you prefer to write your own loot pools via datapack or simply don't want it, disable this feature. [true / false]")
                .define("enableDungeonsGearLoot", true);
        ENABLE_DUNGEONS_GEAR_LOOT_ON_BONUS_CHEST = builder
                .comment("Enable the addition of Dungeons Gear items to the Minecraft Spawn Bonus chest. \n" +
                        "Theoretically, this can be controlled through the loot tables, this option is added for additional control. [true / false]")
                .define("enableDungeonsGearLootOnBonusChest", true);
        ENABLE_EXPERIMENTAL = builder
                .comment("Enables the experimental features.\n" +
                        "If you like living on the edge with broken items, enable this feature. [true / false]")
                .define("enableExperimental", false);
        ENABLE_SALVAGING = builder
                .comment("Enable the salvaging of Dungeons Gear items by shift-right clicking villagers with the item in your hand. \n" +
                        "If you have other mods messing with shift-right clicking villagers or simply don't want it, disable this feature. [true / false]")
                .define("enableSalvaging", true);
        ENABLE_VILLAGER_TRADES = builder
                .comment("Enable Weaponsmith, Fletcher, Armorer and Leatherworker Villagers trading Dungeons Gear items. \n" +
                        "If you have other mods messing with the trades of those professions or simply don't want it, disable this feature. [true / false]")
                .define("enableVillagerTrades", true);
        ENABLE_FRIENDLY_PET_FIRE = builder
                .comment("Enable Friendly Fire on Pets \n" +
                        "If you dislike (or outright hate) your pets and would like to kill them, enable this feature. [true / false]")
                .define("enableFriendlyPetFire", false);
        ENABLE_ENCHANTMENT_TRADES = builder
                .comment("Enable Librarian Villagers trading books enchanted with this mod's enchantments. \n" +
                        "Disable this feature if you want to prevent this. [true / false]")
                .define("enableEnchantmentTrades", true);
        ENABLE_ENCHANTMENT_LOOT = builder
                .comment("Enable enchantments from this mod appearing in any type of generated loot. \n" +
                        "Disable this feature if you want to prevent this. [true / false]")
                .define("enableEnchantmentLoot", true);
        ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR = builder
                .comment("Enable applying enchantments from this mod on non-Dungeons gear using the Enchanting Table. \n" +
                        "If you don't want your enchantments to become too cluttered for non-Dungeons gear, or simply don't like it, disable this feature. \n" +
                        "You can still use the anvil to put the enchantments onto them and have them work correctly. [true / false]")
                .define("enableEnchantsOnNonDungeonsGear", true);
        ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS = builder
                .comment("Enable applying enchantments together to create combinations would  be considered too overpowered. \n" +
                        "If you don't want overpowered enchantment combinations, like Sharpness and Committed on a sword, disable this feature. [true / false]")
                .define("enableOverpoweredEnchantmentDualWields", false);
        ENCHANTMENT_BLACKLIST = builder
                .comment("Add enchantments that should be prevented from being applied to any gear. \n"
                        + "To do so, enter their registry names.")
                .defineList("enchantmentBlacklist", Lists.newArrayList(
                                "dungeons_gear:lucky_explorer"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        TREASURE_ONLY_ENCHANTMENTS = builder
                .comment("Add enchantments that should be designated as treasure-only. \n"
                        + "To do so, enter their registry names.")
                .defineList("treasureOnlyEnchantments", Lists.newArrayList(
                                "dungeons_gear:masters_call"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        ENABLE_MELEE_WEAPON_LOOT = builder
                .comment("Enable melee weapons appearing in chest loot and trades. \n" +
                        "If you Ewant to disable obtaining this mod's melee weapons, disable this feature. [true / false]")
                .define("enableMeleeWeaponLoot", true);
        ENABLE_RANGED_WEAPON_LOOT = builder
                .comment("Enable ranged weapons appearing in chest loot and trades. \n" +
                        "If you want to disable obtaining this mod's ranged weapons, disable this feature. [true / false]")
                .define("enableRangedWeaponLoot", true);
        ENABLE_ARMOR_LOOT = builder
                .comment("Enable armors appearing in chest loot and trades. \n" +
                        "If you want to disable obtaining this mod's armors, disable this feature. [true / false]")
                .define("enableArmorLoot", true);
        ENABLE_ARTIFACT_LOOT = builder
                .comment("Enable artifacts appearing in chest loot and trades. \n" +
                        "If you want to disable obtaining this mod's artifacts, disable this feature. [true / false]")
                .define("enableArtifactLoot", true);

        ENABLE_MELEE_WEAPON_TAB = builder
                .comment("Enable melee weapons appearing in their own tab in the creative menu. \n" +
                        "Disabling this feature puts them in the COMBAT tab. [true / false]")
                .define("enableMeleeWeaponTab", true);
        ENABLE_RANGED_WEAPON_TAB = builder
                .comment("Enable ranged weapons appearing in their own tab in the creative menu. \n" +
                        "Disabling this feature puts them in the COMBAT tab. [true / false]")
                .define("enableRangedWeaponTab", true);
        ENABLE_ARMOR_TAB = builder
                .comment("Enable armors appearing in their own tab in the creative menu. \n" +
                        "Disabling this feature puts them in the COMBAT tab. [true / false]")
                .define("enableArmorTab", true);
        ENABLE_ARTIFACT_TAB = builder
                .comment("Enable artifacts appearing in their own tab in the creative menu. \n" +
                        "Disabling this feature puts them in the COMBAT tab. [true / false]")
                .define("enableArtifactTab", true);
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

        builder.comment("Compatibility Configuration").push("compatibility_configuration");
        ENABLE_ELENAI_DODGE_COMPAT = builder
                .comment("Enable Elenai Dodge 2 compatibility. Effects that trigger on jump will now trigger on dodge. \n" +
                        "Does nothing if Elenai Dodge is not installed. [true / false]")
                .define("enableElenaiDodgeCompat", true);
        ENABLE_WAR_DANCE_COMPAT = builder
                .comment("Enable Project: War Dance compatibility. \n" +
                        "Dual wield weapon offhand functions are suppressed in favor of using War Dance's offhand attacks. \n" +
                        "Block reach attributes are added to spears so they benefit from skills. \n" +
                        "Does nothing if Project: War Dance is not installed. [true / false]")
                .define("enableProjectWarDanceCompat", true);
        builder.pop();


        builder.comment("Item Configuration").push("item_configuration");
        METAL_MELEE_WEAPON_DURABILITY = builder
                .comment("Set the durability for melee weapons. [0-1024, default: 250")
                .defineInRange("meleeWeaponDurability", 250, 0, 1024);
        builder.pop();

        builder.comment("Combat Configuration").push("combat_configuration");
        ENABLE_AREA_OF_EFFECT_ON_PLAYERS = builder
                .comment("Enable area of effects also being applied to players. \n" +
                        "If you do not want area of effects being applied to other players, disable this feature. [true / false]")
                .define("enableAreaOfEffectOnPlayers", false);
        ENEMY_BLACKLIST = builder
                .comment("Add entities that will never be targeted by aggressive Dungeons Gear effects. \n"
                        + "To do so, enter their registry names.")
                .defineList("effectTargetBlacklist", Lists.newArrayList(
                                "guardvillagers:guard",
                                "minecraft:bat",
                                "minecraft:bee",
                                "minecraft:chicken",
                                "minecraft:cod",
                                "minecraft:cow",
                                "minecraft:dolphin",
                                "minecraft:donkey",
                                "minecraft:fox",
                                "minecraft:horse",
                                "minecraft:iron_golem",
                                "minecraft:mooshroom",
                                "minecraft:ocelot",
                                "minecraft:panda",
                                "minecraft:parrot",
                                "minecraft:pig",
                                "minecraft:polar_bear",
                                "minecraft:pufferfish",
                                "minecraft:rabbit",
                                "minecraft:salmon",
                                "minecraft:sheep",
                                "minecraft:squid",
                                "minecraft:strider",
                                "minecraft:trader_llama",
                                "minecraft:tropical_fish",
                                "minecraft:turtle",
                                "minecraft:villager",
                                "minecraft:wandering_trader",
                                "minecraft:wolf",
                                "minecraft:cat"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        builder.pop();

        builder.comment("Rarity Loot Table Configuration").push("rarity_loot_table_configuration");
        builder.comment("This section determines the quality of gear found in loot tables.\n"
                + "The whitelist allows gear in, the blacklist bans it from getting gear. Blacklist trumps whitelist.\n"
                + "Enter the path of the specific loot table, or the path of the folder containing the loot tables.\n"
                + "You can write an incomplete path, and the mod will add to loot tables containing that incomplete path. \n"
                + "Make sure to always include a complete namespace (like minecraft) and at least a partial path.");
        COMMON_LOOT_TABLES = builder
                .defineList("commonLootTables", lootTables.getLootTable(LootTableRarity.COMMON),
                        (itemRaw) -> itemRaw instanceof String);
        COMMON_LOOT_TABLES_BLACKLIST = builder
                .defineList("commonLootTablesBlacklist", Lists.newArrayList(
                                "dungeonsplus:_map"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        FANCY_LOOT_TABLES = builder
                .defineList("fancyLootTables", lootTables.getLootTable(LootTableRarity.FANCY),
                        (itemRaw) -> itemRaw instanceof String);
        FANCY_LOOT_TABLES_BLACKLIST = builder
                .defineList("fancyLootTablesBlacklist", Lists.newArrayList(
                                "minecraft:chests/_dispenser",
                                "repurposed_structures:_dispenser",
                                "repurposed_structures:_dispenser",
                                "repurposed_structures:_dispenser",
                                "repurposed_structures:_dispenser",
                                "repurposed_structures:_dispenser",
                                "dungeonsplus:_map"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        OBSIDIAN_LOOT_TABLES = builder
                .defineList("obsidianLootTables", lootTables.getLootTable(LootTableRarity.OBSIDIAN),
                        (itemRaw) -> itemRaw instanceof String);
        OBSIDIAN_LOOT_TABLES_BLACKLIST = builder
                .defineList("obsidianLootTableBlacklist", Lists.newArrayList(
                                "dungeonsplus:_map"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        builder.pop();

        builder.comment("Type Loot Table Configuration").push("type_loot_table_configuration");
        builder.comment("This section determines the type of gear found in loot tables, generally based on biomes and dimensions.\n"
                + "The whitelist allows gear in, the blacklist bans it from getting gear. Blacklist trumps whitelist.\n"
                + "Enter the path of the specific loot table, or the path of the folder containing the loot tables.\n"
                + "You can write an incomplete path, and the mod will add to loot tables containing that incomplete path. \n"
                + "Make sure to always include a complete namespace (like minecraft) and at least a partial path.");
        BASIC_LOOT_TABLES = builder
                .defineList("basicLootTables", lootTables.getLootTable(LootTableType.BASIC),
                        (itemRaw) -> itemRaw instanceof String);
        BASIC_LOOT_TABLES_BLACKLIST = builder
                .defineList("basicLootTablesBlacklist", Lists.newArrayList(
                        ),
                        (itemRaw) -> itemRaw instanceof String);

        DESERT_LOOT_TABLES = builder
                .defineList("desertLootTables", lootTables.getLootTable(LootTableType.DESERT),
                        (itemRaw) -> itemRaw instanceof String);
        DESERT_LOOT_TABLES_BLACKLIST = builder
                .defineList("desertLootTablesBlacklist", Lists.newArrayList(
                        ),
                        (itemRaw) -> itemRaw instanceof String);

        OCEAN_LOOT_TABLES = builder
                .defineList("oceanLootTables", lootTables.getLootTable(LootTableType.OCEAN),
                        (itemRaw) -> itemRaw instanceof String);
        OCEAN_LOOT_TABLES_BLACKLIST = builder
                .defineList("oceanLootTablesBlacklist", Lists.newArrayList(
                                "minecraft:chests/buried_treasure"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        COLD_LOOT_TABLES = builder
                .defineList("coldLootTables", lootTables.getLootTable(LootTableType.COLD),
                        (itemRaw) -> itemRaw instanceof String);
        COLD_LOOT_TABLES_BLACKLIST = builder
                .defineList("coldLootTablesBlacklist", Lists.newArrayList(
                ), (itemRaw) -> itemRaw instanceof String);

        JUNGLE_LOOT_TABLES = builder
                .defineList("jungleLootTables", lootTables.getLootTable(LootTableType.JUNGLE),
                        (itemRaw) -> itemRaw instanceof String);
        JUNGLE_LOOT_TABLES_BLACKLIST = builder
                .defineList("jungleLootTablesBlacklist", Lists.newArrayList(
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        NETHER_LOOT_TABLES = builder
                .defineList("netherLootTables", lootTables.getLootTable(LootTableType.NETHER),
                        (itemRaw) -> itemRaw instanceof String);
        NETHER_LOOT_TABLES_BLACKLIST = builder
                .defineList("netherLootTablesBlacklist", Lists.newArrayList(
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        END_LOOT_TABLES = builder
                .defineList("endLootTables", lootTables.getLootTable(LootTableType.END),
                        (itemRaw) -> itemRaw instanceof String);
        END_LOOT_TABLES_BLACKLIST = builder
                .defineList("endLootTablesBlacklist", Lists.newArrayList(
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        builder.pop();

        builder.comment("Effect Specific Configuration").push("effect_specific_configuration");
        PARTY_STARTER_DAMAGE = builder
                .comment("The damage caused by Party Starter. [0-10000, default: 4]")
                .defineInRange("partyStarterDamage", 4, 0, 10000);
        builder.pop();

        builder.comment("Enchantment Specific Configuration").push("enchantment_specific_configuration");
        BUSY_BEE_BASE_CHANCE = builder
                .comment("The decimal base chance for a busy bee to spawn [0.0-1.0, default: 0.1]")
                .defineInRange("busyBeeBaseChance", 0.1, 0, 1.0);
        BUSY_BEE_CHANCE_PER_LEVEL = builder
                .comment("The decimal chance per level added for a busy bee to spawn [0.0-1.0, default: 0.1]")
                .defineInRange("busyBeeChancePerLevel", 0.1, 0, 1.0);
        TUMBLE_BEE_CHANCE_PER_LEVEL = builder
                .comment("The decimal chance per level added for a tumble bee to spawn [0.0-1.0, default: 0.1]")
                .defineInRange("tumbleBeeChancePerLevel", 0.1, 0, 1.0);
        RAMPAGING_CHANCE = builder
                .comment("The decimal chance for rampaging to trigger [0.0-1.0, default: 0.1]")
                .defineInRange("rampagingChance", 0.1, 0, 1.0);
        RAMPAGING_DURATION = builder
                .comment("The duration in ticks (20ticks = 1 second) per level added for rampaging. [0-10000, default: 100]")
                .defineInRange("rampagingDuration", 100, 0, 10000);
        RUSHDOWN_DURATION = builder
                .comment("The duration in ticks (20ticks = 1 second) per level added for rushdown. [0-10000, default: 20]")
                .defineInRange("rushdownDuration", 20, 0, 10000);
        WEAKENING_DURATION = builder
                .comment("The duration in ticks (20ticks = 1 second) for weakening. [0-10000, default: 100]")
                .defineInRange("weakeningDuration", 100, 0, 10000);
        WEAKENING_DISTANCE = builder
                .comment("The application distance in blocks for weakening. [0-10000, default: 5]")
                .defineInRange("weakeningDistance", 5, 0, 10000);
        LEECHING_BASE_MULTIPLIER = builder
                .comment("The decimal base multiplier on the victim's max health for leeching [0.0-5.0, default: 0.2]")
                .defineInRange("leechingBaseMultiplier", 0.2, 0, 5.0);
        LEECHING_MULTIPLIER_PER_LEVEL = builder
                .comment("The multiplier increase per level for leeching [0.0-5.0, default: 0.2]")
                .defineInRange("leechingMultiplierPerLevel", 0.2, 0, 5.0);
        DYNAMO_MAX_STACKS = builder
                .comment("The max stacks for dynamo. [0-10000, default: 20]")
                .defineInRange("dynamoMaxStacks", 20, 0, 10000);
        DYNAMO_DAMAGE_MULTIPLIER_PER_STACK = builder
                .comment("Multiplier per level applied to the damage. " +
                        "damage * (1 + (configValue*Stacks)) [0.0-5.0, default: 0.1]")
                .defineInRange("dynamoDamageMultiplierPerStack", 0.1, 0, 5.0);
        COMMITTED_BASE_MULTIPLIER = builder
                .comment("The decimal base multiplier on the damage for max damage committed.  [0.0-5.0, default: 1.25]")
                .defineInRange("committedBaseMultiplier", 1.25, 0, 5.0);
        COMMITTED_MULTIPLIER_PER_LEVEL = builder
                .comment("The multiplier increase per level for max damage committed [0.0-5.0, default: 0.25]")
                .defineInRange("committedMultiplierPerLevel", 0.25, 0, 5.0);
        FREEZING_DURATION = builder
                .comment("The duration in ticks (20ticks = 1 second) for freezing. [0-10000, default: 60]")
                .defineInRange("freezingDuration", 60, 0, 10000);
        SOUL_SIPHON_CHANCE = builder
                .comment("The decimal chance for Soul Siphon to trigger [0.0-1.0, default: 0.05]")
                .defineInRange("soulSiphonChance", 0.05, 0, 1.0);
        SOUL_SIPHON_SOULS_PER_LEVEL = builder
                .comment("The amount of souls per trigger of Soul Siphon. " +
                        "Each souls will give a value based on Soul Gathering. [0-100, default: 2]")
                .defineInRange("soulSiphonSoulsPerLevel", 2, 0, 100);
        CHAINS_CHANCE = builder
                .comment("The decimal chance for Chains to trigger [0.0-1.0, default: 0.3]")
                .defineInRange("chainsChance", 0.3, 0, 1.0);
        RADIANCE_CHANCE = builder
                .comment("The decimal chance for Radiance to trigger [0.0-1.0, default: 0.2]")
                .defineInRange("radianceChance", 0.2, 0, 1.0);
        THUNDERING_CHANCE = builder
                .comment("The decimal chance for Thundering to trigger [0.0-1.0, default: 0.3]")
                .defineInRange("thunderingChance", 0.3, 0, 1.0);
        THUNDERING_BASE_DAMAGE = builder
                .comment("The base damage for Thundering [0-10000, default: 5]")
                .defineInRange("thunderingBaseDamage", 5, 0, 10000);
        ALTRUISTIC_DAMAGE_TO_HEALING_PER_LEVEL = builder
                .comment("Multiplier per level damage to healing conversion. [0.0-5.0, default: 0.25]")
                .defineInRange("altruisticDamageToHealingPerLevel", 0.25, 0, 5.0);
        BEAST_BOSS_BASE_MULTIPLIER = builder
                .comment("The decimal base multiplier on the minions's damage for beast boss [0.0-5.0, default: 0.1]")
                .defineInRange("beastBossBaseMultiplier", 0.1, 0, 5.0);
        BEAST_BOSS_MULTIPLIER_PER_LEVEL = builder
                .comment("The multiplier increase per level for beast boss [0.0-5.0, default: 0.1]")
                .defineInRange("beastBossMultiplierPerLevel", 0.1, 0, 5.0);
        BEAST_BURST_DAMAGE_PER_LEVEL = builder
                .comment("The amount of damage per trigger of Beast burst. " +
                        "Each trigger causes an explosion around each minion [0-100, default: 5]")
                .defineInRange("beastBurstDamagePerLevel", 5, 0, 100);
        BEAST_SURGE_DURATION = builder
                .comment("The duration in ticks of the speed boost applied by Beast Surge. [0-10000, default: 200]")
                .defineInRange("beastSurgeDuration", 200, 0, 10000);
        COWARDICE_BASE_MULTIPLIER = builder
                .comment("The decimal base multiplier on the damage for cowardice [0.0-5.0, default: 0.1]")
                .defineInRange("cowardiceBaseMultiplier", 0.1, 0, 5.0);
        COWARDICE_MULTIPLIER_PER_LEVEL = builder
                .comment("The multiplier increase per level for cowardice [0.0-5.0, default: 0.1]")
                .defineInRange("cowardiceMultiplierPerLevel", 0.1, 0, 5.0);
        DEFLECT_CHANCE_PER_LEVEL = builder
                .comment("The chance per level for deflect to trigger [0.0-5.0, default: 0.2]")
                .defineInRange("deflectChancePerLevel", 0.2, 0, 5.0);
        FOCUS_MULTIPLIER_PER_LEVEL = builder
                .comment("The multiplier increase per level for Focus Enchantments [0.0-5.0, default: 0.25]")
                .defineInRange("focusMultiplierPerLevel", 0.25, 0, 5.0);
        FRENZIED_MULTIPLIER_PER_LEVEL = builder
                .comment("The multiplier increase per level for Frenzied [0.0-5.0, default: 0.1]")
                .defineInRange("frenziedMultiplierPerLevel", 0.1, 0, 5.0);
        GRAVITY_PULSE_BASE_STRENGTH = builder
                .comment("The decimal base pull strength for Gravity Pulse [0.0-5.0, default: 0.1]")
                .defineInRange("gravityPulseBaseStrength", 0.1, 0, 5.0);
        GRAVITY_PULSE_STRENGTH_PER_LEVEL = builder
                .comment("The strength increase per level for Gravity Pulse [0.0-5.0, default: 0.1]")
                .defineInRange("gravityPulseStrengthPerLevel", 0.1, 0, 5.0);
        POTION_BARRIER_BASE_DURATION = builder
                .comment("The decimal base duration for Potion Barrier [0-10000, default: 60]")
                .defineInRange("potionBarrierBaseDuration", 60, 0, 10000);
        POTION_BARRIER_DURATION_PER_LEVEL = builder
                .comment("The duration increase per level for Potion Barrier [0-10000, default: 20]")
                .defineInRange("potionBarrierDurationPerLevel", 20, 0, 10000);
        RECKLESS_MAX_HEALTH_MULTIPLIER = builder
                .comment("The multiplier to max health for reckless. Balanced as a negative number. [-5.0-5.0, default: -0.6]")
                .defineInRange("recklessMaxHealthMultiplier", -0.6, -5.0, 5.0);
        RECKLESS_ATTACK_DAMAGE_BASE_MULTIPLIER = builder
                .comment("The decimal base multiplier on the damage for reckless [-5.0-5.0, default: 0.2]")
                .defineInRange("recklessAttackDamageBaseMultiplier", 0.2, -5.0, 5.0);
        RECKLESS_ATTACK_DAMAGE_MULTIPLIER_PER_LEVEL = builder
                .comment("The multiplier increase per level for reckless [-5.0-5.0, default: 0.2]")
                .defineInRange("recklessAttackDamageMultiplierPerLevel", 0.2, -5.0, 5.0);
        EXPLODING_MULTIPLIER_PER_LEVEL = builder
                .comment("The multiplier increase per level for exploding [-5.0-5.0, default: 0.2]")
                .defineInRange("explodingMultiplierPerLevel", 0.2, -5.0, 5.0);
        PROSPECTOR_CHANCE_PER_LEVEL = builder
                .comment("The chance per level for prospector to trigger [-5.0-5.0, default: 0.25]")
                .defineInRange("prospectorChancePerLevel", 0.25, -5.0, 5.0);
        POISON_CLOUD_CHANCE = builder
                .comment("The chance for Poison Cloud to trigger [-5.0-5.0, default: 0.3]")
                .defineInRange("prospectorChancePerLevel", 0.3, -5.0, 5.0);
        DODGE_CHANCE_PER_LEVEL = builder
                .comment("The chance per level for dodge to trigger [-5.0-5.0, default: 0.25]")
                .defineInRange("dodgeChancePerLevel", 0.01, -5.0, 5.0);
        VOID_DODGE_CHANCE_PER_LEVEL = builder
                .comment("The chance per level for void dodge to trigger [-5.0-5.0, default: 0.25]")
                .defineInRange("voidDodgeChancePerLevel", 0.05, -5.0, 5.0);
        BEEHIVE_CHANCE_PER_LEVEL = builder
                .comment("The chance per level for beehive to trigger [-5.0-5.0, default: 0.25]")
                .defineInRange("beehiveChancePerLevel", 0.1, -5.0, 5.0);
        builder.pop();

        builder.comment("Artifact Specific Configuration").push("artifact_specific_configuration");
        LOVE_MEDALLION_BLACKLIST = builder
                .comment("Entities that can not be converted by Love Medallion.")
                .defineList("loveMedallionBlacklist", Lists.newArrayList(
                                "minecraft:ender_dragon",
                                "minecraft:elder_guardian",
                                "minecraft:wither"
                                ),
                        (itemRaw) -> itemRaw instanceof String);
        builder.pop();
    }
}