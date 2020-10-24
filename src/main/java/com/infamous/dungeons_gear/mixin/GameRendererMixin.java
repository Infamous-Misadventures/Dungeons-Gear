package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.init.AttributeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    private Minecraft mc;

    @ModifyConstant(
            method = "getMouseOver",
            constant = @Constant(doubleValue = 6.0D)
    )
    private double getExtendedAttackReach(double value) {
        assert mc.player != null;
        return mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) * 2.0D;
    }

    @ModifyConstant(
            method = "getMouseOver",
            constant = @Constant(doubleValue = 3.0D)
    )
    private double getAttackReach(double value) {
        assert mc.player != null;
        return mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
    }

    @ModifyConstant(
            method = "getMouseOver",
            constant = @Constant(doubleValue = 9.0D)
    )
    private double getAttackReachSquared(double value) {
        assert mc.player != null;
        double attackReachValue = mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
        return attackReachValue * attackReachValue;
    }
}
