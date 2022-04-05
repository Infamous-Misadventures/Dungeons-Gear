package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.util.ResourceLocation;

public class PetBatArmorGear extends ArmorGear {

    public PetBatArmorGear(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, ResourceLocation texture) {
        super(armorMaterial, slotType, properties, texture);
    }

    public boolean doGivesYouAPetBat() {
        return true;
    }
}
