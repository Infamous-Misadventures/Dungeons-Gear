package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.registry.AttributeRegistry;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Inject(
            method = "func_234570_el_",
            at = @At("RETURN")
    )
    private static void initAttributes(CallbackInfoReturnable<AttributeModifierMap.MutableAttribute> ci) {
        ci.getReturnValue().createMutableAttribute(AttributeRegistry.ATTACK_REACH.get());
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
    @Redirect(method = "attackTargetEntityWithCurrentItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getDistanceSq(Lnet/minecraft/entity/Entity;)D"))
    private double getModifiedDistance(PlayerEntity playerEntity, Entity entityIn) {
        double reach = playerEntity.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) - 3;
        return playerEntity.getDistanceSq(entityIn) - (6 * reach + reach * reach);
    }

    @Inject(
            method = "attackTargetEntityWithCurrentItem",
            at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/enchantment/EnchantmentHelper;getFireAspectModifier(Lnet/minecraft/entity/LivingEntity;)I")
    )
    private void kindOfAddCombo(CallbackInfo ci) {
        ICombo ic = CapabilityHelper.getComboCapability(this);
        if (ic != null) {
            ic.setComboCount(ic.getComboCount() - 1);
        }
    }

    @Inject(
            method = "resetCooldown",
            at = @At("HEAD")
    )
    private void addCombo(CallbackInfo ci) {
        ICombo ic = CapabilityHelper.getComboCapability(this);
        if (ic != null) {
            ic.setComboCount(ic.getComboCount() + 1);
            ic.setComboTimer(60);
            ic.setCachedCooldown(((PlayerEntity) (Object) this).getCooledAttackStrength(0.5f));
        }
    }
}
