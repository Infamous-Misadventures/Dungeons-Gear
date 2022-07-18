package com.infamous.dungeons_gear.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import static com.infamous.dungeons_gear.loot.LootConditionRegistry.DUNGEONS_GEAR_EXPERIMENTAL_CONDITION;

public class ExperimentalCondition implements LootItemCondition {

    public static final class ExperimentalConditionSerializer implements Serializer<ExperimentalCondition> {

        @Override
        public void serialize(JsonObject p_230424_1_, ExperimentalCondition p_230424_2_, JsonSerializationContext p_230424_3_) {
            // NO-OP
        }

        @Override
        public ExperimentalCondition deserialize(JsonObject p_230423_1_, JsonDeserializationContext p_230423_2_) {
            return new ExperimentalCondition();
        }
    }

    @Override
    public LootItemConditionType getType() {
        return DUNGEONS_GEAR_EXPERIMENTAL_CONDITION;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return DungeonsGearConfig.ENABLE_EXPERIMENTAL.get();
    }
}
