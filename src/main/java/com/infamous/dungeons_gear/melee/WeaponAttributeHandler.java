package com.infamous.dungeons_gear.melee;

import com.infamous.dungeons_gear.init.ItemRegistry;

public class WeaponAttributeHandler {

    public static void setWeaponAttributeModifiers(){
        SpearItem.setAttributeModifierMultimap((SpearItem) ItemRegistry.SPEAR.get());
        SpearItem.setAttributeModifierMultimap((SpearItem) ItemRegistry.FORTUNE_SPEAR.get());
        SpearItem.setAttributeModifierMultimap((SpearItem) ItemRegistry.WHISPERING_SPEAR.get());

        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) ItemRegistry.GLAIVE.get());
        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) ItemRegistry.VENOM_GLAIVE.get());
        GlaiveItem.setAttributeModifierMultimap((GlaiveItem) ItemRegistry.GRAVE_BANE.get());

//        DaggerItem.setAttributeModifierMultimap((DaggerItem) ItemRegistry.FANG_OF_FROST.get());
//        DaggerItem.setAttributeModifierMultimap((DaggerItem) ItemRegistry.DAGGER.get());
//        DaggerItem.setAttributeModifierMultimap((DaggerItem) ItemRegistry.MOON_DAGGER.get());
//        DaggerItem.setAttributeModifierMultimap((DaggerItem) ItemRegistry.SHEAR_DAGGER.get());
//
//        GauntletItem.setAttributeModifierMultimap((GauntletItem) ItemRegistry.GAUNTLET.get());
//        GauntletItem.setAttributeModifierMultimap((GauntletItem) ItemRegistry.SOUL_FIST.get());
//        GauntletItem.setAttributeModifierMultimap((GauntletItem) ItemRegistry.FIGHTERS_BINDING.get());
//        GauntletItem.setAttributeModifierMultimap((GauntletItem) ItemRegistry.MAULER.get());
//
//        SickleItem.setAttributeModifierMultimap((SickleItem) ItemRegistry.SICKLE.get());
//        SickleItem.setAttributeModifierMultimap((SickleItem) ItemRegistry.THE_LAST_LAUGH.get());
//        SickleItem.setAttributeModifierMultimap((SickleItem) ItemRegistry.NIGHTMARES_BITE.get());

        WhipItem.setAttributeModifierMultimap((WhipItem) ItemRegistry.WHIP.get());
        WhipItem.setAttributeModifierMultimap((WhipItem) ItemRegistry.VINE_WHIP.get());
    }
}
