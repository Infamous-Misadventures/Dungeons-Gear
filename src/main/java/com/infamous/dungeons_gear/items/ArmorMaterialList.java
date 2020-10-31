package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

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

    VEST("vest", DungeonsGearConfig.COMMON.VEST_ARMOR_DURABILITY.get(), new int[] {2, 5, 6, 2}, 9, () -> Ingredient.fromTag(ItemTagWrappers.NON_METAL_ARMOR_REPAIR_ITEMS), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f),
    ROBE("robe", DungeonsGearConfig.COMMON.ROBE_ARMOR_DURABILITY.get(), new int[] {2, 5, 6, 2}, 9, () -> Ingredient.fromTag(ItemTagWrappers.NON_METAL_ARMOR_REPAIR_ITEMS), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f),
    PELT("pelt", DungeonsGearConfig.COMMON.PELT_ARMOR_DURABILITY.get(), new int[] {2, 5, 6, 2}, 9, () -> Ingredient.fromTag(ItemTagWrappers.NON_METAL_ARMOR_REPAIR_ITEMS), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f),
    BONE("bone", DungeonsGearConfig.COMMON.BONE_ARMOR_DURABILITY.get(), new int[] {2, 5, 6, 2}, 9, () -> Ingredient.fromTag(ItemTagWrappers.NON_METAL_ARMOR_REPAIR_ITEMS), SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 0.0f, 0.0f),
    LIGHT_PLATE("light_plate", DungeonsGearConfig.COMMON.LIGHT_PLATE_ARMOR_DURABILITY.get(), new int[] {2, 5, 6, 2}, 9, () -> Ingredient.fromTag(ItemTagWrappers.METAL_ARMOR_REPAIR_ITEMS), SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f),
    MEDIUM_PLATE("medium_plate", DungeonsGearConfig.COMMON.MEDIUM_PLATE_ARMOR_DURABILITY.get(), new int[] {2, 5, 6, 2}, 9, () -> Ingredient.fromTag(ItemTagWrappers.METAL_ARMOR_REPAIR_ITEMS), SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f, 0.0f),
    HEAVY_PLATE("heavy_plate", DungeonsGearConfig.COMMON.HEAVY_PLATE_ARMOR_DURABILITY.get(), new int[] {2, 5, 6, 2}, 9, () -> Ingredient.fromTag(ItemTagWrappers.METAL_ARMOR_REPAIR_ITEMS), SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f, 0.0f);


    // Armor order: boots, leggings, chestplate, helmet
    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private String name;
    private SoundEvent equipSound;
    private int durability, enchantability;
    private final LazyValue<Ingredient> repairItem;
    private int[] damageReductionAmounts;
    private float toughness;
    private float knockbackResistance;

    private ArmorMaterialList(String name, int durability, int[] damageReductionAmounts, int enchantability, Supplier<Ingredient> repairItem, SoundEvent equipSound, float toughness, float knockbackResistance)
    {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = new LazyValue<>(repairItem);
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
        return MAX_DAMAGE_ARRAY[slot.getIndex()] * this.durability;
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
        return this.repairItem.getValue();
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
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}