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

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/Effect;applyInstantenousEffect(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/LivingEntity;ID)V"), method = "tick", require = 0)
    private void instantHack(Effect effect, Entity source, Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        if (indirectSource instanceof LivingEntity) {
            final boolean isEnemy = effect.isBeneficial() == AbilityHelper.isAlly((LivingEntity) indirectSource, entityLivingBaseIn);
            final boolean isSelf = effect.isBeneficial() && (indirectSource == entityLivingBaseIn);
            if (isEnemy || isSelf) {
                effect.applyInstantenousEffect(source, indirectSource, entityLivingBaseIn, amplifier, health);
            }
        } else effect.applyInstantenousEffect(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addEffect(Lnet/minecraft/potion/EffectInstance;)Z"), method = "tick", require = 0)
    private boolean extendedHack(LivingEntity livingEntity, EffectInstance effectInstanceIn) {
        if (getOwner() != null) {
            final boolean isEnemy = effectInstanceIn.getEffect().isBeneficial() == AbilityHelper.isAlly(getOwner(), livingEntity);
            final boolean isSelf = effectInstanceIn.getEffect().isBeneficial() && (getOwner() == livingEntity);
            if (isEnemy || isSelf) {
                livingEntity.addEffect(effectInstanceIn);
            }
        } else livingEntity.addEffect(effectInstanceIn);
        return true;
    }
}
