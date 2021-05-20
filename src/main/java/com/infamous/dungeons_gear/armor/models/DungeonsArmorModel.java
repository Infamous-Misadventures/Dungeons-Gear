package com.infamous.dungeons_gear.armor.models;

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
public class DungeonsArmorModel<T extends LivingEntity> extends BipedModel<T> {
    public static final ResourceLocation SKELETON_VILLAGER_RESOURCE = new ResourceLocation("savageandravage:skeleton_villager");
    public static final ResourceLocation GUARD_VILLAGER_RESOURCE = new ResourceLocation("guardvillagers:guard");
    private final EquipmentSlotType slot;
    private final LivingEntity entity;

    // helmet
    public ModelRenderer armorHeadTestificate;
    public ModelRenderer armorHead;

    // chestplate
    public ModelRenderer armorBody;
    public ModelRenderer armorRightArm;
    public ModelRenderer armorLeftArm;

    // leggings
    public ModelRenderer armorRightLeg;
    public ModelRenderer armorLeftLeg;

    // boots
    public ModelRenderer armorRightFoot;
    public ModelRenderer armorLeftFoot;

    public static final int TESTIFICATE_HELMET_SHIFT = 2;

    protected DungeonsArmorModel(EquipmentSlotType slot, LivingEntity entity, float modelSize, float yOffsetIn, int textureWidthIn, int textureHeightIn) {
        super(modelSize, yOffsetIn, textureWidthIn, textureHeightIn);
        this.slot = slot;
        this.entity = entity;
        this.armorHeadTestificate = new ModelRenderer(this, 0, 0);
        this.armorHeadTestificate.addBox(-4.0F, -8.0F - TESTIFICATE_HELMET_SHIFT, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
        this.armorHeadTestificate.copyModelAngles(this.bipedHead);

        this.armorHead = new ModelRenderer(this, 0, 0);
        this.armorHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
        this.armorHead.setRotationPoint(0.0F, 0.0F + yOffsetIn, 0.0F);

        this.armorBody = new ModelRenderer(this, 16, 16);
        this.armorBody.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize);
        this.armorBody.setRotationPoint(0.0F, 0.0F + yOffsetIn, 0.0F);

        this.armorRightArm = new ModelRenderer(this, 40, 16);
        this.armorRightArm.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
        this.armorRightArm.setRotationPoint(-5.0F, 2.0F + yOffsetIn, 0.0F);

        this.armorLeftArm = new ModelRenderer(this, 40, 16);
        this.armorLeftArm.mirror = true;
        this.armorLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
        this.armorLeftArm.setRotationPoint(5.0F, 2.0F + yOffsetIn, 0.0F);

        this.armorRightLeg = new ModelRenderer(this, 0, 16);
        this.armorRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
        this.armorRightLeg.setRotationPoint(-1.9F, 12.0F + yOffsetIn, 0.0F);

        this.armorLeftLeg = new ModelRenderer(this, 0, 16);
        this.armorLeftLeg.mirror = true;
        this.armorLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
        this.armorLeftLeg.setRotationPoint(1.9F, 12.0F + yOffsetIn, 0.0F);

        this.armorRightFoot = new ModelRenderer(this, 0, 16);
        this.armorRightFoot.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
        this.armorRightFoot.setRotationPoint(-1.9F, 12.0F + yOffsetIn, 0.0F);

        this.armorLeftFoot = new ModelRenderer(this, 0, 16);
        this.armorLeftFoot.mirror = true;
        this.armorLeftFoot.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
        this.armorLeftFoot.setRotationPoint(1.9F, 12.0F + yOffsetIn, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if(this.slot == EquipmentSlotType.HEAD){
            handleHelmet(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        if(this.slot == EquipmentSlotType.CHEST){
            handleChestplate(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        if(this.slot == EquipmentSlotType.LEGS){
            handleLeggings(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        if(this.slot == EquipmentSlotType.FEET){
            handleBoots(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    public void handleHelmet(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
        matrixStackIn.push();
        this.armorHead.copyModelAngles(this.bipedHead);
        if (this.entity.isChild()) {
            matrixStackIn.scale(0.8F, 0.8F, 0.8F);
            this.armorHead.setRotationPoint(0.0F, 15.0F, 0.0F);
        }
        if(this.isWornByTestificate()){
            this.armorHeadTestificate.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        else{
            this.armorHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        matrixStackIn.pop();
    }

    public void handleChestplate(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
        matrixStackIn.push();
        this.armorBody.copyModelAngles(this.bipedBody);
        this.armorLeftArm.copyModelAngles(this.bipedLeftArm);
        this.armorRightArm.copyModelAngles(this.bipedRightArm);
        if (this.entity.isChild()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            this.armorBody.setRotationPoint(0.0F, 24.0F, 0.0F);
            this.armorLeftArm.setRotationPoint(5.0F, 24.0F, 0.0F);
            this.armorRightArm.setRotationPoint(-5.0F, 24.0F, 0.0F);
        }
        this.armorLeftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.armorRightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.isWornByTestificate()) {
            matrixStackIn.scale(1.0F, 1.0F, 1.3F);
        }
        this.armorBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    public void handleLeggings(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
        matrixStackIn.push();
        matrixStackIn.scale(1.01F, 1.0F, 1.01F);
        this.armorLeftLeg.copyModelAngles(this.bipedLeftLeg);
        this.armorRightLeg.copyModelAngles(this.bipedRightLeg);
        if (this.entity.isChild()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            this.armorLeftLeg.setRotationPoint(2.0F, 36.0F, 0.0F);
            this.armorRightLeg.setRotationPoint(-2.0F, 36.0F, 0.0F);
        }
        this.armorLeftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.armorRightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.isWornByTestificate()) {
            matrixStackIn.scale(1.0F, 1.0F, 1.32F);
        }
        matrixStackIn.pop();
    }

    public void handleBoots(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha){
        matrixStackIn.push();
        matrixStackIn.scale(1.05F, 1.0F, 1.05F);

        this.armorLeftFoot.copyModelAngles(this.bipedLeftLeg);
        this.armorRightFoot.copyModelAngles(this.bipedRightLeg);
        if (this.entity.isChild()) {
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            this.armorLeftFoot.setRotationPoint(2.0F, 37.0F, 0.0F);
            this.armorRightFoot.setRotationPoint(-2.0F, 37.0F, 0.0F);
        }
        this.armorLeftFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.armorRightFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    public ModelRenderer getTestificateHelmetModel(){
        return this.armorHeadTestificate;
    }

    public boolean isWornByTestificate(){
        return this.entity instanceof AbstractIllagerEntity ||
                this.entity.getType() == ForgeRegistries.ENTITIES.getValue(SKELETON_VILLAGER_RESOURCE) ||
                this.entity instanceof ZombieVillagerEntity ||
                this.entity instanceof AbstractVillagerEntity ||
                this.entity.getType() == ForgeRegistries.ENTITIES.getValue(GUARD_VILLAGER_RESOURCE);
    }
}
