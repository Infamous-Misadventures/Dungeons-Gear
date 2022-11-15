package com.infamous.dungeons_gear.capabilities.offhand;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

import static com.infamous.dungeons_gear.capabilities.ModCapabilities.DUAL_WIELD_CAPABILITY;

public class DualWieldHelper {

    public static DualWield getDualWieldCapability(Entity entity)
    {
        return entity.getCapability(DUAL_WIELD_CAPABILITY).orElse(new DualWield());
    }
}
