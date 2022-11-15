package com.infamous.dungeons_gear.client.renderer.projectiles;

import com.infamous.dungeons_gear.client.models.projectiles.SoulWizardOrbModel;
import com.infamous.dungeons_gear.entities.SoulWizardOrbEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SoulWizardOrbRenderer extends GeoProjectilesRenderer<SoulWizardOrbEntity> {

    public SoulWizardOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SoulWizardOrbModel());
    }

	@Override
	public void renderEarly(SoulWizardOrbEntity animatable, PoseStack stackIn, float partialTicks,
							MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		
		float scaleFactor = 1.0F;
		if (animatable.lifeTime <= 3) {
			scaleFactor = 0.0F;
		} else {
			scaleFactor = 1.0F;
		}
		stackIn.scale(scaleFactor, scaleFactor, scaleFactor);
	}
	
	@Override
	protected int getBlockLightLevel(SoulWizardOrbEntity p_225624_1_, BlockPos p_225624_2_) {
		return 15;
	}

	
    @Override
    public void render(SoulWizardOrbEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        float scaleFactor = 1.0F;
        matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
        
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public RenderType getRenderType(SoulWizardOrbEntity animatable, float partialTicks, PoseStack stack,
									MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
									ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
