package com.infamous.dungeons_gear.artifacts.corruptedbeacon;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeamRenderer extends AbstractBeamRenderer<BeamEntity> {

    public BeamRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }


    public ResourceLocation getEntityTexture(BeamEntity entity) {
        return new ResourceLocation(DungeonsGear.MODID,"textures/entity/beam.png");
    }


}
