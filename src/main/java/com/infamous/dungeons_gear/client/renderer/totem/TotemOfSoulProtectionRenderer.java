package com.infamous.dungeons_gear.client.renderer.totem;

import com.infamous.dungeons_gear.client.models.totem.TotemOfSoulProtectionModel;
import com.infamous.dungeons_gear.entities.TotemOfSoulProtectionEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class TotemOfSoulProtectionRenderer extends GeoProjectilesRenderer<TotemOfSoulProtectionEntity> {
	public TotemOfSoulProtectionRenderer(EntityRendererManager renderManager) {
		super(renderManager, new TotemOfSoulProtectionModel());
	}
	

	@Override
	public RenderType getRenderType(TotemOfSoulProtectionEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}
}
