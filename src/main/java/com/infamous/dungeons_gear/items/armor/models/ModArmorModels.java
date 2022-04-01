package com.infamous.dungeons_gear.items.armor.models;

import com.infamous.dungeons_gear.items.armor.models.old_models.ArchersArmorModel;
import com.infamous.dungeons_gear.items.armor.models.old_models.HuntersArmorModel;
import com.infamous.dungeons_libraries.items.gearconfig.client.ArmorGearModels;

import static com.infamous.dungeons_gear.registry.ItemRegistry.*;

public class ModArmorModels {

    public static void setupArmorModels(){
        ArmorGearModels.addModel(HUNTERS_ARMOR.get().getRegistryName(), HuntersArmorModel::new);
        ArmorGearModels.addModel(ARCHERS_ARMOR.get().getRegistryName(), ArchersArmorModel::new);
        ArmorGearModels.addModel(ARCHERS_ARMOR_HOOD.get().getRegistryName(), ArchersArmorModel::new);
    }
}
