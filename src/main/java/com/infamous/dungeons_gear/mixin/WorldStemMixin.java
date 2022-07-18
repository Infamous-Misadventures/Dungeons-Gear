package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.server.WorldStem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldStem.class)
public class WorldStemMixin {

    @Inject(method = "updateGlobals()V",
            at = @At(value = "TAIL"))
    private void inject_traderReload(CallbackInfo ci) {
        DungeonsGear.LOGGER.info("Beginning retriggering trader events");
        VillagerTradingManagerAccessor.invokePostWandererEvent();
        VillagerTradingManagerAccessor.invokePostVillagerEvents();
        DungeonsGear.LOGGER.info("Finished retriggering trader events");
    }

}
