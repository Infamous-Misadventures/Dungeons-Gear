package com.infamous.dungeons_gear.artifacts.corruptedbeacon;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractBeamRenderer<T extends AbstractBeamEntity> extends EntityRenderer<T> {
    public AbstractBeamRenderer(EntityRendererManager p_i46193_1_) {
        super(p_i46193_1_);
    }

    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
        int lvt_7_1_ = 0;
        float lvt_8_1_ = 0.0F;
        float lvt_9_1_ = 0.5F;
        float lvt_10_1_ = 0.0F;
        float lvt_11_1_ = 0.15625F;
        float lvt_12_1_ = 0.0F;
        float lvt_13_1_ = 0.15625F;
        float lvt_14_1_ = 0.15625F;
        float lvt_15_1_ = 0.3125F;
        float lvt_16_1_ = 0.05625F;
        float lvt_17_1_ = (float)entityIn.beamShake - partialTicks;
        if (lvt_17_1_ > 0.0F) {
            float lvt_18_1_ = -MathHelper.sin(lvt_17_1_ * 3.0F) * lvt_17_1_;
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(lvt_18_1_));
        }

        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(45.0F));
        matrixStackIn.scale(0.05625F, 0.05625F, 0.05625F);
        matrixStackIn.translate(-4.0D, 0.0D, 0.0D);
        IVertexBuilder vertexBuilder = bufferIn.getBuffer(RenderType.getEntityCutout(this.getEntityTexture(entityIn)));
        MatrixStack.Entry matrixStackInLast = matrixStackIn.getLast();
        Matrix4f matrix = matrixStackInLast.getMatrix();
        Matrix3f normal = matrixStackInLast.getNormal();
        this.drawVertex(matrix, normal, vertexBuilder, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix, normal, vertexBuilder, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix, normal, vertexBuilder, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix, normal, vertexBuilder, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
        this.drawVertex(matrix, normal, vertexBuilder, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix, normal, vertexBuilder, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix, normal, vertexBuilder, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
        this.drawVertex(matrix, normal, vertexBuilder, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

        for(int i = 0; i < 4; ++i) {
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90.0F));
            this.drawVertex(matrix, normal, vertexBuilder, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix, normal, vertexBuilder, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix, normal, vertexBuilder, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
            this.drawVertex(matrix, normal, vertexBuilder, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
        }

        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public void drawVertex(Matrix4f matrix4f, Matrix3f matrix3f, IVertexBuilder vertexBuilder, int p_229039_4_, int p_229039_5_, int p_229039_6_, float p_229039_7_, float p_229039_8_, int p_229039_9_, int p_229039_10_, int p_229039_11_, int p_229039_12_) {
        vertexBuilder.pos(matrix4f, (float)p_229039_4_, (float)p_229039_5_, (float)p_229039_6_).color(255, 255, 255, 255).tex(p_229039_7_, p_229039_8_).overlay(OverlayTexture.NO_OVERLAY).lightmap(p_229039_12_).normal(matrix3f, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_).endVertex();
    }
}
