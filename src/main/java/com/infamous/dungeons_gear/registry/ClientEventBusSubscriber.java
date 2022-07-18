package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.client.renderer.BeamEntityRenderer;
import com.infamous.dungeons_gear.client.renderer.IceCloudRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.BuzzyNestRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.FireworksDisplayRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.TotemOfRegenerationRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.TotemOfShieldingRenderer;
import com.infamous.dungeons_gear.entities.ModEntityTypes;
import com.infamous.dungeons_libraries.client.renderer.SoulOrbRenderer;
import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import static com.infamous.dungeons_libraries.DungeonsLibraries.MODID;
import static net.minecraftforge.api.distmarker.Dist.CLIENT;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.ICE_CLOUD.get(), IceCloudRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BUZZY_NEST.get(), BuzzyNestRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.TOTEM_OF_SHIELDING.get(), TotemOfShieldingRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.TOTEM_OF_REGENERATION.get(), TotemOfRegenerationRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.FIREWORKS_DISPLAY.get(), FireworksDisplayRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BEAM_ENTITY.get(), BeamEntityRenderer::new);
    }
}
