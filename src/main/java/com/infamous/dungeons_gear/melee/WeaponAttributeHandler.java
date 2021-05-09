package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.DeferredItemInit;
import com.infamous.dungeons_gear.items.WeaponList;

public class WeaponAttributeHandler {

    public static void setWeaponAttributeModifiers(){
        SpearItem.setAttributeModifierMultimap((SpearItem) DeferredItemInit.SPEAR.get());
        SpearItem.setAttributeModifierMultimap((SpearItem) DeferredItemInit.FORTUNE_SPEAR.get());
        SpearItem.setAttributeModifierMultimap((SpearItem) DeferredItemInit.WHISPERING_SPEAR.get());

        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) DeferredItemInit.GLAIVE.get());
        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) DeferredItemInit.VENOM_GLAIVE.get());
        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) DeferredItemInit.GRAVE_BANE.get());

        DaggerItem.setAttributeModifierMultimap((DaggerItem) DeferredItemInit.FANG_OF_FROST.get());
        DaggerItem.setAttributeModifierMultimap((DaggerItem) DeferredItemInit.DAGGER.get());
        DaggerItem.setAttributeModifierMultimap((DaggerItem) DeferredItemInit.MOON_DAGGER.get());
        DaggerItem.setAttributeModifierMultimap((DaggerItem) DeferredItemInit.SHEAR_DAGGER.get());

        GauntletItem.setAttributeModifierMultimap((GauntletItem) DeferredItemInit.GAUNTLET.get());
        GauntletItem.setAttributeModifierMultimap((GauntletItem) DeferredItemInit.SOUL_FIST.get());
        GauntletItem.setAttributeModifierMultimap((GauntletItem) DeferredItemInit.FIGHTERS_BINDING.get());
        GauntletItem.setAttributeModifierMultimap((GauntletItem) DeferredItemInit.MAULER.get());

        SickleItem.setAttributeModifierMultimap((SickleItem) DeferredItemInit.SICKLE.get());
        SickleItem.setAttributeModifierMultimap((SickleItem) DeferredItemInit.THE_LAST_LAUGH.get());
        SickleItem.setAttributeModifierMultimap((SickleItem) DeferredItemInit.NIGHTMARES_BITE.get());

        WhipItem.setAttributeModifierMultimap((WhipItem) DeferredItemInit.WHIP.get());
        WhipItem.setAttributeModifierMultimap((WhipItem) DeferredItemInit.VINE_WHIP.get());
    }
}
