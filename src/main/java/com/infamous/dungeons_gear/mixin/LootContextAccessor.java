package com.infamous.dungeons_gear.mixin;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LootContext.class)
public interface LootContextAccessor {
    @Accessor(value = "queriedLootTableId", remap = false)
    void dungeonsgear_setQueriedLootTableId(ResourceLocation queriedLootTableId);

    @Accessor(value = "params", remap = false)
    Map<LootContextParam<?>, Object> dungeonsgear_getParams();
}