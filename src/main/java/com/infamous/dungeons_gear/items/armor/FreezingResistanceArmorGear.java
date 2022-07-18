package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.Item.Properties;

public class FreezingResistanceArmorGear extends ArmorGear {

    public FreezingResistanceArmorGear(EquipmentSlot slotType, Properties properties, ResourceLocation texture) {
        super(slotType, properties, texture, texture, texture);
    }

    public double getFreezingResistance() {
        return 25;
    }
}
