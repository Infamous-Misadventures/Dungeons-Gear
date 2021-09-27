package com.infamous.dungeons_gear.items.armor.models.old_models;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class GuardsArmorModel<T extends LivingEntity> extends BipedModel<T> {
	private final EquipmentSlotType slot;
	private final LivingEntity entity;
	private final ModelRenderer Everything;
	private final ModelRenderer Body;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer Head;

	public GuardsArmorModel(float modelSize, EquipmentSlotType slot, LivingEntity entity) {
		super(modelSize, 0.0F, 64, 64);
		this.slot = slot;
		this.entity = entity;
		texWidth = 64;
		texHeight = 64;

		//Everything = new ModelRenderer(this);
		//Everything.setRotationPoint(0.0F, 24.0F, 0.0F);

		/*
		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		//Everything.addChild(Body);
		//-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F
		Body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		//Everything.addChild(Head);
		//-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F
		Head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		Head.setTextureOffset(0, 0).addBox(-1.0F, -9.0F, 3.7F, 2.0F, 6.0F, 1.0F, -0.2F, false);
		Head.setTextureOffset(0, 0).addBox(-1.0F, -9.0F, -4.7F, 2.0F, 6.0F, 1.0F, -0.2F, false);
		Head.setTextureOffset(33, 1).addBox(-1.0F, -9.0F, -4.3F, 2.0F, 1.0F, 9.0F, -0.2F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		//Everything.addChild(LeftArm);
		//-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F
		LeftArm.setTextureOffset(0, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.2F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		//Everything.addChild(RightArm);
		//-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F
		RightArm.setTextureOffset(0, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.2F, true);
		RightArm.setTextureOffset(16, 32).addBox(-3.0F, 4.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, true);

		 */
		Everything = new ModelRenderer(this);
		Everything.setPos(0.0F, 24.0F, 0.0F);

		boolean testificate =
				this.entity instanceof AbstractIllagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("savageandravage:skeleton_villager")) ||
						this.entity instanceof ZombieVillagerEntity ||
						this.entity instanceof AbstractVillagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("guardvillagers:guard"));

		int testificateHelmetShiftUp = testificate ? 2 : 0;

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 0.0F, 0.0F);
		Everything.addChild(Head);
		Head.texOffs(0, 0).addBox(-4.0F, -26.0F+18.0F - testificateHelmetShiftUp, -4.0F, 8.0F, 8.0F, 8.0F, 1.0F, false);
		Head.texOffs(0, 0).addBox(-1.0F, -27.0F+18.0F - 0.125F + 0.1F - testificateHelmetShiftUp, 3.7F + 1.25F + 0.1F, 2.0F, 6.0F, 1.0F, 0.0F, 1.0F, 0.0F);
		Head.texOffs(0, 0).addBox(-1.0F, -27.0F+18.0F - 0.125F + 0.1F - testificateHelmetShiftUp, -4.7F - 1.25F - 0.1F, 2.0F, 6.0F, 1.0F, 0.0F, 1.0F, 0.0F);
		Head.texOffs(33, 1).addBox(-1.0F, -27.0F+18.0F - 1.0F - 0.01F - testificateHelmetShiftUp, -4.3F, 2.0F, 1.0F, 9.0F, 0.0F, 0.0F, 1.0F);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 0.0F, 0.0F);
		Everything.addChild(Body);
		Body.texOffs(16, 16).addBox(-4.0F, -18.0F+18.0F, -2.0F, 8.0F, 12.0F, 4.0F,  1.0F, 1.0F, 1.0F);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(0.0F, 0.0F, 0.0F);
		Everything.addChild(LeftArm);
		LeftArm.texOffs(0, 32).addBox(4.0F-5.0F, -18.0F+16.0F, -2.0F, 4.0F, 6.0F, 4.0F, 1.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(0.0F, 0.0F, 0.0F);
		Everything.addChild(RightArm);
		RightArm.texOffs(0, 32).addBox(-8.0F+5.0F, -18.0F+16.0F, -2.0F, 4.0F, 6.0F, 4.0F, 1.0F, true);
		RightArm.texOffs(16, 32).addBox(-8.0F+5.0F, -12.0F+16.0F, -2.0F, 3.0F, 2.0F, 4.0F, 0.5F, 0.5F, 0.5F);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
		boolean testificate =
				this.entity instanceof AbstractIllagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("savageandravage:skeleton_villager")) ||
						this.entity instanceof ZombieVillagerEntity ||
						this.entity instanceof AbstractVillagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("guardvillagers:guard"));

		boolean piglin =
				this.entity instanceof PiglinEntity ||
						this.entity instanceof ZombifiedPiglinEntity;



		if (this.slot == EquipmentSlotType.HEAD) {
		/*
			if (piglin) {
				matrixStackIn.push();
				this.piglin_helmet1.copyModelAngles(this.bipedHead);
				if (this.entity.isChild()) {
					matrixStack.scale(0.8F, 0.8F, 0.8F);
					this.piglin_helmet1.setRotationPoint(0.0F, 15.0F, 0.0F);
				}
				this.piglin_helmet1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
				matrixStackIn.pop();

			} else if (testificate) {
				matrixStackIn.push();
				this.illager_helmet1.copyModelAngles(this.bipedHead);
				if (this.entity.isChild()) {
					matrixStackIn.scale(0.8F, 0.8F, 0.8F);
					this.illager_helmet1.setRotationPoint(0.0F, 15.0F, 0.0F);
				}
				this.illager_helmet1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
				matrixStackIn.pop();
			} else {

		 */
				matrixStackIn.pushPose();
				this.Head.copyFrom(this.head);
				if (this.entity.isBaby()) {
					matrixStackIn.scale(0.8F, 0.8F, 0.8F);
					this.Head.setPos(0.0F, 15.0F, 0.0F);
				}
				this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
				matrixStackIn.popPose();
			//}
		}

		if (this.slot == EquipmentSlotType.CHEST) {
			matrixStackIn.pushPose();

			this.Body.copyFrom(this.body);
			this.LeftArm.copyFrom(this.leftArm);
			this.RightArm.copyFrom(this.rightArm);
			if (this.entity.isBaby()) {
				matrixStackIn.scale(0.5F, 0.5F, 0.5F);
				this.Body.setPos(0.0F, 24.0F, 0.0F);
				this.LeftArm.setPos(5.0F, 24.0F, 0.0F);
				this.RightArm.setPos(-5.0F, 24.0F, 0.0F);
			}
			this.LeftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			this.RightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			if (testificate) {
				matrixStackIn.scale(1.0F, 1.0F, 1.3F);
			}
			this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			matrixStackIn.popPose();
		}
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}