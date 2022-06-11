package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import com.infamous.dungeons_libraries.items.materials.armor.ArmorMaterialBaseType;
import com.infamous.dungeons_libraries.items.materials.armor.ArmorMaterials;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.stream.Collectors;

public class ArmorHelper {
    public static List<Item> getArmorList(List<ArmorMaterialBaseType> baseTypes, boolean unique){
        List<IArmorMaterial> armorMaterials = baseTypes.stream().flatMap(baseType -> ArmorMaterials.getArmorMaterials(baseType).stream()).collect(Collectors.toList());
        return ItemRegistry.ARMORS.values().stream().map(RegistryObject::get).filter(entry -> entry instanceof ArmorGear && ((ArmorGear) entry).isUnique() == unique && armorMaterials.contains(((ArmorItem) entry).getMaterial())).collect(Collectors.toList());
    }
}
