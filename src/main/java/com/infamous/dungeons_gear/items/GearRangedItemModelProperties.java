package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_libraries.items.RangedItemModelProperties;

public class GearRangedItemModelProperties {
    public static void init(){
        ItemRegistry.RANGED_WEAPONS.forEach((resourceLocation, itemRegistryObject) ->  RangedItemModelProperties.addRangedModelProperties(itemRegistryObject));
    }
}
