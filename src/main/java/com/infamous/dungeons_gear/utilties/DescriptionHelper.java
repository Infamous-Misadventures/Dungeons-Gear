package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.interfaces.*;
import com.infamous.dungeons_libraries.items.interfaces.ISoulConsumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class DescriptionHelper {

    public static void addFullDescription(List<Component> list, ItemStack itemStack){
        addLoreDescription(list, itemStack);
        addArtifactInfo(list, itemStack);
        addChargeableDescription(list, itemStack);
    }

    public static void addLoreDescription(List<Component> list, ItemStack itemStack){
        list.add(new TranslatableComponent(
                "lore.dungeons_gear." + itemStack.getItem().getRegistryName().getPath())
                .withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC));
    }

    public static void addChargeableDescription(List<Component> list, ItemStack itemStack) {
        if(itemStack.getItem() instanceof IChargeableItem){
            IChargeableItem chargeableItem = (IChargeableItem) itemStack.getItem();
            int chargeTimeInSeconds = chargeableItem.getChargeTimeInSeconds();
            if(chargeTimeInSeconds > 0) {
                list.add(new TranslatableComponent(
                        "artifact.dungeons_gear.charge_time", chargeTimeInSeconds)
                        .withStyle(ChatFormatting.BLUE));
            }
        }
    }

    public static void addArtifactInfo(List<Component> list, ItemStack itemStack) {
        if (itemStack.getItem() instanceof ArtifactItem) {

            list.add(new TranslatableComponent(
                    "ability.dungeons_gear." + itemStack.getItem().getRegistryName().getPath())
                    .withStyle(ChatFormatting.GREEN));

            ArtifactItem artifactItem = (ArtifactItem) itemStack.getItem();
            int durationInSeconds = artifactItem.getDurationInSeconds();
            int cooldownInSeconds = artifactItem.getCooldownInSeconds();

            if(durationInSeconds > 0) {
                list.add(new TranslatableComponent(
                        "artifact.dungeons_gear.duration", durationInSeconds)
                        .withStyle(ChatFormatting.BLUE));
            }
            if(cooldownInSeconds > 0) {
                list.add(new TranslatableComponent(
                        "artifact.dungeons_gear.cooldown", cooldownInSeconds)
                        .withStyle(ChatFormatting.BLUE));
            }
        }
    }

}
