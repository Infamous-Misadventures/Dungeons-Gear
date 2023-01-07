package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.loot.ExperimentalCondition;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class LootConditionInit {
    public static final DeferredRegister<LootItemConditionType> LOOT_ITEM_CONDITION_TYPES = DeferredRegister.create(Registry.LOOT_ITEM_REGISTRY, MODID);

    public static final RegistryObject<LootItemConditionType> DUNGEONS_GEAR_EXPERIMENTAL_CONDITION = LOOT_ITEM_CONDITION_TYPES.register("experimental_condition", () -> new LootItemConditionType(new ExperimentalCondition.ExperimentalConditionSerializer()));

}
