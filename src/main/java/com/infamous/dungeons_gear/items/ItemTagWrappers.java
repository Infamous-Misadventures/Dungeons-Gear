package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ItemTagWrappers {
    public static final ITag.INamedTag<Item> METAL_MELEE_WEAPON_REPAIR_ITEMS = ItemTags.makeWrapperTag(DungeonsGear.MODID + ":" + "metal_weapon_repair_items");
    public static final ITag.INamedTag<Item> METAL_ARMOR_REPAIR_ITEMS = ItemTags.makeWrapperTag(DungeonsGear.MODID + ":" + "metal_armor_repair_items");
    public static final ITag.INamedTag<Item> NON_METAL_ARMOR_REPAIR_ITEMS = ItemTags.makeWrapperTag(DungeonsGear.MODID + ":" + "non_metal_armor_repair_items");
    public static final ITag.INamedTag<Item> ARTIFACT_REPAIR_ITEMS = ItemTags.makeWrapperTag(DungeonsGear.MODID + ":" + "artifact_repair_items");
}
