package com.infamous.dungeons_gear.utilties;

import com.infamous.dungeons_gear.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.interfaces.IArmor;
import com.infamous.dungeons_gear.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.interfaces.IRangedWeapon;
import com.infamous.dungeons_gear.interfaces.ISoulGatherer;
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
        addSoulGatheringDescription(list, itemStack);
    }

    public static void addLoreDescription(List<ITextComponent> list, ItemStack itemStack){
        list.add(new TranslationTextComponent(
                "lore.dungeons_gear." + itemStack.getItem().getRegistryName().getPath())
                .mergeStyle(TextFormatting.WHITE, TextFormatting.ITALIC));
    }

    public static void addAbilityDescription(List<ITextComponent> list, ItemStack itemStack){
        List<String> abilities = getAbilities(itemStack);
        for(String ability : abilities){
            list.add(new TranslationTextComponent(
                    "ability.dungeons_gear." + ability)
                    .mergeStyle(TextFormatting.GREEN));
        }
    }

    public static void addSoulGatheringDescription(List<ITextComponent> list, ItemStack itemStack) {
        if(itemStack.getItem() instanceof ISoulGatherer){
            ISoulGatherer soulGatherer = (ISoulGatherer) itemStack.getItem();
            int gatherAmount = soulGatherer.getGatherAmount(itemStack);
            int activationCost = soulGatherer.getActivationCost(itemStack);
            if(gatherAmount > 0) {
                list.add(new TranslationTextComponent(
                        "ability.dungeons_gear.soul_gathering", gatherAmount)
                        .mergeStyle(TextFormatting.LIGHT_PURPLE));
            }
            if(activationCost > 0) {
                list.add(new TranslationTextComponent(
                        "artifact.dungeons_gear.activation", activationCost)
                        .mergeStyle(TextFormatting.LIGHT_PURPLE));
            }
        }
    }

    public static void addPenaltyDescription(List<ITextComponent> list, ItemStack itemStack) {
        List<String> penalties = getPenalties(itemStack);
        for (String penalty : penalties) {
            list.add(new TranslationTextComponent(
                    "penalty.dungeons_gear." + penalty)
                    .mergeStyle(TextFormatting.RED));
        }
    }

    public static void addArtifactInfo(List<ITextComponent> list, ItemStack itemStack) {
        if (itemStack.getItem() instanceof ArtifactItem) {
            ArtifactItem artifactItem = (ArtifactItem) itemStack.getItem();
            int durationInSeconds = artifactItem.getDurationInSeconds();
            int cooldownInSeconds = artifactItem.getCooldownInSeconds();
            if(durationInSeconds > 0) {
                list.add(new TranslationTextComponent(
                        "artifact.dungeons_gear.duration", durationInSeconds)
                        .mergeStyle(TextFormatting.BLUE));
            }
            if(cooldownInSeconds > 0) {
                list.add(new TranslationTextComponent(
                        "artifact.dungeons_gear.cooldown", cooldownInSeconds)
                        .mergeStyle(TextFormatting.BLUE));
            }
        }
    }

    public static List<String> getAbilities(ItemStack itemStack){
        List<String> abilities = new ArrayList<>();
        checkRangedWeapon(itemStack, abilities);
        checkArmor(itemStack, abilities);
        checkMeleeWeapon(itemStack, abilities);
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
        }
    }

    private static void checkMeleeWeapon(ItemStack itemStack, List<String> abilities) {
        if (itemStack.getItem() instanceof IMeleeWeapon) {
            IMeleeWeapon meleeWeapon = (IMeleeWeapon) itemStack.getItem();
            if (meleeWeapon.boostsAttackSpeed(itemStack)) {
                abilities.add("boosts_attack_speed");
            }
            if (meleeWeapon.hasBusyBeeBuiltIn(itemStack)) {
                abilities.add("busy_bee");
            }
            if (meleeWeapon.hasChainsBuiltIn(itemStack)) {
                abilities.add("chains");
            }
            if (meleeWeapon.hasContinuousAttacks(itemStack)) {
                abilities.add("continuous_attacks");
            }
            if (meleeWeapon.hasCommittedBuiltIn(itemStack)) {
                abilities.add("committed");
            }
            if (meleeWeapon.hasCriticalHitBuiltIn(itemStack)) {
                abilities.add("critical_hit");
            }
            if (meleeWeapon.hasDynamoBuiltIn(itemStack)) {
                abilities.add("dynamo");
            }
            if (meleeWeapon.canDualWield(itemStack)) {
                abilities.add("dual_wield");
            }
            if (meleeWeapon.hasEchoBuiltIn(itemStack)) {
                abilities.add("echo");
            }
            if (meleeWeapon.hasEnigmaResonatorBuiltIn(itemStack)) {
                abilities.add("enigma_resonator");
            }
            if (meleeWeapon.hasExplodingBuiltIn(itemStack)) {
                abilities.add("exploding");
            }
            if (meleeWeapon.hasFastThrusts(itemStack)) {
                abilities.add("fast_thrusts");
            }
            if (meleeWeapon.hasFireAspectBuiltIn(itemStack)) {
                abilities.add("fire_aspect");
            }
            if (meleeWeapon.hasFortuneBuiltIn(itemStack)) {
                abilities.add("fortune");
            }
            if (meleeWeapon.hasFreezingBuiltIn(itemStack)) {
                abilities.add("freezing");
            }
            if (meleeWeapon.hasGravityBuiltIn(itemStack)) {
                abilities.add("gravity");
            }
            if (meleeWeapon.hasGreatSplash(itemStack)) {
                abilities.add("great_splash");
            }
            if (meleeWeapon.hasIllagersBaneBuiltIn(itemStack)) {
                abilities.add("illagers_bane");
            }
            if (meleeWeapon.hasKnockbackBuiltIn(itemStack)) {
                abilities.add("knockback");
            }
            if (meleeWeapon.hasLeechingBuiltIn(itemStack)) {
                abilities.add("leeching");
            }
            if (meleeWeapon.hasPoisonCloudBuiltIn(itemStack)) {
                abilities.add("poison_cloud");
            }
            if (meleeWeapon.hasProspectorBuiltIn(itemStack)) {
                abilities.add("prospector");
            }
            if (meleeWeapon.hasRadianceBuiltIn(itemStack)) {
                abilities.add("radiance");
            }
            if (meleeWeapon.hasRampagingBuiltIn(itemStack)) {
                abilities.add("rampaging");
            }
            if (meleeWeapon.hasReliableCombo(itemStack)) {
                abilities.add("reliable_combo");
            }
            if (meleeWeapon.hasRelentlessCombo(itemStack)) {
                abilities.add("relentless_combo");
            }
            if (meleeWeapon.hasRushdownBuiltIn(itemStack)) {
                abilities.add("rushdown");
            }
            if (meleeWeapon.hasSharpnessBuiltIn(itemStack)) {
                abilities.add("sharpness");
            }
            if (meleeWeapon.hasShockwaveBuiltIn(itemStack)) {
                abilities.add("shockwave");
            }
            if (meleeWeapon.hasSmiteBuiltIn(itemStack)) {
                abilities.add("smite");
            }
            if (meleeWeapon.hasSoulSiphonBuiltIn(itemStack)) {
                abilities.add("soul_siphon");
            }
            if (meleeWeapon.hasSpinAttack(itemStack)) {
                abilities.add("spin_attack");
            }
            if (meleeWeapon.hasStunningBuiltIn(itemStack)) {
                abilities.add("stunning");
            }
            if (meleeWeapon.hasStylishCombo(itemStack)) {
                abilities.add("stylish_combo");
            }
            if (meleeWeapon.hasSwirlingBuiltIn(itemStack)) {
                abilities.add("swirling");
            }
            if (meleeWeapon.hasThrustAttack(itemStack)) {
                abilities.add("thrust_attack");
            }
            if (meleeWeapon.hasThunderingBuiltIn(itemStack)) {
                abilities.add("thundering");
            }
            if (meleeWeapon.hasWeakeningBuiltIn(itemStack)) {
                abilities.add("weakening");
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
        if (itemStack.getItem() instanceof IMeleeWeapon) {
            IMeleeWeapon meleeWeapon = (IMeleeWeapon) itemStack.getItem();
            if (meleeWeapon.canOverheat(itemStack)) {
                penalties.add("can_overheat");
            }
        }
        if (itemStack.getItem() instanceof IRangedWeapon) {
            IRangedWeapon rangedWeapon = (IRangedWeapon) itemStack.getItem();
            if (rangedWeapon.hasSlowFireRate(itemStack)) {
                penalties.add("slow_fire_rate");
            }
        }
        return penalties;
    }

}
