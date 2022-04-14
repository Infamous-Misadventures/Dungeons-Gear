package com.infamous.dungeons_gear.client.renderer.totem;

import com.infamous.dungeons_gear.client.models.totem.TotemOfRegernationModel;
import com.infamous.dungeons_gear.entities.TotemOfRegenerationEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class TotemOfRegenerationRenderer extends GeoProjectilesRenderer<TotemOfRegenerationEntity> {
	public TotemOfRegenerationRenderer(EntityRendererManager renderManager) {
		super(renderManager, new TotemOfRegernationModel());
	}
	

	@Override
	public RenderType getRenderType(TotemOfRegenerationEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
