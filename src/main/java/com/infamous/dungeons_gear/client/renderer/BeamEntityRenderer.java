package com.infamous.dungeons_gear.client.renderer;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.entities.BeamEntity;
import com.infamous.dungeons_gear.items.artifacts.beacon.BeamColor;
import com.infamous.dungeons_gear.items.artifacts.beacon.MyRenderType;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeamEntityRenderer<T extends BeamEntity> extends EntityRenderer<T> {
   @Override
   public ResourceLocation getTextureLocation(T p_110775_1_) {
      return new ResourceLocation(DungeonsGear.MODID + ":textures/misc/beacon_beam_core.png");
   }

   public BeamEntityRenderer(EntityRendererManager p_i46193_1_) {
      super(p_i46193_1_);
   }

   public void render(T pEntity, float pEntityYaw, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pPackedLight) {
      double distance = pEntity.beamTraceDistance(BeamEntity.MAX_RAYTRACE_DISTANCE, 1.0f, false);

      float speedModifier = -0.02f;

      BeamColor beamColor = new BeamColor((short) 90, (short) 0, (short) 90, (short) 255, (short) 255, (short) 255);
      drawBeams(distance, beamColor, pEntity.getBeamWidth(), pEntity, pPartialTicks, speedModifier, pMatrixStack);
   }

   private static void drawBeams(double distance, BeamColor beamColor, float thickness, BeamEntity entity, float ticks, float speedModifier, MatrixStack pMatrixStack) {
      IVertexBuilder builder;
      long gameTime = entity.level.getGameTime();
      double v = gameTime * speedModifier;
      float additiveThickness = (thickness * 1.75f) * calculateLaserFlickerModifier(gameTime);

      float beam1r = beamColor.getRedValue() / 255f;
      float beam1g = beamColor.getGreenValue() / 255f;
      float beam1b = beamColor.getBlueValue() / 255f;
      float beam2r = beamColor.getInnerRedValue() / 255f;
      float beam2g = beamColor.getInnerGreenValue() / 255f;
      float beam2b = beamColor.getInnerBlueValue() / 255f;
      IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();

      pMatrixStack.pushPose();
      pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((MathHelper.lerp(ticks, boundDegrees(-entity.yRot), boundDegrees(-entity.yRotO)))));
      pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(MathHelper.lerp(ticks, boundDegrees(entity.xRot), boundDegrees(entity.xRotO))));

      MatrixStack.Entry matrixstack$entry = pMatrixStack.last();
      Matrix3f matrixNormal = matrixstack$entry.normal();
      Matrix4f positionMatrix = matrixstack$entry.pose();

      //additive laser beam
      builder = buffer.getBuffer(MyRenderType.BEACON_BEAM_GLOW);
      drawClosingBeam(builder, positionMatrix, matrixNormal, additiveThickness, distance/10, 0.5, 1, ticks, beam2r,beam2g,beam2b,0.9f);

      //main laser, colored part
      builder = buffer.getBuffer(MyRenderType.BEACON_BEAM_MAIN);
      drawBeam(builder, positionMatrix, matrixNormal, thickness, distance, v, v + distance * 1.5, ticks,  beam2r,beam2g,beam2b, 0.7f);

      //core
      builder = buffer.getBuffer(MyRenderType.BEACON_BEAM_CORE);
      drawBeam(builder, positionMatrix, matrixNormal, thickness*0.7f, distance, v, v + distance * 1.5, ticks, beam1r,beam1g,beam1b, 1f);
      pMatrixStack.popPose();
      buffer.endBatch();
   }

   private static float boundDegrees(float v){
      // Time wasted trying to get beams not jumping around due to networking: TOO DAMN LONG!
      return (v % 360 + 360) % 360;
   }

   private static float calculateLaserFlickerModifier(long gameTime) {
      return 0.9f + 0.1f * MathHelper.sin(gameTime * 0.99f) * MathHelper.sin(gameTime * 0.3f) * MathHelper.sin(gameTime * 0.1f);
   }

   private static void drawBeam(IVertexBuilder builder, Matrix4f positionMatrix, Matrix3f matrixNormalIn, float thickness, double distance, double v1, double v2, float ticks, float r, float g, float b, float alpha) {
      Vector3f vector3f = new Vector3f(0.0f, 1.0f, 0.0f);
      vector3f.transform(matrixNormalIn);
      float xMin = -thickness;
      float xMax = thickness;
      float yMin = -thickness - 0.115f;
      float yMax = thickness - 0.115f;
      float zMin = 0;
      float zMax = (float) distance;

      Vector4f vec1 = new Vector4f(xMin, yMin, zMin, 1.0F);
      vec1.transform(positionMatrix);
      Vector4f vec2 = new Vector4f(xMin, yMin, zMax, 1.0F);
      vec2.transform(positionMatrix);
      Vector4f vec3 = new Vector4f(xMin, yMax, zMax, 1.0F);
      vec3.transform(positionMatrix);
      Vector4f vec4 = new Vector4f(xMin, yMax, zMin, 1.0F);
      vec4.transform(positionMatrix);
      drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

      vec1 = new Vector4f(xMax, yMin, zMin, 1.0F);
      vec1.transform(positionMatrix);
      vec2 = new Vector4f(xMax, yMin, zMax, 1.0F);
      vec2.transform(positionMatrix);
      vec3 = new Vector4f(xMax, yMax, zMax, 1.0F);
      vec3.transform(positionMatrix);
      vec4 = new Vector4f(xMax, yMax, zMin, 1.0F);
      vec4.transform(positionMatrix);
      drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

      vec1 = new Vector4f(xMin, yMax, zMin, 1.0F);
      vec1.transform(positionMatrix);
      vec2 = new Vector4f(xMin, yMax, zMax, 1.0F);
      vec2.transform(positionMatrix);
      vec3 = new Vector4f(xMax, yMax, zMax, 1.0F);
      vec3.transform(positionMatrix);
      vec4 = new Vector4f(xMax, yMax, zMin, 1.0F);
      vec4.transform(positionMatrix);
      drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

      vec1 = new Vector4f(xMin, yMin, zMin, 1.0F);
      vec1.transform(positionMatrix);
      vec2 = new Vector4f(xMin, yMin, zMax, 1.0F);
      vec2.transform(positionMatrix);
      vec3 = new Vector4f(xMax, yMin, zMax, 1.0F);
      vec3.transform(positionMatrix);
      vec4 = new Vector4f(xMax, yMin, zMin, 1.0F);
      vec4.transform(positionMatrix);
      drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);
   }

   private static void drawClosingBeam(IVertexBuilder builder, Matrix4f positionMatrix, Matrix3f matrixNormalIn, float thickness, double distance, double v1, double v2, float ticks, float r, float g, float b, float alpha) {
      Vector3f vector3f = new Vector3f(0.0f, 1.0f, 0.0f);
      vector3f.transform(matrixNormalIn);

      float xMin = -thickness;
      float xMax = thickness;
      float yMin = -thickness - 0.115f;
      float yMax = thickness - 0.115f;
      float zMin = 0;
      float zMax = (float) distance;

      Vector4f vec1 = new Vector4f(xMin, yMin, zMin, 1.0F);
      vec1.transform(positionMatrix);
      Vector4f vec2 = new Vector4f(0, 0, zMax, 1.0F);
      vec2.transform(positionMatrix);
      Vector4f vec3 = new Vector4f(0, 0, zMax, 1.0F);
      vec3.transform(positionMatrix);
      Vector4f vec4 = new Vector4f(xMin, yMax, zMin, 1.0F);
      vec4.transform(positionMatrix);
      drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

      vec1 = new Vector4f(xMax, yMin, zMin, 1.0F);
      vec1.transform(positionMatrix);
      vec2 = new Vector4f(0, 0, zMax, 1.0F);
      vec2.transform(positionMatrix);
      vec3 = new Vector4f(0, 0, zMax, 1.0F);
      vec3.transform(positionMatrix);
      vec4 = new Vector4f(xMax, yMax, zMin, 1.0F);
      vec4.transform(positionMatrix);
      drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

      vec1 = new Vector4f(xMin, yMax, zMin, 1.0F);
      vec1.transform(positionMatrix);
      vec2 = new Vector4f(0, 0, zMax, 1.0F);
      vec2.transform(positionMatrix);
      vec3 = new Vector4f(0, 0, zMax, 1.0F);
      vec3.transform(positionMatrix);
      vec4 = new Vector4f(xMax, yMax, zMin, 1.0F);
      vec4.transform(positionMatrix);
      drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

      vec1 = new Vector4f(xMin, yMin, zMin, 1.0F);
      vec1.transform(positionMatrix);
      vec2 = new Vector4f(0, 0, zMax, 1.0F);
      vec2.transform(positionMatrix);
      vec3 = new Vector4f(0, 0, zMax, 1.0F);
      vec3.transform(positionMatrix);
      vec4 = new Vector4f(xMax, yMin, zMin, 1.0F);
      vec4.transform(positionMatrix);
      drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);
   }

   private static void drawQuad(IVertexBuilder builder, float v1, float v2, float r, float g, float b, float alpha, Vector3f vector3f, Vector4f vec1, Vector4f vec2, Vector4f vec3, Vector4f vec4) {
      builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
      builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
      builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
      builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
   }
}
