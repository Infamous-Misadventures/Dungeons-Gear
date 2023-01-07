package com.infamous.dungeons_gear.registry;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEventInit {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
            DungeonsGear.MODID);

    public static final RegistryObject<SoundEvent> ICE_CHUNK_IDLE_LOOP = SOUNDS.register("entity.ice_chunk.idle_loop", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.ice_chunk.idle_loop")));
    public static final RegistryObject<SoundEvent> ICE_CHUNK_SUMMONED = SOUNDS.register("entity.ice_chunk.summoned", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.ice_chunk.summoned")));
    public static final RegistryObject<SoundEvent> ICE_CHUNK_FALL = SOUNDS.register("entity.ice_chunk.fall", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.ice_chunk.fall")));
    public static final RegistryObject<SoundEvent> ICE_CHUNK_LAND = SOUNDS.register("entity.ice_chunk.land", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.ice_chunk.land")));

    public static final RegistryObject<SoundEvent> SOUL_WIZARD_APPEAR = SOUNDS.register("entity.soul_wizard.appear", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.soul_wizard.appear")));
    public static final RegistryObject<SoundEvent> SOUL_WIZARD_HURT = SOUNDS.register("entity.soul_wizard.hurt", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.soul_wizard.hurt")));
    public static final RegistryObject<SoundEvent> SOUL_WIZARD_SHOOT = SOUNDS.register("entity.soul_wizard.shoot", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.soul_wizard.shoot")));
    public static final RegistryObject<SoundEvent> SOUL_WIZARD_PROJECTILE_IMPACT = SOUNDS.register("entity.soul_wizard.projectile_impact", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.soul_wizard.projectile_impact")));
    public static final RegistryObject<SoundEvent> SOUL_WIZARD_FLY_LOOP = SOUNDS.register("entity.soul_wizard.fly_loop", () -> new SoundEvent(new ResourceLocation(DungeonsGear.MODID, "entity.soul_wizard.fly_loop")));
}
