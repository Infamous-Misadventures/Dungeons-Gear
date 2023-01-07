package com.infamous.dungeons_gear.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resources.ResourceLocation;

import static com.infamous.dungeons_gear.utilties.GeneralHelper.modLoc;

public enum LootTableRarity {
    COMMON("common"),
    FANCY("fancy"),
    OBSIDIAN("obsidian");

    public static final Codec<LootTableRarity> CODEC = Codec.STRING.flatComapMap((s) -> {
        return byName(s, null);
    }, (d) -> {
        return DataResult.success(d.getName());
    });
    private final String name;

    LootTableRarity(String name) {
        this.name = name;
    }

    public static LootTableRarity byName(String name, LootTableRarity defaultRank) {
        LootTableRarity[] var2 = values();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            LootTableRarity factionRank = var2[var4];
            if (factionRank.name.equals(name)) {
                return factionRank;
            }
        }

        return defaultRank;
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation getTable(LootTableType type) {
        return modLoc("gear_addition/" + this.getName() + "_" + type.getName());
    }
}