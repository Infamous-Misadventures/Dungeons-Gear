package com.infamous.dungeons_gear.damagesources;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.EntityDamageSource;

public class OffhandAttackDamageSource extends EntityDamageSource {

    public OffhandAttackDamageSource(Entity damageSourceEntityIn) {
        super("player", damageSourceEntityIn);
    }
}
