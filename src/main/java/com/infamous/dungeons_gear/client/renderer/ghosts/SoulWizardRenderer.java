package com.infamous.dungeons_gear.client.renderer.ghosts;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.client.models.ghosts.SoulWizardModel;
import com.infamous.dungeons_gear.client.renderer.layers.PulsatingGlowLayer;
import com.infamous.dungeons_gear.entities.SoulWizardEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SoulWizardRenderer extends GeoEntityRenderer<SoulWizardEntity> {
	
    public SoulWizardRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SoulWizardModel());
        this.addLayer(new PulsatingGlowLayer<>(this, new ResourceLocation(DungeonsGear.MODID, "textures/entity/ghosts/soul_wizard_glow.png"), 0.1F, 1.0F, 0.25F));
    }

    @Override
    protected void applyRotations(SoulWizardEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks,
                                  float rotationYaw, float partialTicks) {
    	float scaleFactor = 0.0F;
    	if (entityLiving.appearAnimationTick < entityLiving.appearAnimationLength - 2) {
	        scaleFactor = 1.0F;
    	} else {
    		scaleFactor = 0.0F;
    	}
	        matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
	        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

    }

    @Override
    public RenderType getRenderType(SoulWizardEntity animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}