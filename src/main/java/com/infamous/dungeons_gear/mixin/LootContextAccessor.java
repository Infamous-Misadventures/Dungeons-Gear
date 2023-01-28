package com.infamous.dungeons_gear.mixin;

import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LootContext.class)
public interface LootContextAccessor {
    @Accessor(value = "queriedLootTableId", remap = false)
    void dungeonsgear_setQueriedLootTableId(ResourceLocation queriedLootTableId);
}