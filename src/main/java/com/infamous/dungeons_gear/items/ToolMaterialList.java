package com.infamous.dungeons_gear.items;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

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

    METAL(0.0F, 6.0f, DungeonsGearConfig.METAL_MELEE_WEAPON_DURABILITY.get(), 2, 14, () -> Ingredient.fromTag(ItemTagWrappers.METAL_MELEE_WEAPON_REPAIR_ITEMS)),
    DIAMOND(1.0F, 8.0f, 1561, 3, 10, () -> Ingredient.fromTag(Tags.Items.GEMS_DIAMOND));

    private float attackDamage, efficiency;
    private int durability;
    private int harvestLevel;
    private int enchantability;
    private LazyValue<Ingredient> repairMaterial;

    private ToolMaterialList(float attackDamage, float efficiency, int durability, int harvestLevel, int enchantability, Supplier<Ingredient> repairMaterial)
    {
        this.attackDamage = attackDamage;
        this.efficiency = efficiency;
        this.durability = durability;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
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
        return this.repairMaterial.getValue();
    }
}
