package com.infamous.dungeons_gear.client.renderer.projectiles;

import com.infamous.dungeons_gear.client.models.projectiles.SoulWizardOrbModel;
import com.infamous.dungeons_gear.entities.SoulWizardOrbEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SoulWizardOrbRenderer extends GeoProjectilesRenderer<SoulWizardOrbEntity> {

    public SoulWizardOrbRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SoulWizardOrbModel());
    }

	@Override
	public void renderEarly(SoulWizardOrbEntity animatable, MatrixStack stackIn, float partialTicks,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn,
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
    public void render(SoulWizardOrbEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        float scaleFactor = 1.0F;
        matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
        
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public RenderType getRenderType(SoulWizardOrbEntity animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
