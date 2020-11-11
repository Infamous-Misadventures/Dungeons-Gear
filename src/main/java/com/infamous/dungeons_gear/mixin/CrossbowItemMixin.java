package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends ShootableItem {

    public CrossbowItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(at = @At("RETURN"), method = "<init>", cancellable = true)
    private void init(Properties properties, CallbackInfo callbackInfo){
        this.addPropertyOverride(new ResourceLocation("pull"), (itemStack, world, livingEntity) -> {
            if (livingEntity != null && itemStack.getItem() == this) {
                return CrossbowItem.isCharged(itemStack) ?
                        0.0F :
                        (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / RangedAttackHelper.getVanillaCrossbowChargeTime(itemStack);
            } else {
                return 0.0F;
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"),
                (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
        this.addPropertyOverride(new ResourceLocation("charged"),
                (itemStack, world, livingEntity) -> livingEntity != null && CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
        this.addPropertyOverride(new ResourceLocation("firework"),
                (itemStack, world, livingEntity) -> livingEntity != null && CrossbowItem.isCharged(itemStack) && RangedAttackHelper.hasChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

    }

    @Inject(at = @At("RETURN"), method = "getChargeTime(Lnet/minecraft/item/ItemStack;)I", cancellable = true)
    private static void getChargeTime(ItemStack stack, CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(RangedAttackHelper.getVanillaCrossbowChargeTime(stack));
    }
}
