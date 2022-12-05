package com.infamous.dungeons_gear.utilties;


import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.registry.ItemInit;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class MissingMappings {

    private static Map<String, Item> toRemapItems = new HashMap<>();

    private static void init() {
        toRemapItems.put("dungeons_gear:archers_hood", ItemInit.ARCHERS_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:archers_vest", ItemInit.ARCHERS_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:battle_robe", ItemInit.BATTLE_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:ember_hat", ItemInit.EMBER_ROBES.getHead().get());
        toRemapItems.put("dungeons_gear:ember_robe", ItemInit.EMBER_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:evocation_hat", ItemInit.EVOCATION_ROBES.getHead().get());
        toRemapItems.put("dungeons_gear:evocation_robe", ItemInit.EVOCATION_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:fox_hood", ItemInit.FOX_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:fox_vest", ItemInit.FOX_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:hunters_vest", ItemInit.HUNTERS_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:ocelot_hood", ItemInit.OCELOT_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:ocelot_vest", ItemInit.OCELOT_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:shadow_walker_hood", ItemInit.SHADOW_WALKER_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:shadow_walker_vest", ItemInit.SHADOW_WALKER_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:soul_hood", ItemInit.SOUL_ROBES.getHead().get());
        toRemapItems.put("dungeons_gear:soul_robe", ItemInit.SOUL_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:souldancer_hood", ItemInit.SOULDANCER_ROBES.getHead().get());
        toRemapItems.put("dungeons_gear:souldancer_robe", ItemInit.SOULDANCER_ROBES.getChest().get());
        toRemapItems.put("dungeons_gear:spider_hood", ItemInit.SPIDER_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:spider_vest", ItemInit.SPIDER_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:thief_hood", ItemInit.THIEF_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:thief_vest", ItemInit.THIEF_ARMOR.getChest().get());
        toRemapItems.put("dungeons_gear:wolf_hood", ItemInit.WOLF_ARMOR.getHead().get());
        toRemapItems.put("dungeons_gear:wolf_vest", ItemInit.WOLF_ARMOR.getChest().get());
    }

    @SubscribeEvent
    public static void MissingMappingsItem(MissingMappingsEvent event){
        if(toRemapItems.isEmpty()) init();
        List<MissingMappingsEvent.Mapping<Item>> allMappings = event.getAllMappings(ForgeRegistries.ITEMS.getRegistryKey());
        for(MissingMappingsEvent.Mapping<Item> mapping : allMappings){
            String name = mapping.getKey().toString().toUpperCase();
            if(toRemapItems.containsKey(name)){
                mapping.remap(toRemapItems.get(name));
            }
        }
    }
}
