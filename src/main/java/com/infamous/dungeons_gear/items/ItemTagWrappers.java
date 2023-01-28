package com.infamous.dungeons_gear.items;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class ItemTagWrappers {


    public static final Tags.IOptionalNamedTag<Item> FOOD = ItemTags.createOptional(new ResourceLocation(MOD_ID, "food"));
    public static final Tags.IOptionalNamedTag<Item> FOOD_PROCESSED = ItemTags.createOptional(new ResourceLocation(MOD_ID, "food/processed"));
    public static final Tags.IOptionalNamedTag<Item> FOOD_RAW = ItemTags.createOptional(new ResourceLocation(MOD_ID, "food/raw"));
    public static final Tags.IOptionalNamedTag<Item> WEAPONS = ItemTags.createOptional(new ResourceLocation(MOD_ID, "weapons"));
    public static final Tags.IOptionalNamedTag<Item> ARMOR = ItemTags.createOptional(new ResourceLocation(MOD_ID, "armor"));

    public static final Tags.IOptionalNamedTag<Item> CURIOS_ARTIFACTS = ItemTags.createOptional(new ResourceLocation("curios", "artifact"));
}
