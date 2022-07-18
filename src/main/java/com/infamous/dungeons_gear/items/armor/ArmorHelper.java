package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import com.infamous.dungeons_libraries.items.materials.armor.ArmorMaterialBaseType;
import com.infamous.dungeons_libraries.items.materials.armor.DungeonsArmorMaterials;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.stream.Collectors;

public class ArmorHelper {
    public static List<Item> getArmorList(List<ArmorMaterialBaseType> baseTypes, boolean unique){
        List<ArmorMaterial> armorMaterials = baseTypes.stream().flatMap(baseType -> DungeonsArmorMaterials.getArmorMaterials(baseType).stream()).collect(Collectors.toList());
        return ItemRegistry.ARMORS.values().stream().map(RegistryObject::get).filter(entry -> entry instanceof ArmorGear && ((ArmorGear) entry).isUnique() == unique && armorMaterials.contains(((ArmorItem) entry).getMaterial())).collect(Collectors.toList());
    }
}
