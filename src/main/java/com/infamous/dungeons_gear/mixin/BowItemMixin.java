package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.infamous.dungeons_gear.utilties.RangedAttackHelper.getVanillaArrowVelocity;


@Mixin(BowItem.class)
public abstract class BowItemMixin extends ShootableItem {

    public BowItemMixin(Properties properties) {
        super(properties);}

    @Inject(at = @At("RETURN"), method = "<init>", cancellable = true)
    private void init(Properties properties, CallbackInfo callbackInfo){
        this.addPropertyOverride(new ResourceLocation("pull"), (itemStack, world, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return !(livingEntity.getActiveItemStack().getItem() instanceof BowItem) ?
                        0.0F :
                        (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / RangedAttackHelper.getVanillaBowChargeTime(itemStack);
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"),
                (itemStack, world, livingEntity) -> livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);

    }

    @ModifyVariable(at = @At("STORE"), method = "onPlayerStoppedUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V")
    private float setArrowVelocity(float arrowVelocity, ItemStack stack, World worldIn, LivingEntity livingEntity, int timeLeft) {
        PlayerEntity playerentity = (PlayerEntity)livingEntity;

        boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
        ItemStack itemstack = playerentity.findAmmo(stack);

        int i = 72000 - timeLeft;
        i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
        if (i < 0) return 0;

        arrowVelocity = getVanillaArrowVelocity(stack, i);
        return arrowVelocity;
    }
}
