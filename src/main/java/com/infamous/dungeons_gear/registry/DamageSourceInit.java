package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.entities.IceCloudEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import java.util.Random;

public class DamageSourceInit {

	static Random rand = new Random();
	
	public static DamageSource iceChunk(IceCloudEntity p_233549_0_, Entity p_233549_1_) {
		return (new IndirectEntityDamageSource("ice_chunk", p_233549_0_, p_233549_1_)).setExplosion();
	}
	
}
