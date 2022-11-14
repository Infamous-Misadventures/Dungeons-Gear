package com.infamous.dungeons_gear.utilties;


import com.google.common.collect.ImmutableList;
import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class MissingMappings {

    private static Map<String, Item> toRemapItems = new HashMap<>();

    private static void init() {
        toRemapItems.put("dungeons_gear:archers_hood", ItemRegistry.ARCHERS_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:archers_vest", ItemRegistry.ARCHERS_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:battle_robe", ItemRegistry.BATTLE_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:ember_hat", ItemRegistry.EMBER_ROBES.getHead().get());
        toRemapItems.put("dungeons_gear:ember_robe", ItemRegistry.EMBER_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:evocation_hat", ItemRegistry.EVOCATION_ROBES.getHead().get());
        toRemapItems.put("dungeons_gear:evocation_robe", ItemRegistry.EVOCATION_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:fox_hood", ItemRegistry.FOX_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:fox_vest", ItemRegistry.FOX_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:hunters_vest", ItemRegistry.HUNTERS_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:ocelot_hood", ItemRegistry.OCELOT_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:ocelot_vest", ItemRegistry.OCELOT_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:shadow_walker_hood", ItemRegistry.SHADOW_WALKER_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:shadow_walker_vest", ItemRegistry.SHADOW_WALKER_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:soul_hood", ItemRegistry.SOUL_ROBES.getHead().get());
        toRemapItems.put("dungeons_gear:soul_robe", ItemRegistry.SOUL_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:souldancer_hood", ItemRegistry.SOULDANCER_ROBES.getHead().get());
        toRemapItems.put("dungeons_gear:souldancer_robe", ItemRegistry.SOULDANCER_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:spider_hood", ItemRegistry.SPIDER_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:spider_vest", ItemRegistry.SPIDER_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:thief_hood", ItemRegistry.THIEF_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:thief_vest", ItemRegistry.THIEF_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:wolf_hood", ItemRegistry.WOLF_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:wolf_vest", ItemRegistry.WOLF_ARMOR.getChest().get());
    }

    @SubscribeEvent
    public static void MissingMappingsItem(RegistryEvent.MissingMappings<Item> event){
        if(toRemapItems.isEmpty()) init();
        ImmutableList<RegistryEvent.MissingMappings.Mapping<Item>> allMappings = event.getAllMappings();
        for(RegistryEvent.MissingMappings.Mapping<Item> mapping : allMappings){
            String name = mapping.key.toString().toUpperCase();
            if(toRemapItems.containsKey(name)){
                mapping.remap(toRemapItems.get(name));
            }
        }
    }
}
