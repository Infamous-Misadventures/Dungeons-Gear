package com.infamous.dungeons_gear.compat;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraftforge.fml.ModList;

public class DungeonsGearCompatibility {
    public static boolean warDance;
    public static boolean saveYourPets;

    public static void checkCompatStatus() {
        warDance = ModList.get().isLoaded("wardance") && DungeonsGearConfig.ENABLE_WAR_DANCE_COMPAT.get();
        saveYourPets = ModList.get().isLoaded("syp");
    }
}
