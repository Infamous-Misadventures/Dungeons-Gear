package com.infamous.dungeons_gear.client.renderer;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.entities.ArtifactBeamEntity;
import com.infamous.dungeons_gear.items.artifacts.beacon.BeamColor;
import com.infamous.dungeons_gear.items.artifacts.beacon.MyRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeamEntityRenderer<T extends ArtifactBeamEntity> extends EntityRenderer<T> {
    @Override
    public ResourceLocation getTextureLocation(T p_110775_1_) {
        return new ResourceLocation(DungeonsGear.MODID + ":textures/misc/beacon_beam_core.png");
    }

    public BeamEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        double distance = pEntity.beamTraceDistance(ArtifactBeamEntity.MAX_RAYTRACE_DISTANCE, 1.0f, false);

        float speedModifier = -0.02f;

        drawBeams(distance, pEntity, pPartialTicks, speedModifier, pMatrixStack);
    }

    private static void drawBeams(double distance, ArtifactBeamEntity entity, float ticks, float speedModifier, PoseStack pMatrixStack) {
        VertexConsumer builder;
        long gameTime = entity.level.getGameTime();
        double v = gameTime * speedModifier;
        float additiveThickness = (entity.getBeamWidth() * 1.75f) * calculateLaserFlickerModifier(gameTime);

        BeamColor beamColor = entity.getBeamColor();
        float beam1r = beamColor.getRedValue() / 255f;
        float beam1g = beamColor.getGreenValue() / 255f;
        float beam1b = beamColor.getBlueValue() / 255f;
        float beam2r = beamColor.getInnerRedValue() / 255f;
        float beam2g = beamColor.getInnerGreenValue() / 255f;
        float beam2b = beamColor.getInnerBlueValue() / 255f;
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();

        pMatrixStack.pushPose();
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((Mth.lerp(ticks, boundDegrees(-entity.getYRot()), boundDegrees(-entity.yRotO)))));
        pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(ticks, boundDegrees(entity.getXRot()), boundDegrees(entity.xRotO))));

        PoseStack.Pose matrixstack$entry = pMatrixStack.last();
        Matrix3f matrixNormal = matrixstack$entry.normal();
        Matrix4f positionMatrix = matrixstack$entry.pose();

        //additive laser beam
        builder = buffer.getBuffer(MyRenderType.BEACON_BEAM_GLOW);
        drawClosingBeam(builder, positionMatrix, matrixNormal, additiveThickness, distance / 10, 0.5, 1, ticks, beam2r, beam2g, beam2b, 0.9f);

        //main laser, colored part
        builder = buffer.getBuffer(MyRenderType.BEACON_BEAM_MAIN);
        drawBeam(builder, positionMatrix, matrixNormal, entity.getBeamWidth(), distance, v, v + distance * 1.5, ticks, beam2r, beam2g, beam2b, 0.7f);

        //core
        builder = buffer.getBuffer(MyRenderType.BEACON_BEAM_CORE);
        drawBeam(builder, positionMatrix, matrixNormal, entity.getBeamWidth() * 0.7f, distance, v, v + distance * 1.5, ticks, beam1r, beam1g, beam1b, 1f);
        pMatrixStack.popPose();
        buffer.endBatch();
    }

    private static float boundDegrees(float v) {
        // Time wasted trying to get beams not jumping around due to networking: TOO DAMN LONG!
        return (v % 360 + 360) % 360;
    }

    private static float calculateLaserFlickerModifier(long gameTime) {
        return 0.9f + 0.1f * Mth.sin(gameTime * 0.99f) * Mth.sin(gameTime * 0.3f) * Mth.sin(gameTime * 0.1f);
    }

    private static void drawBeam(VertexConsumer builder, Matrix4f positionMatrix, Matrix3f matrixNormalIn, float thickness, double distance, double v1, double v2, float ticks, float r, float g, float b, float alpha) {
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

    private static void drawClosingBeam(VertexConsumer builder, Matrix4f positionMatrix, Matrix3f matrixNormalIn, float thickness, double distance, double v1, double v2, float ticks, float r, float g, float b, float alpha) {
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

    private static void drawQuad(VertexConsumer builder, float v1, float v2, float r, float g, float b, float alpha, Vector3f vector3f, Vector4f vec1, Vector4f vec2, Vector4f vec3, Vector4f vec4) {
        builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
    }
}
