package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.items.WeaponList;

public class WeaponAttributeHandler {

    public static void setWeaponAttributeModifiers(){
        SpearItem.setAttributeModifierMultimap((SpearItem) WeaponList.SPEAR);
        SpearItem.setAttributeModifierMultimap((SpearItem) WeaponList.FORTUNE_SPEAR);
        SpearItem.setAttributeModifierMultimap((SpearItem) WeaponList.WHISPERING_SPEAR);

        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) WeaponList.GLAIVE);
        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) WeaponList.VENOM_GLAIVE);
        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) WeaponList.GRAVE_BANE);

        WhipItem.setAttributeModifierMultimap((WhipItem) WeaponList.WHIP);
        WhipItem.setAttributeModifierMultimap((WhipItem) WeaponList.VINE_WHIP);
    }
}
