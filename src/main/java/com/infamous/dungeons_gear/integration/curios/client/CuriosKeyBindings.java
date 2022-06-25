 package com.infamous.dungeons_gear.integration.curios.client;

import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.IArtifactUsage;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.integration.curios.client.message.CuriosArtifactStartMessage;
import com.infamous.dungeons_gear.integration.curios.client.message.CuriosArtifactStopMessage;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.ArtifactUseContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
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
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START || event.side != LogicalSide.CLIENT) {
            return;
        }
        PlayerEntity player = event.player;
        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
        if(cap.isUsingArtifact()) {
            if (!activateArtifact1.isDown()) {
                sendCuriosStopMessageToServer(0, player, cap);
            }
            if (!activateArtifact2.isDown()) {
                sendCuriosStopMessageToServer(1, player, cap);
            }
            if (!activateArtifact3.isDown()) {
                sendCuriosStopMessageToServer(2, player, cap);
            }
        }else{
            if (activateArtifact1.isDown()) {
                sendCuriosStartMessageToServer(0);
            }
            if (activateArtifact2.isDown()) {
                sendCuriosStartMessageToServer(1);
            }
            if (activateArtifact3.isDown()) {
                sendCuriosStartMessageToServer(2);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        stopUsingAllArtifacts(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event){
        stopUsingAllArtifacts(event.getPlayer());

    }

    @SubscribeEvent
    public static void onPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event){
        stopUsingAllArtifacts(event.getPlayer());
    }

    private static void stopUsingAllArtifacts(PlayerEntity player) {
        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
            Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
            if (artifactStackHandler.isPresent()) {
                int slots = artifactStackHandler.get().getStacks().getSlots();
                for(int slot = 0; slot < slots; slot++) {
                    ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(slot);
                    if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem) {
                        ((ArtifactItem) artifact.getItem()).stopUsingArtifact(player);
                    }
                }
            }
        });
    }

    private static void sendCuriosStartMessageToServer(int slot) {
        RayTraceResult hitResult = Minecraft.getInstance().hitResult;
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player != null) {
            if (hitResult == null || hitResult.getType() == RayTraceResult.Type.MISS) {
                BlockRayTraceResult blockRayTraceResult = new BlockRayTraceResult(player.position(), Direction.UP, player.blockPosition(), false);
                curiosStartMessage(slot, blockRayTraceResult, player);
            } else if (hitResult.getType() == RayTraceResult.Type.BLOCK) {
                curiosStartMessage(slot, (BlockRayTraceResult) hitResult, player);
            } else if (hitResult.getType() == RayTraceResult.Type.ENTITY) {
                EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) hitResult;
                BlockRayTraceResult blockRayTraceResult = new BlockRayTraceResult(entityRayTraceResult.getEntity().position(), Direction.UP, entityRayTraceResult.getEntity().blockPosition(), false);
                curiosStartMessage(slot, blockRayTraceResult, player);
            }
        }
    }

    private static void curiosStartMessage(int slot, BlockRayTraceResult blockRayTraceResult, ClientPlayerEntity player) {
        NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactStartMessage(slot, blockRayTraceResult));
        CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
            Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
            if (artifactStackHandler.isPresent()) {
                ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(slot);
                if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem) {
                    ArtifactUseContext iuc = new ArtifactUseContext(player.level, player, artifact, blockRayTraceResult);
                    ((ArtifactItem) artifact.getItem()).activateArtifact(iuc);
                }
            }
        });
    }

    private static void sendCuriosStopMessageToServer(int slot, PlayerEntity player, IArtifactUsage cap) {
        if(player != null) {
            CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
                Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
                if (artifactStackHandler.isPresent()) {
                    ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(slot);
                    if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem && cap.isSameUsingArtifact(artifact)) {
                        NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactStopMessage(slot));
                        IArtifactUsage capability = ArtifactUsageHelper.getArtifactUsageCapability(player);
                        capability.stopUsingArtifact();
                    }
                }
            });
        }
    }
}