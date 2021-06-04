package com.infamous.dungeons_gear.loot;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

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

    private static final Random myRNG = new Random();

    public static List<ItemStack> generateLootFromValues(double uniqueItemChance, double artifactChance){
        boolean selectedMeleeWeapon = false;
        boolean selectedRangedWeapon = false;
        boolean selectedArmor = false;
        boolean selectedArtifact = false;

        Item commonMeleeWeapon = Items.AIR;
        Item uniqueMeleeWeapon = Items.AIR;

        if(ConfigurableLootHelper.isMeleeWeaponLootEnabled()){
            commonMeleeWeapon = getRandomSetElement(commonWeaponMap.keySet());
            uniqueMeleeWeapon = getRandomSetElement(uniqueWeaponMap.keySet());
            selectedMeleeWeapon = true;
        }
        Item commonRangedWeapon = Items.AIR;
        Item uniqueRangedWeapon = Items.AIR;

        if(ConfigurableLootHelper.isMeleeWeaponLootEnabled()){
            commonRangedWeapon = getRandomSetElement(commonRangedWeaponMap.keySet());
            uniqueRangedWeapon = getRandomSetElement(uniqueRangedWeaponMap.keySet());
            selectedRangedWeapon = true;
        }

        Item commonLeatherArmor = Items.AIR;
        Item uniqueLeatherArmor = Items.AIR;
        Item commonMetalArmor = Items.AIR;
        Item uniqueMetalArmor = Items.AIR;

        if(ConfigurableLootHelper.isArmorLootEnabled()){
            commonLeatherArmor = getRandomSetElement(commonLeatherArmorMap.keySet());
            uniqueLeatherArmor = getRandomSetElement(uniqueLeatherArmorMap.keySet());

            commonMetalArmor = getRandomSetElement(commonMetalArmorMap.keySet());
            uniqueMetalArmor = getRandomSetElement(uniqueMetalArmorMap.keySet());
            selectedArmor = true;
        }

        Item artifact = Items.AIR;

        if(ConfigurableLootHelper.isArtifactLootEnabled()){
            artifact = getRandomSetElement(artifactMap.keySet());
            selectedArtifact = true;
        }

        List<ItemStack> generatedLoot = new ArrayList<>(Collections.emptyList());

        if(selectedMeleeWeapon && selectedRangedWeapon){
            pickFromTwoTypes(uniqueItemChance, commonMeleeWeapon, uniqueMeleeWeapon, commonRangedWeapon, uniqueRangedWeapon, generatedLoot);
        } else if(selectedMeleeWeapon){
            addCommonOrUnique(uniqueItemChance, commonMeleeWeapon, uniqueMeleeWeapon, generatedLoot);

        } else if(selectedRangedWeapon){
            addCommonOrUnique(uniqueItemChance, commonRangedWeapon, uniqueRangedWeapon, generatedLoot);
        }

        if(selectedArmor){
            pickFromTwoTypes(uniqueItemChance, commonLeatherArmor, uniqueLeatherArmor, commonMetalArmor, uniqueMetalArmor, generatedLoot);
        }
        if(selectedArtifact){
            addArtifact(artifactChance, artifact, generatedLoot);
        }

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
