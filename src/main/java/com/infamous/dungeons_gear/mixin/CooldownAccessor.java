package com.infamous.dungeons_gear.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.util.CooldownTracker$Cooldown")
public interface CooldownAccessor {

    @Accessor
    int getCreateTicks();

    @Accessor
    int getExpireTicks();
}
