package com.infamous.dungeons_gear.loot;

import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class LootConditionRegistry {
    public static final LootItemConditionType DUNGEONS_GEAR_EXPERIMENTAL_CONDITION = new LootItemConditionType(new ExperimentalCondition.ExperimentalConditionSerializer());


    public static void init() {
        Registry.register(Registry.LOOT_CONDITION_TYPE, "dungeons_gear:experimental_condition", DUNGEONS_GEAR_EXPERIMENTAL_CONDITION);
    }
}
