package com.infamous.dungeons_gear.entities;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class IceCloudModel<T extends IceCloudEntity> extends EntityModel<Entity> {
	private final ModelRenderer icecloud;

	public IceCloudModel() {
		textureWidth = 64;
		textureHeight = 64;

		icecloud = new ModelRenderer(this);
		icecloud.setRotationPoint(0.0F, 24.0F, 0.0F);
		icecloud.setTextureOffset(0, 0).addBox(0.0F, -16.0F + 1.0F, -16.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		icecloud.setTextureOffset(0, 0).addBox(-16.0F, -16.0F, -16.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		icecloud.setTextureOffset(0, 0).addBox(-16.0F, -16.0F - 2.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
		icecloud.setTextureOffset(0, 0).addBox(0.0F, -16.0F - 1.0F, 0.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		icecloud.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}