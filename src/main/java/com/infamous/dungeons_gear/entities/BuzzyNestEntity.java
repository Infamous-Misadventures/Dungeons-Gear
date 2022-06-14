package com.infamous.dungeons_gear.entities;

import com.infamous.dungeons_libraries.capabilities.minionmaster.IMaster;
import com.infamous.dungeons_libraries.entities.TotemBaseEntity;
import com.infamous.dungeons_libraries.summon.SummonHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import static com.infamous.dungeons_libraries.capabilities.minionmaster.MinionMasterHelper.getMasterCapability;

public class BuzzyNestEntity extends TotemBaseEntity implements IAnimatable {

    AnimationFactory factory = new AnimationFactory(this);

    public BuzzyNestEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_, 200, 20);
    }

    @Override
    protected void applyTotemEffect() {
        if(!this.level.isClientSide() && this.lifeTicks % 20 == 0 && this.getOwner() != null) {
            IMaster summonerCap = getMasterCapability(this.getOwner());
            if (summonerCap != null) {
                SummonHelper.summonEntity(this.getOwner(), this.blockPosition(), EntityType.BEE);
                this.level.playSound(null, this.blockPosition(), SoundEvents.BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
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
        if (this.lifeTicks > 0 && this.lifeTicks % 20 == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.buzzy_nest.spawn", false));
        } else if (this.lifeTicks > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.buzzy_nest.activate", false));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.buzzy_nest.deactivate", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
