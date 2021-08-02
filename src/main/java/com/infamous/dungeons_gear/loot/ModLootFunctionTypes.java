package com.infamous.dungeons_gear.loot;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class ModLootFunctionTypes {
    public static final LootFunctionType ADD_POTION = register("add_potion", new AddPotionLootFunction.Serializer());

    public static void register() {
        // No-op method to ensure that this class is loaded and its static initialisers are run
    }

    private static LootFunctionType register(final String name, final ILootSerializer<? extends ILootFunction> serializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(MODID, name), new LootFunctionType(serializer));
    }
}
