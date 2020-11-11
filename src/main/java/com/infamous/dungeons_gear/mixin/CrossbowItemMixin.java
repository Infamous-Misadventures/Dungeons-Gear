package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Inject(at = @At("RETURN"), method = "getChargeTime(Lnet/minecraft/item/ItemStack;)I", cancellable = true)
    private static void getChargeTime(ItemStack stack, CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(RangedAttackHelper.getVanillaCrossbowChargeTime(stack));
    }
}
