package com.infamous.dungeons_gear.mixin;

import com.infamous.dungeons_libraries.utils.AbilityHelper;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

import static com.infamous.dungeons_libraries.utils.AbilityHelper.canApplyToEnemy;

@Mixin(AreaEffectCloud.class)
public abstract class AreaEffectCloudEntityMixin {

    @Shadow
    @Nullable
    public abstract LivingEntity getOwner();

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffect;applyInstantenousEffect(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/LivingEntity;ID)V"), method = "tick", require = 0)
    private void instantHack(MobEffect effect, Entity source, Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        if (indirectSource instanceof LivingEntity) {
            final boolean isEnemy = effect.isBeneficial() == AbilityHelper.isAlly((LivingEntity) indirectSource, entityLivingBaseIn);
            final boolean isSelf = effect.isBeneficial() && (indirectSource == entityLivingBaseIn);
            if (isEnemy || isSelf) {
                effect.applyInstantenousEffect(source, indirectSource, entityLivingBaseIn, amplifier, health);
            }
        } else effect.applyInstantenousEffect(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"), method = "tick", require = 0)
    private boolean extendedHack(LivingEntity livingEntity, MobEffectInstance effectInstanceIn, Entity entity) {
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
