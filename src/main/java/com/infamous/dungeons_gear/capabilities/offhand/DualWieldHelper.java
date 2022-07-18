package com.infamous.dungeons_gear.capabilities.offhand;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

import static com.infamous.dungeons_gear.capabilities.ModCapabilities.DUAL_WIELD_CAPABILITY;

public class DualWieldHelper {

    public static LazyOptional<DualWield> getDualWieldCapabilityLazy(Entity entity)
    {
        if(DUAL_WIELD_CAPABILITY == null) {
            return LazyOptional.empty();
        }
        LazyOptional<DualWield> lazyCap = entity.getCapability(DUAL_WIELD_CAPABILITY);
        return lazyCap;
    }

    public static DualWield getDualWieldCapability(Entity entity)
    {
        LazyOptional<DualWield> lazyCap = entity.getCapability(DUAL_WIELD_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the Artifact Usage capability from the Entity!"));
        }
        return null;
    }
}
