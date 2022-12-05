package com.infamous.dungeons_gear.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public class PotionInit {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, MODID);

    public static RegistryObject<Potion> SHORT_STRENGTH = POTIONS.register("short_strength", () -> new Potion("strength", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600)));
    public static RegistryObject<Potion> SHORT_SWIFTNESS = POTIONS.register("short_speed", () -> new Potion("swiftness", new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400)));
    public static RegistryObject<Potion> SHADOW_BREW = POTIONS.register("shadow_brew", () -> new Potion("shadow_brew", new MobEffectInstance(MobEffects.INVISIBILITY, 200)));
    public static RegistryObject<Potion> OAKWOOD_BREW = POTIONS.register("oakwood_brew", () -> new Potion("oakwood_brew", new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300)));
}
