package com.infamous.dungeons_gear.loot;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class ModLootFunctionTypes {
    public static final LootItemFunctionType ADD_POTION = register("add_potion", new AddPotionLootFunction.Serializer());

    public static void register() {
        // No-op method to ensure that this class is loaded and its static initialisers are run
    }

    private static LootItemFunctionType register(final String name, final Serializer<? extends LootItemFunction> serializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(MODID, name), new LootItemFunctionType(serializer));
    }
}
