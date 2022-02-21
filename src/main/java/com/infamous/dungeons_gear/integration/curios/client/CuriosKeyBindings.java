package com.infamous.dungeons_gear.integration.curios.client;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.integration.curios.client.message.CuriosArtifactMessage;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class CuriosKeyBindings {

    public static final KeyBinding activateArtifact1 = new KeyBinding("key.dungeons_gear.curiosintegration.description", GLFW.GLFW_KEY_V, "key.dungeons_gear.curiosintegration.category");
    public static final KeyBinding activateArtifact2 = new KeyBinding("key.dungeons_gear.curiosintegration.description", GLFW.GLFW_KEY_B, "key.dungeons_gear.curiosintegration.category");
    public static final KeyBinding activateArtifact3 = new KeyBinding("key.dungeons_gear.curiosintegration.description", GLFW.GLFW_KEY_N, "key.dungeons_gear.curiosintegration.category");

    public static void setupCuriosKeybindings() {
        activateArtifact1.setKeyConflictContext(KeyConflictContext.IN_GAME);
        ClientRegistry.registerKeyBinding(activateArtifact1);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (activateArtifact1.isDown()) {
            NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactMessage(0));
        }
        if (activateArtifact2.isDown()) {
            NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactMessage(1));
        }
        if (activateArtifact3.isDown()) {
            NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactMessage(2));
        }
    }
}