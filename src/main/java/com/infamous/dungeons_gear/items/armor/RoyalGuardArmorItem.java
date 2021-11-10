package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Item.Properties;

public class RoyalGuardArmorItem extends DarkArmorItem{
    public RoyalGuardArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties, unique);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return DungeonsGear.MODID + ":textures/models/armor/royal_guard_armor.png";
    }
}
