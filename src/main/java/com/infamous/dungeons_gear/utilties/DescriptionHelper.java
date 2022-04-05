package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.interfaces.*;
import com.infamous.dungeons_libraries.items.interfaces.ISoulConsumer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class DescriptionHelper {

    public static void addFullDescription(List<ITextComponent> list, ItemStack itemStack){
        addLoreDescription(list, itemStack);
        addArtifactInfo(list, itemStack);
        addChargeableDescription(list, itemStack);
    }

    public static void addLoreDescription(List<ITextComponent> list, ItemStack itemStack){
        list.add(new TranslationTextComponent(
                "lore.dungeons_gear." + itemStack.getItem().getRegistryName().getPath())
                .withStyle(TextFormatting.WHITE, TextFormatting.ITALIC));
    }

    public static void addChargeableDescription(List<ITextComponent> list, ItemStack itemStack) {
        if(itemStack.getItem() instanceof IChargeableItem){
            IChargeableItem chargeableItem = (IChargeableItem) itemStack.getItem();
            int chargeTimeInSeconds = chargeableItem.getChargeTimeInSeconds();
            if(chargeTimeInSeconds > 0) {
                list.add(new TranslationTextComponent(
                        "artifact.dungeons_gear.charge_time", chargeTimeInSeconds)
                        .withStyle(TextFormatting.BLUE));
            }
        }
    }

    public static void addArtifactInfo(List<ITextComponent> list, ItemStack itemStack) {
        if (itemStack.getItem() instanceof ArtifactItem) {

            list.add(new TranslationTextComponent(
                    "ability.dungeons_gear." + itemStack.getItem().getRegistryName().getPath())
                    .withStyle(TextFormatting.GREEN));

            ArtifactItem artifactItem = (ArtifactItem) itemStack.getItem();
            int durationInSeconds = artifactItem.getDurationInSeconds();
            int cooldownInSeconds = artifactItem.getCooldownInSeconds();

            if(durationInSeconds > 0) {
                list.add(new TranslationTextComponent(
                        "artifact.dungeons_gear.duration", durationInSeconds)
                        .withStyle(TextFormatting.BLUE));
            }
            if(cooldownInSeconds > 0) {
                list.add(new TranslationTextComponent(
                        "artifact.dungeons_gear.cooldown", cooldownInSeconds)
                        .withStyle(TextFormatting.BLUE));
            }
        }
    }

}
