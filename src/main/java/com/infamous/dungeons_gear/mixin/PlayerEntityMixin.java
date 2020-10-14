package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.init.AttributeRegistry;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @ModifyConstant(
            method = "attackTargetEntityWithCurrentItem",
            constant = @Constant(doubleValue = 9.0D)
    )
    private double getAttackReachSquared(double value) {
        return Math.pow(this.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()), 2);
    }

    @Inject(
            method = "func_234570_el_",
            at = @At("RETURN")
    )
    private static void initAttributes(CallbackInfoReturnable<AttributeModifierMap.MutableAttribute> ci) {
        ci.getReturnValue().createMutableAttribute(AttributeRegistry.ATTACK_REACH.get());
    }
}
