package com.infamous.dungeons_gear.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public final class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static final RegistryObject<EntityType<IceCloudEntity>> ICE_CLOUD = ENTITY_TYPES.register("ice_cloud", () ->
            EntityType.Builder.<IceCloudEntity>of(IceCloudEntity::new, EntityClassification.MISC)
                    .fireImmune()
                    .sized(2.0F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .setCustomClientFactory((spawnEntity,world) -> new IceCloudEntity(world))
                    .build(new ResourceLocation(MODID, "ice_cloud").toString())
    );

    public static final RegistryObject<EntityType<BuzzyNestEntity>> BUZZY_NEST = ENTITY_TYPES.register("buzzy_nest", () ->
            EntityType.Builder.of(BuzzyNestEntity::new, EntityClassification.MISC)
                    .fireImmune()
                    .sized(2.0F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build(new ResourceLocation(MODID, "buzzy_nest").toString())
    );

    public static final RegistryObject<EntityType<TotemOfShieldingEntity>> TOTEM_OF_SHIELDING = ENTITY_TYPES.register("totem_of_shielding", () ->
            EntityType.Builder.of(TotemOfShieldingEntity::new, EntityClassification.MISC)
                    .fireImmune()
                    .sized(2.0F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build(new ResourceLocation(MODID, "totem_of_shielding").toString())
    );

    public static final RegistryObject<EntityType<TotemOfRegenerationEntity>> TOTEM_OF_REGENERATION = ENTITY_TYPES.register("totem_of_regeneration", () ->
            EntityType.Builder.of(TotemOfRegenerationEntity::new, EntityClassification.MISC)
                    .fireImmune()
                    .sized(2.0F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build(new ResourceLocation(MODID, "totem_of_shielding").toString())
    );

    public static final RegistryObject<EntityType<BeamEntity>> BEAM_ENTITY = ENTITY_TYPES.register("beam_entity", () ->
            EntityType.Builder.<BeamEntity>of(BeamEntity::new, EntityClassification.MISC)
                    .fireImmune()
                    .setShouldReceiveVelocityUpdates(false)
                    .sized(2.0F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build(new ResourceLocation(MODID, "beam_entity").toString())
    );

}
