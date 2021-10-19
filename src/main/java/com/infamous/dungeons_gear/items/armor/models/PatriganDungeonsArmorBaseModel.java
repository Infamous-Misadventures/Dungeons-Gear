package com.infamous.dungeons_gear.items.armor.models;

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
public class PatriganDungeonsArmorBaseModel<T extends LivingEntity> extends BipedModel<T> {
	private final EquipmentSlotType slot;
	private final LivingEntity entity;
	protected final ModelRenderer Everything;
	protected final ModelRenderer Head;
	protected final ModelRenderer Body;
	protected final ModelRenderer LeftArm;
	protected final ModelRenderer RightArm;
	protected final ModelRenderer LeftLeg;
	protected final ModelRenderer RightLeg;
	protected final ModelRenderer LeftFoot;
	protected final ModelRenderer RightFoot;

	public PatriganDungeonsArmorBaseModel(float modelSize, EquipmentSlotType slot, LivingEntity entity) {
		super(modelSize, 0.0F, 64, 64);
		this.slot = slot;
		this.entity = entity;
		texWidth = 64;
		texHeight = 64;
		Everything = new ModelRenderer(this);
		Everything.setPos(0.0F, 24.0F, 0.0F);

		getTestificateShiftUp();

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 0.0F, 0.0F);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 0.0F, 0.0F);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-5.0F, 2.0F, 0.0F);
		setRotationAngle(RightArm, 0.0F, 0, 0.0F);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(5.0F, 2.0F, 0.0F);
		setRotationAngle(leftArm, 0.0F, 0, 0.0F);

		RightLeg = new ModelRenderer(this);
		RightLeg.setPos(-5.0F, 2.0F, 0.0F);
		setRotationAngle(RightLeg, 0.0F, 0, 0.0F);

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setPos(5.0F, 2.0F, 0.0F);
		setRotationAngle(LeftLeg, 0.0F, 0, 0.0F);

		RightFoot = new ModelRenderer(this);
		RightFoot.setPos(-5.0F, 2.0F, 0.0F);
		setRotationAngle(RightFoot, 0.0F, 0, 0.0F);

		LeftFoot = new ModelRenderer(this);
		LeftFoot.setPos(5.0F, 2.0F, 0.0F);
		setRotationAngle(LeftFoot, 0.0F, 0, 0.0F);

	}

	protected int getTestificateShiftUp() {
		boolean testificate =
				this.entity instanceof AbstractIllagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("savageandravage:skeleton_villager")) ||
						this.entity instanceof ZombieVillagerEntity ||
						this.entity instanceof AbstractVillagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("guardvillagers:guard"));

		return testificate ? 2 : 0;
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
			handleHead(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		}

		if (this.slot == EquipmentSlotType.CHEST) {
			handleChest(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha, testificate);
		}
	}

	private void handleChest(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, boolean testificate) {
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

	private void handleHead(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		matrixStackIn.pushPose();
		this.Head.copyFrom(this.head);
		if (this.entity.isBaby()) {
			matrixStackIn.scale(0.8F, 0.8F, 0.8F);
			this.Head.setPos(0.0F, 15.0F, 0.0F);
		}
		this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		matrixStackIn.popPose();
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}