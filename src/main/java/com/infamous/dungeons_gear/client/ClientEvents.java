package com.infamous.dungeons_gear.client;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.BeaconBeamRenderer;
import com.infamous.dungeons_gear.items.interfaces.IArmor;
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
            if(armor.getArtifactCooldown() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "-" + armor.getArtifactCooldown() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.artifactCooldown"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getChanceToNegateHits() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getChanceToNegateHits() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.chanceToNegateHits"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getChanceToTeleportAwayWhenHit() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getChanceToTeleportAwayWhenHit() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.chanceToTeleportAwayWhenHit"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getFreezingResistance() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getFreezingResistance() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.freezingResistance"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getHealthPotionBoost() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getHealthPotionBoost() + " ")
                        .append(new TranslationTextComponent(
                                "attribute.name.healthPotionBoost"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getLongerRolls() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLongerRolls() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.longerRolls"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getLongerRollCooldown() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLongerRollCooldown() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.longerRollCooldown"))
                        .withStyle(TextFormatting.RED));
            }
            if(armor.getLifeSteal() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLifeSteal() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.lifeSteal"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getMagicDamage() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getMagicDamage() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.magicDamage"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getRangedDamage() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getRangedDamage() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.rangedDamage"))
                        .withStyle(TextFormatting.GREEN));
            }
            if(armor.getSoulsGathered() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getSoulsGathered() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.soulsGathered"))
                        .withStyle(TextFormatting.LIGHT_PURPLE));
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


                    ItemStack heldItem = AbstractBeaconItem.getBeacon(player);
                    if (player.isUsingItem()
                            && heldItem.getItem() instanceof AbstractBeaconItem
                            && ((AbstractBeaconItem)heldItem.getItem()).canFire(player, heldItem)) {
                            BeaconBeamRenderer.renderBeam(event, player, Minecraft.getInstance().getFrameTime());
                    }
                }
            }
        }
    }
}
