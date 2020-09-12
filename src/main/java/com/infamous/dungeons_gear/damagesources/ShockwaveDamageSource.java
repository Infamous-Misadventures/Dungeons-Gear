package com.infamous.dungeons_gear.damagesources;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class ShockwaveDamageSource extends EntityDamageSource {

    public ShockwaveDamageSource(Entity damageSourceEntityIn) {
        super("shockwaveAttack", damageSourceEntityIn);
    }
}
