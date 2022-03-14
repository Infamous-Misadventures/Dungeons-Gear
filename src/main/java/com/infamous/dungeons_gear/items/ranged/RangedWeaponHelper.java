package com.infamous.dungeons_gear.items.ranged;

import com.infamous.dungeons_gear.registry.ItemRegistry;
import com.infamous.dungeons_libraries.items.interfaces.IRangedWeapon;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.stream.Collectors;

public class RangedWeaponHelper {
    public static List<Item> getRangedWeaponList(boolean unique){
        return ItemRegistry.RANGED_WEAPONS.values().stream().map(RegistryObject::get).filter(entry -> entry instanceof IRangedWeapon && ((IRangedWeapon) entry).isUnique() == unique).collect(Collectors.toList());
    }
}
