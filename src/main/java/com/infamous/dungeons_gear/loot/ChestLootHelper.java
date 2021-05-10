package com.infamous.dungeons_gear.loot;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

import static com.infamous.dungeons_gear.init.ItemRegistry.commonMetalArmorMap;
import static com.infamous.dungeons_gear.init.ItemRegistry.commonLeatherArmorMap;
import static com.infamous.dungeons_gear.init.ItemRegistry.uniqueMetalArmorMap;
import static com.infamous.dungeons_gear.init.ItemRegistry.uniqueLeatherArmorMap;
import static com.infamous.dungeons_gear.init.ItemRegistry.commonRangedWeaponMap;
import static com.infamous.dungeons_gear.init.ItemRegistry.uniqueRangedWeaponMap;
import static com.infamous.dungeons_gear.init.ItemRegistry.commonWeaponMap;
import static com.infamous.dungeons_gear.init.ItemRegistry.uniqueWeaponMap;
import static com.infamous.dungeons_gear.init.ItemRegistry.artifactMap;

public class ChestLootHelper {

    private static Random myRNG = new Random();

    public static List<ItemStack> generateLootFromValues(double uniqueItemChance, double artifactChance){
        Item commonMeleeWeapon = getRandomSetElement(commonWeaponMap.keySet());
        Item uniqueMeleeWeapon = getRandomSetElement(uniqueWeaponMap.keySet());

        Item commonRangedWeapon = getRandomSetElement(commonRangedWeaponMap.keySet());
        Item uniqueRangedWeapon = getRandomSetElement(uniqueRangedWeaponMap.keySet());

        Item commonLeatherArmor = getRandomSetElement(commonLeatherArmorMap.keySet());
        Item uniqueLeatherArmor = getRandomSetElement(uniqueLeatherArmorMap.keySet());

        Item commonMetalArmor = getRandomSetElement(commonMetalArmorMap.keySet());
        Item uniqueMetalArmor = getRandomSetElement(uniqueMetalArmorMap.keySet());

        Item artifact = getRandomSetElement(artifactMap.keySet());

        List<ItemStack> generatedLoot = new ArrayList<>(Collections.emptyList());
        pickFromTwoTypes(uniqueItemChance, commonMeleeWeapon, uniqueMeleeWeapon, commonRangedWeapon, uniqueRangedWeapon, generatedLoot);
        pickFromTwoTypes(uniqueItemChance, commonLeatherArmor, uniqueLeatherArmor, commonMetalArmor, uniqueMetalArmor, generatedLoot);
        addArtifact(artifactChance, artifact, generatedLoot);

        return generatedLoot;
    }

    private static void addArtifact(double artifactChance, Item artifact, List<ItemStack> generatedLoot) {
        if(myRNG.nextDouble() < artifactChance){
            generatedLoot.add(new ItemStack(artifact));
        }
    }

    private static void pickFromTwoTypes(double uniqueItemChance, Item commonItem1, Item uniqueItem1, Item commonItem2, Item uniqueItem2, List<ItemStack> generatedLoot) {
        if(myRNG.nextBoolean()){
            addCommonOrUnique(uniqueItemChance, commonItem1, uniqueItem1, generatedLoot);
        }
        else{
            addCommonOrUnique(uniqueItemChance, commonItem2, uniqueItem2, generatedLoot);
        }
    }

    private static void addCommonOrUnique(double uniqueItemChance, Item commonItem, Item uniqueItem, List<ItemStack> generatedLoot) {
        if(myRNG.nextDouble() < uniqueItemChance){
            generatedLoot.add(new ItemStack(uniqueItem));
        }
        else{
            generatedLoot.add(new ItemStack(commonItem));
        }
    }

    public static Item getRandomSetElement(Set<Item> set) {
        int index = myRNG.nextInt(set.size());
        Iterator<Item> iter = set.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }
        return iter.next();
    }
}
