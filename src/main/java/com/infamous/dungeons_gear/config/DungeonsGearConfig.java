package com.infamous.dungeons_gear.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DungeonsGearConfig {

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_DUNGEONS_GEAR_LOOT;
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_SALVAGING;
        public final ForgeConfigSpec.ConfigValue<Boolean> ENABLE_VILLAGER_TRADES;

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

        public Common(ForgeConfigSpec.Builder builder){

            builder.comment("General Mod Configuration").push("general_mod_configuration");
            ENABLE_DUNGEONS_GEAR_LOOT = builder
                    .comment("Enable the mass addition of Dungeons Gear items to various vanilla loot chests by the mod itself. \n" +
                            "If you prefer to write your own loot pools via datapack, disable this feature. [true / false]")
                    .define("enableDungeonsGearLoot", true);
            ENABLE_SALVAGING = builder
                    .comment("Enable the salvaging of Dungeons Gear items by shift-right clicking villagers with the item in your hand [true / false]")
                    .define("enableSalvaging", true);
            ENABLE_VILLAGER_TRADES = builder
                    .comment("Enable Weaponsmith, Fletcher, Armorer and Leatherworker Villagers trading Dungeons Gear items. \n" +
                    "If you have other mods messing with the trades of those professions or simply don't like it, disable this feature. [true / false]")
                    .define("enableVillagerTrades", true);
            builder.pop();

            builder.comment("Common Loot Table Configuration").push("common_loot_table_configuration");
           COMMON_WEAPON_COMMON_LOOT = builder
                   .comment("The decimal chance for a common weapon to appear in common loot chests [0.0-1.0, default: 1.0]")
                   .defineInRange("commonWeaponCommonLoot", 1.0, 0.0, 1.0);
            UNIQUE_WEAPON_COMMON_LOOT = builder
                    .comment("The decimal chance for a unique weapon to appear in common loot chests [0.0-1.0, default: 0.15]")
                    .defineInRange("uniqueWeaponCommonLoot", 0.15, 0.0, 1.0);
            COMMON_ARMOR_COMMON_LOOT = builder
                    .comment("The decimal chance for a common armor to appear in common loot chests [0.0-1.0, default: 1.0]")
                    .defineInRange("commonArmorCommonLoot", 1.0, 0.0, 1.0);
            UNIQUE_ARMOR_COMMON_LOOT = builder
                    .comment("The decimal chance for a unique armor to appear in common loot chests [0.0-1.0, default: 0.15]")
                    .defineInRange("uniqueArmorCommonLoot", 0.15, 0.0, 1.0);
            ARTIFACT_COMMON_LOOT = builder
                    .comment("The decimal chance for an artifact to appear in common loot chests [0.0-1.0, default: 0.25]")
                    .defineInRange("artifactCommonLoot", 0.25, 0.0, 1.0);
            builder.pop();

            builder.comment("Uncommon Loot Table Configuration").push("uncommon_loot_table_configuration");
            COMMON_WEAPON_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for a common weapon to appear in uncommon loot chests [0.0-1.0, default: 1.0]")
                    .defineInRange("commonWeaponUncommonLoot", 1.0, 0.0, 1.0);
            UNIQUE_WEAPON_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for a unique weapon to appear in uncommon loot chests [0.0-1.0, default: 0.3]")
                    .defineInRange("uniqueWeaponUncommonLoot", 0.3, 0.0, 1.0);
            COMMON_ARMOR_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for a common armor to appear in uncommon loot chests [0.0-1.0, default: 1.0]")
                    .defineInRange("commonArmorUncommonLoot", 1.0, 0.0, 1.0);
            UNIQUE_ARMOR_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for a unique armor to appear in uncommon loot chests [0.0-1.0, default: 0.3]")
                    .defineInRange("uniqueArmorUncommonLoot", 0.3, 0.0, 1.0);
            ARTIFACT_UNCOMMON_LOOT = builder
                    .comment("The decimal chance for an artifact to appear in uncommon loot chests [0.0-1.0, default: 0.5]")
                    .defineInRange("artifactUncommonLoot", 0.5, 0.0, 1.0);
            builder.pop();

            builder.comment("Rare Loot Table Configuration").push("rare_loot_table_configuration");
            COMMON_WEAPON_RARE_LOOT = builder
                    .comment("The decimal chance for a common weapon to appear in rare loot chests [0.0-1.0, default: 1.0]")
                    .defineInRange("commonWeaponRareLoot", 1.0, 0.0, 1.0);
            UNIQUE_WEAPON_RARE_LOOT = builder
                    .comment("The decimal chance for a unique weapon to appear in rare loot chests [0.0-1.0, default: 0.45]")
                    .defineInRange("uniqueWeaponRareLoot", 0.45, 0.0, 1.0);
            COMMON_ARMOR_RARE_LOOT = builder
                    .comment("The decimal chance for a common armor to appear in rare loot chests [0.0-1.0, default: 1.0]")
                    .defineInRange("commonArmorRareLoot", 1.0, 0.0, 1.0);
            UNIQUE_ARMOR_RARE_LOOT = builder
                    .comment("The decimal chance for a unique armor to appear in rare loot chests [0.0-1.0, default: 0.45]")
                    .defineInRange("uniqueArmorRareLoot", 0.45, 0.0, 1.0);
            ARTIFACT_RARE_LOOT = builder
                    .comment("The decimal chance for an artifact to appear in rare loot chests [0.0-1.0, default: 0.75]")
                    .defineInRange("artifactRareLoot", 0.75, 0.0, 1.0);
            builder.pop();

            builder.comment("Super Rare Loot Table Configuration").push("super_rare_loot_table_configuration");
            COMMON_WEAPON_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for a common weapon to appear in super rare loot chests [0.0-1.0, default: 1.0]")
                    .defineInRange("commonWeaponSuperRareLoot", 1.0, 0, 1.0);
            UNIQUE_WEAPON_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for a unique weapon to appear in super rare loot chests [0.0-1.0, default: 0.6]")
                    .defineInRange("uniqueWeaponSuperRareLoot", 0.6, 0, 1.0);
            COMMON_ARMOR_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for a common armor to appear in super rare loot chests [0.0-1.0, default: 1.0]")
                    .defineInRange("commonArmorSuperRareLoot", 1.0, 0, 1.0);
            UNIQUE_ARMOR_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for a unique armor to appear in super rare loot chests [0.0-1.0, default: 0.6]")
                    .defineInRange("uniqueArmorSuperRareLoot", 0.6, 0, 1.0);
            ARTIFACT_SUPER_RARE_LOOT = builder
                    .comment("The decimal chance for an artifact to appear in super rare loot chests [0.0-1.0, default: 1.0]")
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