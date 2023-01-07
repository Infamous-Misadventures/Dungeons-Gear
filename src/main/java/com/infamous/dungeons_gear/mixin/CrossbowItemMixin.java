package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_libraries.utils.RangedAttackHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {

    @Shadow
    public static void performShooting(Level worldIn, LivingEntity shooter, InteractionHand handIn, ItemStack stack, float velocityIn, float inaccuracyIn) {
    }

    @Inject(at = @At("RETURN"), method = "Lnet/minecraft/world/item/CrossbowItem;getChargeDuration(Lnet/minecraft/world/item/ItemStack;)I", cancellable = true)
    private static void getChargeDuration(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) RangedAttackHelper.getVanillaCrossbowChargeTime(null, stack)); // TODO: Should take in a LivingEntity to be able to check for Roll Charge
    }

//    @Redirect(at=@At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;performShooting(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;FF)V"), method = "use")
//    private void hack(World worldIn, LivingEntity shooter, Hand handIn, ItemStack stack, float velocityIn, float inaccuracyIn){
//        if(stack.getItem() instanceof AbstractDungeonsCrossbowItem){
//            ((AbstractDungeonsCrossbowItem)stack.getItem()).fireCrossbowProjectiles(worldIn, shooter, handIn, stack, velocityIn, inaccuracyIn);
//        } else performShooting(worldIn, shooter, handIn, stack, velocityIn, inaccuracyIn);
//    }
}
