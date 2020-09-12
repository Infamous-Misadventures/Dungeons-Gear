package com.infamous.dungeons_gear.damagesources;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class SwirlingDamageSource extends EntityDamageSource {

    public SwirlingDamageSource(Entity damageSourceEntityIn) {
        super("swirlingAttack", damageSourceEntityIn);
    }
}
