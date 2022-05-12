package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.client.event.RenderWorldLastEvent;

// borrowed from direwolf20's MiningGadget mod
public class BeaconBeamRenderer {
    public static void renderBeam(RenderWorldLastEvent event, PlayerEntity player, float ticks, ItemStack stack) {
        double range = AbstractBeaconItem.RAYTRACE_DISTANCE;

        Vector3d playerPos = player.getEyePosition(ticks);
        RayTraceResult trace = AbstractBeaconItem.beamTrace(player, range, 1.0f, false);

        float speedModifier = -0.02f;

        BeaconBeamColor beaconBeamColor = AbstractBeaconItem.getBeaconBeamColor(stack);
        if(beaconBeamColor != null){
            drawBeams(event, playerPos, trace, beaconBeamColor, 0.04f, player, ticks, speedModifier);
        }
    }

    private static void drawBeams(RenderWorldLastEvent event, Vector3d from, RayTraceResult trace, BeaconBeamColor beaconBeamColor, float thickness, PlayerEntity player, float ticks, float speedModifier) {
        IVertexBuilder builder;
        double distance = Math.max(1, from.subtract(trace.getLocation()).length());
        long gameTime = player.level.getGameTime();
        double v = gameTime * speedModifier;
        float additiveThickness = (thickness * 1.75f) * calculateLaserFlickerModifier(gameTime);

        float beam1r = beaconBeamColor.getRedValue() / 255f;
        float beam1g = beaconBeamColor.getGreenValue() / 255f;
        float beam1b = beaconBeamColor.getBlueValue() / 255f;
        float beam2r = beaconBeamColor.getInnerRedValue() / 255f;
        float beam2g = beaconBeamColor.getInnerGreenValue() / 255f;
        float beam2b = beaconBeamColor.getInnerBlueValue() / 255f;

        Vector3d view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();

        MatrixStack matrix = event.getMatrixStack();

        matrix.pushPose();

        matrix.translate(-view.x(), -view.y(), -view.z());
        matrix.translate(from.x, from.y, from.z);
        matrix.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(ticks, -player.yRot, -player.yRotO)));
        matrix.mulPose(Vector3f.XP.rotationDegrees(MathHelper.lerp(ticks, player.xRot, player.xRotO)));

        MatrixStack.Entry matrixstack$entry = matrix.last();
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
        matrix.popPose();
        buffer.endBatch();
    }

    private static float calculateLaserFlickerModifier(long gameTime) {
        return 0.9f + 0.1f * MathHelper.sin(gameTime * 0.99f) * MathHelper.sin(gameTime * 0.3f) * MathHelper.sin(gameTime * 0.1f);
    }

    private static void drawBeam(IVertexBuilder builder, Matrix4f positionMatrix, Matrix3f matrixNormalIn, float thickness, double distance, double v1, double v2, float ticks, float r, float g, float b, float alpha) {
        Vector3f vector3f = new Vector3f(0.0f, 1.0f, 0.0f);
        vector3f.transform(matrixNormalIn);
        ClientPlayerEntity player = Minecraft.getInstance().player;
        float f = 0;
        if (player != null) {
            f = (MathHelper.lerp(ticks, player.xRotO, player.xRot) - MathHelper.lerp(ticks, player.xBobO, player.xBob));
        }
        float f1 = 0;
        if (player != null) {
            f1 = (MathHelper.lerp(ticks, player.yRotO, player.yRot) - MathHelper.lerp(ticks, player.yBobO, player.yBob));
        }
        float movementXOffset = (f1 / 750);
        float movementYOffset = (f / 750);

        float xMin = -thickness;
        float xMax = thickness;
        float yMin = -thickness - 0.115f;
        float yMax = thickness - 0.115f;
        float zMin = 0;
        float zMax = (float) distance;
        if (player != null) {
            zMin = 0.65f + (1 - player.getFieldOfViewModifier());
        }

        Vector4f vec1 = new Vector4f(xMin + movementXOffset, yMin + movementYOffset, zMin, 1.0F);
        vec1.transform(positionMatrix);
        Vector4f vec2 = new Vector4f(xMin, yMin, zMax, 1.0F);
        vec2.transform(positionMatrix);
        Vector4f vec3 = new Vector4f(xMin, yMax, zMax, 1.0F);
        vec3.transform(positionMatrix);
        Vector4f vec4 = new Vector4f(xMin + movementXOffset, yMax + movementYOffset, zMin, 1.0F);
        vec4.transform(positionMatrix);
        drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

        vec1 = new Vector4f(xMax + movementXOffset, yMin + movementYOffset, zMin, 1.0F);
        vec1.transform(positionMatrix);
        vec2 = new Vector4f(xMax, yMin, zMax, 1.0F);
        vec2.transform(positionMatrix);
        vec3 = new Vector4f(xMax, yMax, zMax, 1.0F);
        vec3.transform(positionMatrix);
        vec4 = new Vector4f(xMax + movementXOffset, yMax + movementYOffset, zMin, 1.0F);
        vec4.transform(positionMatrix);
        drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

        vec1 = new Vector4f(xMin + movementXOffset, yMax + movementYOffset, zMin, 1.0F);
        vec1.transform(positionMatrix);
        vec2 = new Vector4f(xMin, yMax, zMax, 1.0F);
        vec2.transform(positionMatrix);
        vec3 = new Vector4f(xMax, yMax, zMax, 1.0F);
        vec3.transform(positionMatrix);
        vec4 = new Vector4f(xMax + movementXOffset, yMax + movementYOffset, zMin, 1.0F);
        vec4.transform(positionMatrix);
        drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

        vec1 = new Vector4f(xMin + movementXOffset, yMin + movementYOffset, zMin, 1.0F);
        vec1.transform(positionMatrix);
        vec2 = new Vector4f(xMin, yMin, zMax, 1.0F);
        vec2.transform(positionMatrix);
        vec3 = new Vector4f(xMax, yMin, zMax, 1.0F);
        vec3.transform(positionMatrix);
        vec4 = new Vector4f(xMax + movementXOffset, yMin + movementYOffset, zMin, 1.0F);
        vec4.transform(positionMatrix);
        drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);
    }

    private static void drawClosingBeam(IVertexBuilder builder, Matrix4f positionMatrix, Matrix3f matrixNormalIn, float thickness, double distance, double v1, double v2, float ticks, float r, float g, float b, float alpha) {
        Vector3f vector3f = new Vector3f(0.0f, 1.0f, 0.0f);
        vector3f.transform(matrixNormalIn);
        ClientPlayerEntity player = Minecraft.getInstance().player;
        float f = 0;
        if (player != null) {
            f = (MathHelper.lerp(ticks, player.xRotO, player.xRot) - MathHelper.lerp(ticks, player.xBobO, player.xBob));
        }
        float f1 = 0;
        if (player != null) {
            f1 = (MathHelper.lerp(ticks, player.yRotO, player.yRot) - MathHelper.lerp(ticks, player.yBobO, player.yBob));
        }
        float movementXOffset = (f1 / 750);
        float movementYOffset = (f / 750);

        float xMin = -thickness;
        float xMax = thickness;
        float yMin = -thickness - 0.115f;
        float yMax = thickness - 0.115f;
        float zMin = 0;
        float zMax = (float) distance;
        if (player != null) {
            zMin = 0.65f + (1 - player.getFieldOfViewModifier());
        }

        Vector4f vec1 = new Vector4f(xMin + movementXOffset, yMin + movementYOffset, zMin, 1.0F);
        vec1.transform(positionMatrix);
        Vector4f vec2 = new Vector4f(0, 0, zMax, 1.0F);
        vec2.transform(positionMatrix);
        Vector4f vec3 = new Vector4f(0, 0, zMax, 1.0F);
        vec3.transform(positionMatrix);
        Vector4f vec4 = new Vector4f(xMin + movementXOffset, yMax + movementYOffset, zMin, 1.0F);
        vec4.transform(positionMatrix);
        drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

        vec1 = new Vector4f(xMax + movementXOffset, yMin + movementYOffset, zMin, 1.0F);
        vec1.transform(positionMatrix);
        vec2 = new Vector4f(0, 0, zMax, 1.0F);
        vec2.transform(positionMatrix);
        vec3 = new Vector4f(0, 0, zMax, 1.0F);
        vec3.transform(positionMatrix);
        vec4 = new Vector4f(xMax + movementXOffset, yMax + movementYOffset, zMin, 1.0F);
        vec4.transform(positionMatrix);
        drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

        vec1 = new Vector4f(xMin + movementXOffset, yMax + movementYOffset, zMin, 1.0F);
        vec1.transform(positionMatrix);
        vec2 = new Vector4f(0, 0, zMax, 1.0F);
        vec2.transform(positionMatrix);
        vec3 = new Vector4f(0, 0, zMax, 1.0F);
        vec3.transform(positionMatrix);
        vec4 = new Vector4f(xMax + movementXOffset, yMax + movementYOffset, zMin, 1.0F);
        vec4.transform(positionMatrix);
        drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);

        vec1 = new Vector4f(xMin + movementXOffset, yMin + movementYOffset, zMin, 1.0F);
        vec1.transform(positionMatrix);
        vec2 = new Vector4f(0, 0, zMax, 1.0F);
        vec2.transform(positionMatrix);
        vec3 = new Vector4f(0, 0, zMax, 1.0F);
        vec3.transform(positionMatrix);
        vec4 = new Vector4f(xMax + movementXOffset, yMin + movementYOffset, zMin, 1.0F);
        vec4.transform(positionMatrix);
        drawQuad(builder, (float) v1, (float) v2, r, g, b, alpha, vector3f, vec1, vec2, vec3, vec4);
    }

    private static void drawQuad(IVertexBuilder builder, float v1, float v2, float r, float g, float b, float alpha, Vector3f vector3f, Vector4f vec1, Vector4f vec2, Vector4f vec3, Vector4f vec4) {
        builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        //Rendering a 2nd time to allow you to see both sides in multiplayer, shouldn't be necessary with culling disabled but here we are....
        /*builder.vertex(vec1.x(), vec1.y(), vec1.z(), r, g, b, alpha, 1, v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec2.x(), vec2.y(), vec2.z(), r, g, b, alpha, 1, v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec3.x(), vec3.y(), vec3.z(), r, g, b, alpha, 0, v2, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());
        builder.vertex(vec4.x(), vec4.y(), vec4.z(), r, g, b, alpha, 0, v1, OverlayTexture.NO_OVERLAY, 15728880, vector3f.x(), vector3f.y(), vector3f.z());*/
    }
}
