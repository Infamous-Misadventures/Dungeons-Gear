package com.infamous.dungeons_gear.client.models.totem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class BuzzyNestModel extends AnimatedGeoModel {

    @Override
    public ResourceLocation getAnimationResource(Object entity) {
        return new ResourceLocation(MODID, "animations/buzzy_nest.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(Object entity) {
        return new ResourceLocation(MODID, "geo/buzzy_nest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Object entity) {
        return new ResourceLocation(MODID, "textures/entity/buzzy_nest.png");
    }
}

