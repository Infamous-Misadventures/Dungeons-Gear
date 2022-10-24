package com.infamous.dungeons_gear.registry;

import java.util.Random;

import com.infamous.dungeons_gear.entities.IceCloudEntity;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class DamageSourceInit {

	static Random rand = new Random();
	
	public static DamageSource iceChunk(IceCloudEntity p_233549_0_, Entity p_233549_1_) {
		return (new IndirectEntityDamageSource("ice_chunk", p_233549_0_, p_233549_1_)).setExplosion();
	}
	
}
