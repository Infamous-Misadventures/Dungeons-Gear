package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.DungeonsGear;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum ArmorMaterialList implements IArmorMaterial
{
    //Armor order: boots, leggings, chestplate, helmet

    /*
     * Durability:
     * Leather - 5
     * Iron = 14
     * Turtle = 25
     * Diamond = 33
     * Netherite = 37
     * Gold = 7
     *
     * Enchantability:
     * Leather = 15
     * Iron = 9
     * Turtle = 9
     * Diamond = 10
     * Netherite = 15
     * Gold = 25
     *
     * Damage Reduction Amounts (boots, leggings, chestplate, helmet:
     * Leather = 1/2/3/1
     * Chain = 1/4/5/2
     * Iron = 2/5/6/2
     * Diamond = 3/6/8/3
     * Turtle = 2/5/6/2
     * Netherite = 3/6/8/3
     * Gold = 1/3/5/2
     *
     * ChampionArmorItem, - medium
     * DarkArmorItem, - heavy
     * PlateArmorItem, - heavy
     * GrimArmorItem uniq, - bone
     * ScaleMailItem, - medium
     * MercenaryArmorItem, - heavy
     * OcelotArmorItem, - pelt
     * ReinforcedMailItem - medium
     * reduce incoming damage by 35% => +2 armor toughness
     *
     *
     */

    // Tier 1
    VEST("vest", 14, new int[] {2, 5, 6, 2}, 9, Items.AIR, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f),
    ROBE("robe", 14, new int[] {2, 5, 6, 2}, 9, Items.AIR, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f),
    PELT("pelt", 14, new int[] {2, 5, 6, 2}, 9, Items.AIR, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f),
    BONE("bone", 14, new int[] {2, 5, 6, 2}, 9, Items.AIR, SoundEvents.ENTITY_SKELETON_AMBIENT, 0.0f, 0.0f),
    LIGHT_PLATE("light_plate", 14, new int[] {2, 5, 6, 2}, 9, Items.AIR, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f),
    MEDIUM_PLATE("medium_plate", 14, new int[] {2, 5, 6, 2}, 9, Items.AIR, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f, 0.0f),
    HEAVY_PLATE("heavy_plate", 14, new int[] {2, 5, 6, 2}, 9, Items.AIR, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f, 0.0f);


    // Armor order: boots, leggings, chestplate, helmet
    private static final int[] maxDamageArray = new int[]{13, 15, 16, 11};
    private String name;
    private SoundEvent equipSound;
    private int durability, enchantability;
    private Item repairItem;
    private int[] damageReductionAmounts;
    private float toughness;
    private float knockbackResistance;

    private ArmorMaterialList(String name, int durability, int[] damageReductionAmounts, int enchantability, Item repairItem, SoundEvent equipSound, float toughness, float knockbackResistance)
    {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
        this.damageReductionAmounts = damageReductionAmounts;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slot)
    {
        return this.damageReductionAmounts[slot.getIndex()];
    }

    @Override
    public int getDurability(EquipmentSlotType slot)
    {
        return maxDamageArray[slot.getIndex()] * this.durability;
    }

    @Override
    public int getEnchantability()
    {
        return this.enchantability;
    }

    @Override
    public String getName()
    {
        return DungeonsGear.MODID + ":" + this.name;
    }

    @Override
    public Ingredient getRepairMaterial()
    {
        return Ingredient.fromItems(this.repairItem);
    }

    @Override
    public SoundEvent getSoundEvent()
    {
        return this.equipSound;
    }

    @Override
    public float getToughness()
    {
        return this.toughness;
    }

    //getKnockbackResistance
    @Override
    public float func_230304_f_() {
        return this.knockbackResistance;
    }
}