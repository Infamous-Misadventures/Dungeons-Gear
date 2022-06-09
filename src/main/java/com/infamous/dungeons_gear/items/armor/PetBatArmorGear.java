package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.util.ResourceLocation;

public class PetBatArmorGear extends ArmorGear {

    public PetBatArmorGear(EquipmentSlotType slotType, Properties properties, ResourceLocation texture) {
        super(slotType, properties, texture);
    }

    public boolean doGivesYouAPetBat() {
        return true;
    }
}
