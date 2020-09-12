package com.infamous.dungeons_gear.mixin;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(at = @At("RETURN"), method = "func_241741_a_", cancellable = true)
    private static void func_241741_a_(AbstractClientPlayerEntity clientPlayerEntity, Hand hand, CallbackInfoReturnable<BipedModel.ArmPose> cir){
        ItemStack itemstack = clientPlayerEntity.getHeldItem(hand);
        
        if (!clientPlayerEntity.isSwingInProgress && itemstack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack)) {
            cir.setReturnValue(BipedModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
