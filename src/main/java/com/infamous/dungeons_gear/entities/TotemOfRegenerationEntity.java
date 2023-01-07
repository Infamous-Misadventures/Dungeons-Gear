package com.infamous.dungeons_gear.entities;

import com.infamous.dungeons_libraries.entities.TotemBaseEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.infamous.dungeons_gear.DungeonsGear.PROXY;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static com.infamous.dungeons_libraries.utils.AreaOfEffectHelper.getCanHealPredicate;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;

public class TotemOfRegenerationEntity extends TotemBaseEntity implements IAnimatable {

    AnimationFactory factory = new AnimationFactory(this);

    public TotemOfRegenerationEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_, 240, 2);
    }

    @Override
    protected void applyTotemEffect() {
        LivingEntity owner = getOwner();
        if(owner == null) return;
        if(this.lifeTicks % 20 == 0) {
            applyToNearbyEntities(owner, 8,
                    getCanHealPredicate(owner), (LivingEntity nearbyEntity) -> {
                        if (nearbyEntity.getHealth() < nearbyEntity.getMaxHealth()) {
                            nearbyEntity.heal(1F);
                            PROXY.spawnParticles(nearbyEntity, ParticleTypes.HEART);
                        }
                    }
            );
            if(owner.getHealth() < owner.getMaxHealth()){
                owner.heal(1F);
                PROXY.spawnParticles(owner, ParticleTypes.HEART);
            }
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 5, this::predicate));
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.totem_of_regeneration.idle", LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
