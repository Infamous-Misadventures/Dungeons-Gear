package com.infamous.dungeons_gear.client.models.totem;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class TotemOfSoulProtectionModel extends AnimatedGeoModel {
	   
		@Override
		public ResourceLocation getAnimationFileLocation(Object entity) {
			return new ResourceLocation(MODID, "animations/totem_of_soul_protection.animation.json");
		}

		@Override
		public ResourceLocation getModelLocation(Object entity) {
			return new ResourceLocation(MODID, "geo/totem_of_soul_protection.geo.json");
		}

		@Override
		public ResourceLocation getTextureLocation(Object entity) {
			return new ResourceLocation(MODID, "textures/entity/totem_of_soul_protection.png");
		}
}

