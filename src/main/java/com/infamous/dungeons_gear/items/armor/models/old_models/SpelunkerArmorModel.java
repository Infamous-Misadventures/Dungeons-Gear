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
import net.minecraftforge.registries.ForgeRegistries;

public class SpelunkerArmorModel<T extends LivingEntity> extends BipedModel<T> {
	private final EquipmentSlotType slot;
	private final LivingEntity entity;
	private boolean unique;
	private final ModelRenderer Everything;
	private final ModelRenderer CaveCrawlerHead;
	private final ModelRenderer CaveCrawlerTrim;
	private final ModelRenderer SpelunkerArmorHead;
	private final ModelRenderer Body;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;

	public SpelunkerArmorModel(float modelSize, EquipmentSlotType slot, LivingEntity entity, boolean unique) {
		super(modelSize, 0.0F, 64, 64);
		this.slot = slot;
		this.entity = entity;
		this.unique = unique;
		texWidth = 64;
		texHeight = 64;

		Everything = new ModelRenderer(this);
		Everything.setPos(0.0F, 24.0F, 0.0F);


		boolean testificate =
				this.entity instanceof AbstractIllagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("savageandravage:skeleton_villager")) ||
						this.entity instanceof ZombieVillagerEntity ||
						this.entity instanceof AbstractVillagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("guardvillagers:guard"));

		int testificateHelmetShiftUp = testificate ? 2 : 0;

		// Cave Crawler Head
		CaveCrawlerHead = new ModelRenderer(this);
		CaveCrawlerHead.setPos(0.0F, -24.0F, 0.0F);
		Everything.addChild(CaveCrawlerHead);
		CaveCrawlerHead.texOffs(32, 0).addBox(-4.0F, -10.0F - testificateHelmetShiftUp, -4.0F, 8.0F, 4.0F, 8.0F, 1.0F, false);
		CaveCrawlerHead.texOffs(40, 12).addBox(-1.0F, -14.0F - testificateHelmetShiftUp, -7.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		CaveCrawlerHead.texOffs(48, 18).addBox(-0.5F, -16.0F - testificateHelmetShiftUp, -6.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		CaveCrawlerTrim = new ModelRenderer(this);
		CaveCrawlerTrim.setPos(0.0F, -1.0F, 0.0F);
		CaveCrawlerHead.addChild(CaveCrawlerTrim);
		CaveCrawlerTrim.texOffs(42, 12).addBox(4.0F, -5.0F - testificateHelmetShiftUp, -5.0F, 1.0F, 1.0F, 10.0F, 0.5F, false);
		CaveCrawlerTrim.texOffs(42, 12).addBox(-5.0F, -5.0F - testificateHelmetShiftUp, -5.0F, 1.0F, 1.0F, 10.0F, 0.5F, false);
		CaveCrawlerTrim.texOffs(46, 21).addBox(-4.0F, -5.0F - testificateHelmetShiftUp, -5.0F, 8.0F, 1.0F, 1.0F, 0.5F, false);
		CaveCrawlerTrim.texOffs(46, 21).addBox(-4.0F, -5.0F - testificateHelmetShiftUp, 4.0F, 8.0F, 1.0F, 1.0F, 0.5F, false);

		// Spelunker Armor head
		SpelunkerArmorHead = new ModelRenderer(this);
		SpelunkerArmorHead.setPos(0.0F, -25.0F, 0.0F);
		Everything.addChild(SpelunkerArmorHead);
		SpelunkerArmorHead.texOffs(0, 52).addBox(4.0F, -8.0F - testificateHelmetShiftUp, -5.0F, 1.0F, 2.0F, 10.0F, 0.0F, false);
		SpelunkerArmorHead.texOffs(0, 52).addBox(-5.0F, -8.0F - testificateHelmetShiftUp, -5.0F, 1.0F, 2.0F, 10.0F, 0.0F, false);
		SpelunkerArmorHead.texOffs(0, 52).addBox(-1.0F, -10.0F - testificateHelmetShiftUp, -5.0F, 2.0F, 2.0F, 10.0F, 0.0F, false);
		SpelunkerArmorHead.texOffs(0, 61).addBox(-4.0F, -8.0F - testificateHelmetShiftUp, -5.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
		SpelunkerArmorHead.texOffs(0, 61).addBox(-4.0F, -8.0F - testificateHelmetShiftUp, 4.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);
		SpelunkerArmorHead.texOffs(24, 54).addBox(-1.0F, -15.0F - testificateHelmetShiftUp, -7.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		SpelunkerArmorHead.texOffs(10, 47).addBox(-0.5F, -17.0F - testificateHelmetShiftUp, -6.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, -24.0F, 0.0F);
		Everything.addChild(Body);
		Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 1.0F, false);
		Body.texOffs(0, 16).addBox(-4.0F, 8.0F, -2.0F, 8.0F, 4.0F, 4.0F, 1.2F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(5.0F, -22.0F, 0.0F);
		Everything.addChild(RightArm);
		RightArm.texOffs(24, 12).addBox(-1.0F - 2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, 1.0F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(-5.0F, -22.0F, 0.0F);
		Everything.addChild(LeftArm);
		LeftArm.texOffs(24, 12).addBox(-3.0F + 2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, 1.0F, true);
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
			if(this.unique){
				this.CaveCrawlerHead.copyFrom(this.head);
				if (this.entity.isBaby()) {
					matrixStackIn.scale(0.8F, 0.8F, 0.8F);
					this.CaveCrawlerHead.setPos(0.0F, 15.0F, 0.0F);
				}
				this.CaveCrawlerHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
				matrixStackIn.popPose();
			}
			else{
				this.SpelunkerArmorHead.copyFrom(this.head);
				if (this.entity.isBaby()) {
					matrixStackIn.scale(0.8F, 0.8F, 0.8F);
					this.SpelunkerArmorHead.setPos(0.0F, 15.0F, 0.0F);
				}
				this.SpelunkerArmorHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
				matrixStackIn.popPose();
			}
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