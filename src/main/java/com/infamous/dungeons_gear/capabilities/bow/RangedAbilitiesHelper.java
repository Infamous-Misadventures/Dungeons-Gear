package com.infamous.dungeons_gear.capabilities.bow;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import static com.infamous.dungeons_gear.capabilities.ModCapabilities.ARTIFACT_USAGE_CAPABILITY;
import static com.infamous.dungeons_gear.capabilities.ModCapabilities.RANGED_ABILITIES_CAPABILITY;


public class RangedAbilitiesHelper {

    public static LazyOptional<RangedAbilities> getRangedAbilitiesCapabilityLazy(ItemStack itemStack)
    {
        if(RANGED_ABILITIES_CAPABILITY == null) {
            return LazyOptional.empty();
        }
        LazyOptional<RangedAbilities> lazyCap = itemStack.getCapability(RANGED_ABILITIES_CAPABILITY);
        return lazyCap;
    }

    public static RangedAbilities getRangedAbilitiesCapability(ItemStack itemStack)
    {
        LazyOptional<RangedAbilities> lazyCap = itemStack.getCapability(RANGED_ABILITIES_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the Artifact Usage capability from the Entity!"));
        }
        return null;
    }

}
