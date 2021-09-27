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
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class HuntersArmorModel<T extends LivingEntity> extends BipedModel<T> {
	private final EquipmentSlotType slot;
	private final LivingEntity entity;
	private final ModelRenderer Everything;
	private final ModelRenderer Body;
	private final ModelRenderer Neckpiece;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;

	public HuntersArmorModel(float modelSize, EquipmentSlotType slot, LivingEntity entity) {
		super(modelSize, 0.0F, 64, 64);
		this.slot = slot;
		this.entity = entity;
		texWidth = 64;
		texHeight = 64;

		Everything = new ModelRenderer(this);
		Everything.setPos(0.0F, 24.0F, 0.0F);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, -24.0F + 24.0F, 0.0F);
		Everything.addChild(Body);
		Body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 1.0F, false);

		Neckpiece = new ModelRenderer(this);
		Neckpiece.setPos(0.0F, 25.0F, 0.0F);
		Body.addChild(Neckpiece);
		Neckpiece.texOffs(38, 0).addBox(-6.0F, -26.0F, -4.0F, 3.0F, 3.0F, 8.0F, 0.2F, true);
		Neckpiece.texOffs(43, 5).addBox(-3.0F, -26.0F, 1.0F, 6.0F, 3.0F, 3.0F, 0.2F, true);
		Neckpiece.texOffs(38, 0).addBox(3.0F, -26.0F, -4.0F, 3.0F, 3.0F, 8.0F, 0.2F, false);
		

		LeftArm = new ModelRenderer(this);
		LeftArm.setPos(5.0F, -22.0F, 0.0F);
		Everything.addChild(LeftArm);
		LeftArm.texOffs(24, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, 1.0F, true);

		RightArm = new ModelRenderer(this);
		RightArm.setPos(-5.0F, -22.0F, 0.0F);
		Everything.addChild(RightArm);
		RightArm.texOffs(24, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, 1.0F, false);

	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		//ImmutableList.of(this.Neck, this.Torso, this.RightShoulderPad, this.LeftShoulderPad).forEach((modelRenderer) -> {
		//modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		//});

		boolean testificate =
				this.entity instanceof AbstractIllagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("savageandravage:skeleton_villager")) ||
						this.entity instanceof ZombieVillagerEntity ||
						this.entity instanceof AbstractVillagerEntity ||
						this.entity.getType() == ForgeRegistries.ENTITIES.getValue(new ResourceLocation("guardvillagers:guard"));

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