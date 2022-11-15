package com.infamous.dungeons_gear.client.models.ghosts;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.entities.SoulWizardEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SoulWizardModel extends AnimatedGeoModel<SoulWizardEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(SoulWizardEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "animations/soul_wizard.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(SoulWizardEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "geo/soul_wizard.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SoulWizardEntity entity) {
		return new ResourceLocation(DungeonsGear.MODID, "textures/entity/ghosts/soul_wizard.png");
	}

    @Override
    public void setLivingAnimations(SoulWizardEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (extraData.headPitch != 0 || extraData.netHeadYaw != 0) {
            head.setRotationX(head.getRotationX() + (extraData.headPitch * ((float) Math.PI / 180F)));
            head.setRotationY(head.getRotationY() + (extraData.netHeadYaw * ((float) Math.PI / 180F)));
        }
    }
}
