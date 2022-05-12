package com.infamous.dungeons_gear.client;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.capabilities.artifact.ArtifactUsageHelper;
import com.infamous.dungeons_gear.capabilities.artifact.IArtifactUsage;
import com.infamous.dungeons_gear.items.armor.FreezingResistanceArmorGear;
import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.BeaconBeamRenderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
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
    public static void renderPlayerEvent(RenderPlayerEvent.Post event) {
        PlayerEntity player = event.getPlayer();
        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
        if(cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof AbstractBeaconItem){
            PlayerModel<AbstractClientPlayerEntity> playermodel = event.getRenderer().getModel();
            playermodel.rightArmPose = BipedModel.ArmPose.ITEM;
            playermodel.leftArmPose = BipedModel.ArmPose.ITEM;
            renderArmWithItem(player, event.getRenderer().getModel(), cap.getUsingArtifact(), ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, HandSide.RIGHT, event.getMatrixStack(), event.getBuffers(), event.getLight());

        }
    }

    private static void renderArmWithItem(LivingEntity p_229135_1_, PlayerModel<AbstractClientPlayerEntity> model, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack p_229135_5_, IRenderTypeBuffer p_229135_6_, int p_229135_7_) {
        if (!p_229135_2_.isEmpty()) {
            p_229135_5_.pushPose();
            model.translateToHand(p_229135_4_, p_229135_5_);
            p_229135_5_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            p_229135_5_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            boolean flag = p_229135_4_ == HandSide.LEFT;
            p_229135_5_.translate((double)((float)(flag ? -1 : 1) / 16.0F), 0.125D, -0.625D);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(p_229135_1_, p_229135_2_, p_229135_3_, flag, p_229135_5_, p_229135_6_, p_229135_7_);
            p_229135_5_.popPose();
        }
    }

    @SubscribeEvent
    public static void renderPlayerHandEvent(RenderHandEvent event) {
        AbstractClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null) return;
        IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
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
    @SubscribeEvent
    public static void renderWorldLastEvent(RenderWorldLastEvent event) {
        List<AbstractClientPlayerEntity> players = null;
        if (Minecraft.getInstance().level != null) {
            players = Minecraft.getInstance().level.players();

            PlayerEntity myplayer = Minecraft.getInstance().player;
            if(myplayer != null){
                for (PlayerEntity player : players) {
                    if (player.distanceToSqr(myplayer) > 500)
                        continue;

                    IArtifactUsage cap = ArtifactUsageHelper.getArtifactUsageCapability(player);
                    if(cap.isUsingArtifact() && cap.getUsingArtifact().getItem() instanceof AbstractBeaconItem){
                        BeaconBeamRenderer.renderBeam(event, player, Minecraft.getInstance().getFrameTime(), cap.getUsingArtifact());
                    }
                }
            }
        }
    }
}
