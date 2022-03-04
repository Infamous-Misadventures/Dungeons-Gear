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
        addPenaltyDescription(list, itemStack);
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

    public static void addPenaltyDescription(List<ITextComponent> list, ItemStack itemStack) {
        List<String> penalties = getPenalties(itemStack);
        for (String penalty : penalties) {
            list.add(new TranslationTextComponent(
                    "penalty.dungeons_gear." + penalty)
                    .withStyle(TextFormatting.RED));
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
        checkRangedWeapon(itemStack, abilities);
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

    private static void checkRangedWeapon(ItemStack itemStack, List<String> abilities) {
        if(itemStack.getItem() instanceof IRangedWeapon){
            IRangedWeapon rangedWeapon = (IRangedWeapon) itemStack.getItem();
            if(rangedWeapon.hasAccelerateBuiltIn(itemStack)){
                 abilities.add("accelerate");
            }
            if(rangedWeapon.hasBonusShotBuiltIn(itemStack)){
                abilities.add("bonus_shot");
            }
            if(rangedWeapon.hasBubbleDamage(itemStack)){
                abilities.add("bubble_damage");
            }
            if(rangedWeapon.hasChainReactionBuiltIn(itemStack)){
                abilities.add("chain_reaction");
            }
            if(rangedWeapon.hasMultishotWhenCharged(itemStack)){
                abilities.add("charged_multishot");
            }
            if(rangedWeapon.hasDynamoBuiltIn(itemStack)){
                abilities.add("dynamo");
            }
            if(rangedWeapon.canDualWield(itemStack)){
                abilities.add("dual_wield");
            }
            if(rangedWeapon.hasEnigmaResonatorBuiltIn(itemStack)){
                abilities.add("enigma_resonator");
            }
            if(rangedWeapon.shootsExplosiveArrows(itemStack)){
                abilities.add("explosive_arrows");
            }
            if(rangedWeapon.hasExtraMultishot(itemStack)){
                abilities.add("extra_multishot");
            }
            if(rangedWeapon.shootsFasterArrows(itemStack)){
                abilities.add("faster_arrows");
            }
            if(rangedWeapon.firesHarpoons(itemStack)){
                abilities.add("fires_harpoons");
            }
            if(rangedWeapon.shootsFreezingArrows(itemStack)){
                abilities.add("freezing_arrows");
            }
            if(rangedWeapon.hasFuseShotBuiltIn(itemStack)){
                abilities.add("fuse_shot");
            }
            if(rangedWeapon.shootsGaleArrows(itemStack)){
                abilities.add("gale_arrows");
            }
            if(rangedWeapon.hasGravityBuiltIn(itemStack)){
                abilities.add("gravity");
            }
            if(rangedWeapon.hasGrowingBuiltIn(itemStack)){
                abilities.add("growing");
            }
            if(rangedWeapon.hasGuaranteedRicochet(itemStack)){
                abilities.add("guaranteed_ricochet");
            }
            if(rangedWeapon.shootsHeavyArrows(itemStack)){
                abilities.add("heavy_arrows");
            }
            if(rangedWeapon.hasHighFireRate(itemStack)){
                abilities.add("high_fire_rate");
            }
            if(rangedWeapon.hasMultishotBuiltIn(itemStack)){
                abilities.add("multishot");
            }
            if(rangedWeapon.setsPetsAttackTarget(itemStack)){
                abilities.add("pet_targeting");
            }
            if(rangedWeapon.hasPiercingBuiltIn(itemStack)){
                abilities.add("piercing");
            }
            if(rangedWeapon.hasPoisonCloudBuiltIn(itemStack)){
                abilities.add("poison_cloud");
            }
            if(rangedWeapon.hasPowerBuiltIn(itemStack)){
                abilities.add("power");
            }
            if(rangedWeapon.hasPunchBuiltIn(itemStack)){
                abilities.add("punch");
            }
            if(rangedWeapon.hasQuickChargeBuiltIn(itemStack)){
                abilities.add("quick_charge");
            }
            if(rangedWeapon.hasRadianceShotBuiltIn(itemStack)){
                abilities.add("radiance_shot");
            }
            if(rangedWeapon.hasReplenishBuiltIn(itemStack)){
                abilities.add("replenish");
            }
            if(rangedWeapon.hasRicochetBuiltIn(itemStack)){
                abilities.add("ricochet");
            }
            if(rangedWeapon.hasRollChargeBuiltIn(itemStack)){
                abilities.add("roll_charge");
            }
            if(rangedWeapon.shootsStrongChargedArrows(itemStack)){
                abilities.add("strong_charged");
            }
            if(rangedWeapon.hasSuperChargedBuiltIn(itemStack)){
                abilities.add("supercharged");
            }
            if(rangedWeapon.hasTempoTheftBuiltIn(itemStack)){
                abilities.add("tempo_theft");
            }
            if(rangedWeapon.hasWildRageBuiltIn(itemStack)){
                abilities.add("wild_rage");
            }
            if(rangedWeapon.hasWindUpAttack(itemStack)){
                abilities.add("wind_up_attack");
            }
        }
    }

    public static List<String> getPenalties(ItemStack itemStack) {
        List<String> penalties = new ArrayList<>();
        if (itemStack.getItem() instanceof IRangedWeapon) {
            IRangedWeapon rangedWeapon = (IRangedWeapon) itemStack.getItem();
            if (rangedWeapon.hasSlowFireRate(itemStack)) {
                penalties.add("slow_fire_rate");
            }
        }
        return penalties;
    }

}
