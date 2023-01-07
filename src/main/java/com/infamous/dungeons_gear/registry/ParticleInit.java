package com.infamous.dungeons_gear.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class ParticleInit {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<SimpleParticleType> ELECTRIC_SHOCK = PARTICLES.register("electric_shock", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SNOWFLAKE = PARTICLES.register("snowflake", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SOUL_DUST = PARTICLES.register("soul_dust", () -> new SimpleParticleType(true));
}
