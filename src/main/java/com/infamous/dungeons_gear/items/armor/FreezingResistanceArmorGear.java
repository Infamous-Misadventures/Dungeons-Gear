package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

public class FreezingResistanceArmorGear extends ArmorGear {

    public FreezingResistanceArmorGear(EquipmentSlot slotType, Properties properties, ResourceLocation armorSet, ResourceLocation modelLocation, ResourceLocation textureLocation, ResourceLocation animationFileLocation) {
        super(slotType, properties, armorSet, modelLocation, textureLocation, animationFileLocation);
    }

    public double getFreezingResistance() {
        return 25;
    }
}
