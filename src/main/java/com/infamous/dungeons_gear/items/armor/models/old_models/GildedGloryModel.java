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
public class GildedGloryModel<T extends LivingEntity> extends BipedModel<T> {
	private final EquipmentSlotType slot;
	private final LivingEntity entity;
	private final ModelRenderer Everything;
	private final ModelRenderer Body;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer Head;
	private final ModelRenderer Helmet_r1;
	private final ModelRenderer Helmet_r2;
	private final ModelRenderer Helmet_r3;

	public GildedGloryModel(float modelSize, EquipmentSlotType slot, LivingEntity entity) {
		super(modelSize, 0.0F, 64, 64);
		this.slot = slot;
		this.entity = entity;
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


		Head = new ModelRenderer(this);
		Head.setPos(0.0F, 0.0F, 0.0F);
		Head.texOffs(0, 0).addBox(-5.0F, -8.25F - testificateHelmetShiftUp, -4.5F, 10.0F, 8.0F, 9.0F, 0.0F, false);

		Helmet_r1 = new ModelRenderer(this);
		Helmet_r1.setPos(-5.0F, -8.24F, 2.02F);
		Head.addChild(Helmet_r1);
		setRotationAngle(Helmet_r1, 0.0F, 1.5708F, -1.5708F);
		Helmet_r1.texOffs(4, 25).addBox(-4.5F, 0.0F - testificateHelmetShiftUp, -1.0F, 9.0F, 0.0F, 2.0F, 0.0F, true);

		Helmet_r2 = new ModelRenderer(this);
		Helmet_r2.setPos(5.0F, -8.49F, 1.77F);
		Head.addChild(Helmet_r2);
		setRotationAngle(Helmet_r2, 0.0F, 1.5708F, -1.5708F);
		Helmet_r2.texOffs(4, 21).addBox(-4.75F, 0.01F - testificateHelmetShiftUp, -1.25F, 9.0F, 0.0F, 2.0F, 0.0F, false);

		Helmet_r3 = new ModelRenderer(this);
		Helmet_r3.setPos(0.0F, -1.75F, -5.0F);
		Head.addChild(Helmet_r3);
		setRotationAngle(Helmet_r3, 0.0F, 0.0F, -1.5708F);
		Helmet_r3.texOffs(29, 2).addBox(-0.26F, -1.0F - testificateHelmetShiftUp, -0.25F, 7.0F, 2.0F, 1.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 0.0F, 0.0F);
		Body.texOffs(27, 26).addBox(-5.0F, -0.25F, -3.5F, 10.0F, 12.0F, 7.0F, -0.24F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-4.25F, 1.0F, 0.0F);
		setRotationAngle(RightArm, 0.0F, 1.5708F, 0.0F);
		RightArm.texOffs(43, 3).addBox(-2.0F, -1.0F, -3.75F, 4.0F, 4.0F, 4.0F, 0.5F, false);
		RightArm.texOffs(43, 14).addBox(-2.0F, 8.0F, -3.75F, 4.0F, 4.0F, 4.0F, 0.25F, false);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(4.25F, 1.0F, 0.0F);
		setRotationAngle(LeftArm, 0.0F, -1.5708F, 0.0F);
		LeftArm.texOffs(43, 3).addBox(-2.0F, -1.0F, -3.75F, 4.0F, 4.0F, 4.0F, 0.5F, true);
		LeftArm.texOffs(43, 14).addBox(-2.0F, 8.0F, -3.75F, 4.0F, 4.0F, 4.0F, 0.25F, true);
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
				matrixStackIn.pushPose();
				this.Head.copyFrom(this.head);
				if (this.entity.isBaby()) {
					matrixStackIn.scale(0.8F, 0.8F, 0.8F);
					this.Head.setPos(0.0F, 15.0F, 0.0F);
				}
				this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
				matrixStackIn.popPose();
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