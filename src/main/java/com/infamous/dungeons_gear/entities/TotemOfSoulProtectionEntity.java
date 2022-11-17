package com.infamous.dungeons_gear.entities;

import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_libraries.entities.TotemBaseEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanHealPredicate;

public class TotemOfSoulProtectionEntity extends TotemBaseEntity implements IAnimatable {

    AnimationFactory factory = new AnimationFactory(this);

    public TotemOfSoulProtectionEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_, 240, 2);
    }

    @Override
    protected void applyTotemEffect() {
        LivingEntity owner = getOwner();
        if(owner == null) return;
        applyToNearbyEntities(getOwner(), 8,
                getCanHealPredicate(getOwner()), (LivingEntity nearbyEntity) -> {
                    EffectInstance effectInstance = new EffectInstance(CustomEffects.SOUL_PROTECTION, 21);
                    nearbyEntity.addEffect(effectInstance);
                }
        );
        EffectInstance resistance = new EffectInstance(CustomEffects.SOUL_PROTECTION, 21);
        owner.addEffect(resistance);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.totem_of_soul_protection.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
