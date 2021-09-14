package com.infamous.dungeons_gear.loot;

import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.Nullable;
import java.util.*;

import static com.infamous.dungeons_gear.registry.ItemRegistry.commonMetalArmorMap;
import static com.infamous.dungeons_gear.registry.ItemRegistry.commonLeatherArmorMap;
import static com.infamous.dungeons_gear.registry.ItemRegistry.uniqueMetalArmorMap;
import static com.infamous.dungeons_gear.registry.ItemRegistry.uniqueLeatherArmorMap;
import static com.infamous.dungeons_gear.registry.ItemRegistry.commonRangedWeaponMap;
import static com.infamous.dungeons_gear.registry.ItemRegistry.uniqueRangedWeaponMap;
import static com.infamous.dungeons_gear.registry.ItemRegistry.commonWeaponMap;
import static com.infamous.dungeons_gear.registry.ItemRegistry.uniqueWeaponMap;
import static com.infamous.dungeons_gear.registry.ItemRegistry.artifactMap;

public class ChestLootHelper {

    private static final Random RANDOM = new Random();

    public static List<ItemStack> generateLootFromValues(double uniqueItemChance, double artifactChance, @Nullable PlayerEntity player){
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
            pickFromTwoTypes(uniqueItemChance, commonMeleeWeapon, uniqueMeleeWeapon, commonRangedWeapon, uniqueRangedWeapon, generatedLoot, player);
        } else if(selectedMeleeWeapon){
            addCommonOrUnique(uniqueItemChance, commonMeleeWeapon, uniqueMeleeWeapon, generatedLoot, player);

        } else if(selectedRangedWeapon){
            addCommonOrUnique(uniqueItemChance, commonRangedWeapon, uniqueRangedWeapon, generatedLoot, player);
        }

        if(selectedArmor){
            pickFromTwoTypes(uniqueItemChance, commonLeatherArmor, uniqueLeatherArmor, commonMetalArmor, uniqueMetalArmor, generatedLoot, player);
        }
        if(selectedArtifact){
            addArtifact(artifactChance, artifact, generatedLoot);
        }

        return generatedLoot;
    }

    private static void addArtifact(double artifactChance, Item artifact, List<ItemStack> generatedLoot) {
        if(RANDOM.nextDouble() < artifactChance){
            generatedLoot.add(new ItemStack(artifact));
        }
    }

    private static void pickFromTwoTypes(double uniqueItemChance, Item commonItem1, Item uniqueItem1, Item commonItem2, Item uniqueItem2, List<ItemStack> generatedLoot, @Nullable PlayerEntity player) {
        if(RANDOM.nextBoolean()){
            addCommonOrUnique(uniqueItemChance, commonItem1, uniqueItem1, generatedLoot, player);
        }
        else{
            addCommonOrUnique(uniqueItemChance, commonItem2, uniqueItem2, generatedLoot, player);
        }
    }

    private static void addCommonOrUnique(double uniqueItemChance, Item commonItem, Item uniqueItem, List<ItemStack> generatedLoot, @Nullable PlayerEntity player) {
        double fortuneAddition = 0;
        if(player != null){
            int fortuneOfTheSeaLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.FORTUNE_OF_THE_SEA, player);
            if(fortuneOfTheSeaLevel > 0){
                fortuneAddition = 0.1 * fortuneOfTheSeaLevel;
            }
        }

        if(RANDOM.nextDouble() < uniqueItemChance + fortuneAddition){
            generatedLoot.add(new ItemStack(uniqueItem));
        }
        else{
            generatedLoot.add(new ItemStack(commonItem));
        }
    }

    public static Item getRandomSetElement(Set<Item> set) {
        int index = RANDOM.nextInt(set.size());
        Iterator<Item> iter = set.iterator();
        for (int i = 0; i < index; i++) {
            iter.next();
        }
        return iter.next();
    }
}
