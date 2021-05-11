package com.infamous.dungeons_gear.enchantments.types;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;

public class ModDamageEnchantment extends DamageEnchantment {
    private static final String[] DAMAGE_NAMES = new String[]{"all", "undead", "arthropods", "illager"};
    private static final int[] MIN_COST = new int[]{1, 5, 5, 5};
    private static final int[] LEVEL_COST = new int[]{11, 8, 8, 8};
    private static final int[] LEVEL_COST_SPAN = new int[]{20, 20, 20, 20};

    public ModDamageEnchantment(Rarity rarityIn, int damageTypeIn, EquipmentSlotType... slots) {
        super(rarityIn, damageTypeIn, slots);
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int enchantmentLevel) {
        return MIN_COST[this.damageType] + (enchantmentLevel - 1) * LEVEL_COST[this.damageType];
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + LEVEL_COST_SPAN[this.damageType];
    }
    /**
     * Calculates the additional damage that will be dealt by an item with this enchantment. This alternative to
     * calcModifierDamage is sensitive to the targets EnumCreatureAttribute.
     */
    public float calcDamageByCreature(int level, CreatureAttribute creatureType) {
        if (this.damageType == 0) {
            return 1.0F + (float)Math.max(0, level - 1) * 0.5F;
        } else if (this.damageType == 1 && creatureType == CreatureAttribute.UNDEAD) {
            return (float)level * 2.5F;
        } else if(this.damageType == 2 && creatureType == CreatureAttribute.ARTHROPOD){
            return (float)level * 2.5F;
        } else if(this.damageType == 3 && creatureType == CreatureAttribute.ILLAGER){
            return (float)level * 2.5F;
        } else {
            return 0.0F;
        }
    }
}
