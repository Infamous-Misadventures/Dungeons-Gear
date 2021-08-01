package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.items.ranged.crossbows.AbstractDungeonsCrossbowItem;
import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;

import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @Shadow
    public static void fireProjectiles(World worldIn, LivingEntity shooter, Hand handIn, ItemStack stack, float velocityIn, float inaccuracyIn) {
    }

    @Inject(at = @At("RETURN"), method = "getChargeTime(Lnet/minecraft/item/ItemStack;)I", cancellable = true)
    private static void getChargeTime(ItemStack stack, CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(RangedAttackHelper.getVanillaCrossbowChargeTime(stack)); // TODO: Should take in a LivingEntity to be able to check for Roll Charge
    }

    @Redirect(at=@At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;fireProjectiles(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;FF)V"), method = "onItemRightClick")
    private void hack(World worldIn, LivingEntity shooter, Hand handIn, ItemStack stack, float velocityIn, float inaccuracyIn){
        if(stack.getItem() instanceof AbstractDungeonsCrossbowItem){
            ((AbstractDungeonsCrossbowItem)stack.getItem()).fireCrossbowProjectiles(worldIn, shooter, handIn, stack, velocityIn, inaccuracyIn);
        } else fireProjectiles(worldIn, shooter, handIn, stack, velocityIn, inaccuracyIn);
    }
}
