package com.infamous.dungeons_gear;

import com.infamous.dungeons_gear.interfaces.IArmor;
import net.minecraft.item.Item;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
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
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.arrowsPerBundle"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getArtifactCooldown() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "-" + armor.getArtifactCooldown() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.artifactCooldown"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getChanceToNegateHits() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getChanceToNegateHits() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.chanceToNegateHits"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getChanceToTeleportAwayWhenHit() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getChanceToTeleportAwayWhenHit() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.chanceToTeleportAwayWhenHit"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getFreezingResistance() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getFreezingResistance() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.freezingResistance"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getHealthPotionBoost() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getHealthPotionBoost() + " ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.healthPotionBoost"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getHigherJumps() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getHigherJumps() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.higherJumps"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getLongerJumpAbilityCooldown() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLongerJumpAbilityCooldown() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.longerJumpAbilityCooldown"))
                        .func_240701_a_(TextFormatting.RED));
            }
            if(armor.getLifeSteal() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getLifeSteal() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.lifeSteal"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getMagicDamage() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getMagicDamage() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.magicDamage"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getRangedDamage() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getRangedDamage() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.rangedDamage"))
                        .func_240701_a_(TextFormatting.GREEN));
            }
            if(armor.getSoulsGathered() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getSoulsGathered() + "% ")
                        .func_230529_a_(new TranslationTextComponent(
                                "attribute.name.soulsGathered"))
                        .func_240701_a_(TextFormatting.LIGHT_PURPLE));
            }
        }

    }
}
