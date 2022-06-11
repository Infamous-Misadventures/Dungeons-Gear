package com.infamous.dungeons_gear.mixin;

import net.minecraftforge.common.VillagerTradingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VillagerTradingManager.class)
public interface VillagerTradingManagerAccessor {

    @Invoker("postWandererEvent")
    static void invokePostWandererEvent(){
        throw new AssertionError();
    }

    @Invoker("postVillagerEvents")
    static void invokePostVillagerEvents(){
        throw new AssertionError();
    }
}
