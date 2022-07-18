package com.infamous.dungeons_gear.utilties;

import net.minecraft.resources.ResourceLocation;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class GeneralHelper {
    public static ResourceLocation modLoc(String resource) {
        return new ResourceLocation(MODID, resource);
    }
}
