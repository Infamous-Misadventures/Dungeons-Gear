package com.infamous.dungeons_gear.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@OnlyIn(Dist.CLIENT)
public class PulsatingGlowLayer<T extends LivingEntity & IAnimatable> extends GeoLayerRenderer<T> {

    public ResourceLocation textureLocation;

    public float pulseSpeed;
    public float pulseAmount;
    public float minimumPulseAmount;

    public PulsatingGlowLayer(IGeoRenderer<T> endermanReplacementRenderer, ResourceLocation textureLocation, float pulseSpeed, float pulseAmount, float minimumPulseAmount) {
        super(endermanReplacementRenderer);
        this.textureLocation = textureLocation;
        this.pulseSpeed = pulseSpeed;
        this.pulseAmount = pulseAmount;
        this.minimumPulseAmount = minimumPulseAmount;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
                       T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        GeoModelProvider<T> geomodel = this.getEntityModel();

        // original speed: 0.045F
        // original amount: 0.25F

        float glow = Math.max(minimumPulseAmount, Mth.cos(ageInTicks * pulseSpeed) * pulseAmount);
        renderModel(geomodel, textureLocation, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0F, glow, glow, glow);
    }

    @Override
    public RenderType getRenderType(ResourceLocation textureLocation) {
        return RenderType.eyes(textureLocation);
    }

}