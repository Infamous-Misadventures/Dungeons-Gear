package com.infamous.dungeons_gear.items.armor.models;

import com.infamous.dungeons_gear.items.armor.models.old_models.HuntersArmorModel;
import com.infamous.dungeons_libraries.items.gearconfig.client.ArmorGearModels;
import net.minecraft.data.BlockStateVariantBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

import static com.infamous.dungeons_gear.registry.ItemRegistry.HUNTERS_ARMOR;

public class ModArmorModels {

    public static void setupArmorModels(){
        ArmorGearModels.addModel(HUNTERS_ARMOR.get().getRegistryName(), HuntersArmorModel::new);
    }
}
