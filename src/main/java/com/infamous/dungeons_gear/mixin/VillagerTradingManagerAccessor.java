package com.infamous.dungeons_gear.mixin;

import net.minecraftforge.common.VillagerTradingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VillagerTradingManager.class)
public interface VillagerTradingManagerAccessor {

    @Invoker(value = "postWandererEvent", remap = false)
    static void invokePostWandererEvent() {
        throw new AssertionError();
    }

    @Invoker(value = "postVillagerEvents", remap = false)
    static void invokePostVillagerEvents() {
        throw new AssertionError();
    }
}
