package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class FreezingResistanceArmorGear extends ArmorGear {


    public FreezingResistanceArmorGear(EquipmentSlotType slotType, Properties properties, ResourceLocation armorSet, ResourceLocation modelLocation, ResourceLocation textureLocation, ResourceLocation animationFileLocation) {
        super(slotType, properties, armorSet, modelLocation, textureLocation, animationFileLocation);
    }

    public double getFreezingResistance() {
        return 25;
    }
}
