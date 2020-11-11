package com.infamous.dungeons_gear.mixin;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(at = @At("RETURN"), method = "getArmPose", cancellable = true)
    private void getArmPose(AbstractClientPlayerEntity playerIn, ItemStack itemStackMain, ItemStack itemStackOff, Hand handIn, CallbackInfoReturnable<BipedModel.ArmPose> cir){
        BipedModel.ArmPose bipedmodel$armpose = cir.getReturnValue();

        boolean flag3 = itemStackMain.getItem() instanceof CrossbowItem;
        boolean flag = CrossbowItem.isCharged(itemStackMain);
        boolean flag1 = itemStackOff.getItem() instanceof CrossbowItem;
        boolean flag2 = CrossbowItem.isCharged(itemStackOff);
        if (flag3 && flag) {
            bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
        }

        if (flag1 && flag2 && itemStackMain.getItem().getUseAction(itemStackMain) == UseAction.NONE) {
            bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
        }

        cir.setReturnValue(bipedmodel$armpose);
    }
}
