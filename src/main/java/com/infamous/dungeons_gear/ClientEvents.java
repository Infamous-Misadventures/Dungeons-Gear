package com.infamous.dungeons_gear;

import com.infamous.dungeons_gear.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_gear.artifacts.beacon.BeaconBeamRenderer;
import com.infamous.dungeons_gear.interfaces.IArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
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
        if (item instanceof IArmor) {
            IArmor armor = (IArmor)item;
            for(int i = 0; i < tooltip.size(); i++) {
                ITextComponent component = tooltip.get(i);
                if(component instanceof TranslationTextComponent) {
                    if(((TranslationTextComponent) component).getKey().equals("attribute.modifier.plus.0")) index = i;
                }
            }

            // DOUBLE OR INT
            if(armor.getArrowsPerBundle() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getArrowsPerBundle() + " ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.arrowsPerBundle"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getArtifactCooldown() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "-" + armor.getArtifactCooldown() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.artifactCooldown"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getChanceToNegateHits() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getChanceToNegateHits() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.chanceToNegateHits"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getChanceToTeleportAwayWhenHit() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getChanceToTeleportAwayWhenHit() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.chanceToTeleportAwayWhenHit"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getFreezingResistance() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getFreezingResistance() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.freezingResistance"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getHealthPotionBoost() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getHealthPotionBoost() + " ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.healthPotionBoost"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getHigherJumps() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getHigherJumps() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.higherJumps"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getLongerJumpAbilityCooldown() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLongerJumpAbilityCooldown() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.longerJumpAbilityCooldown"))
                        .applyTextStyle(TextFormatting.RED));
            }
            if(armor.getLifeSteal() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLifeSteal() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.lifeSteal"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getMagicDamage() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getMagicDamage() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.magicDamage"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getRangedDamage() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getRangedDamage() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.rangedDamage"))
                        .applyTextStyle(TextFormatting.GREEN));
            }
            if(armor.getSoulsGathered() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getSoulsGathered() + "% ")
                        .appendSibling(new TranslationTextComponent(
                                "attribute.name.soulsGathered"))
                        .applyTextStyle(TextFormatting.LIGHT_PURPLE));
            }
        }
    }

// borrowed from direwolf20's MiningGadget mod
    @SubscribeEvent
    public static void renderWorldLastEvent(RenderWorldLastEvent event) {
        List<AbstractClientPlayerEntity> players = null;
        if (Minecraft.getInstance().world != null) {
            players = Minecraft.getInstance().world.getPlayers();

            PlayerEntity myplayer = Minecraft.getInstance().player;
            if(myplayer != null){
                for (PlayerEntity player : players) {
                    if (player.getDistanceSq(myplayer) > 500)
                        continue;


                    ItemStack heldItem = AbstractBeaconItem.getBeacon(player);
                    if (player.isHandActive()
                            && heldItem.getItem() instanceof AbstractBeaconItem
                            && AbstractBeaconItem.canFire(player)) {
                            BeaconBeamRenderer.renderBeam(event, player, Minecraft.getInstance().getRenderPartialTicks());
                    }
                }
            }
        }
    }
}
