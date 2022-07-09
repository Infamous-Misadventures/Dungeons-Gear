package com.infamous.dungeons_gear.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.TableBonus;

import static com.infamous.dungeons_gear.loot.LootConditionRegistry.DUNGEONS_GEAR_EXPERIMENTAL_CONDITION;

public class ExperimentalCondition implements ILootCondition {

    public static final class Serializer implements ILootSerializer<ExperimentalCondition> {

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
    public LootConditionType getType() {
        return DUNGEONS_GEAR_EXPERIMENTAL_CONDITION;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return DungeonsGearConfig.ENABLE_EXPERIMENTAL.get();
    }
}
