package com.infamous.dungeons_gear.client.models.totem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class TotemOfRegernationModel extends AnimatedGeoModel {
	   
		@Override
		public ResourceLocation getAnimationFileLocation(Object entity) {
			return new ResourceLocation(MODID, "animations/totem_of_regeneration.animation.json");
		}

		@Override
		public ResourceLocation getModelLocation(Object entity) {
			return new ResourceLocation(MODID, "geo/totem_of_regeneration.geo.json");
		}

		@Override
		public ResourceLocation getTextureLocation(Object entity) {
			return new ResourceLocation(MODID, "textures/entity/totem_of_regeneration.png");
		}
}

