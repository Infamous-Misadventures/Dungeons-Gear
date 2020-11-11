package com.infamous.dungeons_gear.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RoyalGuardArmorItem extends DarkArmorItem{
    public RoyalGuardArmorItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Item.Properties properties, boolean unique) {
        super(armorMaterial, slotType, properties, unique);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return DungeonsGear.MODID + ":textures/models/armor/royal_guard_armor.png";
    }
}
