package com.infamous.dungeons_gear.damagesources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class ElectricShockDamageSource extends EntityDamageSource {

    public ElectricShockDamageSource(Entity damageSourceEntityIn) {
        super("electricShock", damageSourceEntityIn);
    }
}
