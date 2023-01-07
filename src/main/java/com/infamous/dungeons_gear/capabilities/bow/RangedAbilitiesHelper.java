package com.infamous.dungeons_gear.capabilities.bow;

import net.minecraft.world.item.ItemStack;

import static com.infamous.dungeons_gear.capabilities.ModCapabilities.RANGED_ABILITIES_CAPABILITY;


public class RangedAbilitiesHelper {

    public static RangedAbilities getRangedAbilitiesCapability(ItemStack itemStack) {
        return itemStack.getCapability(RANGED_ABILITIES_CAPABILITY).orElse(new RangedAbilities());
    }

}
