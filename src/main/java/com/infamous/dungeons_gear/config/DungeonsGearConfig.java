package com.infamous.dungeons_gear.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.google.common.collect.Lists;
import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.util.List;

public class DungeonsGearConfig {
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec.ConfigValue<Integer> VEST_ARMOR_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> ROBE_ARMOR_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> PELT_ARMOR_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> BONE_ARMOR_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> LIGHT_PLATE_ARMOR_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> MEDIUM_PLATE_ARMOR_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> HEAVY_PLATE_ARMOR_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> METAL_MELEE_WEAPON_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> BOW_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> CROSSBOW_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> ARTIFACT_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DUNGEONS_GEAR_LOOT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_SALVAGING;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_VILLAGER_TRADES;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ENCHANTS_ON_NON_DUNGEONS_GEAR;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ELENAI_DODGE_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_WAR_DANCE_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Integer> COMMON_ITEM_VALUE;
    public static ForgeConfigSpec.ConfigValue<Integer> UNIQUE_ITEM_VALUE;
    public static ForgeConfigSpec.ConfigValue<Integer> ARTIFACT_VALUE;
    public static ForgeConfigSpec.ConfigValue<Double> UNIQUE_ITEM_COMMON_LOOT;
    public static ForgeConfigSpec.ConfigValue<Double> ARTIFACT_COMMON_LOOT;
    public static ForgeConfigSpec.ConfigValue<Double> UNIQUE_ITEM_UNCOMMON_LOOT;
    public static ForgeConfigSpec.ConfigValue<Double> ARTIFACT_UNCOMMON_LOOT;
    public static ForgeConfigSpec.ConfigValue<Double> UNIQUE_ITEM_RARE_LOOT;
    public static ForgeConfigSpec.ConfigValue<Double> ARTIFACT_RARE_LOOT;
    public static ForgeConfigSpec.ConfigValue<Double> UNIQUE_ITEM_SUPER_RARE_LOOT;
    public static ForgeConfigSpec.ConfigValue<Double> ARTIFACT_SUPER_RARE_LOOT;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> COMMON_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> COMMON_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> UNCOMMON_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> UNCOMMON_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> SUPER_RARE_LOOT_TABLES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> SUPER_RARE_LOOT_TABLES_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENEMY_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_AREA_OF_EFFECT_ON_PLAYERS;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ENCHANTMENT_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> TREASURE_ONLY_ENCHANTMENTS;


    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_MELEE_WEAPON_LOOT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_RANGED_WEAPON_LOOT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ARMOR_LOOT;
    public static ForgeConfigSpec.ConfigValue<Boolean> ENABLE_ARTIFACT_LOOT;

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
        ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS = builder
                .comment("Enable applying enchantments together to create combinations would  be considered too overpowered. \n" +
                        "If you don't want overpowered enchantment combinations, like Sharpness and Committed on a sword, disable this feature. [true / false]")
                .define("enableOverpoweredEnchantmentCombos", false);
        ENCHANTMENT_BLACKLIST = builder
                .comment("Add enchantments that should be prevented from being applied to any gear. \n"
                        + "To do so, enter their registry names.")
                .defineList("enchantmentBlacklist", Lists.newArrayList(

                        ),
                        (itemRaw) -> itemRaw instanceof String);
        TREASURE_ONLY_ENCHANTMENTS = builder
                .comment("Add enchantments that should be designated as treasure-only. \n"
                        + "To do so, enter their registry names.")
                .defineList("treasureOnlyEnchantments", Lists.newArrayList(

                        ),
                        (itemRaw) -> itemRaw instanceof String);
        ENABLE_MELEE_WEAPON_LOOT = builder
                .comment("Enable melee weapons appearing in chest loot and trades. \n" +
                        "If you want to disable obtaining  this mod's melee weapons, disable this feature. [true / false]")
                .define("enableMeleeWeaponLoot", true);
        ENABLE_RANGED_WEAPON_LOOT = builder
                .comment("Enable ranged weapons appearing in chest loot and trades. \n" +
                        "If you want to disable obtaining  this mod's ranged weapons, disable this feature. [true / false]")
                .define("enableRangedWeaponLoot", true);
        ENABLE_ARMOR_LOOT = builder
                .comment("Enable armors appearing in chest loot and trades. \n" +
                        "If you want to disable obtaining  this mod's armors, disable this feature. [true / false]")
                .define("enableArmorLoot", true);
        ENABLE_ARTIFACT_LOOT = builder
                .comment("Enable artifacts appearing in chest loot and trades. \n" +
                        "If you want to disable obtaining  this mod's artifacts, disable this feature. [true / false]")
                .define("enableArtifactLoot", true);
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
        builder.comment("For armor durability configuration reference, here are the vanilla armor durability multiplier values: \n" +
                "Leather - 5\n" +
                "Gold = 7\n" +
                "Iron = 14\n" +
                "Turtle = 25\n" +
                "Diamond = 33\n" +
                "Netherite = 37"
        ).push("armor_durability_multiplier_reference");
        builder.comment("For tool durability configuration reference, here are the vanilla tool durability values: \n" +
                "Gold - 32\n" +
                "Wood = 59\n" +
                "Stone = 131\n" +
                "Iron = 250\n" +
                "Crossbow - 326\n" +
                "Bow - 384\n" +
                "Diamond = 1561\n" +
                "Netherite = 2031"
        ).push("tool_durability_reference");
        VEST_ARMOR_DURABILITY = builder
                .comment("Set the durability multiplier for armors that can be classified as a vest, such as Hunter's Armor. [0-1024, default: 14")
                .defineInRange("vestArmorDurabilityMultiplier", 14, 0, 1024);
        ROBE_ARMOR_DURABILITY = builder
                .comment("Set the durability multiplier for armors that can be classified as a robe, such as the Evocation Robe. [0-1024, default: 14")
                .defineInRange("robeArmorDurabilityMultiplier", 14, 0, 1024);
        PELT_ARMOR_DURABILITY = builder
                .comment("Set the durability multiplier for armors that are made out of pelts, such as Wolf Armor. [0-1024, default: 14")
                .defineInRange("peltArmorDurabilityMultiplier", 14, 0, 1024);
        BONE_ARMOR_DURABILITY = builder
                .comment("Set the durability multiplier for armors that are made out of bones, such as Grim Armor. [0-1024, default: 14")
                .defineInRange("boneArmorDurabilityMultiplier", 14, 0, 1024);
        LIGHT_PLATE_ARMOR_DURABILITY = builder
                .comment("Set the durability multiplier for armors that can be classified as light plate, such as Guard's Armor. [0-1024, default: 14")
                .defineInRange("lightArmorDurabilityMultiplier", 14, 0, 1024);
        MEDIUM_PLATE_ARMOR_DURABILITY = builder
                .comment("Set the durability multiplier for armors that can be classified as medium plate, such as Reinforced Mail. [0-1024, default: 14")
                .defineInRange("mediumArmorDurabilityMultiplier", 14, 0, 1024);
        HEAVY_PLATE_ARMOR_DURABILITY = builder
                .comment("Set the durability multiplier for armors that can be classified as heavy plate, such as Mercenary Armor. [0-1024, default: 14")
                .defineInRange("heavyArmorDurabilityMultiplier", 14, 0, 1024);
        METAL_MELEE_WEAPON_DURABILITY = builder
                .comment("Set the durability for melee weapons. [0-1024, default: 250")
                .defineInRange("meleeWeaponDurability", 250, 0, 1024);
        BOW_DURABILITY = builder
                .comment("Set the durability for bows. [0-1024, default: 384")
                .defineInRange("bowDurability", 384, 0, 1024);
        CROSSBOW_DURABILITY = builder
                .comment("Set the durability for crossbows. [0-1024, default: 326")
                .defineInRange("crossbowDurability", 326, 0, 1024);
        ARTIFACT_DURABILITY = builder
                .comment("Set the durability for artifacts. [0-1024, default: 64")
                .defineInRange("artifactDurability", 64, 0, 1024);
        builder.pop();

