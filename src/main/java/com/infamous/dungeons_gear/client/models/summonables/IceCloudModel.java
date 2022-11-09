package com.infamous.dungeons_gear.client.models.summonables;

import com.infamous.dungeons_gear.DungeonsGear;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IceCloudModel extends AnimatedGeoModel {

	@Override
	public ResourceLocation getAnimationFileLocation(Object entity) {
		return new ResourceLocation(DungeonsGear.MODID, "animations/ice_chunk.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(Object entity) {
		return new ResourceLocation(DungeonsGear.MODID, "geo/ice_chunk.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Object entity) {
		return new ResourceLocation(DungeonsGear.MODID, "textures/entity/ice_chunk.png");
	}
}