package com.infamous.dungeons_gear.registry;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.infamous.dungeons_gear.entities.*;

import com.infamous.dungeons_gear.items.artifacts.TotemOfSoulProtectionItem;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    
    public static final RegistryObject<EntityType<IceCloudEntity>> ICE_CLOUD = ENTITY_TYPES.register("ice_cloud", () ->
    		EntityType.Builder.<IceCloudEntity>of(IceCloudEntity::new, EntityClassification.MISC)
            		.fireImmune()
            		.sized(2.0F, 1.0F)
            		.clientTrackingRange(6)
            		.updateInterval(1)
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

    public static final RegistryObject<EntityType<TotemOfSoulProtectionEntity>> TOTEM_OF_SOUL_PROTECTION = ENTITY_TYPES.register("totem_of_soul_protection", () ->
            EntityType.Builder.of(TotemOfSoulProtectionEntity::new, EntityClassification.MISC)
                    .fireImmune()
                    .sized(2.0F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build(new ResourceLocation(MODID, "totem_of_soul_protection").toString())
    );

    public static final RegistryObject<EntityType<ArtifactBeamEntity>> BEAM_ENTITY = ENTITY_TYPES.register("beam_entity", () ->
            EntityType.Builder.<ArtifactBeamEntity>of(ArtifactBeamEntity::new, EntityClassification.MISC)
                    .fireImmune()
                    .setShouldReceiveVelocityUpdates(false)
                    .sized(2.0F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build(new ResourceLocation(MODID, "beam_entity").toString())
    );

    public static final RegistryObject<EntityType<FireworksDisplayEntity>> FIREWORKS_DISPLAY = ENTITY_TYPES.register("fireworks_display", () ->
            EntityType.Builder.of(FireworksDisplayEntity::new, EntityClassification.MISC)
                    .fireImmune()
                    .setShouldReceiveVelocityUpdates(false)
                    .sized(2.0F, 1.0F)
                    .clientTrackingRange(6)
                    .updateInterval(2)
                    .build(new ResourceLocation(MODID, "fireworks_display").toString())
    );

    public static final RegistryObject<EntityType<SoulWizardOrbEntity>> SOUL_WIZARD_ORB = ENTITY_TYPES.register("soul_wizard_orb", () ->
    EntityType.Builder.<SoulWizardOrbEntity>of(SoulWizardOrbEntity::new, EntityClassification.MISC)
            .fireImmune()
            .sized(0.3F, 0.3F)
            .updateInterval(1)
            .build(new ResourceLocation(MODID, "soul_wizard_orb").toString())
    );
    
    public static final RegistryObject<EntityType<SoulWizardEntity>> SOUL_WIZARD = ENTITY_TYPES.register("soul_wizard", () ->
    EntityType.Builder.<SoulWizardEntity>of(SoulWizardEntity::new, EntityClassification.MONSTER)
            .sized(0.25F, 1.0F)
            .clientTrackingRange(8)
            .build(new ResourceLocation(MODID, "soul_wizard").toString())
    );
}
