package com.infamous.dungeons_gear.client;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.items.armor.FreezingResistanceArmorGear;
import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsage;
import com.infamous.dungeons_libraries.capabilities.artifact.ArtifactUsageHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void handleToolTip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        int index = 0;
        Item item = event.getItemStack().getItem();
        if (item instanceof FreezingResistanceArmorGear) {
            FreezingResistanceArmorGear armor = (FreezingResistanceArmorGear)item;
            // DOUBLE OR INT
            if(armor.getFreezingResistance() > 0){
                tooltip.add(index + 1, new TranslatableComponent(
                        "+" + armor.getFreezingResistance() + "% ")
                        .append(new TranslatableComponent(
                                "attribute.name.freezingResistance"))
                        .withStyle(ChatFormatting.GREEN));
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerHandEvent(RenderHandEvent event) {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        if(player == null) return;
        ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
        if(cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof AbstractBeaconItem){
            event.setCanceled(true);
            if(event.getHand() == InteractionHand.MAIN_HAND) {
                float partialTicks = event.getPartialTicks();
                float f1 = Mth.lerp(partialTicks, player.xRotO, player.getXRot());
                Minecraft.getInstance().getItemInHandRenderer().renderArmWithItem(player, partialTicks, f1, event.getHand(), 0.0f, cap.getUsingArtifact(), 0.0f, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
            }
        }
    }

// borrowed from direwolf20's MiningGadget mod
//    @SubscribeEvent
//    public static void renderWorldLastEvent(RenderWorldLastEvent event) {
//        List<AbstractClientPlayerEntity> players = null;
//        if (Minecraft.getInstance().level != null) {
//            players = Minecraft.getInstance().level.players();
//
//            PlayerEntity myplayer = Minecraft.getInstance().player;
//            if(myplayer != null){
//                for (PlayerEntity player : players) {
//                    if (player.distanceToSqr(myplayer) > 500)
//                        continue;
//
//                    ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
//                    if(cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof AbstractBeaconItem){
//                        BeaconBeamRenderer.renderBeam(event, player, Minecraft.getInstance().getFrameTime(), cap.getUsingArtifact());
//                    }
//                }
//            }
//        }
//    }

//    @SubscribeEvent
//    public static void renderLivingEvent(RenderLivingEvent.Post event) {
//        PlayerEntity myplayer = Minecraft.getInstance().player;
//        if (myplayer != null) {
//                if (event.getEntity().distanceToSqr(myplayer) > 500) return;
//
//                ArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(event.getEntity());
//                if (cap != null && cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof AbstractBeaconItem) {
//                    BeaconBeamRenderer.renderBeam(event, event.getEntity(), Minecraft.getInstance().getFrameTime(), cap.getUsingArtifact());
//                }
//        }
//    }
}
