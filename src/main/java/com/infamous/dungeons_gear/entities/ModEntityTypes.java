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
            EntityType.Builder.<IceCloudEntity>create(IceCloudEntity::new, EntityClassification.MISC)
                    .immuneToFire()
                    .size(2.0F, 1.0F)
                    .trackingRange(6)
                    .func_233608_b_(2)
                    .setCustomClientFactory((spawnEntity,world) -> new IceCloudEntity(world))
                    .build(new ResourceLocation(MODID, "ice_cloud").toString())
    );

}
