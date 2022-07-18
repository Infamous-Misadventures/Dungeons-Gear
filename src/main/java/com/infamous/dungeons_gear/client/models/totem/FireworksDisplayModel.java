package com.infamous.dungeons_gear.client.models.totem;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class FireworksDisplayModel extends AnimatedGeoModel {
	   
		@Override
		public ResourceLocation getAnimationFileLocation(Object entity) {
			return new ResourceLocation(MODID, "animations/fireworks_box.animation.json");
		}

		@Override
		public ResourceLocation getModelLocation(Object entity) {
			return new ResourceLocation(MODID, "geo/fireworks_box.geo.json");
		}

		@Override
		public ResourceLocation getTextureLocation(Object entity) {
			return new ResourceLocation(MODID, "textures/entity/fireworks_display.png");
		}
}

