package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.init.AttributeRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlayNetHandler.class)
public abstract class ServerPlayNetHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @ModifyConstant(
        method = "processUseEntity",
        constant = {
            @Constant(doubleValue = 36.0D)
        }
    )
    private double getExtendedAttackReachSquared(double value) {
        double extendedAttackReachValue = player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) * 2.0D;
        return extendedAttackReachValue * extendedAttackReachValue;
    }
}