package com.infamous.dungeons_gear.integration.curios.client;

import com.infamous.dungeons_gear.combat.NetworkHandler;
import com.infamous.dungeons_gear.integration.curios.client.message.CuriosArtifactMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class CuriosKeyBindings {

    public static final KeyBinding activateArtifact1 = new KeyBinding("key.dungeons_gear.curiosintegration.description_slot1", GLFW.GLFW_KEY_V, "key.dungeons_gear.curiosintegration.category");
    public static final KeyBinding activateArtifact2 = new KeyBinding("key.dungeons_gear.curiosintegration.description_slot2", GLFW.GLFW_KEY_B, "key.dungeons_gear.curiosintegration.category");
    public static final KeyBinding activateArtifact3 = new KeyBinding("key.dungeons_gear.curiosintegration.description_slot3", GLFW.GLFW_KEY_N, "key.dungeons_gear.curiosintegration.category");

    public static void setupCuriosKeybindings() {
        activateArtifact1.setKeyConflictContext(KeyConflictContext.IN_GAME);
        ClientRegistry.registerKeyBinding(activateArtifact1);
        activateArtifact2.setKeyConflictContext(KeyConflictContext.IN_GAME);
        ClientRegistry.registerKeyBinding(activateArtifact2);
        activateArtifact3.setKeyConflictContext(KeyConflictContext.IN_GAME);
        ClientRegistry.registerKeyBinding(activateArtifact3);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (activateArtifact1.isDown()) {
            sendCuriosMessageToServer(0);
        }
        if (activateArtifact2.isDown()) {
            sendCuriosMessageToServer(1);
        }
        if (activateArtifact3.isDown()) {
            sendCuriosMessageToServer(2);
        }
    }

    private static void sendCuriosMessageToServer(int slot) {
        RayTraceResult hitResult = Minecraft.getInstance().hitResult;
        if(hitResult == null || hitResult.getType() == RayTraceResult.Type.MISS){
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if(player != null) {
                BlockRayTraceResult blockRayTraceResult = new BlockRayTraceResult(player.position(), Direction.UP, player.blockPosition(), false);
                NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactMessage(slot, blockRayTraceResult));
            }
        }else if(hitResult.getType() == RayTraceResult.Type.BLOCK) {
            NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactMessage(slot, (BlockRayTraceResult) hitResult));
        }else if(hitResult.getType() == RayTraceResult.Type.ENTITY){
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) hitResult;
            BlockRayTraceResult blockRayTraceResult = new BlockRayTraceResult(entityRayTraceResult.getEntity().position(), Direction.UP, entityRayTraceResult.getEntity().blockPosition(), false);
            NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactMessage(slot, blockRayTraceResult));
        }
    }
}