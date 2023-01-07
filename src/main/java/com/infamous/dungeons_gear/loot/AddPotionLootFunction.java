package com.infamous.dungeons_gear.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.List;

import static com.infamous.dungeons_gear.loot.ModLootFunctionTypes.ADD_POTION;
import static net.minecraft.world.item.Items.POTION;
import static net.minecraft.world.item.Items.TIPPED_ARROW;

public class AddPotionLootFunction extends LootItemConditionalFunction {
    private final ImmutableList<Pair<Potion, Float>> weightedPotionOptions;
    private final boolean isRegistryRandom;
    private final float totalWeight;

    public AddPotionLootFunction(LootItemCondition[] conditions, Collection<Pair<Potion, Float>> weightedPotionOptions, float totalWeight) {
        super(conditions);
        if (weightedPotionOptions.size() == 0) {
            isRegistryRandom = true;
            this.weightedPotionOptions = ForgeRegistries.POTIONS.getEntries().stream().map(e -> Pair.of(e.getValue(), 1.0f)).collect(ImmutableList.toImmutableList());
        } else {
            isRegistryRandom = false;
            this.weightedPotionOptions = ImmutableList.copyOf(weightedPotionOptions);
        }
        this.totalWeight = totalWeight;
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        if (canAddPotionToItemStack(stack)) {
            Potion potion = weightedPotionOptions.get(0).getFirst();
            if (weightedPotionOptions.size() != 1) {
                float rnd = context.getRandom().nextFloat() * totalWeight;
                for (int i = 0; i < weightedPotionOptions.size(); i++) {
                    Pair<Potion, Float> pair = weightedPotionOptions.get(i);
                    if (rnd <= pair.getSecond()) {
                        potion = pair.getFirst();
                        break;
                    }
                    rnd -= pair.getSecond();
                }
            }
            return PotionUtils.setPotion(stack, potion);
        }
        return stack;
    }

    private boolean canAddPotionToItemStack(ItemStack stack) {
        return stack.getItem().equals(POTION) ||
                stack.getItem().equals(TIPPED_ARROW);
    }

    @Override
    public LootItemFunctionType getType() {
        return ADD_POTION;
    }

    public static AddPotionLootFunction.Builder builder() {
        return new Builder();
    }

    public static class Builder extends LootItemConditionalFunction.Builder<AddPotionLootFunction.Builder> {
        private final List<Pair<Potion, Float>> options = Lists.newArrayList();

        public Builder() {
        }

        public AddPotionLootFunction.Builder with(Potion p) {
            return with(p, 1);
        }

        public AddPotionLootFunction.Builder with(Potion p, float weight) {
            options.add(Pair.of(p, weight));
            return this;
        }

        @Override
        protected AddPotionLootFunction.Builder getThis() {
            return this;
        }

        @Override
        public AddPotionLootFunction build() {
            return new AddPotionLootFunction(this.getConditions(), options, (float) options.stream().mapToDouble(Pair::getSecond).sum());
        }
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<AddPotionLootFunction> {
        @Override
        public AddPotionLootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditionsIn) {
            AddPotionLootFunction.Builder b = builder();
            boolean hasEntries = false;
            if (object.has("potions")) {
                JsonArray potions = GsonHelper.getAsJsonArray(object, "potions");
                for (JsonElement e : potions) {
                    float weight = 1;
                    Potion p;
                    if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) {
                        p = getPotion(e.getAsString());
                    } else {
                        JsonObject obj = e.getAsJsonObject();
                        p = getPotion(GsonHelper.getAsString(obj, "potion"));
                        weight = GsonHelper.getAsFloat(obj, "weight");
                    }
                    b.with(p, weight);
                }

                hasEntries = true;
            }
            if (object.has("potion")) {
                if (hasEntries) {
                    throw new IllegalStateException("Cannot specify both 'potion' and 'potions' at the same time!");
                }
                b.with(getPotion(GsonHelper.getAsString(object, "potion")));
            }
            if (!hasEntries) {
                ForgeRegistries.POTIONS.forEach(b::with);
            }
            return b.build();
        }

        private Potion getPotion(String name) {
            ResourceLocation key = new ResourceLocation(name);
            Potion p = ForgeRegistries.POTIONS.getValue(key);
            if (p == null)
                throw new IllegalStateException("No potion found with name " + key);
            return p;
        }

        @Override
        public void serialize(JsonObject json, AddPotionLootFunction lootFunction, JsonSerializationContext ctx) {
            super.serialize(json, lootFunction, ctx);

            if (lootFunction.weightedPotionOptions.size() > 0 && !lootFunction.isRegistryRandom) {
                boolean useStrings = lootFunction.weightedPotionOptions.stream().allMatch(e -> e.getSecond() == 1.0f);
                JsonArray potions = new JsonArray();
                for (int i = 0; i < lootFunction.weightedPotionOptions.size(); i++) {
                    Pair<Potion, Float> pair = lootFunction.weightedPotionOptions.get(i);
                    if (useStrings) {
                        potions.add(ForgeRegistries.POTIONS.getKey(pair.getFirst()).toString());
                    } else {
                        JsonObject entry = new JsonObject();
                        entry.addProperty("potion", ForgeRegistries.POTIONS.getKey(pair.getFirst()).toString());
                        entry.addProperty("weight", pair.getSecond());
                        potions.add(entry);
                    }
                }
                json.add("potions", potions);
            }
        }
    }
}