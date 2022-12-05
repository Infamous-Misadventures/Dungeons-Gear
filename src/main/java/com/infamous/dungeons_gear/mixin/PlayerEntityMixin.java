package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboHelper;
import com.infamous.dungeons_gear.registry.AttributeInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType_1, Level world_1) {
        super(entityType_1, world_1);
    }

    @Inject(
            method = "Lnet/minecraft/world/entity/player/Player;createAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;",
            at = @At("RETURN")
    )
    private static void initAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> ci) {
        ci.getReturnValue().add(AttributeInit.ATTACK_REACH.get());
    }

//    @ModifyConstant(
//            method = "attackTargetEntityWithCurrentItem",
//            constant = @Constant(doubleValue = 9.0D)
//    )
//    private double getAttackReachSquared(double value) {
//        double attackReachValue = this.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
//        return attackReachValue * attackReachValue;
//    }

    //Sometimes my genius... it's almost frightening.
//    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;distanceToSqr(Lnet/minecraft/entity/Entity;)D"))
//    private double getModifiedDistance(Player playerEntity, Entity entityIn) {
//        double reach = playerEntity.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) - 3;
//        return playerEntity.distanceToSqr(entityIn) - (6 * reach + reach * reach);
//    }

    @Inject(
            method = "Lnet/minecraft/world/entity/player/Player;attack(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getFireAspect(Lnet/minecraft/world/entity/LivingEntity;)I")
    )
    private void kindOfAddDualWield(CallbackInfo ci) {
        Combo ic = ComboHelper.getComboCapability(this);
        if (ic != null) {
            ic.setComboCount(ic.getComboCount() - 1);
        }
    }

    @Inject(
            method = "Lnet/minecraft/world/entity/player/Player;resetAttackStrengthTicker()V",
            at = @At("HEAD")
    )
    private void addDualWield(CallbackInfo ci) {
        Combo ic = ComboHelper.getComboCapability(this);
        if (ic != null) {
            ic.setComboCount(ic.getComboCount() + 1);
            ic.setDualWieldTimer(60);
            ic.setCachedCooldown(((Player) (Object) this).getAttackStrengthScale(0.5f));
        }
    }
}
