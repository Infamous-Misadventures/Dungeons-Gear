package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_gear.utilties.AbilityHelper;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(AreaEffectCloudEntity.class)
public abstract class AreaEffectCloudEntityMixin {

    @Shadow
    @Nullable
    public abstract LivingEntity getOwner();

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/Effect;affectEntity(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/LivingEntity;ID)V"), method = "tick")
    private void instantHack(Effect effect, Entity source, Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        if (indirectSource instanceof LivingEntity) {
            if (effect.isBeneficial() != AbilityHelper.canApplyToEnemy((LivingEntity) indirectSource, entityLivingBaseIn)) {
                effect.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
            }
        } else effect.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addPotionEffect(Lnet/minecraft/potion/EffectInstance;)Z"), method = "tick")
    private boolean extendedHack(LivingEntity livingEntity, EffectInstance effectInstanceIn) {
        if (getOwner()!=null) {
            if (effectInstanceIn.getPotion().isBeneficial() != AbilityHelper.canApplyToEnemy(getOwner(), livingEntity)) {
                livingEntity.addPotionEffect(effectInstanceIn);
            }
        } else livingEntity.addPotionEffect(effectInstanceIn);
        return false;
    }
}
