package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.init.AttributeRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraftforge.common.ForgeMod;
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
    private double getExtendedAttackReachSquared(double value, CUseEntityPacket packetIn) {
        if(packetIn.getAction() == CUseEntityPacket.Action.ATTACK){
            double attackReachValue = player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
            return attackReachValue * attackReachValue;
        }
        double creativeReachValue = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()) + 1.0D;
        return creativeReachValue * creativeReachValue;
    }
}