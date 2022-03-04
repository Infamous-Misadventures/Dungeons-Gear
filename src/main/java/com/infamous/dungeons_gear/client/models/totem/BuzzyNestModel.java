package com.infamous.dungeons_gear.client.models.totem;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class BuzzyNestModel extends AnimatedGeoModel {
	   
		@Override
		public ResourceLocation getAnimationFileLocation(Object entity) {
			return new ResourceLocation(MODID, "animations/buzzy_nest.animation.json");
		}

		@Override
		public ResourceLocation getModelLocation(Object entity) {
			return new ResourceLocation(MODID, "geo/buzzy_nest.geo.json");
		}

		@Override
		public ResourceLocation getTextureLocation(Object entity) {
			return new ResourceLocation(MODID, "textures/entity/buzzy_nest.png");
		}
}

