package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.items.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.items.interfaces.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class DescriptionHelper {

    public static void addFullDescription(List<ITextComponent> list, ItemStack itemStack){
        addLoreDescription(list, itemStack);
        addAbilityDescription(list, itemStack);
        addArtifactInfo(list, itemStack);
        addChargeableDescription(list, itemStack);
        addSoulGatheringDescription(list, itemStack);
    }

    public static void addLoreDescription(List<ITextComponent> list, ItemStack itemStack){
        list.add(new TranslationTextComponent(
                "lore.dungeons_gear." + itemStack.getItem().getRegistryName().getPath())
                .withStyle(TextFormatting.WHITE, TextFormatting.ITALIC));
    }

    public static void addAbilityDescription(List<ITextComponent> list, ItemStack itemStack){
        List<String> abilities = getAbilities(itemStack);
        for(String ability : abilities){
            list.add(new TranslationTextComponent(
                    "ability.dungeons_gear." + ability)
                    .withStyle(TextFormatting.GREEN));
        }
    }

    public static void addSoulGatheringDescription(List<ITextComponent> list, ItemStack itemStack) {
        if(itemStack.getItem() instanceof ISoulGatherer){
            ISoulGatherer soulGatherer = (ISoulGatherer) itemStack.getItem();
            int gatherAmount = soulGatherer.getGatherAmount(itemStack);
            double activationCost = soulGatherer.getActivationCost(itemStack);
            if(gatherAmount > 0) {
                list.add(new TranslationTextComponent(
                        "ability.dungeons_gear.soul_gathering", gatherAmount)
                        .withStyle(TextFormatting.LIGHT_PURPLE));
            }
            if(activationCost > 0) {
                list.add(new TranslationTextComponent(
                        "artifact.dungeons_gear.activation", activationCost)
                        .withStyle(TextFormatting.LIGHT_PURPLE));
            }
        }
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

    public static List<String> getAbilities(ItemStack itemStack){
        List<String> abilities = new ArrayList<>();
        checkArmor(itemStack, abilities);
        return abilities;
    }

    private static void checkArmor(ItemStack itemStack, List<String> abilities) {
        if (itemStack.getItem() instanceof IArmor) {
            IArmor armor = (IArmor) itemStack.getItem();
            if (armor.hasBurningBuiltIn(itemStack)) {
                abilities.add("burning");
            }
            if (armor.hasSnowballBuiltIn(itemStack)) {
                abilities.add("snowball");
            }
            if (armor.hasPotionBarrierBuiltIn(itemStack)) {
                abilities.add("potion_barrier");
            }
            if(armor.hasSwiftfootedBuiltIn(itemStack)){
                abilities.add("swiftfooted");
            }
            if(armor.hasChillingBuiltIn(itemStack)){
                abilities.add("chilling");
            }
            if(armor.hasArrowHoarderBuiltIn(itemStack)){
                abilities.add("arrow_hoarder");
            }
        }
    }

}
