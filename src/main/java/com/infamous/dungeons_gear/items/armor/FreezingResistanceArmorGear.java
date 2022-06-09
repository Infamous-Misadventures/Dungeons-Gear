package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class FreezingResistanceArmorGear extends ArmorGear {

    public FreezingResistanceArmorGear(EquipmentSlotType slotType, Properties properties, ResourceLocation texture) {
        super(slotType, properties, texture);
    }

    public double getFreezingResistance() {
        return 25;
    }
}
