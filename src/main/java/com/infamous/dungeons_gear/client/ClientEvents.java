package com.infamous.dungeons_gear.client;

import com.infamous.dungeons_gear.DungeonsGear;
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
                        .append(new TranslationTextComponent(
                                "attribute.name.arrowsPerBundle"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getArtifactCooldown() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "-" + armor.getArtifactCooldown() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.artifactCooldown"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getChanceToNegateHits() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getChanceToNegateHits() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.chanceToNegateHits"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getChanceToTeleportAwayWhenHit() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getChanceToTeleportAwayWhenHit() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.chanceToTeleportAwayWhenHit"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getFreezingResistance() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getFreezingResistance() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.freezingResistance"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getHealthPotionBoost() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getHealthPotionBoost() + " ")
                        .append(new TranslationTextComponent(
                                "attribute.name.healthPotionBoost"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getLongerRolls() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLongerRolls() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.longerRolls"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getLongerRollCooldown() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLongerRollCooldown() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.longerRollCooldown"))
                        .mergeStyle(TextFormatting.RED));
            }
            if(armor.getLifeSteal() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLifeSteal() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.lifeSteal"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getMagicDamage() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getMagicDamage() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.magicDamage"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getRangedDamage() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getRangedDamage() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.rangedDamage"))
                        .mergeStyle(TextFormatting.GREEN));
            }
            if(armor.getSoulsGathered() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getSoulsGathered() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.soulsGathered"))
                        .mergeStyle(TextFormatting.LIGHT_PURPLE));
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
                            && AbstractBeaconItem.canFire(player, heldItem)) {
                            BeaconBeamRenderer.renderBeam(event, player, Minecraft.getInstance().getRenderPartialTicks());
                    }
                }
            }
        }
    }
}
