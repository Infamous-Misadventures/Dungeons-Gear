package com.infamous.dungeons_gear.mixin;

import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootContext.class)
public interface LootContextAccessor {
    @Accessor(value = "queriedLootTableId", remap = false)
    void dungeonsgear_setQueriedLootTableId(ResourceLocation queriedLootTableId);
}