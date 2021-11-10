package com.infamous.dungeons_gear.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@OnlyIn(Dist.CLIENT)
public class IceCloudRenderer<T extends IceCloudEntity> extends EntityRenderer<T> {
   private static final ResourceLocation ICE_CLOUD_TEXTURE = new ResourceLocation(MODID, "textures/entity/ice_cloud.png");

   protected final IceCloudModel<T> iceCloudModel = new IceCloudModel<T>();

   public IceCloudRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
   }

   @Override
   public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
      matrixStackIn.pushPose();
      matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180));
      matrixStackIn.translate(0.0D, -1.5D, 0.0D);
      ResourceLocation resourceLocation = this.getTextureLocation(entityIn);
      RenderType renderType = this.iceCloudModel.renderType(resourceLocation);
      IVertexBuilder ivertexbuilder = bufferIn.getBuffer(renderType);
      this.iceCloudModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.popPose();
      super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   @Override
   public ResourceLocation getTextureLocation(T entity) {
      return ICE_CLOUD_TEXTURE;
   }
}