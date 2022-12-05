package com.infamous.dungeons_gear.items.melee;

import com.infamous.dungeons_gear.registry.ItemInit;
import com.infamous.dungeons_libraries.items.interfaces.IUniqueGear;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.stream.Collectors;

public class MeleeWeaponHelper {
    public static List<Item> getMeleeWeaponList(boolean unique){
        return ItemInit.MELEE_WEAPONS.values().stream().map(RegistryObject::get).filter(entry -> entry instanceof IUniqueGear && ((IUniqueGear) entry).isUnique() == unique).collect(Collectors.toList());
    }
}
