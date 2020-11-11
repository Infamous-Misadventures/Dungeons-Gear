package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.DeferredItemInit;

public class WeaponAttributeHandler {

    public static void setWeaponAttributeModifiers(){
        SpearItem.setAttributeModifierMultimap((SpearItem) DeferredItemInit.SPEAR.get());
        SpearItem.setAttributeModifierMultimap((SpearItem) DeferredItemInit.FORTUNE_SPEAR.get());
        SpearItem.setAttributeModifierMultimap((SpearItem) DeferredItemInit.WHISPERING_SPEAR.get());

        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) DeferredItemInit.GLAIVE.get());
        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) DeferredItemInit.VENOM_GLAIVE.get());
        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) DeferredItemInit.GRAVE_BANE.get());

        WhipItem.setAttributeModifierMultimap((WhipItem) DeferredItemInit.WHIP.get());
        WhipItem.setAttributeModifierMultimap((WhipItem) DeferredItemInit.VINE_WHIP.get());
    }
}
