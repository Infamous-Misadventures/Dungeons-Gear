package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.item.Item.Properties;

public class PetBatArmorGear extends ArmorGear {

    public PetBatArmorGear(EquipmentSlot slotType, Properties properties, ResourceLocation armorSet, ResourceLocation modelLocation, ResourceLocation textureLocation, ResourceLocation animationFileLocation) {
        super(slotType, properties, armorSet, modelLocation, textureLocation, animationFileLocation);
    }

    public boolean doGivesYouAPetBat() {
        return true;
    }
}
