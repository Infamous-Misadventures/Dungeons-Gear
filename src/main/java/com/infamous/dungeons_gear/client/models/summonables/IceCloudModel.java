package com.infamous.dungeons_gear.client.models.summonables;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.entities.IceCloudEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IceCloudModel extends AnimatedGeoModel<IceCloudEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(IceCloudEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "animations/ice_chunk.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(IceCloudEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "geo/ice_chunk.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(IceCloudEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "textures/entity/ice_chunk.png");
	}
}