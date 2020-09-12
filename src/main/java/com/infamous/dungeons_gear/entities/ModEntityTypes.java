package com.infamous.dungeons_gear.entities;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.artifacts.corruptedbeacon.BeamEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

public final class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static final String BEAM_NAME = "beam";
    public static final RegistryObject<EntityType<BeamEntity>> BEAM = ENTITY_TYPES.register(BEAM_NAME, () ->
            EntityType.Builder.<BeamEntity>create(BeamEntity::new, EntityClassification.MISC)
                    .size(0.5F,1)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(24)
                    .setUpdateInterval(60)
                    .setCustomClientFactory((spawnEntity,world) -> new BeamEntity(world))
                    .build(new ResourceLocation(DungeonsGear.MODID, BEAM_NAME).toString())
    );

    /*
    @SubscribeEvent
        public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> entityRegistryEvent) {
            entityRegistryEvent.getRegistry().registerAll(
                    EntityType.Builder.<BeamEntity>create(BeamEntity::new,EntityClassification.MISC).size(0.5F,1)
                            .setShouldReceiveVelocityUpdates(true)
                            .setTrackingRange(24)
                            .setUpdateInterval(60)
                            .setCustomClientFactory((spawnEntity,world) -> new BeamEntity(world))
                            .build("beam")
                            .setRegistryName(MODID,"beam")
            );
        }
     */

}
