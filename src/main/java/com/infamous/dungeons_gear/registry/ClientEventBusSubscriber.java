package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.client.renderer.BeamEntityRenderer;
import com.infamous.dungeons_gear.client.renderer.IceCloudRenderer;
import com.infamous.dungeons_gear.client.renderer.ghosts.SoulWizardRenderer;
import com.infamous.dungeons_gear.client.renderer.projectiles.SoulWizardOrbRenderer;
import com.infamous.dungeons_gear.client.renderer.totem.*;
import com.infamous.dungeons_gear.items.armor.FreezingResistanceArmorGear;
import com.infamous.dungeons_gear.items.armor.PetBatArmorGear;
import com.infamous.dungeons_gear.items.artifacts.TotemOfSoulProtectionItem;
import com.infamous.dungeons_libraries.client.renderer.gearconfig.ArmorGearRenderer;
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
        event.registerEntityRenderer(ModEntityTypes.TOTEM_OF_SOUL_PROTECTION.get(), TotemOfSoulProtectionRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.FIREWORKS_DISPLAY.get(), FireworksDisplayRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BEAM_ENTITY.get(), BeamEntityRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SOUL_WIZARD.get(), SoulWizardRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SOUL_WIZARD_ORB.get(), SoulWizardOrbRenderer::new);
    }

    @SubscribeEvent
    public static void registerArmorRenderers(EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(PetBatArmorGear.class, ArmorGearRenderer::new);
        GeoArmorRenderer.registerArmorRenderer(FreezingResistanceArmorGear.class, ArmorGearRenderer::new);
    }
}
