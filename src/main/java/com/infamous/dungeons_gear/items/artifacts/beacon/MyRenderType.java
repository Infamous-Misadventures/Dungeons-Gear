package com.infamous.dungeons_gear.items.artifacts.beacon;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

// borrowed from direwolf20's MiningGadget mod
public class MyRenderType extends RenderType {
    private final static ResourceLocation beaconBeamCore = new ResourceLocation(DungeonsGear.MODID + ":textures/misc/beacon_beam_core.png");
    private final static ResourceLocation beaconBeamMain = new ResourceLocation(DungeonsGear.MODID + ":textures/misc/beacon_beam_main.png");
    private final static ResourceLocation beaconBeamGlow = new ResourceLocation(DungeonsGear.MODID + ":textures/misc/beacon_beam_glow.png");
    // Dummy
    public MyRenderType(String name, VertexFormat format, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable runnablePre, Runnable runnablePost) {
        super(name, format, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, runnablePre, runnablePost);
    }

    public static final RenderType BEACON_BEAM_MAIN = makeType("BeaconBeamMain",
            DefaultVertexFormats.POSITION_COLOR_TEX, GL11.GL_QUADS, 256,
            RenderType.State.getBuilder().texture(new TextureState(beaconBeamMain, false, false))
                    .layer(field_239235_M_)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .depthTest(DEPTH_ALWAYS)
                    .cull(CULL_DISABLED)
                    .lightmap(LIGHTMAP_DISABLED)
                    .writeMask(COLOR_WRITE)
                    .build(false));

    public static final RenderType BEACON_BEAM_GLOW = makeType("BeaconBeamGlow",
            DefaultVertexFormats.POSITION_COLOR_TEX, GL11.GL_QUADS, 256,
            RenderType.State.getBuilder().texture(new TextureState(beaconBeamGlow, false, false))
                    .layer(field_239235_M_)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .depthTest(DEPTH_ALWAYS)
                    .cull(CULL_DISABLED)
                    .lightmap(LIGHTMAP_DISABLED)
                    .writeMask(COLOR_WRITE)
                    .build(false));

    public static final RenderType BEACON_BEAM_CORE = makeType("BeaconBeamCore",
            DefaultVertexFormats.POSITION_COLOR_TEX, GL11.GL_QUADS, 256,
            RenderType.State.getBuilder().texture(new TextureState(beaconBeamCore, false, false))
                    .layer(field_239235_M_)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .depthTest(DEPTH_ALWAYS)
                    .cull(CULL_DISABLED)
                    .lightmap(LIGHTMAP_DISABLED)
                    .writeMask(COLOR_WRITE)
                    .build(false));
}