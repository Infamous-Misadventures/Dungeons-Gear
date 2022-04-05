package com.infamous.dungeons_gear.items.armor;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_libraries.items.gearconfig.ArmorGear;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.infamous.dungeons_gear.items.ArmorMaterialList.*;

public class ArmorHelper {
    public static List<IArmorMaterial> LEATHER_MATERIALS = Arrays.asList(VEST, ROBE, PELT, BONE);
    public static List<IArmorMaterial> METAL_MATERIALS = Arrays.asList(LIGHT_PLATE, MEDIUM_PLATE, HEAVY_PLATE);

    public static List<Item> getArmorList(List<IArmorMaterial> materials, boolean unique){
        return ItemRegistry.ARMORS.values().stream().map(RegistryObject::get).filter(entry -> entry instanceof ArmorGear && ((ArmorGear) entry).isUnique() == unique && materials.contains(((ArmorItem) entry).getMaterial())).collect(Collectors.toList());
    }

}
