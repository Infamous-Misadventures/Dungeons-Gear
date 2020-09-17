package com.infamous.dungeons_gear.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ArmorList {

    // RANGED ARMORS
    public static Item HUNTERS_ARMOR;
    public static Item ARCHERS_ARMOR_HOOD;
    public static Item ARCHERS_ARMOR;

    public static Item PHANTOM_ARMOR_HELMET;
    public static Item PHANTOM_ARMOR;
    public static Item FROST_BITE_HELMET;
    public static Item FROST_BITE;

    public static Item THIEF_ARMOR_HOOD;
    public static Item THIEF_ARMOR;
    public static Item SPIDER_ARMOR_HOOD;
    public static Item SPIDER_ARMOR;

    // MAGIC ARMORS
    public static Item BATTLE_ROBE;
    public static Item SPLENDID_ROBE;

    public static Item SOUL_ROBE_HOOD;
    public static Item SOUL_ROBE;
    public static Item SOULDANCER_ROBE_HOOD;
    public static Item SOULDANCER_ROBE;

    public static Item EVOCATION_ROBE_HAT;
    public static Item EVOCATION_ROBE;
    public static Item EMBER_ROBE_HAT;
    public static Item EMBER_ROBE;

    // MOB ARMORS
    public static Item GRIM_ARMOR_HELMET;
    public static Item GRIM_ARMOR;
    public static Item WITHER_ARMOR_HELMET;
    public static Item WITHER_ARMOR;

    public static Item WOLF_ARMOR_HOOD;
    public static Item WOLF_ARMOR;
    //public static Item WOLF_ARMOR_BOOTS;
    public static Item FOX_ARMOR_HOOD;
    public static Item FOX_ARMOR;
    //public static Item FOX_ARMOR_BOOTS;

    public static Item OCELOT_ARMOR_HOOD;
    public static Item OCELOT_ARMOR;
    //public static Item OCELOT_ARMOR_BOOTS;
    public static Item SHADOW_WALKER_HOOD;
    public static Item SHADOW_WALKER;
    //public static Item SHADOW_WALKER_BOOTS;

    // LIGHT ARMORS
    public static Item GUARDS_ARMOR_HELMET;
    public static Item GUARDS_ARMOR;
    public static Item CURIOUS_ARMOR_HELMET;
    public static Item CURIOUS_ARMOR;

    public static Item SCALE_MAIL;
    public static Item HIGHLAND_ARMOR_HELMET;
    public static Item HIGHLAND_ARMOR;

    public static Item SPELUNKER_ARMOR_HELMET;
    public static Item SPELUNKER_ARMOR;
    public static Item CAVE_CRAWLER_HELMET;
    public static Item CAVE_CRAWLER;

    // MEDIUM ARMORS
    public static Item CHAMPIONS_ARMOR_HELMET;
    public static Item CHAMPIONS_ARMOR;
    public static Item HEROS_ARMOR_HELMET;
    public static Item HEROS_ARMOR;

    public static Item REINFORCED_MAIL_HELMET;
    public static Item REINFORCED_MAIL;
    public static Item STALWART_ARMOR_HELMET;
    public static Item STALWART_ARMOR;

    public static Item SNOW_ARMOR_HELMET;
    public static Item SNOW_ARMOR;
    public static Item FROST_ARMOR_HELMET;
    public static Item FROST_ARMOR;

    // HEAVY ARMORS
    public static Item DARK_ARMOR_HELMET;
    public static Item DARK_ARMOR;
    public static Item ROYAL_GUARD_ARMOR_HELMET;
    public static Item ROYAL_GUARD_ARMOR;
    public static Item TITANS_SHROUD_HELMET;
    public static Item TITANS_SHROUD;

    public static Item MERCENARY_ARMOR_HELMET;
    public static Item MERCENARY_ARMOR;
    public static Item RENEGADE_ARMOR_HELMET;
    public static Item RENEGADE_ARMOR;

    public static Item PLATE_ARMOR_HELMET;
    public static Item PLATE_ARMOR;
    public static Item FULL_METAL_ARMOR_HELMET;
    public static Item FULL_METAL_ARMOR;


    public static Map<Item, ResourceLocation> commonLeatherArmorMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> commonMetalArmorMap = new HashMap<Item, ResourceLocation>();

    public static Map<Item, ResourceLocation> uniqueLeatherArmorMap = new HashMap<Item, ResourceLocation>();
    public static Map<Item, ResourceLocation> uniqueMetalArmorMap = new HashMap<Item, ResourceLocation>();
}
