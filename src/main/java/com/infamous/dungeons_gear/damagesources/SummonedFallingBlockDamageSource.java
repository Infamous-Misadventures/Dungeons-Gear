package com.infamous.dungeons_gear.damagesources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class SummonedFallingBlockDamageSource extends IndirectEntityDamageSource {

    public SummonedFallingBlockDamageSource(FallingBlockEntity fallingBlockEntity, @Nullable Entity indirectEntity) {
        super("fallingBlock", fallingBlockEntity, indirectEntity);
    }
}
