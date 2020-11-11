package com.infamous.dungeons_gear.damagesources;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class ElectricShockDamageSource extends EntityDamageSource {

    public ElectricShockDamageSource(Entity damageSourceEntityIn) {
        super("electricShock", damageSourceEntityIn);
    }
}
