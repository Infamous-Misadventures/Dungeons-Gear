package com.infamous.dungeons_gear.loot;

import net.minecraft.loot.LootConditionType;
import net.minecraft.util.registry.Registry;

public class LootConditionRegistry {

    public static final LootConditionType DUNGEONS_GEAR_EXPERIMENTAL_CONDITION = new LootConditionType(new ExperimentalCondition.Serializer());


    public static void init() {
        Registry.register(Registry.LOOT_CONDITION_TYPE, "dungeons_gear:experimental_condition", DUNGEONS_GEAR_EXPERIMENTAL_CONDITION);
    }
}
