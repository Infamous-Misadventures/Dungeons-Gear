package com.infamous.dungeons_gear.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ArmorSet {
    private final String armorSetId;
    private final RegistryObject<Item> head;
    private final RegistryObject<Item> chest;
    private final RegistryObject<Item> legs;
    private final RegistryObject<Item> feet;

    public ArmorSet(String armorSetId, RegistryObject<Item> head, RegistryObject<Item> chest, RegistryObject<Item> legs, RegistryObject<Item> feet) {
        this.armorSetId = armorSetId;
        this.head = head;
        this.chest = chest;
        this.legs = legs;
        this.feet = feet;
    }

    public String getArmorSetId() {
        return armorSetId;
    }

    public RegistryObject<Item> getHead() {
        return head;
    }

    public RegistryObject<Item> getChest() {
        return chest;
    }

    public RegistryObject<Item> getLegs() {
        return legs;
    }

    public RegistryObject<Item> getFeet() {
        return feet;
    }
}
