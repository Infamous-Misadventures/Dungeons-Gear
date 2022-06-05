package com.infamous.dungeons_gear.client;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.IArtifactUsage;
import com.infamous.dungeons_gear.items.armor.FreezingResistanceArmorGear;
import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
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
        List<ITextComponent> tooltip = event.getToolTip();
        int index = 0;
        Item item = event.getItemStack().getItem();
        if (item instanceof FreezingResistanceArmorGear) {
            FreezingResistanceArmorGear armor = (FreezingResistanceArmorGear)item;
            // DOUBLE OR INT
            if(armor.getFreezingResistance() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getFreezingResistance() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.freezingResistance"))
                        .withStyle(TextFormatting.GREEN));
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerHandEvent(RenderHandEvent event) {
        AbstractClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null) return;
        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
        if(cap == null) return;
        if(cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof AbstractBeaconItem){
            event.setCanceled(true);
            if(event.getHand() == Hand.MAIN_HAND) {
                float partialTicks = event.getPartialTicks();
                float f1 = MathHelper.lerp(partialTicks, player.xRotO, player.xRot);
                Minecraft.getInstance().getItemInHandRenderer().renderArmWithItem(player, partialTicks, f1, event.getHand(), 0.0f, cap.getUsingArtifact(), 0.0f, event.getMatrixStack(), event.getBuffers(), event.getLight());
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
//                    IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
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
//                IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(event.getEntity());
//                if (cap != null && cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof AbstractBeaconItem) {
//                    BeaconBeamRenderer.renderBeam(event, event.getEntity(), Minecraft.getInstance().getFrameTime(), cap.getUsingArtifact());
//                }
//        }
//    }
}
