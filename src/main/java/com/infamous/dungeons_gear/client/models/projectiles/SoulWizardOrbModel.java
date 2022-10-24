package com.infamous.dungeons_gear.client.models.projectiles;


import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.entities.SoulWizardOrbEntity;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SoulWizardOrbModel extends AnimatedGeoModel<SoulWizardOrbEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(SoulWizardOrbEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "animations/soul_wizard_orb.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(SoulWizardOrbEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "geo/soul_wizard_orb.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SoulWizardOrbEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "textures/entity/projectile/soul_wizard_orb_" + entity.textureChange % 2 + ".png");
	}

	@Override
	public void setLivingAnimations(SoulWizardOrbEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone everything = this.getAnimationProcessor().getBone("everything");

		everything.setRotationY(-1.5708F);
	}
}