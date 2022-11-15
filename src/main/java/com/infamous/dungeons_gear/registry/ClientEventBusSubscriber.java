package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.client.renderer.BeamEntityRenderer;
import com.infamous.dungeons_gear.client.renderer.IceCloudRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.BuzzyNestRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.FireworksDisplayRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.TotemOfRegenerationRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.TotemOfShieldingRenderer;
import com.infamous.dungeons_gear.items.armor.FreezingResistanceArmorGear;
import com.infamous.dungeons_gear.items.armor.PetBatArmorGear;
import com.infamous.dungeons_libraries.items.gearconfig.client.ArmorGearRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
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

    @SubscribeEvent
    public static void registerArmorRenderers(EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(PetBatArmorGear.class, ArmorGearRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(FreezingResistanceArmorGear.class, ArmorGearRenderer::new);
    }
}
