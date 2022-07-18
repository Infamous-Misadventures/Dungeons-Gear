package com.infamous.dungeons_gear.damagesources;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class ElectricShockDamageSource extends EntityDamageSource {

    public ElectricShockDamageSource(Entity damageSourceEntityIn) {
        super("electricShock", damageSourceEntityIn);
    }
}
