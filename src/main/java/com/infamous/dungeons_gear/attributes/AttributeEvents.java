package com.infamous.dungeons_gear.attributes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.stream.Collectors;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.registry.AttributeRegistry.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class AttributeEvents {

    @SubscribeEvent
    public static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event){
        addAttributeToPlayer(event, ROLL_COOLDOWN.get());
        addAttributeToPlayer(event, ROLL_LIMIT.get());
        addAttributeToPlayer(event, ATTACK_REACH.get());
    }

    private static void addAttributeToAll(EntityAttributeModificationEvent event, Attribute attribute) {
        List<EntityType<? extends LivingEntity>> entitiesWithoutAttribute= event.getTypes().stream().filter(entityType -> !event.has(entityType, attribute)).collect(Collectors.toList());
        entitiesWithoutAttribute.forEach(entityType -> event.add(entityType, attribute, attribute.getDefaultValue()));
    }

    private static void addAttributeToPlayer(EntityAttributeModificationEvent event, Attribute attribute) {
        List<EntityType<? extends LivingEntity>> entitiesWithoutAttribute= event.getTypes().stream().filter(entityType -> !event.has(entityType, attribute) && entityType == EntityType.PLAYER).collect(Collectors.toList());
        entitiesWithoutAttribute.forEach(entityType -> event.add(entityType, attribute, attribute.getDefaultValue()));
    }
}
