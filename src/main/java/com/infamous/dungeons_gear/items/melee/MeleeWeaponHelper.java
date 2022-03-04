package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_libraries.items.interfaces.IMeleeWeapon;
import com.infamous.dungeons_gear.registry.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.stream.Collectors;

public class MeleeWeaponHelper {
    public static List<Item> getMeleeWeaponList(boolean unique){
        return ItemRegistry.MELEE_WEAPONS.values().stream().map(RegistryObject::get).filter(entry -> entry instanceof IMeleeWeapon && ((IMeleeWeapon<?>) entry).isUnique() == unique).collect(Collectors.toList());
    }
}
