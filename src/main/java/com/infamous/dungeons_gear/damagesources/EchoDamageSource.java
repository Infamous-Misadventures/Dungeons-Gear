package com.infamous.dungeons_gear.damagesources;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class EchoDamageSource extends EntityDamageSource {

    public EchoDamageSource(Entity damageSourceEntityIn) {
        super("echoAttack", damageSourceEntityIn);
    }
}
