package com.infamous.dungeons_gear.capabilities.combo;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

import static com.infamous.dungeons_gear.capabilities.ModCapabilities.COMBO_CAPABILITY;

public class ComboHelper {

    public static LazyOptional<Combo> getComboCapabilityLazy(Entity entity)
    {
        if(COMBO_CAPABILITY == null) {
            return LazyOptional.empty();
        }
        LazyOptional<Combo> lazyCap = entity.getCapability(COMBO_CAPABILITY);
        return lazyCap;
    }

    public static Combo getComboCapability(Entity entity)
    {
        LazyOptional<Combo> lazyCap = entity.getCapability(COMBO_CAPABILITY);
        if (lazyCap.isPresent()) {
            return lazyCap.orElseThrow(() -> new IllegalStateException("Couldn't get the Combo capability from the Entity!"));
        }
        return null;
    }
}
