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
public class GoatGearModel<T extends LivingEntity> extends BipedModel<T> {
	private final EquipmentSlotType slot;
	private final LivingEntity entity;
	private final ModelRenderer Everything;
	private final ModelRenderer Body;
	private final ModelRenderer LeftArm;
	private final ModelRenderer LeftArmArmor_r1;
	private final ModelRenderer LeftArmArmor_r2;
	private final ModelRenderer LeftArmArmor_r3;
	private final ModelRenderer RightArm;
	private final ModelRenderer RightArmArmor_r1;
	private final ModelRenderer RightArmArmor_r2;
	private final ModelRenderer RightArmArmor_r3;
	private final ModelRenderer Head;
	private final ModelRenderer horn1;
	private final ModelRenderer Helmet_r1;
	private final ModelRenderer horn2;
	private final ModelRenderer Helmet_r2;

	public GoatGearModel(float modelSize, EquipmentSlotType slot, LivingEntity entity) {
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
		Head.texOffs(0, 0).addBox(-5.0F, -8.5F - testificateHelmetShiftUp, -5.0F, 10.0F, 9.0F, 10.0F, -0.25F, false);
		Head.texOffs(0, 19).addBox(-3.0F, -10.5F - testificateHelmetShiftUp, -7.75F, 6.0F, 6.0F, 10.0F, 0.0F, false);

		horn1 = new ModelRenderer(this);
		horn1.setPos(2.0F, -9.5F, 2.25F);
		Head.addChild(horn1);


		Helmet_r1 = new ModelRenderer(this);
		Helmet_r1.setPos(0.75F, -0.75F, 0.0F);
		horn1.addChild(Helmet_r1);
		setRotationAngle(Helmet_r1, 0.0F, 1.5708F, 0.0F);
		Helmet_r1.texOffs(22, 3).addBox(-8.0F, -0.25F, -1.75F, 8.0F, 2.0F, 2.0F, 0.0F, false);

		horn2 = new ModelRenderer(this);
		horn2.setPos(-2.0F, -9.5F, 2.25F);
		Head.addChild(horn2);


		Helmet_r2 = new ModelRenderer(this);
		Helmet_r2.setPos(4.25F, -0.75F, 0.0F);
		horn2.addChild(Helmet_r2);
		setRotationAngle(Helmet_r2, 0.0F, 1.5708F, 0.0F);
		Helmet_r2.texOffs(22, 3).addBox(-8.0F, -0.25F, -5.25F, 8.0F, 2.0F, 2.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 0.0F, 0.0F);
		Body.texOffs(32, 24).addBox(-5.0F, 0.25F, -3.0F, 10.0F, 11.0F, 6.0F, 0.24F, false);
		Body.texOffs(29, 42).addBox(-5.0F, 0.0F, -3.5F, 10.0F, 5.0F, 7.0F, 0.0F, false);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-5.0F, 2.0F, 0.0F);

		RightArmArmor_r1 = new ModelRenderer(this);
		RightArmArmor_r1.setPos(0.0F, 0.0F, 0.0F);
		RightArm.addChild(RightArmArmor_r1);
		setRotationAngle(RightArmArmor_r1, 0.0F, 1.5708F, 0.0F);
		RightArmArmor_r1.texOffs(45, 11).addBox(-2.0F, 6.25F, -3.0F, 4.0F, 2.0F, 4.0F, 0.25F, false);

		RightArmArmor_r2 = new ModelRenderer(this);
		RightArmArmor_r2.setPos(0.0F, 0.0F, 0.0F);
		RightArm.addChild(RightArmArmor_r2);
		setRotationAngle(RightArmArmor_r2, 0.0F, 1.5708F, 0.0F);
		RightArmArmor_r2.texOffs(45, 17).addBox(-2.0F, 8.25F, -3.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);

		RightArmArmor_r3 = new ModelRenderer(this);
		RightArmArmor_r3.setPos(0.0F, 0.0F, 0.0F);
		RightArm.addChild(RightArmArmor_r3);
		setRotationAngle(RightArmArmor_r3, 0.0F, 1.5708F, 0.0F);
		RightArmArmor_r3.texOffs(45, 0).addBox(-2.0F, -2.25F, -3.5F, 4.0F, 6.0F, 4.0F, 0.5F, true);

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(5.0F, 2.0F, 0.0F);

		LeftArmArmor_r1 = new ModelRenderer(this);
		LeftArmArmor_r1.setPos(0.0F, 0.0F, 0.0F);
		LeftArm.addChild(LeftArmArmor_r1);
		setRotationAngle(LeftArmArmor_r1, 0.0F, -1.5708F, 0.0F);
		LeftArmArmor_r1.texOffs(45, 11).addBox(-2.0F, 6.25F, -3.0F, 4.0F, 2.0F, 4.0F, 0.25F, true);

		LeftArmArmor_r2 = new ModelRenderer(this);
		LeftArmArmor_r2.setPos(0F, 0F, 0.0F);
		LeftArm.addChild(LeftArmArmor_r2);
		setRotationAngle(LeftArmArmor_r2, 0.0F, -1.5708F, 0.0F);
		LeftArmArmor_r2.texOffs(45, 17).addBox(-2.0F, 8.25F, -3.0F, 4.0F, 3.0F, 4.0F, 0.0F, true);

		LeftArmArmor_r3 = new ModelRenderer(this);
		LeftArmArmor_r3.setPos(0.0F, 0.0F, 0.0F);
		LeftArm.addChild(LeftArmArmor_r3);
		setRotationAngle(LeftArmArmor_r3, 0.0F, -1.5708F, 0.0F);
		LeftArmArmor_r3.texOffs(45, 0).addBox(-2.0F, -2.25F, -3.0F, 4.0F, 6.0F, 4.0F, 0.5F, false);
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