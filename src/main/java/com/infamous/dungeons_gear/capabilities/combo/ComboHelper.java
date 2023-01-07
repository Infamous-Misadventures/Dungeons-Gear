package com.infamous.dungeons_gear.capabilities.combo;

import net.minecraft.world.entity.Entity;

import static com.infamous.dungeons_gear.capabilities.ModCapabilities.COMBO_CAPABILITY;

public class ComboHelper {

    public static Combo getComboCapability(Entity entity) {
        return entity.getCapability(COMBO_CAPABILITY).orElse(new Combo());
    }
}
