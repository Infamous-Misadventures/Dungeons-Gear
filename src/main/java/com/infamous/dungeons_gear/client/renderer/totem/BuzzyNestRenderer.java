package com.infamous.dungeons_gear.client.renderer.totem;

import com.infamous.dungeons_gear.client.models.totem.BuzzyNestModel;
import com.infamous.dungeons_gear.entities.BuzzyNestEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BuzzyNestRenderer extends GeoProjectilesRenderer<BuzzyNestEntity> {
    public BuzzyNestRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BuzzyNestModel());
    }

    @Override
    public RenderType getRenderType(BuzzyNestEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
