package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.loot.TradeEventsReloader;
import net.minecraft.command.Commands;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.IReloadableResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DataPackRegistries.class)
public class DataPackRegistriesMixin {

    @Inject(method = "Lnet/minecraft/resources/DataPackRegistries;updateGlobals()V",
            at = @At(value = "TAIL"))
    private void inject_traderReload(CallbackInfo ci) {
        DungeonsGear.LOGGER.info("Beginning retriggering trader events");
        VillagerTradingManagerAccessor.invokePostWandererEvent();
        VillagerTradingManagerAccessor.invokePostVillagerEvents();
        DungeonsGear.LOGGER.info("Finished retriggering trader events");
    }

}
