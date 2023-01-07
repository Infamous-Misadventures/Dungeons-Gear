package com.infamous.dungeons_gear.client.renderer.summonables;

import com.infamous.dungeons_gear.client.models.summonables.IceCloudModel;
import com.infamous.dungeons_gear.entities.IceCloudEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class IceCloudRenderer extends GeoProjectilesRenderer<IceCloudEntity> {
    public IceCloudRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new IceCloudModel());
    }

    @Override
    protected int getBlockLightLevel(IceCloudEntity p_114496_, BlockPos p_114497_) {
        return p_114496_.level.getBrightness(LightLayer.BLOCK, p_114497_) > 10
                ? p_114496_.level.getBrightness(LightLayer.BLOCK, p_114497_)
                : 5;
    }

    @Override
    public RenderType getRenderType(IceCloudEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}