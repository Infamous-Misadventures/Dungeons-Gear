package com.infamous.dungeons_gear.client.renderer.summonables;

import com.infamous.dungeons_gear.client.models.summonables.IceCloudModel;
import com.infamous.dungeons_gear.entities.IceCloudEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class IceCloudRenderer extends GeoProjectilesRenderer<IceCloudEntity> {
   public IceCloudRenderer(EntityRendererManager renderManager) {
      super(renderManager, new IceCloudModel());
   }
   
	@Override
	protected int getBlockLightLevel(IceCloudEntity p_114496_, BlockPos p_114497_) {
		return p_114496_.level.getBrightness(LightType.BLOCK, p_114497_) > 10
				? p_114496_.level.getBrightness(LightType.BLOCK, p_114497_)
				: 5;
	}

   @Override
   public RenderType getRenderType(IceCloudEntity animatable, float partialTicks, MatrixStack stack,
                                   IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                   ResourceLocation textureLocation) {
      return RenderType.entityTranslucent(getTextureLocation(animatable));
   }
}