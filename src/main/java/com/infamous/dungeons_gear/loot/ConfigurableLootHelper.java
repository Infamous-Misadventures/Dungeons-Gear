package com.infamous.dungeons_gear.loot;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;

public class ConfigurableLootHelper {

    public static boolean isMeleeWeaponLootEnabled(){
        return DungeonsGearConfig.ENABLE_MELEE_WEAPON_LOOT.get();
    }

    public static boolean isRangedWeaponLootEnabled(){
        return DungeonsGearConfig.ENABLE_RANGED_WEAPON_LOOT.get();
    }

    public static boolean isArmorLootEnabled(){
        return DungeonsGearConfig.ENABLE_ARMOR_LOOT.get();
    }

    public static boolean isArtifactLootEnabled(){
        return DungeonsGearConfig.ENABLE_ARTIFACT_LOOT.get();
    }
}
