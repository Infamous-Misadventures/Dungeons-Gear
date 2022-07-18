package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.registry.AttributeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    private Minecraft minecraft;

//    @Redirect(method = "pick", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/util/math/vector/Vector3d;distanceToSqr(Lnet/minecraft/util/math/vector/Vector3d;)D"))
//    private double getModifiedDistance1(Vec3 vector3d, Vec3 vec) {
//        Player playerEntity=Minecraft.getInstance().player;
//        double reach = playerEntity.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) - 3;
//        return vector3d.distanceToSqr(vec) + (6 * reach + reach * reach);
//    }
//@Redirect(method = "pick", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/util/math/vector/Vector3d;distanceToSqr(Lnet/minecraft/util/math/vector/Vector3d;)D"))
//    private double getModifiedDistance2(Vec3 vector3d, Vec3 vec) {
//        Player playerEntity=Minecraft.getInstance().player;
//        double reach = playerEntity.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) - 3;
//        return vector3d.distanceToSqr(vec) - (6 * reach + reach * reach);
//    }

//    @ModifyConstant(
//            method = "getMouseOver",
//            constant = @Constant(doubleValue = 6.0D)
//    )
//    private double getExtendedAttackReach(double value) {
//        assert mc.player != null;
//        return mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) + 3.0D;
//    }
//
//    @ModifyConstant(
//            method = "getMouseOver",
//            constant = @Constant(doubleValue = 3.0D)
//    )
//    private double getAttackReach(double value) {
//        assert mc.player != null;
//        return mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
//    }
//
//    @ModifyConstant(
//            method = "getMouseOver",
//            constant = @Constant(doubleValue = 9.0D)
//    )
//    private double getAttackReachSquared(double value) {
//        assert mc.player != null;
//        double attackReachValue = mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
//        return attackReachValue * attackReachValue;
//    }
}
