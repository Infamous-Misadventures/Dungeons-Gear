package com.infamous.dungeons_gear.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class ItemTagWrappers {
    public static final TagKey<Item> FOOD = ItemTags.create(new ResourceLocation(MOD_ID, "food"));
    public static final TagKey<Item> FOOD_PROCESSED = ItemTags.create(new ResourceLocation(MOD_ID, "food/processed"));
    public static final TagKey<Item> FOOD_RAW = ItemTags.create(new ResourceLocation(MOD_ID, "food/raw"));
    public static final TagKey<Item> WEAPONS = ItemTags.create(new ResourceLocation(MOD_ID, "weapons"));
    public static final TagKey<Item> ARMOR = ItemTags.create(new ResourceLocation(MOD_ID, "armor"));

    public static final TagKey<Item> CURIOS_ARTIFACTS = ItemTags.create(new ResourceLocation("curios", "artifact"));
}
