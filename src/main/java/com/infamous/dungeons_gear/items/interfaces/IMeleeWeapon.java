package com.infamous.dungeons_gear.items.interfaces;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IMeleeWeapon<T extends Item> {

    // Non-enchantment abilities
    default boolean canDualWield(ItemStack stack){
        return false;
    }
    default boolean boostsAttackSpeed(ItemStack stack) { return false; }
    default boolean hasSpinAttack(ItemStack stack){ return false;}
    default boolean hasFastThrusts(ItemStack stack) { return false; }
    default boolean hasReliableCombo(ItemStack stack){ return false;}
    default boolean hasRelentlessCombo(ItemStack stack){ return false;}
    default boolean hasGreatSplash(ItemStack stack){ return false;}
    default boolean hasThrustAttack(ItemStack stack){ return false;}
    default boolean hasStylishCombo(ItemStack stack){ return false;}
    default boolean hasContinuousAttacks(ItemStack stack){ return false;}
    default boolean canOverheat(ItemStack stack){ return this.hasContinuousAttacks(stack);}
    default boolean hasRapidSlashes(ItemStack stack){ return false;}
    default boolean dealsAbsorbedDamageDuringAttackCombo(ItemStack stack){ return false;}

    // Enchantment abilities
    default boolean hasKnockbackBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasSharpnessBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasLeechingBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasDynamoBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasRampagingBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasWeakeningBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasFreezingBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasEnigmaResonatorBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasSwirlingBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasExplodingBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasShockwaveBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasFireAspectBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasStunningBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasProspectorBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasCriticalHitBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasSmiteBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasPoisonCloudBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasThunderingBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasGravityBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasChainsBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasRadianceBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasBusyBeeBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasSoulSiphonBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasCommittedBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasEchoBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasFortuneBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasRushdownBuiltIn(ItemStack stack){return false;}
    default boolean hasIllagersBaneBuiltIn(ItemStack stack){return false;}
    default boolean hasRefreshmentBuiltIn(ItemStack stack){
        return false;
    }

}