        builder.comment("Combat Configuration").push("combat_configuration");
        ENABLE_AREA_OF_EFFECT_ON_PLAYERS = builder
                .comment("Enable area of effects also being applied to players. \n" +
                        "If you do not want area of effects being applied to other players, disable this feature. [true / false]")
                .define("enableAreaOfEffectOnPlayers", true);
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
                        + "To do so, enter the full path of the specific loot table.")
                .defineList("commonLootTablesBlacklist", Lists.newArrayList(

                        ),
                        (itemRaw) -> itemRaw instanceof String);
        UNIQUE_ITEM_COMMON_LOOT = builder
                .comment("The decimal chance for a unique item to appear in common loot tables instead of a common one [0.0-1.0, default: 0.25]")
                .defineInRange("uniqueItemCommonLoot", 0.25, 0.0, 1.0);
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
                        + "To do so, enter the full path of the specific loot table.")
                .defineList("uncommonLootTablesBlacklist", Lists.newArrayList(
                        "minecraft:chests/jungle_temple_dispenser"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        UNIQUE_ITEM_UNCOMMON_LOOT = builder
                .comment("The decimal chance for a unique item to appear in uncommon loot tables instead of a common one [0.0-1.0, default: 0.5]")
                .defineInRange("uniqueItemUncommonLoot", 0.5, 0.0, 1.0);
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
                        + "To do so, enter the full path of the specific loot table.")
                .defineList("rareLootTableBlacklist", Lists.newArrayList(

                        ),
                        (itemRaw) -> itemRaw instanceof String);
        UNIQUE_ITEM_RARE_LOOT = builder
                .comment("The decimal chance for a unique weapon to appear in rare loot table instead of a common ones [0.0-1.0, default: 0.75]")
                .defineInRange("uniqueItemRareLoot", 0.75, 0.0, 1.0);
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
                        + "To do so, enter the full path of the specific loot table.")
                .defineList("superRareLootTablesBlacklist", Lists.newArrayList(

                        ),
                        (itemRaw) -> itemRaw instanceof String);
        UNIQUE_ITEM_SUPER_RARE_LOOT = builder
                .comment("The decimal chance for a unique item to appear in super rare loot tables instead of a common one [0.0-1.0, default: 1.0]")
                .defineInRange("uniqueItemSuperRareLoot", 1.0, 0, 1.0);
        ARTIFACT_SUPER_RARE_LOOT = builder
                .comment("The decimal chance for an artifact to appear in super rare loot tables [0.0-1.0, default: 1.0]")
                .defineInRange("artifactSuperRareLoot", 1.0, 0, 1.0);
        builder.pop();

        builder.comment("Entity Configuration").push("super_rare_loot_table_configuration");
        ENEMY_BLACKLIST = builder
                .comment("Add entities that will never be targeted by aggressive Dungeons Gear effects. \n"
                        + "To do so, enter their registry names.")
                .defineList("effectTargetBlacklist", Lists.newArrayList(
                        "minecraft:chicken",
                        "minecraft:cow",
                        "minecraft:pig",
                        "minecraft:sheep",
                        "minecraft:bee",
                        "minecraft:wolf",
                        "minecraft:fox",
                        "minecraft:villager",
                        "minecraft:horse",
                        "minecraft:donkey",
                        "minecraft:mooshroom",
                        "minecraft:parrot",
                        "minecraft:ocelot",
                        "minecraft:rabbit",
                        "minecraft:squid",
                        "minecraft:strider",
                        "minecraft:turtle",
                        "minecraft:salmon",
                        "minecraft:cod",
                        "minecraft:pufferfish",
                        "minecraft:tropical_fish",
                        "minecraft:dolphin",
                        "minecraft:panda",
                        "minecraft:polar_bear",
                        "minecraft:bat",
                        "minecraft:trader_llama"
                        ),
                        (itemRaw) -> itemRaw instanceof String);
        builder.pop();
    }
}