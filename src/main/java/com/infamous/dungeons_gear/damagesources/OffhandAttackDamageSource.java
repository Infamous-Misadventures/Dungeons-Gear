package com.infamous.dungeons_gear.damagesources;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class OffhandAttackDamageSource extends EntityDamageSource {

    public OffhandAttackDamageSource(Entity damageSourceEntityIn) {
        super("player", damageSourceEntityIn);
    }
}
