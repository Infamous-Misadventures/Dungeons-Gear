package com.infamous.dungeons_gear.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.ResourceLocation;

import static com.infamous.dungeons_gear.utilties.GeneralHelper.modLoc;

public enum LootTableType {
    BASIC("basic"),
    DESERT("desert"),
    OCEAN("ocean"),
    COLD("cold"),
    JUNGLE("jungle"),
    NETHER("nether"),
    END("end"),
    GIFT("gift"),
    ALL("all");

    public static final Codec<LootTableType> CODEC = Codec.STRING.flatComapMap((s) -> {
        return byName(s, (LootTableType)null);
    }, (d) -> {
        return DataResult.success(d.getName());
    });
    private final String name;

    private LootTableType(String name) {
        this.name = name;
    }

    public static LootTableType byName(String name, LootTableType defaultRank) {
        LootTableType[] var2 = values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            LootTableType factionRank = var2[var4];
            if (factionRank.name.equals(name)) {
                return factionRank;
            }
        }

        return defaultRank;
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation normalTable(){
        return modLoc("gear_addition/subtype/" + this.name + "_normal");
    }

    public ResourceLocation uniqueTable(){
        return modLoc("gear_addition/subtype/" + this.name + "_unique");
    }

    public ResourceLocation artifactTable(){
        return modLoc("gear_addition/subtype/" + this.name + "_artifact");
    }
}