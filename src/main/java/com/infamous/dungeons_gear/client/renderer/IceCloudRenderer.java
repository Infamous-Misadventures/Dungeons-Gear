package com.infamous.dungeons_gear.client.renderer;

import com.infamous.dungeons_gear.client.models.summonables.IceCloudModel;
import com.infamous.dungeons_gear.entities.IceCloudEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

@OnlyIn(Dist.CLIENT)
public class IceCloudRenderer extends GeoProjectilesRenderer<IceCloudEntity> {
   public IceCloudRenderer(EntityRendererProvider.Context renderManager) {
      super(renderManager, new IceCloudModel());
   }

   @Override
   public RenderType getRenderType(IceCloudEntity animatable, float partialTicks, PoseStack stack,
                                   MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                   ResourceLocation textureLocation) {
      return RenderType.entityTranslucent(getTextureLocation(animatable));
   }
}