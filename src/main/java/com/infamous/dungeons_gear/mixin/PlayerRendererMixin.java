package com.infamous.dungeons_gear.mixin;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(at = @At("RETURN"), method = "getArmPose", cancellable = true)
    private static void getArmPose(AbstractClientPlayerEntity clientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedModel.ArmPose> cir){
        ItemStack itemstack = clientPlayerEntity.getItemInHand(hand);
        
        if (!clientPlayerEntity.swinging && itemstack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack)) {
            cir.setReturnValue(BipedModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
