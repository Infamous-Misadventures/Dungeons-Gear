package com.infamous.dungeons_gear.items.interfaces;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IMeleeWeapon<T extends Item> {

    boolean isUnique();

    // Non-enchantment abilities
    default boolean canDualWield(ItemStack stack){
        return false;
    }
    default boolean hasSpinAttack(ItemStack stack){ return false;}
    default boolean hasGreatSplash(ItemStack stack){ return false;}
    default boolean hasContinuousAttacks(ItemStack stack){ return false;}
    default boolean canOverheat(ItemStack stack){ return this.hasContinuousAttacks(stack);}
    default boolean hasRapidSlashes(ItemStack stack){ return false;}
    default boolean dealsAbsorbedDamageDuringAttackCombo(ItemStack stack){ return false;}

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

    default boolean hasPoisonCloudBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasThunderingBuiltIn(ItemStack stack){
        return false;
    }
    default boolean hasGravityBuiltIn(ItemStack stack){
        return false;
    }

}
