 package com.infamous.dungeons_gear.integration.curios.client;

import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsage;
import com.infamous.dungeons_gear.network.NetworkHandler;
import com.infamous.dungeons_gear.integration.curios.client.message.CuriosArtifactStartMessage;
import com.infamous.dungeons_gear.integration.curios.client.message.CuriosArtifactStopMessage;
import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.artifacts.ArtifactUseContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class CuriosKeyBindings {

    public static final KeyMapping activateArtifact1 = new KeyMapping("key.dungeons_gear.curiosintegration.description_slot1", GLFW.GLFW_KEY_V, "key.dungeons_gear.curiosintegration.category");
    public static final KeyMapping activateArtifact2 = new KeyMapping("key.dungeons_gear.curiosintegration.description_slot2", GLFW.GLFW_KEY_B, "key.dungeons_gear.curiosintegration.category");
    public static final KeyMapping activateArtifact3 = new KeyMapping("key.dungeons_gear.curiosintegration.description_slot3", GLFW.GLFW_KEY_N, "key.dungeons_gear.curiosintegration.category");

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
        Player player = event.player;
        ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
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

    private static void stopUsingAllArtifacts(Player player) {
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
        HitResult hitResult = Minecraft.getInstance().hitResult;
        LocalPlayer player = Minecraft.getInstance().player;
        if(player != null) {
            if (hitResult == null || hitResult.getType() == HitResult.Type.MISS) {
                BlockHitResult blockRayTraceResult = new BlockHitResult(player.position(), Direction.UP, player.blockPosition(), false);
                curiosStartMessage(slot, blockRayTraceResult, player);
            } else if (hitResult.getType() == HitResult.Type.BLOCK) {
                curiosStartMessage(slot, (BlockHitResult) hitResult, player);
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityRayTraceResult = (EntityHitResult) hitResult;
                BlockHitResult blockRayTraceResult = new BlockHitResult(entityRayTraceResult.getEntity().position(), Direction.UP, entityRayTraceResult.getEntity().blockPosition(), false);
                curiosStartMessage(slot, blockRayTraceResult, player);
            }
        }
    }

    private static void curiosStartMessage(int slot, BlockHitResult blockRayTraceResult, LocalPlayer player) {
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

    private static void sendCuriosStopMessageToServer(int slot, Player player, ArtifactUsage cap) {
        if(player != null) {
            CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
                Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
                if (artifactStackHandler.isPresent()) {
                    ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(slot);
                    if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem && cap.isSameUsingArtifact(artifact)) {
                        NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactStopMessage(slot));
                        ArtifactUsage capability = ArtifactUsageHelper.getArtifactUsageCapability(player);
                        capability.stopUsingArtifact();
                    }
                }
            });
        }
    }
}