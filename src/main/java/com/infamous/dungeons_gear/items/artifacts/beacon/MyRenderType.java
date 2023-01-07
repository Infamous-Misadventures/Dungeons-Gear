package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.DungeonsGear;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MyRenderType extends RenderType {
    private final static ResourceLocation beaconBeamCore = new ResourceLocation(DungeonsGear.MODID + ":textures/misc/beacon_beam_core.png");
    private final static ResourceLocation beaconBeamMain = new ResourceLocation(DungeonsGear.MODID + ":textures/misc/beacon_beam_main.png");
    private final static ResourceLocation beaconBeamGlow = new ResourceLocation(DungeonsGear.MODID + ":textures/misc/beacon_beam_glow.png");

    // Dummy
    public MyRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static final RenderType BEACON_BEAM_MAIN = create("BeaconBeamMain",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new TextureStateShard(beaconBeamMain, false, false))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
                    .createCompositeState(false));

    public static final RenderType BEACON_BEAM_GLOW = create("BeaconBeamGlow",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new TextureStateShard(beaconBeamGlow, false, false))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
                    .createCompositeState(false));

    public static final RenderType BEACON_BEAM_CORE = create("BeaconBeamCore",
            DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder().setTextureState(new TextureStateShard(beaconBeamCore, false, false))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(NO_DEPTH_TEST)
                    .setCullState(NO_CULL)
                    .setLightmapState(NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
                    .createCompositeState(false));
}