package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.capabilities.CapabilityHandler;
import com.infamous.dungeons_gear.capabilities.combo.Combo;
import com.infamous.dungeons_gear.capabilities.combo.ComboProvider;
import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.init.AttributeRegistry;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
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

    @ModifyConstant(
            method = "attackTargetEntityWithCurrentItem",
            constant = @Constant(doubleValue = 9.0D)
    )
    private double getAttackReachSquared(double value) {
        double attackReachValue = this.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
        return attackReachValue * attackReachValue;
    }

    @Inject(
            method = "resetCooldown",
            at = @At("HEAD")
    )
    private void resetCooldown(CallbackInfo ci) {
        ICombo ic = CapabilityHelper.getComboCapability(this);
        if (ic != null) {
            ic.setComboCount(ic.getComboCount() + 1);
            ic.setComboTimer(60);
            ic.setCachedCooldown(((PlayerEntity) (Object) this).getCooledAttackStrength(0.5f));
        }
    }
}
