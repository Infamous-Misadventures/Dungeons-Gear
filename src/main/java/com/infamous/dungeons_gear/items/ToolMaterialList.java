package com.infamous.dungeons_gear.items;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public enum ToolMaterialList implements IItemTier {
    /*
     * Durability:
     * Wood = 59
     * Stone = 131
     * Iron = 250
     * Diamond = 1561
     * Netherite = 2031
     * Gold = 32
     *
     * Damage (Swords):
     * Wood = 4
     * Gold = 4
     * Stone = 5
     * Iron = 6
     * Diamond = 7
     * Netherite = 8
     *
     * Attack Speeds
     * Swords = 1.6 = -2.4f
     * Axes = 0.8 to 1 = -3.4 to -3.2
     * Pickaxes = 1.2 = -2.8
     * Shovels = 1 = -3.0
     * Hoes = 1 to 4 = -3.2 to -0.2
     *
     * Enchantability:
     * Wood = 15
     * Stone = 5
     * Iron = 14
     * Diamond = 10
     * Netherite = 15
     * Gold = 22
     *
     * WOOD(0, 59, 2.0F, 0.0F, 15, () -> {
      return Ingredient.fromTag(ItemTags.PLANKS);
   }),
   STONE(1, 131, 4.0F, 1.0F, 5, () -> {
      return Ingredient.fromTag(ItemTags.field_232909_aa_);
   }),
   IRON(2, 250, 6.0F, 2.0F, 14, () -> {
      return Ingredient.fromItems(Items.IRON_INGOT);
   }),
   DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> {
      return Ingredient.fromItems(Items.DIAMOND);
   }),
   GOLD(0, 32, 12.0F, 0.0F, 22, () -> {
      return Ingredient.fromItems(Items.GOLD_INGOT);
   }),
   NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> {
      return Ingredient.fromItems(Items.field_234759_km_);
   });
     */

    DUNGEONS_MELEE_WEAPON(0.0F, 6.0f, 250, 2, 14, Items.AIR);


    private float attackDamage, efficiency;
    private int durability, harvestLevel, enchantability;
    private Item repairMaterial;

    private ToolMaterialList(float attackDamage, float efficiency, int durability, int harvestLevel, int enchantability, Item repairMaterial)
    {
        this.attackDamage = attackDamage;
        this.efficiency = efficiency;
        this.durability = durability;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public float getAttackDamage()
    {
        return this.attackDamage;
    }

    @Override
    public float getEfficiency()
    {
        return this.efficiency;
    }

    @Override
    public int getEnchantability()
    {
        return this.enchantability;
    }

    @Override
    public int getHarvestLevel()
    {
        return this.harvestLevel;
    }

    @Override
    public int getMaxUses()
    {
        return this.durability;
    }

    @Override
    public Ingredient getRepairMaterial()
    {
        return Ingredient.fromItems(this.repairMaterial);
    }
}
