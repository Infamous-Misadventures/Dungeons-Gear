package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.utilties.RangedAttackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BowItem.class)
public class BowItemMixin {

    @ModifyVariable(at = @At("STORE"), method = "releaseUsing(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V")
    private float setArrowVelocity(float arrowVelocity, ItemStack stack, World worldIn, LivingEntity livingEntity, int timeLeft) {
        PlayerEntity playerentity = (PlayerEntity)livingEntity;

        boolean flag = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
        ItemStack itemstack = playerentity.getProjectile(stack);

        int i = 72000 - timeLeft;
        i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
        if (i < 0) return 0;

        arrowVelocity = RangedAttackHelper.getVanillaArrowVelocity(livingEntity, stack, i);
        return arrowVelocity;
    }
}
