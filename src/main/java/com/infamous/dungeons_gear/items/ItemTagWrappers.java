package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class ItemTagWrappers {
    public static final TagKey<Item> METAL_MELEE_WEAPON_REPAIR_ITEMS = ItemTags.create(new ResourceLocation(DungeonsGear.MODID, "metal_weapon_repair_items"));
    public static final TagKey<Item> METAL_ARMOR_REPAIR_ITEMS = ItemTags.create(new ResourceLocation(DungeonsGear.MODID, "metal_armor_repair_items"));
    public static final TagKey<Item> NON_METAL_ARMOR_REPAIR_ITEMS = ItemTags.create(new ResourceLocation(DungeonsGear.MODID, "non_metal_armor_repair_items"));
    public static final TagKey<Item> ARTIFACT_REPAIR_ITEMS = ItemTags.create(new ResourceLocation(DungeonsGear.MODID, "artifact_repair_items"));


    public static final TagKey<Item> FOOD = ItemTags.create(new ResourceLocation(MOD_ID, "food"));
    public static final TagKey<Item> FOOD_PROCESSED = ItemTags.create(new ResourceLocation(MOD_ID, "food/processed"));
    public static final TagKey<Item> FOOD_RAW = ItemTags.create(new ResourceLocation(MOD_ID, "food/raw"));

    public static final TagKey<Item> CURIOS_ARTIFACTS = ItemTags.create(new ResourceLocation("curios", "artifact"));
}
